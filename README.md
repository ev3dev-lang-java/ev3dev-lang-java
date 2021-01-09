# ev3dev-lang-java

*EV3Dev-lang-Java* is project to learn Java and create software for Mindstorms Robots using hardware supported by [EV3Dev](http://www.ev3dev.org/)
& the [LeJOS](http://www.lejos.org/) way.

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](/LICENSE)
[![Travis CI](https://travis-ci.org/ev3dev-lang-java/ev3dev-lang-java.svg?branch=develop)](https://travis-ci.org/ev3dev-lang-java/ev3dev-lang-java)

![ScreenShot](https://raw.githubusercontent.com/jabrena/ev3dev-lang-java/master/docs/images/theThreeAmigos.jpg)

# How to test?

For non Linux computers use:

```
./gradlew dockerTest
```

For Linux computers use:

```
./gradlew test
```

## Introduction

In Lego Mindstorms ecosystem, the default solution to develop Java software for Lego Mindstorms
was [LeJOS](http://www.lejos.org/) but now exists one alternative, `EV3Dev-lang-java` a Java project
running on the top of [EV3Dev](http://www.ev3dev.org/).

Lego Mindstorms ecosystem is `a nice educational way to learn Java programming in general and Robotics in particular`.
Now, it is possible to install a complete Linux distro in the third generation of the product and others companies like
[Mindsensors](http://www.mindsensors.com/) & [Dexter Industries](https://www.dexterindustries.com/) has released products
 which interact with Sensors & Actuators from Lego ecosystem and that boards can use the Power of the popular board
 [Raspberry Pi 3](https://www.raspberrypi.org/)

But, with the help of `EV3Dev`, it is possible to have the same Linux experience for multiple boards.
So... why not develop a Java library for that Linux Distro? The answer is `EV3Dev-lang-java`.
The project, takes the good things of both worlds: EV3Dev with the complete linux experience
and LeJOS with the rich local navigation stack and the remote support in the future.

**What Debian versions are supported with this library?**

The library has support for `Debian Jessie` & `Debian Stretch`.
In this release, we have finished the support for `Debian Stretch` for EV3 and you could
 use OpenJDK 11, or OpenJDK 15 and it is amazing!

If you need to use any Raspberry Pi Boards, I recommend to use the stable `EV3Dev`
[Debian Jessie](http://www.ev3dev.org/downloads/) release.

**What is the hardware platforms supported in this project?**

Using the same `Java` objects, it is possible to deploy the software for Robots on EV3 Brick, Raspberry Pi 3 with BrickPi 3 & PiStorms.

| # | Element  | EV3 P-Brick                                | Raspberry Pi 3 Model B+            |
|---|----------|--------------------------------------------|------------------------------------|
| 1 | Hardware | EV3 Brick                                  | BrickPi+, BrickPi3, PiStorms       |
| 2 | SoC      | TI Sitara AM1808                           | Broadcom BCM2837B0                 |
| 3 | CPU      | 1× ARM926EJ-S @ 300MHz ([max 456MHz][clk]) | 4× ARM Cortex-A53 @ 1400MHz        |
| 4 | RAM      | 64 MB LPDDR                                | 1024 MB LPDDR2 (900 MHz)           |
| 5 | USB Host | 1× USB 1.1  (without onboard hub)          | 4× USB 2.0 (from onboard hub)      |
| 6 | Year     | 2010                                       | 2018                               |

[clk]: https://lechnology.com/2018/06/overclocking-lego-mindstorms-ev3-part-2/

## Project architecture

The project has been designed with the following solution in mind:

| # | Layer            | Option 1                                   | Option 2                |
|---|------------------|--------------------------------------------|-------------------------|
| 1 | Platforms        | BrickPi BrickPi3 PiStorms              | EV3                     |
| 2 | OS               | Debian Jessie                              | Debian Jessie/Stretch          |
| 3 | JVM              | OpenJDK 15                               | OpenJDK JRI 11 / 12 / 13 / 14 / 15          |
| 4 | EV3Dev Kernel    | 4.4.47-19-ev3dev-rpi2 | 4.14.71-ev3dev-2.3.0-ev3 |
| 5 | ev3dev-lang-java | 0.7.0                                      | 2.4.12                   |

**Note:** At the moment, we will maintain the whole solution until the next `LEGO Mindstorms` product estimated
for next January of 2022.

## Features included in the whole project

**Java features**

* OpenJDK Java 11/12/13/14/15 support for EV3 Brick, Brickpi+/PiStorms + Raspberry Pi 3
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
* [RPLidar A1/A2](https://github.com/ev3dev-lang-java/usb-devices) (2D Lidar) Support

## Subprojects

Stable projects:

- [OpenJDK for EV3](https://github.com/ev3dev-lang-java/openjdk-ev3): A custom OpenJDK JRI/JDK (9,10,11,12) build for EV3
- [EV3Dev-lang-java](https://github.com/ev3dev-lang-java/ev3dev-lang-java): Low level integration with EV3Dev
- [lejos-commons](https://github.com/ev3dev-lang-java/lejos-commons): LeJOS interfaces & utilities
- [Installer](https://github.com/ev3dev-lang-java/installer): A set of Bash scripts to automate some operations with your brick
- [Template project](https://github.com/ev3dev-lang-java/template_project_gradle): A Gradle project ready to test the project
- [Examples](https://github.com/ev3dev-lang-java/examples): A repository with several examples about the usage of this project

Incubator projects:

- [lejos-navigation](https://github.com/ev3dev-lang-java/lejos-navigation): LeJOS navigation stack
- [Usb Devices](https://github.com/ev3dev-lang-java/usb-devices): A library to use Arduino, Grove Sensors, GPS, IMU, LIDAR, and more devices...
- [ROS](https://github.com/ev3dev-lang-java/ros): A library to publish Sensor data to ROS for SLAM purposes

## Roadmap

Review the `backlog` to follow the features in course:

https://github.com/ev3dev-lang-java/ev3dev-lang-java/issues

## Getting Started

Take a look the documentation to use the project:

http://ev3dev-lang-java.github.io/docs/support/index.html

and use this easy template project with examples to use the project in a quick way:

https://github.com/ev3dev-lang-java/template-project-gradle

## Examples

Take a look the following repository to learn how to use this project:

https://github.com/ev3dev-lang-java/examples

## Videos

- https://www.youtube.com/watch?v=6l5NqRXmz7s
- https://www.youtube.com/watch?v=Gxew3aNH6ks
- https://www.youtube.com/watch?v=1d9q24aNMHQ
- https://www.youtube.com/watch?v=SIwG848ODI8

## UML Design

![ScreenShot](https://github.com/ev3dev-lang-java/ev3dev-lang-java/raw/master/docs/uml/graph.png)

## Architecture decision record (ADR)

* [Why this project was started](./docs/adr/adr-lejos-support.md)
* [Logging support](./docs/adr/adr-logging-support.md)
* [New Display API](./docs/adr/adr-display-api.md)
* [Changes to leJOS sensor API](./docs/adr/adr-lejos-sensor.md)
* [Support for newer Java versions](./docs/adr/adr-openjdk-builds.md)

## References:

* LeJOS: http://www.lejos.org/
* LeJOS Git: http://sourceforge.net/p/lejos/ev3/code/ci/master/tree/
* LeJOS Forum: https://lejos.sourceforge.io/forum/search.php?keywords=ev3dev&sid=8642d9d1b361bcfdcdeabb26db89e632
* EV3Dev: http://www.ev3dev.org/
* EV3Dev // Getting Started: http://www.ev3dev.org/docs/getting-started/
* EVEDev // Linux Kernel Drivers: http://docs.ev3dev.org/projects/lego-linux-drivers/en/ev3dev-jessie/
* Dexter Forum: http://forum.dexterindustries.com/search?q=java
* AdoptOpenJDK CI: https://ci.adoptopenjdk.net/view/ev3dev/
* OpenJDK: https://openjdk.java.net/
