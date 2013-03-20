package data;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class CurrentVersion implements Serializable {
	@Element
	private long versionNumber;
	// DateTime?
	
	public CurrentVersion() {
	}
	public CurrentVersion(long versionNumber) {
		this.versionNumber = versionNumber;
	}

	public long getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}
	
}
