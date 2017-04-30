package ev3dev.hardware;

/**
 * Define the platforms supported by EV3Dev project.
 */
public enum EV3DevPlatforms {

    EV3BRICK("EV3BRICK"),
    PISTORMS("PISTORMS"),
    BRICKPI("BRICKPI");

    private String platform;

    private EV3DevPlatforms(String stringVal) {
        platform = stringVal;
    }
    public String toString(){
        return platform;
    }

    public static String getPlatformByString(final String code){
        for(EV3DevPlatforms e : EV3DevPlatforms.values()){
            if(code == e.platform){
                return e.name();
            }
        }
        return null;
    }
}
