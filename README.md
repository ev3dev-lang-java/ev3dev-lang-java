# ev3dev-lang-java

*EV3Dev-lang-Java* is a Java library designed to interact with [EV3Dev](http://www.ev3dev.org/) hardware using the [LeJOS](http://www.lejos.org/) way.

[![Dependency Status](https://www.versioneye.com/user/projects/5904679be57fd500418cacdc/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5904679be57fd500418cacdc)
[![Travis CI](https://travis-ci.org/ev3dev-lang-java/ev3dev-lang-java.svg?branch=develop)](https://travis-ci.org/ev3dev-lang-java/ev3dev-lang-java)

The project support the latest `EV3Dev` stable kernels:

- [EV3 Brick](https://education.lego.com/en-us/products/lego-mindstorms-education-ev3-core-set-/5003400): 4.4.47-19-ev3dev-ev3
- [RaspberryPi 3](https://www.raspberrypi.org/products/raspberry-pi-3-model-b/) with [BrickPi+](https://www.dexterindustries.com/brickpi/) & [PiStorms](http://www.mindsensors.com/content/78-pistorms-lego-interface): 4.4.47-19-ev3dev-rpi2

If you need further information about stable EV3Dev images, [click here](http://www.ev3dev.org/news/2017/02/11/ev3dev-jessie-2017-02-11-release/)

## Introduction

In Lego Mindstorms ecosystem, the default solution to develop Java software for a Lego Mindstorms is [LeJOS](http://www.lejos.org/).
But now, it exists another alternative, `EV3Dev-lang-java` a Java project for EV3Dev, a Debian distro.
  
**What libraries contains EV3Dev-lang-java?**
  
The project contains the following libraries:
  
    - EV3Dev-lang-java: Low level interation with EV3Dev
    - lejos-commons: LeJOS interfaces & Utilities
    - lejos-navigation: LeJOS navigation stack
    - RPLidar4J: RPLidar A1 support
    - Installer: A set of Bash scripts to automate some operations

![ScreenShot](https://raw.githubusercontent.com/jabrena/ev3dev-lang-java/master/docs/images/theThreeAmigos.jpg)

## Features

* Java 9 support (Brickpi+/PiStorms + Raspberry Pi 3)
* Java 8 JRE support (EV3 Brick)
* EV3 Brick, PiStorms v1/v2 &amp; BrickPi+ support
* [OpenCV](http://opencv.org/) Computer Vision Support
* Regulated Motor Support
* Unregulated Motor Support
* Sensor Support (EV3 sensors)
* Sounds Support
* [LeJOS Sensor filter](http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/) Support
* [RPLidar A1](https://github.com/ev3dev-lang-java/RPLidar4J) Support (2D Lidar)
* [eSpeak](http://espeak.sourceforge.net/) TTS (Text to speech) Support
* Java profiling tools Support ([VisualVM](https://visualvm.java.net/) & [JConsole](http://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html))
* Centralized logs with [Kibana](https://www.elastic.co/products/kibana)

## Getting Started

### Add the dependency on the project

To use this project, import the library as a Maven dependency.

``` xml
<dependency>
    <groupId>com.github.ev3dev-lang-java</groupId>
    <artifactId>ev3dev-lang-java</artifactId>
    <version>v0.5.0</version>
</dependency>
```
	
Further information: https://jitpack.io/#ev3dev-lang-java/ev3dev-lang-java/v0.5.0

### Example:

https://www.youtube.com/watch?v=SIwG848ODI8

# UML Design

![ScreenShot](https://github.com/ev3dev-lang-java/ev3dev-lang-java/raw/develop/docs/uml/graph.png)

## References:

* LeJOS: http://www.lejos.org/
* LeJOS Git: http://sourceforge.net/p/lejos/ev3/code/ci/master/tree/ 
* EV3Dev: http://www.ev3dev.org/
* EV3Dev // Getting Started: http://www.ev3dev.org/docs/getting-started/

