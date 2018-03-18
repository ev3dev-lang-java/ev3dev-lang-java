# ev3dev-lang-java

*EV3Dev-lang-Java* is a Java project designed to build Software for Robots with [EV3Dev](http://www.ev3dev.org/) 
hardware & the [LeJOS](http://www.lejos.org/) way.

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](/LICENSE)
[![Travis CI](https://travis-ci.org/ev3dev-lang-java/ev3dev-lang-java.svg?branch=develop)](https://travis-ci.org/ev3dev-lang-java/ev3dev-lang-java)

![ScreenShot](https://raw.githubusercontent.com/jabrena/ev3dev-lang-java/master/docs/images/theThreeAmigos.jpg)

**What is the latest EV3Dev version supported?**

The libraries was tested with the latest stable `EV3Dev` release: `Debian Jessie`:

- [Debian Jessie](http://www.ev3dev.org/downloads/) 

**What is the hardware platforms supported in this project?**

This Debian distro run nice with the following platforms:

- [EV3 Brick](https://education.lego.com/en-us/products/lego-mindstorms-education-ev3-core-set-/5003400): 4.4.47-19-ev3dev-ev3
- [RaspberryPi 3](https://www.raspberrypi.org/products/raspberry-pi-3-model-b/) with [BrickPi3](https://www.dexterindustries.com/brickpi/) & [PiStorms](http://www.mindsensors.com/content/78-pistorms-lego-interface): 4.4.47-19-ev3dev-rpi2

If you need further information about stable EV3Dev images, [click here](http://www.ev3dev.org/news/2017/02/11/ev3dev-jessie-2017-02-11-release/)

- **Note:** In the next release, we will include support for [Raspberry Pi 3 Model B+](https://www.raspberrypi.org/products/raspberry-pi-3-model-b-plus/)
- **Note2:** In the next release, we will include support for `Debian Stretch` 

## Introduction

In Lego Mindstorms ecosystem, the default solution to develop Java software for Lego Mindstorms is [LeJOS](http://www.lejos.org/).
But now exists one alternative, `EV3Dev-lang-java` a Java project running on the top of [EV3Dev](http://www.ev3dev.org/).  

Lego Mindstorms ecosystem is `a nice educational way to learn Java programming in general and Robotics in particular`. 
Now, it is possible to install a complete Linux distro in the third generation of the product and others companies like 
[Mindsensors](http://www.mindsensors.com/) & [Dexter Industries](https://www.dexterindustries.com/) has released products
 which interact with Sensors & Actuators from Lego ecosystem and that boards can use the Power of the popular board 
 [Raspberry Pi 3](https://www.raspberrypi.org/)
 
But, with the help of `EV3Dev`, it is possible to have the same Linux experience for multiple boards. 
So... why not develop a Java library for that Linux Distro? The answer is `EV3Dev-lang-java`. 
The project, takes the good things of both worlds: EV3Dev with the complete linux experience 
and LeJOS with the rich local navigation stack.
    
**What projects are developed in EV3Dev-lang-java?**
  
The following list contains the stables projects that it is possible to use without any restriction:
  
- [EV3Dev-lang-java](https://github.com/ev3dev-lang-java/ev3dev-lang-java): Low level interation with EV3Dev
- [lejos-commons](https://github.com/ev3dev-lang-java/lejos-commons): LeJOS interfaces & utilities
- [lejos-navigation](https://github.com/ev3dev-lang-java/lejos-navigation): LeJOS navigation stack
- [RPLidar4J](https://github.com/ev3dev-lang-java/RPLidar4J): RPLidar A1 support
- [Battery Monitor](https://github.com/ev3dev-lang-java/batteryMonitor): A battery monitor to protect your hardware against low battery levels
- [Installer](https://github.com/ev3dev-lang-java/installer): A set of Bash scripts to automate some operations with your brick
- [Template project](https://github.com/ev3dev-lang-java/template_project_gradle): A Gradle project ready to test the project
- [Examples](https://github.com/ev3dev-lang-java/examples): A repository with several examples about the usage of this project

Incubator projects:

- [OpenJDK for EV3]()https://github.com/ev3dev-lang-java/openjdk-ev3): A custom OpenJDK JRE build for EV3
- [Usb Devices](https://github.com/ev3dev-lang-java/usb-devices): A library to use Arduino, Grove Sensors, GPS, IMU, LIDAR, and more devices...
- [ROS](https://github.com/ev3dev-lang-java/ros): A library to publish Sensor data to ROS for SLAM purposes

**Advantages of the usage of this project?**

Basically, with this library you can develop educational robots with Java for multiple bricks:

- EV3 Brick
- BrickPi+
- BrickPi 3
- PiStorms

Using the same objects, it is possible to deploy the software for robots on EV3 Brick, Raspberry Pi 3 with BrickPi & PiStorms.

If you analyze the hardware, any EV3 Brick uses a `SoC: Sitara Processor AM1808` (from year 2010) to manage Sensors & Actuators 
but now with the usage of a BrickPi/PiStorms unit, it is possible to use the power of a Raspberry Pi 3 increasing the 
possibilities to develop complex projects which require por Computational consumption.

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

## Features included in the whole project

**Java features**

* Automatic installation of Oracle Java JDK 8 for Brickpi+/PiStorms + Raspberry Pi 3
* Partial installation of Oracle Java JRE 8 for EV3 Brick
* OpenJDK Java 9 EA support for Brickpi+/PiStorms + Raspberry Pi 3
* Java profiling tools Support ([Oracle mission control](http://www.oracle.com/technetwork/java/javaseproducts/mission-control/java-mission-control-1998576.html) & [JConsole](http://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html))
* Logging support based on [SLF4J](https://www.slf4j.org/)
* Centralized logs with [Kibana](https://www.elastic.co/products/kibana)

**Platform features**

* Support for EV3 Brick, PiStorms v1/v2, BrickPi+ & BrickPi 3

**Lego Mindstorms features**

* Regulated Motor Support
* Unregulated Motor Support
* Sensor Support (EV3 sensors)
* Sounds Support
* EV3 LCD Support

**Robotics**

* Automatic installation of [OpenCV](http://opencv.org/)
* [eSpeak](http://espeak.sourceforge.net/) TTS (Text to speech) Support
* [LeJOS Sensor filter](http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/) Support
* [LeJOS local navigation stack](https://github.com/ev3dev-lang-java/lejos-navigation) Support
* [RPLidar A1](https://github.com/ev3dev-lang-java/RPLidar4J) (2D Lidar) Support

## Roadmap

Review the `backlog` to follow the features in course:
https://github.com/ev3dev-lang-java/ev3dev-lang-java/issues

## Getting Started

### 1. Install EV3Dev on your brick

Follow the link to install EV3Dev on your brick:

http://www.ev3dev.org/docs/getting-started/

Once you finish the installation process and the remote `ssh` connection test, 
you will have a complete Linux distro to run any Programming language.

**Note:** Please, maintain your brick updated. Execute the following commands 
before jump to the next step in this guide.

```
sudo apt-get update
sudo apt-get upgrade
sudo apt-get dist-upgrade
sudo reboot
```

### 2. Install Java on your brick

For this step exist 2 paths. One path is for EV3 Brick and another path for BrickPi users and PiStorms users. 

**2.1 EV3 Brick:**

The EV3 Brick was designed with a SOC based on [ARM EABI](https://wiki.debian.org/ArmEabiPort) 
and the best JVM option for that hardware architecture is the Oracle JRE 8 to install in the brick but, it is not possible to install directly in the brick
 without any human interaction, so you need to download from [here](http://www.oracle.com/technetwork/java/embedded/downloads/javase/javaseemeddedev3-1982511.html)
and later, copy the file `ejdk-8-fcs-b132-linux-arm-sflt-03_mar_2014.tar.gz` to your brick using the command `scp`.

Example:

```
scp "./ejdk-8-fcs-b132-linux-arm-sflt-03_mar_2014.tar.gz" "robot@192.168.1.85:/home/robot"
```

Once, you have the file on the brick, you can continue the Java installation with the installer or do yourself manually.

https://github.com/ev3dev-lang-java/installer

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

https://github.com/ev3dev-lang-java/installer

```
cd /home/robot
mkdir installer
cd installer
wget -N https://raw.githubusercontent.com/ev3dev-lang-java/installer/develop/installer.sh
chmod +x installer.sh
sudo ./installer.sh help
sudo ./installer.sh
```

### 3. Create your first Project and deploy on your Brick

Once you have the required infrastructure on your Brick, it is possible to experiment with the libraries in some ways:

**3.1 Using a Gradle template project**

If you like, you can experiment with the project, using the following project template:

https://github.com/ev3dev-lang-java/template_project_gradle

Download the project, update the file: `config.gradle`: with the IP of you Brick:

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

Besides, exist a task to provide access to a Profiling tool if you execute:

```
./gradlew deployAndProfilingRun
```

**3.2 Create a project from scratch:**

Another alternative is the creation of a project from Scratch using Maven/Gradle.

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
    <version>0.7.0</version>
</dependency>
```
	
Further information about the Maven dependency: https://jitpack.io/#ev3dev-lang-java/ev3dev-lang-java/v0.6.1

## Examples

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

- https://www.youtube.com/watch?v=Gxew3aNH6ks
- https://www.youtube.com/watch?v=1d9q24aNMHQ
- https://www.youtube.com/watch?v=SIwG848ODI8

## Unit testing

Exist many examples used to test the project. In the future we will automate a good % of the tests using 
[JUnit testing](http://junit.org/junit4/) and [Mocks](http://site.mockito.org/) 
but at the moment, many features are tested manually.

## UML Design

![ScreenShot](https://github.com/ev3dev-lang-java/ev3dev-lang-java/raw/develop/docs/uml/graph.png)

## References:

* LeJOS: http://www.lejos.org/
* LeJOS Git: http://sourceforge.net/p/lejos/ev3/code/ci/master/tree/ 
* LeJOS Forum: https://lejos.sourceforge.io/forum/search.php?keywords=ev3dev&sid=8642d9d1b361bcfdcdeabb26db89e632
* EV3Dev: http://www.ev3dev.org/
* EV3Dev // Getting Started: http://www.ev3dev.org/docs/getting-started/
* EVEDev // Linux Kernel Drivers: http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/
* Dexter Forum: http://forum.dexterindustries.com/search?q=java

