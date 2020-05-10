package ev3dev.hardware;

/**
 * Define the platforms supported by EV3Dev project.
 */
public enum EV3DevPlatform {

    EV3BRICK("EV3BRICK", "ev3"),
    PISTORMS("PISTORMS", "pistorms"),
    BRICKPI("BRICKPI",  "brickpi"),
    BRICKPI3("BRICKPI3", "brickpi3"),
    UNKNOWN("UNKNOWN",  "unknown");

    private final String platform;
    private final String propNamespace;

    private EV3DevPlatform(String stringVal, String ns) {
        platform = stringVal;
        propNamespace = ns;
    }

    public String toString() {
        return platform;
    }

    public String getPropertyNamespace() {
        return propNamespace;
    }

    /**
     * Method to find the platform
     *
     * @param code Code
     * @return String representing the Platform
     */
    public static String getPlatformByString(final String code) {
        for (EV3DevPlatform e : EV3DevPlatform.values()) {
            if (code == e.platform) {
                return e.name();
            }
        }
        return UNKNOWN.toString();
    }
}
