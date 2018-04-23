# ev3dev-lang-java

*EV3Dev-lang-Java* is a Java project designed to build Software for Robots with [EV3Dev](http://www.ev3dev.org/) 
hardware & the [LeJOS](http://www.lejos.org/) way.

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](/LICENSE)
[![Travis CI](https://travis-ci.org/ev3dev-lang-java/ev3dev-lang-java.svg?branch=develop)](https://travis-ci.org/ev3dev-lang-java/ev3dev-lang-java)

![ScreenShot](https://raw.githubusercontent.com/jabrena/ev3dev-lang-java/master/docs/images/theThreeAmigos.jpg)

## Project architecture

| # | Layer            | Option 1                                   | Option 2                |
|---|------------------|--------------------------------------------|-------------------------|
| 1 | Platforms        | EV3 BrickPi BrickPi3 PiStorms              | EV3                     |
| 2 | OS               | Debian Jessie                              | Debian Stretch          |
| 3 | JVM              | Oracle JRE 8                               | OpenJDK JRI 10          |
| 4 | EV3Dev Kernel    | 4.4.47-19-ev3dev-ev3 4.4.47-19-ev3dev-rpi2 | 4.9.58-ev3dev-1.6.0-ev3 |
| 5 | ev3dev-lang-java | 0.7.0                                      | 2.3.0                   |

**What Debian versions are supported by the library?**

The library has support for `Debian Jessie` & `Debian Stretch`. 
In this release, we have added initial support for `Debian Stretch`, so the Brick support is limited,
only `EV3 Brick` is supported for this Debian distro but soon you will have all Bricks supported but 
in the other hand, you have the possibility to use OpenJDK 10. 

If you need to any Raspberry Pi Boards, I recommend to use the latest stable `EV3Dev` release: [Debian Jessie](http://www.ev3dev.org/downloads/) 
with

**What is the hardware platforms supported in this project?**

This Debian distro run nice with the following platforms:

- [EV3 Brick](https://education.lego.com/en-us/products/lego-mindstorms-education-ev3-core-set-/5003400): 4.4.47-19-ev3dev-ev3
- [RaspberryPi 3](https://www.raspberrypi.org/products/raspberry-pi-3-model-b/) with [BrickPi3](https://www.dexterindustries.com/brickpi/) & [PiStorms](http://www.mindsensors.com/content/78-pistorms-lego-interface): 4.4.47-19-ev3dev-rpi2

If you need further information about stable EV3Dev images, [click here](http://www.ev3dev.org/news/2017/02/11/ev3dev-jessie-2017-02-11-release/)

- **Note:** In the next release, we will include support for [Raspberry Pi 3 Model B+](https://www.raspberrypi.org/products/raspberry-pi-3-model-b-plus/)

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
- [Installer](https://github.com/ev3dev-lang-java/installer): A set of Bash scripts to automate some operations with your brick
- [Template project](https://github.com/ev3dev-lang-java/template_project_gradle): A Gradle project ready to test the project
- [Examples](https://github.com/ev3dev-lang-java/examples): A repository with several examples about the usage of this project

Incubator projects:

- [lejos-navigation](https://github.com/ev3dev-lang-java/lejos-navigation): LeJOS navigation stack
- [Battery Monitor](https://github.com/ev3dev-lang-java/batteryMonitor): A battery monitor to protect your hardware against low battery levels
- [OpenJDK for EV3](https://github.com/ev3dev-lang-java/openjdk-ev3): A custom OpenJDK JRE build for EV3
- [Usb Devices](https://github.com/ev3dev-lang-java/usb-devices): A library to use Arduino, Grove Sensors, GPS, IMU, LIDAR, and more devices...
- [ROS](https://github.com/ev3dev-lang-java/ros): A library to publish Sensor data to ROS for SLAM purposes

**Advantages of the usage of this project?**

Using this library you can develop educational robots with Java for multiple Bricks:

- EV3 Brick
- BrickPi+
- BrickPi 3
- PiStorms

Using the same objects, it is possible to deploy the software for robots on EV3 Brick, Raspberry Pi 3 with BrickPi 3 & PiStorms.

If you analyze the hardware, any EV3 Brick uses a `SoC: Sitara Processor AM1808` (from year 2010) to manage Sensors & Actuators 
but now with the usage of a BrickPi/PiStorms unit, it is possible to use the power of a Raspberry Pi 3 increasing the 
possibilities to develop complex projects which require por Computational consumption.

**2010 Chip included on EV3 Brick:**

``` 
SoC: Sitara Processor AM1808
CPU: ARM9 300MHz
RAM: 16KB of Instruction Cache, 16KB of Data Cache ,8KB of RAM (Vector Table), 64KB of ROM
```

**2018 Chip included on Raspberry Pi 3 Model B+:**

``` 
SoC: Broadcom BCM2837 RISC de 64 bits
CPU: 4× ARM Cortex-A53, 1.4GHz
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

Take a look the documentation to use the project:

http://ev3dev-lang-java.github.io/docs/support/index.html

## Examples

Take a look the following repository to learn how to use this project:

https://github.com/ev3dev-lang-java/examples

It is very easy to copy one example and use inside of the project template:

https://github.com/ev3dev-lang-java/template_project_gradle

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

