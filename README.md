# ev3dev-lang-java

*EV3Dev-lang-Java* is a Java library designed to interact with [EV3Dev](http://www.ev3dev.org/) hardware using the [LeJOS](http://www.lejos.org/) way.

[![Dependency Status](https://www.versioneye.com/user/projects/5904679be57fd500418cacdc/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5904679be57fd500418cacdc)

![Travis CI](https://travis-ci.org/ev3dev-lang-java/ev3dev-lang-java.svg?branch=develop)

ev3dev kernel support: uname -r (4.4.47-19-ev3dev-ev3 & 4.4.47-19-ev3dev-rpi2)

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
* RPLIDARA1 Support (2D Support)
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

![ScreenShot](https://raw.githubusercontent.com/jabrena/ev3dev-lang-java/master/docs/uml/graph.png)

## References:

* LeJOS: http://www.lejos.org/
* LeJOS Git: http://sourceforge.net/p/lejos/ev3/code/ci/master/tree/ 
* EV3Dev: http://www.ev3dev.org/
* EV3Dev // Getting Started: http://www.ev3dev.org/docs/getting-started/

