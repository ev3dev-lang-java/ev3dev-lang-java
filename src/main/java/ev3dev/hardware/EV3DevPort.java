package ev3dev.hardware;

import ev3dev.hardware.ports.MotorPort;
import ev3dev.hardware.ports.SensorPort;

public class EV3DevPort extends EV3DevPlatform {

    public static String getMotorPort(final String port) {

        EV3DevPort ev3DevPort = new EV3DevPort();
        final String platform = ev3DevPort.getPlatform();

        if(platform.equals(EV3BRICK)){

            if(port.equals(MotorPort.A)){
                return "outA";
            }else if(port.equals(MotorPort.B)){
                return "outB";
            }else if(port.equals(MotorPort.C)){
                return "outC";
            }else if(port.equals(MotorPort.D)){
                return "outD";
            }

        } else if(platform.equals(BRICKPI)) {

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

    public static String getSensorPort(final String port) {

        EV3DevPort ev3DevPort = new EV3DevPort();
        final String platform = ev3DevPort.getPlatform();

        if(platform.equals(EV3BRICK)){

            if(port.equals(SensorPort.S1)){
                return "in1";
            }else if(port.equals(SensorPort.S2)){
                return "in2";
            }else if(port.equals(SensorPort.S3)){
                return "in3";
            }else if(port.equals(SensorPort.S4)){
                return "in4";
            }

        } else if(platform.equals(BRICKPI)) {

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
