package ev3dev.hardware;

public interface SupportedPlatform {

    String EV3BRICK = "EV3BRICK";
    String PISTORMS = "PISTORMS";
    String BRICKPI = "BRICKPI";

    String getPlatform() throws PlatformNotSupportedException;
}
