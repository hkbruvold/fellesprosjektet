package data.communication;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("serial")
@Root
public class CurrentVersion implements Serializable {
	@Element
	private long versionNumber;
	
	public CurrentVersion() {
	}
	public CurrentVersion(long versionNumber) {
		this.versionNumber = versionNumber;
	}

	@Override
	public String toString() {
		return "CurrentVersion [versionNumber=" + versionNumber + "]";
	}

	public long getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}
	
}
