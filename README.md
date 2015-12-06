# ev3dev-lang-java

*EV3Dev-lang-Java* is a Java library designed to develop Software for Robots over [EV3Dev stack](http://www.ev3dev.org/).

![ScreenShot](https://raw.githubusercontent.com/jabrena/ev3dev-lang-java/master/docs/uml/graph.png)

``` bash
git clone https://github.com/jabrena/ev3dev-lang-java.git
cd library
ant -buildfile uml.xml uml
```

# Test the development:

If you like, test development on master branch.

``` bash
git clone https://github.com/jabrena/ev3dev-lang-java.git
cd library
mvn install
```

Open a terminal to connect with your brick:

``` bash
ssh root:r00tme@192.168.2.2
cd /home
java -cp ev3-lang-java-0.2-SNAPSHOT.jar ev3dev.test.MotorTest
```

Example: 

``` java
package ev3dev.test;

import ev3dev.hardware.Battery;
import ev3dev.hardware.port.MotorPort;
import ev3dev.hardware.port.SensorPort;
import ev3dev.hardware.motor.EV3LargeRegulatedMotor;
import ev3dev.hardware.sensor.ev3.EV3IRSensor;
import lejos.robotics.SampleProvider;

public class MotorTest {

	public static void main(String[] args) {

		EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S2);
        SampleProvider sp = ir1.getDistanceMode();
        int distance = 255;

		EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
        mA.setSpeed(50);
        mB.setSpeed(50);

        final int distance_threshold = 35;
        final int iteration_threshold = 100;

        for(int i = 0; i <= iteration_threshold; i++) {
        	System.out.println(Battery.getVoltage());
            mA.forward();
            mB.forward();

            float [] sample = new float[sp.sampleSize()];
            sp.fetchSample(sample, 0);
            distance = (int)sample[0];
            
            if(distance <= distance_threshold){
                mA.stop();
                mB.stop();
                break;
            }else {
                System.out.println(distance);
            }
        }

        mA.stop();
        mB.stop();		
	}
}
```

# Hardware supported

## Lego Mindstorms EV3 Educational core set

![ScreenShot](https://raw.githubusercontent.com/jabrena/ev3dev-lang-java/master/docs/45544.png)

https://education.lego.com/cs-cz/lego-education-product-database/mindstorms-ev3/45544-lego-mindstorms-education-ev3-core-set

# Development priorities:

* Create a website to add Javadocs & Getting Started ([Jekyll](https://jekyllrb.com/))
* Create repository for behaviours: 
* Create repository for navigation:
* Create repository for computer vision: https://github.com/jabrena/ev3dev-lang-java-computer-vision

# Getting Started.

Create a Java Maven project. Update the file pom.xml to add the following repository:

``` xml
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
```

and add the dependency to offer Java support for EV3Dev:


``` xml
	<dependency>
	    <groupId>com.github.jabrena</groupId>
	    <artifactId>ev3dev-lang-java</artifactId>
	    <version>v0.1.0</version>
	</dependency>

```

Further information about the repository usage [here](https://jitpack.io/#jabrena/ev3dev-lang-java).


Create a Java Class in your Maven project:


``` java

package ev3dev.examples;

import ev3dev.hardware.motor.Motor;
import ev3dev.hardware.sensor.ev3.IRSensor;

public class Test {

	public static void main(String[] args) {
		
		Motor mA = new Motor("outA");
		mA.setSpeed(50);
		Motor mB = new Motor("outB");
		mB.setSpeed(50);
		IRSensor ir1 = new IRSensor("in2");

		final int distance_threshold = 35;
		final int iteration_threshold = 100;
		
		for(int i = 0; i <= iteration_threshold; i++) {
			mA.forward();
			mB.forward();
			
			if(ir1.getDistance() <= distance_threshold){
				mA.stop();
				mB.stop();
				break;
			}else {
				System.out.println(ir1.getDistance());
			}
		}

		mA.stop();
		mB.stop();
	}
}

```

To run the example, package your project with Maven to generate a .jar file:

``` bash
mvn package
```

upload your .jar and the library (ev3-lang-java) on your brick. In the path where you have uploaded the jar, execute the following example to run the example:


``` bash
java -cp MyFirstRobot-1.0-SNAPSHOT.jar:ev3-lang-java-0.1-SNAPSHOT.jar ev3dev.java.MyFirstRobot.Test

```

Results:

![ScreenShot](https://raw.githubusercontent.com/jabrena/ev3dev-lang-java/master/docs/MyFirstRobot.png)

This example is included in the folder [examples](https://github.com/jabrena/ev3dev-lang-java/tree/master/examples/java/MyFirstRobot).


## Goals

* Provide a modular set of Java libraries to develop software for robots.
* Reuse LeJOS development on EV3Dev
    * lejos.hardware && lejos.internal == ev3dev.hardware
    * lejos.robotics && lejos.utilities and other robotics libraries == [ev3dev-lang-java-robotics](https://github.com/jabrena/ev3dev-lang-java-robotics)

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
* LeJOS: http://www.lejos.org/
* LeJOS Git: http://sourceforge.net/p/lejos/ev3/code/ci/master/tree/ 
* EV3Dev: http://www.ev3dev.org/
* EV3Dev // Getting Started: http://www.ev3dev.org/docs/getting-started/
* EV3Dev autogen: https://github.com/ev3dev/ev3dev-lang/tree/develop/autogen
* Issue management: https://overv.io
* UML Modelling: http://astah.net/editions/community
