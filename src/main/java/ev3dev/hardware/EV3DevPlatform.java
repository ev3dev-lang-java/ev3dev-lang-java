package ev3dev.hardware;

import ev3dev.utils.Sysfs;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lombok.extern.slf4j.Slf4j;

public @Slf4j abstract class EV3DevPlatform implements SupportedPlatform {

    protected static final String DEVICE_ROOT_PATH = "/sys/class/";

    /**
     * This method returns the platform
     *
     * @return Platform used
     * @throws RuntimeException Exception
     */
    @Override
    public String getPlatform() {

        final String BATTERY =  "power_supply";
        final String BATTERY_PATH = DEVICE_ROOT_PATH + BATTERY;
        final String BATTERY_EV3 =  "legoev3-battery";
        final String BATTERY_PISTORMS =  "pistorms-battery";
        final String BATTERY_BRICKPI =  "brickpi-battery";
        final String EV3BRICK_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_EV3;
        final String PISTORMS_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_PISTORMS;
        final String BRICKPI_DISCOVERY_PATTERN_PATH = BATTERY_PATH + "/" + BATTERY_BRICKPI;

        if(Sysfs.existPath(EV3BRICK_DISCOVERY_PATTERN_PATH)){
            log.trace("Detected platform: " + SupportedPlatform.EV3BRICK);
            return EV3BRICK;
        } else if(Sysfs.existPath(PISTORMS_DISCOVERY_PATTERN_PATH)){
            log.trace("Detected platform: " + SupportedPlatform.PISTORMS);
            return PISTORMS;
        } else if(Sysfs.existPath(BRICKPI_DISCOVERY_PATTERN_PATH)){
            log.trace("Detected platform: " + SupportedPlatform.BRICKPI);
            return BRICKPI;
        } else {
            throw new RuntimeException("Platform not supported");
        }
    }

    public String getMotorPort(final String port) {

        if(this.getPlatform().equals(EV3BRICK)){

            if(port.equals(MotorPort.A)){
                return "outA";
            }else if(port.equals(MotorPort.B)){
                return "outB";
            }else if(port.equals(MotorPort.C)){
                return "outC";
            }else if(port.equals(MotorPort.D)){
                return "outD";
            }

        } else if(this.getPlatform().equals(BRICKPI)) {

            if (port.equals(MotorPort.A)) {
                return "ttyAMA0:MA";
            } else if (port.equals(MotorPort.B)) {
                return "ttyAMA0:MB";
            } else if (port.equals(MotorPort.C)) {
                return "ttyAMA0:MC";
            } else if (port.equals(MotorPort.D)) {
                return "ttyAMA0:MD";
            }

        } else {

            if (port.equals(MotorPort.A)) {
                return "pistorms:BBM1";
            } else if (port.equals(MotorPort.B)) {
                return "pistorms:BBM2";
            } else if (port.equals(MotorPort.C)) {
                return "pistorms:BAM2";
            } else if (port.equals(MotorPort.D)) {
                return "pistorms:BAM1";
            }

        }

        //TODO Improve
        return null;
    }

    public String getSensorPort(final String port) {

        if(this.getPlatform().equals(EV3BRICK)){

            if(port.equals(SensorPort.S1)){
                return "in1";
            }else if(port.equals(SensorPort.S2)){
                return "in2";
            }else if(port.equals(SensorPort.S3)){
                return "in3";
            }else if(port.equals(SensorPort.S4)){
                return "in4";
            }

        } else if(this.getPlatform().equals(BRICKPI)) {

            if (port.equals(SensorPort.S1)) {
                return "ttyAMA0:S1";
            } else if (port.equals(SensorPort.S2)) {
                return "ttyAMA0:S2";
            } else if (port.equals(SensorPort.S3)) {
                return "ttyAMA0:S3";
            } else if (port.equals(SensorPort.S4)) {
                return "ttyAMA0:S4";
            }

        } else {

            if (port.equals(SensorPort.S1)) {
                return "pistorms:BBS1";
            } else if (port.equals(SensorPort.S2)) {
                return "pistorms:BBS2";
            } else if (port.equals(SensorPort.S3)) {
                return "pistorms:BAS2";
            } else if (port.equals(SensorPort.S4)) {
                return "pistorms:BAS1";
            }

        }

        //TODO Improve
        return null;

    }

}
