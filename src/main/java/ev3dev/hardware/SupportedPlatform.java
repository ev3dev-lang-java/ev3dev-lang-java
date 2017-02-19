package ev3dev.hardware;

/**
 * Define the platforms supported by EV3Dev project.
 */
public interface SupportedPlatform {

    String EV3BRICK = "EV3BRICK";
    String PISTORMS = "PISTORMS";
    String BRICKPI = "BRICKPI";

    String getPlatform();
}
