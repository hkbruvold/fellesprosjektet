package data.communication;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class ChangeData implements Serializable {
	@Element
	private long versionNumber;
	@Element
	private int numberOfChanges;

	public ChangeData() {
	}
	public ChangeData(long versionNumber, int numberOfChanges) {
		this.versionNumber = versionNumber;
		this.numberOfChanges = numberOfChanges;
	}

	@Override
	public String toString() {
		return "ChangeData [versionNumber=" + versionNumber + ", numberOfChanges=" + numberOfChanges + "]";
	}

	public long getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}
	public int getNumberOfChanges() {
		return numberOfChanges;
	}
	public void setNumberOfChanges(int numberOfChanges) {
		this.numberOfChanges = numberOfChanges;
	}

}
