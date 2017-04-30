package ev3dev.hardware;

/**
 * Define the platforms supported by EV3Dev project.
 */
public enum EV3DevPlatforms {

    EV3BRICK("EV3BRICK"),
    PISTORMS("PISTORMS"),
    BRICKPI("BRICKPI");

    private String name;

    private EV3DevPlatforms(String stringVal) {
        name = stringVal;
    }
    public String toString(){
        return name;
    }

    public static String getPlatformByString(final String code){
        for(EV3DevPlatforms e : EV3DevPlatforms.values()){
            if(code == e.name){
                return e.name();
            }
        }
        return null;
    }
}
