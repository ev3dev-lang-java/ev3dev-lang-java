package ev3dev.hardware;

import lejos.hardware.port.Port;

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
        // TODO: review in detail
        return instance.getMotorPort(port);
    }

    public String getSensorPort(final Port port) {
        return instance.getSensorPort(port);
    }

    public String getProperty(String base) {
        return instance.getProperty(base);
    }

}
