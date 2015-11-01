# ev3dev-lang-java

*EV3Dev-lang-Java* is a Java project designed to offer an API to use the [lego port interface](http://www.ev3dev.org/docs/drivers/lego-port-class/) in the [LeJOS way](http://www.lejos.org/).

![ScreenShot](https://raw.githubusercontent.com/jabrena/ev3dev-lang-java/master/uml/ev3-lang-java.png)

## Goals

* Provide a modular set of Java libraries to run on EV3Dev
* Reuse LeJOS development on EV3Dev

## Motivation

**1. Enjoy with Linux & Java**

EV3Dev is a successful project to offer a Linux platform for Lego Mindstorms developers based on Debian Project. Using this platform, any developer is able to run several programming languages as Python, GoLang or Node.js so the question is: *Why not Java on EV3Dev?* 

Using the package manager, it is possible to install Java:

```
sudo apt-get install default-jdk
```

To develop the classic *"Hello World"* program on EV3Dev:

``` java
public class HelloWorld {

    public static void main(String[] args){
        System.out.println("Hello World");
    }

}
```

To compile and run the program on EV3Dev:

``` bash
javac HelloWorld.java
java HelloWorld
Hello World
```

So, if the process is easy on EV3Dev, you could think: 

> Maybe, I could develop Software for robots on EV3Dev with *"ev3dev-lang-java"*

**2. Hardware scalability**

In Lego Mindstorms ecosystem, Java developers use LeJOS libraries to develop software for robots with Java but that libraries were coupled with Firmwares for RCX & NXT and now with Busybox for EV3Dev. The performance is nice and the API is really advanced, the best one, but it is complex to experiment with Linux due to the nature of Busybox and the no existence of a package manager.

In the other side, EV3Dev has built a Linux system to run on the EV3 Brick. The project offers a neutral interface for sensors an actuators based on sysfs so if this project build a low level interface for Java, it is possible to develop software for robots with Java running on Debian but the real advantage of EV3Dev is the hardware scalability, EV3Dev is able to run in the following products:

* EV3 Brick
* Raspberry Pi 2 + PiStorm/BrickPi hats

**3. Some Java pending features**

Modern Java development uses Maven/Graddle to manage dependencies in Java projects.

With EV3Dev with ev3dev-lang-java, you can use precompiled Java artifacts or compile the sources on your brick.

Try this exercise:

*1. Connect with your Brick:* 

``` bash
ssh root@192.168.2.2
r00tme
```

*2. Execute the following statements:*

``` bash
sudo apt-get install maven
sudo apt-get install git
git clone https://github.com/jabrena/ev3dev-lang-java.git
cd ev3dev-lang-java/examples/java/helloworld
mvn package
java -cp target/helloworld-1.0-SNAPSHOT.jar i.love.neutrinos.App
```

## References:

* Maven in 5 Minutes: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
* LeJOS website: http://www.lejos.org/
* LeJOS Git: http://sourceforge.net/p/lejos/ev3/code/ci/master/tree/ 
* EV3Dev: http://www.ev3dev.org/
* EV3Dev // Getting Started: http://www.ev3dev.org/docs/getting-started/
* EV3Dev autogen: https://github.com/ev3dev/ev3dev-lang/tree/develop/autogen
* Issue management: https://overv.io
* UML Modelling: http://astah.net/editions/community
