package data.communication;

import java.io.Serializable;
import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class DataList implements Serializable {
	@ElementList
	private ArrayList<Serializable> data;
	
	public DataList() {
		this.data = new ArrayList<Serializable>();
	}
	public DataList(ArrayList<Serializable> data) {
		this.data = data;
	}
	public ArrayList<Serializable> getData() {
		return data;
	}
	public void setData(ArrayList<Serializable> data) {
		this.data = data;
	}
	public void add(Serializable ser) {
		data.add(ser);
	}
	
}
