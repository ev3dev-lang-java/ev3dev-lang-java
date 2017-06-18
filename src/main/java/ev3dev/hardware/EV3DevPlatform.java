package ev3dev.hardware;

/**
 * Define the platforms supported by EV3Dev project.
 */
public enum EV3DevPlatform {

    EV3BRICK("EV3BRICK"),
    PISTORMS("PISTORMS"),
    BRICKPI("BRICKPI"),
    BRICKPI3("BRICKPI3"),
    UNKNOWN("UNKNOWN");

    private String platform;

    private EV3DevPlatform(String stringVal) {
        platform = stringVal;
    }

    public String toString(){
        return platform;
    }

    public static String getPlatformByString(final String code){
        for(EV3DevPlatform e : EV3DevPlatform.values()){
            if(code == e.platform){
                return e.name();
            }
        }
        return UNKNOWN.toString();
    }
}
