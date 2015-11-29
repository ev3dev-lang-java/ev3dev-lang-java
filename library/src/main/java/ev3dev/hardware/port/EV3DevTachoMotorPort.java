package ev3dev.hardware.port;

public class EV3DevTachoMotorPort {
    public static final TachoMotorPortNew A = new TachoMotorPortNew("outA"); //= BrickFinder.getDefault().getPort("S1");
    public static final TachoMotorPortNew B = new TachoMotorPortNew("outB"); //= BrickFinder.getDefault().getPort("S2");
    public static final TachoMotorPortNew C = new TachoMotorPortNew("outC"); //= BrickFinder.getDefault().getPort("S3");
    public static final TachoMotorPortNew D = new TachoMotorPortNew("outD"); //= BrickFinder.getDefault().getPort("S4");
}
