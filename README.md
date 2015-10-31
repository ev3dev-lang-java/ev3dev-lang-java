# ev3dev-lang-java

## Introduction
*EV3Dev-lang-Java* is project designed to offer an API to use the [lego port interface](http://www.ev3dev.org/docs/drivers/lego-port-class/) in the [LeJOS way](http://www.lejos.org/). 

## Motivation

EV3Dev is a successfull project to offer a Linux platform for Lego Mindstorms developers based on Debian Project on EV3 Brick. Using this platform, any developer is able to run several programming languages as Python, GoLang or Node.js so my question is *Why not Java?* 

In Lego Mindstorms ecosystem, Java developers use LeJOS libraries to develop software for robots with Java but that libraries are coupled with Firmware for RCX & NXT and now with Busybox for EV3Dev. 

After running the classic programm "Hello World" on EV3 Brick with EV3Dev:

``` java
public class HelloWorld {

    public static void main(String[] args){
        System.out.println("Hello, World");
    }

}
```

## References:

LeJOS website: http://www.lejos.org/

LeJOS Git: http://sourceforge.net/p/lejos/ev3/code/ci/master/tree/

EV3Dev: http://www.ev3dev.org/

EV3Dev autogen: https://github.com/ev3dev/ev3dev-lang/tree/develop/autogen

Issue management: https://overv.io