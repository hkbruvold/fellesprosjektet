package data.communication;

import java.io.Serializable;
import java.util.Arrays;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class ChangeData implements Serializable {
	@Element
	private long versionNumber;
	@Element(required=false) 
	private String[] tableNames;
	@Element(required=false)
	private String[] identifiers;

	public ChangeData() {
	}
	public ChangeData(long versionNumber, String[] tableNames, String[] identifiers) {
		this.versionNumber = versionNumber;
		this.tableNames = tableNames;
		this.identifiers = identifiers;
	}

	@Override
	public String toString() {
		return "ChangeData [versionNumber=" + versionNumber + ", tableNames="
				+ Arrays.toString(tableNames) + ", identifiers="
				+ Arrays.toString(identifiers) + "]";
	}

	public long getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String[] getTableNames() {
		return tableNames;
	}
	public void setTableNames(String[] tableNames) {
		this.tableNames = tableNames;
	}
	public String[] getIdentifiers() {
		return identifiers;
	}
	public void setIdentifiers(String[] identifiers) {
		this.identifiers = identifiers;
	}

}
