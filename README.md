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

### Use a Gradle template project

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

### Create a project from scratch

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
	
Further information: https://jitpack.io/#ev3dev-lang-java/ev3dev-lang-java/v0.6.0

### Videos:

https://www.youtube.com/watch?v=SIwG848ODI8

# UML Design

![ScreenShot](https://github.com/ev3dev-lang-java/ev3dev-lang-java/raw/develop/docs/uml/graph.png)

## References:

* LeJOS: http://www.lejos.org/
* LeJOS Git: http://sourceforge.net/p/lejos/ev3/code/ci/master/tree/ 
* EV3Dev: http://www.ev3dev.org/
* EV3Dev // Getting Started: http://www.ev3dev.org/docs/getting-started/

