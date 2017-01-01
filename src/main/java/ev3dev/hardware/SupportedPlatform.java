package ev3dev.hardware;

public interface SupportedPlatform {

    //TODO Change using a Enum
	public final String EV3BRICK = "EV3BRICK";
	public final String PISTORMS = "PISTORMS";
	public final String BRICKPI = "BRICKPI";

	public String getPlatform();
}
