package ev3dev.hardware;

public class EV3DevAttributeSpec {
    public static final int ACCESS_RO = 0;
    public static final int ACCESS_WO = 1;
    public static final int ACCESS_RW = 2;

    public final String name;
    public final int access;

    public EV3DevAttributeSpec(String name, int access) {
        this.name = name;
        this.access = access;
    }
}
