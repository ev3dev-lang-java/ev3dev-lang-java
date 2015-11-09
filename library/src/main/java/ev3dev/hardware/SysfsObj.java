package ev3dev.hardware;

public class SysfsObj {

	private String mName;
	private String mValue;
	
	public SysfsObj(String name, String value) {
		mName = name;
		mValue = value;
	}
	
	public String getName() {
		return mName;
	}
	
	public String getValue() {
		return mValue;
	}
	
	public String getFile() {
		return mName;
	}
	
}
