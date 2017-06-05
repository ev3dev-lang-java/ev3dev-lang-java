# Open issues

## Critic issues:

- I think that the class `FusorDetector.java` has some kind of Concurrency issue.
- Usage of mutable objects with `SampleProvider`

``` java
float [] sample = new float[sp.sampleSize()];
sp.fetchSample(sample, 0);
distanceValue = (int)sample[0];
```

- Usage of inner classes for the different modes of Sensors
- Lack of Unit testing in the whole project

- Internal ready indicator. This case appear with Multi threading scenarios:

```
ev3dev#1|Example using Subsumption architecture
        ev3dev#1|2017-06-04 18:14:14 [main] DEBUG ev3dev.hardware.EV3DevDevice - Detecting device on port: in1
        ev3dev#1|2017-06-04 18:14:16 [main] DEBUG ev3dev.hardware.EV3DevDevice - Detected port on path: /sys/class/lego-sensor/sensor0/address
        ev3dev#1|Arbitrator created
        ev3dev#1|2017-06-04 18:14:18 [main] DEBUG e.a.lego.motors.BaseRegulatedMotor - Detecting motor on port: outA
        ev3dev#1|2017-06-04 18:14:18 [main] DEBUG ev3dev.hardware.EV3DevDevice - Detecting device on port: outA
        ev3dev#1|2017-06-04 18:14:18 [main] DEBUG ev3dev.hardware.EV3DevDevice - Detected port on path: /sys/class/lego-port/port4/address
        ev3dev#1|2017-06-04 18:14:18 [main] DEBUG e.a.lego.motors.BaseRegulatedMotor - Setting port in mode: tacho-motor
        ev3dev#1|2017-06-04 18:14:19 [main] DEBUG ev3dev.hardware.EV3DevDevice - Detecting device on port: outA
        ev3dev#1|2017-06-04 18:14:19 [main] DEBUG ev3dev.hardware.EV3DevDevice - Detected port on path: /sys/class/tacho-motor/motor8/address
        ev3dev#1|2017-06-04 18:14:19 [main] ERROR ev3dev.utils.Sysfs - File: '/sys/class/tacho-motor/motor8/command' without write permissions.
        ev3dev#1|Exception in thread "main" java.lang.ExceptionInInitializerError
        ev3dev#1|	at lejos_commons.subsumption.DriveForward.action(DriveForward.java:20)
        ev3dev#1|	at lejos.robotics.subsumption.Arbitrator.go(Arbitrator.java:103)
        ev3dev#1|	at lejos_commons.subsumption.BumperCar.main(BumperCar.java:17)
        ev3dev#1|Caused by: java.lang.RuntimeException: Operation not executed: /sys/class/tacho-motor/motor8/command with value reset
        ev3dev#1|	at ev3dev.hardware.EV3DevDevice.setStringAttribute(EV3DevDevice.java:91)
        ev3dev#1|	at ev3dev.actuators.lego.motors.BaseRegulatedMotor.<init>(BaseRegulatedMotor.java:82)
        ev3dev#1|	at ev3dev.actuators.lego.motors.NXTRegulatedMotor.<init>(NXTRegulatedMotor.java:24)
        ev3dev#1|	at ev3dev.actuators.lego.motors.Motor.<clinit>(Motor.java:31)
        ev3dev#1|	... 3 more
        Failed command ev3dev#1 with status 1: java -server -jar /home/robot/ev3dev-lang-java-all-0.1.0.jar

```

## Medium issues:

- Install Oracle JRE 8, without any login on Oracle website
- Run Java programs from Brickman
- Some LeJOS interface methods was not implemented.
- Motor synchronization

## Minor issues:

- Improve the granularity support for EV3 Buttons
- Class with unsafe operations

```
Note: /Users/jabrena/Documents/DATA/2015/RESEARCH/robotics/ev3dev/java/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/BaseRegulatedMotor.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
```
