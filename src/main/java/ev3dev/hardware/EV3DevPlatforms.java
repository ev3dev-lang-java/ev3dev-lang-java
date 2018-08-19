package ev3dev.hardware;

import ev3dev.hardware.EV3DevPlatformsImpl;
import ev3dev.hardware.EV3DevScreenInfo;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

import java.util.Properties;

public class EV3DevPlatforms {
    private static EV3DevPlatformsImpl globalInstance = null;
    private EV3DevPlatformsImpl instance = null;

    public EV3DevPlatforms() {
        synchronized(EV3DevPlatforms.class) {
            if (globalInstance == null) {
                globalInstance = new EV3DevPlatformsImpl();
            }
        }
        instance = globalInstance;
    }

    public EV3DevPlatforms(EV3DevPlatform platform) {
        instance = new EV3DevPlatformsImpl(platform);
    }

    public EV3DevPlatform getPlatform() {
        return instance.getPlatform();
    }

    public String getMotorPort(final Port port) {

        //TODO Pending to review in detail
        //return instance.getMotorPort(port);


        final EV3DevPropertyLoader ev3DevPropertyLoader = new EV3DevPropertyLoader();
        final Properties ev3DevProperties = ev3DevPropertyLoader.getEV3DevProperties();

        if(getPlatform().equals(EV3DevPlatform.EV3BRICK)){

            if(port.equals(MotorPort.A)){
                return ev3DevProperties.getProperty("ev3.motor.port.a");
            }else if(port.equals(MotorPort.B)){
                return ev3DevProperties.getProperty("ev3.motor.port.b");
            }else if(port.equals(MotorPort.C)){
                return ev3DevProperties.getProperty("ev3.motor.port.c");
            }else if(port.equals(MotorPort.D)){
                return ev3DevProperties.getProperty("ev3.motor.port.d");
            }

        } else if(getPlatform().equals(EV3DevPlatform.BRICKPI)) {

            if (port.equals(MotorPort.A)) {
                return ev3DevProperties.getProperty("brickpi.motor.port.a");
            } else if (port.equals(MotorPort.B)) {
                return ev3DevProperties.getProperty("brickpi.motor.port.b");
            } else if (port.equals(MotorPort.C)) {
                return ev3DevProperties.getProperty("brickpi.motor.port.c");
            } else if (port.equals(MotorPort.D)) {
                return ev3DevProperties.getProperty("brickpi.motor.port.d");
            }

        } else if(getPlatform().equals(EV3DevPlatform.BRICKPI3)) {

            if (port.equals(MotorPort.A)) {
                return ev3DevProperties.getProperty("brickpi3.motor.port.a");
            } else if (port.equals(MotorPort.B)) {
                return ev3DevProperties.getProperty("brickpi3.motor.port.b");
            } else if (port.equals(MotorPort.C)) {
                return ev3DevProperties.getProperty("brickpi3.motor.port.c");
            } else if (port.equals(MotorPort.D)) {
                return ev3DevProperties.getProperty("brickpi3.motor.port.d");
            }

        } else {

            if (port.equals(MotorPort.A)) {
                return ev3DevProperties.getProperty("pistorms.motor.port.a");
            } else if (port.equals(MotorPort.B)) {
                return ev3DevProperties.getProperty("pistorms.motor.port.b");
            } else if (port.equals(MotorPort.C)) {
                return ev3DevProperties.getProperty("pistorms.motor.port.c");
            } else if (port.equals(MotorPort.D)) {
                return ev3DevProperties.getProperty("pistorms.motor.port.d");
            }

        }

        throw new RuntimeException("Bad port used");
    }

    public String getSensorPort(final Port port) {
        return instance.getSensorPort(port);
    }

    public EV3DevScreenInfo getFramebufferInfo() {
        return instance.getFramebufferInfo();
    }

    public String getProperty(String base) {
        return instance.getProperty(base);
    }

}
