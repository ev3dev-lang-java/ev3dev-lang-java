# ev3dev-lang-java

*EV3Dev-lang-Java* is a Java library designed to interact with [EV3Dev](http://www.ev3dev.org/) hardware using the [LeJOS](http://www.lejos.org/) way.

[![Dependency Status](https://www.versioneye.com/user/projects/5904679be57fd500418cacdc/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5904679be57fd500418cacdc)
[![Travis CI](https://travis-ci.org/ev3dev-lang-java/ev3dev-lang-java.svg?branch=develop)](https://travis-ci.org/ev3dev-lang-java/ev3dev-lang-java)

The project has support for the latest `EV3Dev` stable kernels:

- [EV3 Brick](https://education.lego.com/en-us/products/lego-mindstorms-education-ev3-core-set-/5003400): 4.4.47-19-ev3dev-ev3
- [RaspberryPi 3](https://www.raspberrypi.org/products/raspberry-pi-3-model-b/) with [BrickPi+](https://www.dexterindustries.com/brickpi/) & [PiStorms](http://www.mindsensors.com/content/78-pistorms-lego-interface): 4.4.47-19-ev3dev-rpi2

If you need further information about stable EV3Dev images, [click here](http://www.ev3dev.org/news/2017/02/11/ev3dev-jessie-2017-02-11-release/)

## Introduction

In Lego Mindstorms ecosystem, the default solution to develop Java software for a Lego Mindstorms is [LeJOS](http://www.lejos.org/).
But now, it exists another alternative, `EV3Dev-lang-java` a Java project for [EV3Dev](http://www.ev3dev.org/).  
  
**What libraries contains EV3Dev-lang-java?**
  
The project contains the following Java libraries/scripts:
  
    - EV3Dev-lang-java: Low level interation with EV3Dev
    - lejos-commons: LeJOS interfaces & Utilities
    - lejos-navigation: LeJOS navigation stack
    - RPLidar4J: RPLidar A1 support
    - Installer: A set of Bash scripts to automate some operations with your brick

**Advantages of the usage of this project?**

![ScreenShot](https://raw.githubusercontent.com/jabrena/ev3dev-lang-java/master/docs/images/theThreeAmigos.jpg)

Basically, with this library you can develop educational robots with Java for the following bricks:

- EV3 Brick
- BrickPi+
- BrickPi 3 (Scheduled on v0.7.0)
- PiStorms

Now, with the same syntax, it is possible to deploy the software for robots on Raspberry Pi with BrickPi & PiStorms.

Any EV3 Brick uses a `SoC: Sitara Processor AM1808` (from year 2010) to manage Sensors & Actuators 
but now with the usage of a BrickPi/PiStorms unit, it is possible to use the power of a Raspberry Pi 3.

**2010 Chip included on EV3 Brick:**

``` 
SoC: Sitara Processor AM1808
CPU: ARM9 300MHz
RAM: 16KB of Instruction Cache, 16KB of Data Cache ,8KB of RAM (Vector Table), 64KB of ROM
```

**2016 Chip included on Raspberry Pi 3:**

``` 
SoC: Broadcom BCM2837
CPU: 4× ARM Cortex-A53, 1.2GHz
RAM: 1GB LPDDR2 (900 MHz)
```

Anyway, although you could use hardware with more resources, it is necessary to develop stable algorithms (Your turn). 

## Features included in the whole project

**Java features**

* Automatic installation of Oracle Java JDK 8 for Brickpi+/PiStorms + Raspberry Pi 3
* Partial installation of Oracle Java JRE 8 for EV3 Brick
* OpenJDK Java 9 EA support for Brickpi+/PiStorms + Raspberry Pi 3
* Java profiling tools Support ([VisualVM](https://visualvm.java.net/) & [JConsole](http://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html))
* Centralized logs with [Kibana](https://www.elastic.co/products/kibana)

**Platform features**

* Support for EV3 Brick, PiStorms v1/v2, BrickPi+ & BrickPi 3

**Lego Mindstorms features**

* Regulated Motor Support
* Unregulated Motor Support
* Sensor Support (EV3 sensors)
* Sounds Support

**Robotics**

* Automatic installation of [OpenCV](http://opencv.org/)
* [eSpeak](http://espeak.sourceforge.net/) TTS (Text to speech) Support
* [LeJOS Sensor filter](http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/) Support
* [LeJOS local navigation stack](https://github.com/ev3dev-lang-java/lejos-navigation) Support
* [RPLidar A1](https://github.com/ev3dev-lang-java/RPLidar4J) (2D Lidar) Support

## Getting Started

### 1. Install EV3Dev on your brick

Follow the following link to install EV3Dev:

http://www.ev3dev.org/docs/getting-started/

Once you finish this step, you will have a complete Linux distro to run any Programming language

**Note:** Please, update your brick with latest update before continuing with the next step.

```
sudo apt-get update
sudo apt-get upgrade
sudo apt-get dist-upgrade
sudo reboot
```

### 2. Install Java on your brick

For this step exist 2 paths. 

**2.1 EV3 Brick:**

The EV3 Brick was designed with a SOC based on [ARM EABI](https://wiki.debian.org/ArmEabiPort) 
and the fastest JRE option is based on Oracle and it is not possible to download in an automatic way so you have to 
download the JRE on your local machine from [here](http://www.oracle.com/technetwork/java/embedded/downloads/javase/javaseemeddedev3-1982511.html)
and later copy to your brick using `scp`:

```
scp "./ejdk-8-fcs-b132-linux-arm-sflt-03_mar_2014.tar.gz" "robot@192.168.1.85:/home/robot"
```

Once, you have the file on the brick, you can continue the Java installation with the installer or do your self manually.

**Using the installer:**

```
cd /home/robot
mkdir installer
cd installer
wget -N https://raw.githubusercontent.com/ev3dev-lang-java/installer/develop/installer.sh
chmod +x installer.sh
sudo ./installer.sh help
sudo ./installer.sh
```

**Manual way:**

```
tar -zxvf "/home/robot/ejdk-8-fcs-b132-linux-arm-sflt-03_mar_2014.tar.gz" -C /opt
sudo update-alternatives --install /usr/bin/java java /opt/ejdk1.8.0/linux_arm_sflt/jre/bin/java 1
java -version
```

Now, you have Java on your EV3 Brick

**2.2 BrickPi+ / PiStorms:**

Using the installer, it is possible to automate everything:

```
cd /home/robot
mkdir installer
cd installer
wget -N https://raw.githubusercontent.com/ev3dev-lang-java/installer/develop/installer.sh
chmod +x installer.sh
sudo ./installer.sh help
sudo ./installer.sh
```

Further information about the installer here:
https://github.com/ev3dev-lang-java/installer

### 3. Create your first Project and deploy on your Brick

Once you have the required infrastructure on your Brick, it is possible to experiment with the libraries in some ways:

**3.1 Using a Gradle template project**

If you like, you can experiment with the project, using the following project template:

https://github.com/ev3dev-lang-java/template_project_gradle

Download the project, update the file: `deploy.gradle`: with the IP of you Brick:

```
remotes {
    ev3dev {
        host = '10.0.1.3'
        user = 'robot'
        password = 'maker'
    }
}
```

To deploy the example on your brick, open a `terminal` and type:

```
./gradlew deployAndRun
```

**3.2 Create a project from scratch:**

Another alternative is the creation of a project from Scrach using Maven/Gradle.

To start a new project with this library, add the following repository and dependency.

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

``` xml
<dependency>
    <groupId>com.github.ev3dev-lang-java</groupId>
    <artifactId>ev3dev-lang-java</artifactId>
    <version>v0.6.0</version>
</dependency>
```
	
Further information about the Maven dependency: https://jitpack.io/#ev3dev-lang-java/ev3dev-lang-java/v0.6.0

## Examples

Exist many examples used to test the project. In the future we will automate a good % of the tests using [JUnit testing](http://junit.org/junit4/)
and [Mocks](http://site.mockito.org/). But at the moment, many features are tested manually.

Take a look the following examples to discover some features included with this Java project.

**EV3Dev-lang-Java:**

- ev3dev.misc
- [BumperCar](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/misc/BumperCar.java)
- [BumperCar2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/misc/BumperCar2.java)
- ev3dev.robotics.tts
- [TTSDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/robotics/tts/TTSDemo.java)
- [TTSDemo2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/robotics/tts/TTSDemo2.java)
- ev3dev.actuators
- [LCDDrawIconsTest](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/LCDDrawIconsTest.java)
- [LCDDrawImagesTest](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/LCDDrawImagesTest.java)
- [LCDDrawLinesTest](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/LCDDrawLinesTest.java)
- [LCDDrawRectanglesTest](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/LCDDrawRectanglesTest.java)
- [LCDFontTest](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/LCDFontTest.java)
- [LCDTest](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/LCDTest.java)
- [LCDWriteTextTest](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/LCDWriteTextTest.java)
- [SoundDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/SoundDemo.java)
- ev3dev.actuators.ev3
- [LEDExample](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/ev3/LEDExample.java)
- ev3dev.actuators.lego.motors
- [LargeMotorDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/LargeMotorDemo.java)
- [LargeMotorDemo2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/LargeMotorDemo2.java)
- [MediumMotorDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/MediumMotorDemo.java)
- [MotorEventTest](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/MotorEventTest.java)
- [MultipleMotorsDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/MultipleMotorsDemo.java)
- [MultipleMotorsStopDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/MultipleMotorsStopDemo.java)
- [RegulatedMotorRotateDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/RegulatedMotorRotateDemo.java)
- [RegulatedMotorRotateDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/RegulatedMotorRotateDemo.java)
- [RegulatedMotorRotateToDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/RegulatedMotorRotateToDemo.java)
- [UnregulatedMotorDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/UnregulatedMotorDemo.java)
- [UnregulatedMotorDemo2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/UnregulatedMotorDemo2.java)
- [UnregulatedMutilpleMotorDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/actuators/lego/motors/UnregulatedMutilpleMotorDemo.java)
- ev3dev.sensors
- [BatteryDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/BatteryDemo.java)
- [ButtonExample](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ButtonExample.java)
- ev3dev.sensors.ev3
- [ColorSensorDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/ColorSensorDemo.java)
- [ColorSensorDemo2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/ColorSensorDemo2.java)
- [ColorSensorDemo3](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/ColorSensorDemo3.java)
- [ColorSensorMultipleDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/ColorSensorMultipleDemo.java)
- [GyroSensorDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/GyroSensorDemo.java)
- [GyroSensorDemo2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/GyroSensorDemo2.java)
- [GyroSensorDemo3](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/GyroSensorDemo3.java)
- [IRSensorDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/IRSensorDemo.java)
- [IRSensorDemo2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/IRSensorDemo2.java)
- [IRSensorDemo3](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/IRSensorDemo3.java)
- [TouchSensorDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/TouchSensorDemo.java)
- [USSensorDemo](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/USSensorDemo.java)
- [USSensorDemo2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/sensors/ev3/USSensorDemo2.java)
- ev3dev.hardware
- [PlatformTest](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/ev3dev/hardware/PlatformTest.java)

**lejos-commons:**

- lejos.commons.subsumption
- [BumperCar](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/commons/subsumption/BumperCar.java)
- [DriveForward](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/commons/subsumption/DriveForward.java)
- [HitWall](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/commons/subsumption/HitWall.java)

**lejos-navigation:**

- lejos.robotics.navigation
- [PilotConfig](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/pilot/PilotConfig.java)
- [PilotConfig2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/pilot/PilotConfig2.java) 
- [DifferentialPilotStopTest](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/pilot/DifferentialPilotStopTest.java) 
- [DifferentialPilotTest1](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/pilot/DifferentialPilotTest1.java) 
- [DifferentialPilotTest9](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/pilot/DifferentialPilotTest9.java) 
- [MoveControllerTest1](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/navigator/MoveControllerTest1.java) 
- [MoveControllerTest2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/navigator/MoveControllerTest2.java) 
- [NavigatorTest1](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/navigator/NavigatorTest1.java) 
- [NavigatorTest2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/navigator/NavigatorTest2.java) 
- [NavigatorTest3](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/navigator/NavigatorTest3.java) 
- [NavigatorTest4](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/navigator/NavigatorTest4.java) 
- lejos.robotics.objectdetection
- [FeatureAvoider](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/feature/FeatureAvoider.java) 
- [FeatureAvoider2](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/feature/FeatureAvoider2.java) 
- [FeatureAvoider3](https://github.com/ev3dev-lang-java/examples/blob/develop/ev3dev-lang-java/src/main/java/lejos/navigation/feature/FeatureAvoider3.java) 

## Videos

- https://www.youtube.com/watch?v=SIwG848ODI8

## Open issues:

This project is not perfect. Exist some aspects to improve:

- Install Oracle JRE 8, without any login on Oracle website
- Run Java programs from Brickman
- I think that the class `FusorDetector.java` has some kind of Concurrency issue.
- Improve the granularity support for EV3 Buttons
- Usage of mutable objects with `SampleProvider`

``` java
float [] sample = new float[sp.sampleSize()];
sp.fetchSample(sample, 0);
distanceValue = (int)sample[0];
```

- Usage of inner classes for the different modes of Sensors
- Lack of Unit testing in the whole project

## UML Design

![ScreenShot](https://github.com/ev3dev-lang-java/ev3dev-lang-java/raw/develop/docs/uml/graph.png)

## References:

* LeJOS: http://www.lejos.org/
* LeJOS Git: http://sourceforge.net/p/lejos/ev3/code/ci/master/tree/ 
* EV3Dev: http://www.ev3dev.org/
* EV3Dev // Getting Started: http://www.ev3dev.org/docs/getting-started/

