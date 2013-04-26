/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import no.ntnu.fp.net.cl.ClException;
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
	private ClSocket socket = new ClSocket();
    private final int MAXRESENDS = 5;
    private final int MAXRERECEIVES = 5;
    private int resends = 0;
    private int rereceives = 0;
    private KtnDatagram lastDatagramReceived = null;
	
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
		state = State.CLOSED;
	}
	
	public ConnectionImpl(String myAddress, int myPort, String remoteAddress, int remotePort) {
		this.myPort = myPort;
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
			throw new IOException("Error connecting");
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
			throw new IOException("Error connecting");
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
		KtnDatagram ack = conn.receiveAck();
		if(ack == null || ack.getFlag() != Flag.ACK) {
    		throw new SocketTimeoutException();
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
		if(state != State.ESTABLISHED) {
			System.out.println(state);
			throw new ConnectException("Connection not established");
		}
		
		System.out.println(state);
		KtnDatagram packet = constructDataPacket(msg);
		KtnDatagram ack = sendDataPacketWithRetransmit(packet);
		if(ack == null) {
			System.out.println("No ack received");
			if(resends < MAXRESENDS) {
				resends++;
				send(msg);
				resends = 0;
				return;
			} else {
				state = State.CLOSED;
				throw new ConnectException("lost connection");
			}
		} else { 
			System.out.println("got ack");
			if(!isValid(ack)) {
			} else if(ack.getAck() > nextSequenceNo-1) {
			} else if (ack.getAck() < nextSequenceNo-1) {
				//nextSequenceNo--;
				send(msg);
				return;
			} else {
				lastValidPacketReceived = ack;
				System.out.println("ack is valid");
			}
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

		KtnDatagram datagram = null; 
    	try {
    		datagram = receivePacket(false);	
		} catch (EOFException e) { //got a FIN
			state = State.CLOSE_WAIT;
			throw new EOFException();
		}
    	
    	// timeout
		if(datagram == null) {
			if(rereceives < MAXRERECEIVES) {
				rereceives++;
				String msg = receive();
				rereceives = 0;
				return msg;
			} else {
				state = State.CLOSED;
				throw new ConnectException();
			}
		} else { // packet received
			if(!isGhostPacket(datagram)) {
				System.out.println("Not a ghost packet");
				if(isValid(datagram)) {
					if(lastDatagramReceived != null && datagram.getSeq_nr()-1!=lastValidPacketReceived.getSeq_nr()) {
						System.out.println("1");
						sendAck(lastDatagramReceived, false);
						return receive();
					} else {
						System.out.println("2");
						sendAck(datagram, false);
						lastDatagramReceived = datagram;
						lastValidPacketReceived = datagram;
						return (String) datagram.getPayload();
					}
				} else {
 					if(lastDatagramReceived != null) {
 						System.out.println("3");
 						sendAck(lastDatagramReceived, false);
 						return receive();
 					}
 					return receive();
				}
			} else { 
				System.out.println("got ghost package");
				return receive();
			}
		}
	}
	
	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	public void close() throws IOException {
		KtnDatagram ack, datagram, finAck = null;
		if(state == State.CLOSE_WAIT){
			sendAck(lastDatagramReceived, false);
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} //Wait for client to be ready to recieve FIN
			try {
				datagram = constructInternalPacket(Flag.FIN);
				simplySendPacket(datagram);
			} catch (ClException e) {
				e.printStackTrace();
			}
			ack = receiveAck();
			state = State.CLOSED;
		}
		else if(state == State.ESTABLISHED){
			try {
				datagram = constructInternalPacket(Flag.FIN);
				simplySendPacket(datagram);
			} catch (ClException e) {
				e.printStackTrace();
			}
			state = State.FIN_WAIT_1;
			ack = receiveAck();
			if(ack == null){
				if(resends < MAXRESENDS){
					reclose();
					return;	
				}
				else {
					state = State.CLOSED;
				}
			}
			state = State.FIN_WAIT_2;
			finAck = receiveAck();
			if(finAck == null) finAck = receiveAck();
			if(finAck != null) { 
				sendAck(finAck, false);
			}
		}
		System.out.println("closed connection");
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
		if (packet.getChecksum() == packet.calculateChecksum()) {
			return true;
		}
		return false;
	}
	
	private void reclose() {
    	state = State.ESTABLISHED;
		try {
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isGhostPacket(KtnDatagram datagram) {
    	if(datagram.getSrc_addr() != null) {
    		return !(datagram.getSrc_addr().equals(remoteAddress) && datagram.getSrc_port()==remotePort);
    	}
    	return true;
    }
}
