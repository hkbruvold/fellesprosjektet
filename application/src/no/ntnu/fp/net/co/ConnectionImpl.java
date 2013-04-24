/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebj�rn Birkeland and Stein Jakob Nordb�
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {
	
	/** Keeps track of the used ports for each server port. */
	private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
	
	/**
	 * Initialise initial sequence number and setup state machine.
	 * 
	 * @param myPort
	 *            - the local port to associate with this connection
	 */
	public ConnectionImpl(int myPort) {
		super();
		this.myPort = myPort;
		this.myAddress = getIPv4Address();
		this.nextSequenceNo = 1;
		state = State.CLOSED;
	}
	
	public ConnectionImpl(String myAddress, int myPort, String remoteAddress, int remotePort) {
		this(myPort);
		this.myAddress = myAddress;
		this.remoteAddress = remoteAddress;
		this.remotePort = remotePort;
		state = State.SYN_RCVD;
	}
	
	private String getIPv4Address() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}
	
	/**
	 * Establish a connection to a remote location.
	 * 
	 * @param remoteAddress
	 *            - the remote IP-address to connect to
	 * @param remotePort
	 *            - the remote portnumber to connect to
	 * @throws IOException
	 *             If there's an I/O error.
	 * @throws java.net.SocketTimeoutException
	 *             If timeout expires before connection is completed.
	 * @see Connection#connect(InetAddress, int)
	 */
	public void connect(InetAddress remoteAddress, int remotePort) throws IOException, SocketTimeoutException {
		this.remoteAddress = remoteAddress.getHostAddress();
		this.remotePort = remotePort;
		
		// Send syn to receiver
		KtnDatagram syn = constructInternalPacket(Flag.SYN);
		try {
			simplySendPacket(syn);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// Wait for syn-ack
		KtnDatagram synAck = null;
		while (synAck == null) {
			synAck = receiveAck();
		}
		this.remotePort = synAck.getSrc_port();

		// Send ack
		KtnDatagram ack = constructInternalPacket(Flag.ACK);
		try {
			sendAck(ack, false);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		state = State.ESTABLISHED;
	}
	
	/**
	 * Listen for, and accept, incoming connections.
	 * 
	 * @return A new ConnectionImpl-object representing the new connection.
	 * @see Connection#accept()
	 */
	public Connection accept() throws IOException, SocketTimeoutException {
		state = State.LISTEN;
		
		// Wait for syn
		KtnDatagram syn = null;
		while (syn == null || syn.getFlag() != Flag.SYN) {
			syn = receivePacket(true);
		}
		this.remoteAddress = syn.getSrc_addr();
		this.remotePort = syn.getSrc_port();
		this.state = State.SYN_RCVD;

		// Make a connection and Send syn-ack
		ConnectionImpl conn = new ConnectionImpl(this.myAddress, newPort(), this.remoteAddress, this.remotePort);
		KtnDatagram synAck = constructInternalPacket(Flag.SYN_ACK);
		try {
			conn.sendAck(synAck, true);
			conn.state = State.LISTEN;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// Receive ack
		KtnDatagram ack = null;
		while (ack == null) {
			ack = conn.receiveAck();
		}
		// state = State.ESTABLISHED;
		state = State.LISTEN;
		conn.state = State.ESTABLISHED;
		return (Connection) conn;
	}
	private int newPort() {
		int newPort = (int) (Math.random() * 30000 + 10000);
		return newPort;
	}

	/**
	 * Send a message from the application.
	 * 
	 * @param msg
	 *            - the String to be sent.
	 * @throws ConnectException
	 *             If no connection exists.
	 * @throws IOException
	 *             If no ACK was received.
	 * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
	 * @see no.ntnu.fp.net.co.Connection#send(String)
	 */
	public void send(String msg) throws ConnectException, IOException {
		System.out.println("Trying to send with state: ");
		System.out.println(state.name());
		
		KtnDatagram packet = constructDataPacket(msg);
		KtnDatagram ack = sendDataPacketWithRetransmit(packet);
		if(ack != null && ack.getFlag() != Flag.ACK){
			lastDataPacketSent = packet;
			lastValidPacketReceived = ack;
		}
	}
	
	/**
	 * Wait for incoming data.
	 * 
	 * @return The received data's payload as a String.
	 * @see Connection#receive()
	 * @see AbstractConnection#receivePacket(boolean)
	 * @see AbstractConnection#sendAck(KtnDatagram, boolean)
	 */
	public String receive() throws ConnectException, IOException {
//		int seq;
//		
//		// Throw exception if there is no connection
//		if (state == State.CLOSED) {
//			throw new ConnectException();
//		}
//		
//		// start a loop to wait for packet
//		while (true) {
//			try {
//				KtnDatagram datagram = receivePacket(false);
//				
//				// check IP address and port
//				if (!datagram.getDest_addr().equals(myAddress) && !(datagram.getDest_port() == myPort)) {
//					continue;
//				}
//				
//				// check checksum
//				if (datagram.getChecksum() != datagram.calculateChecksum()) {
//					continue;
//				}
//			} catch (EOFException e) { // FIN packet is received
//			
//			}
//		}
		
		// Just receive a pck and see what it is ?
		System.out.println("Run Receive!");
		KtnDatagram packet = null;
		packet = receivePacket(false);
		if (packet == null) { // XXX Why is this not a loop?
			return receive();
		}
		sendAck(packet, false);
//		System.out.println("Tried to receive, was: " + packet.getPayload());
		return (String) packet.getPayload();
	}
	
	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	public void close() throws IOException {
		state = State.CLOSED;
	}
	
	/**
	 * Test a packet for transmission errors. This function should only called
	 * with data or ACK packets in the ESTABLISHED state.
	 * 
	 * @param packet
	 *            Packet to test.
	 * @return true if packet is free of errors, false otherwise.
	 */
	protected boolean isValid(KtnDatagram packet) {
		if (packet != null) {
			System.out.println("The packet is empty"); // XXX Why?
			if (packet.getChecksum() == packet.calculateChecksum()) {
				if (packet.getSeq_nr() == lastDataPacketSent.getSeq_nr() + 1) {
					if (packet.getSrc_port() == remotePort && packet.getSrc_addr() == remoteAddress) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
