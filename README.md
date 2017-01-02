# ev3dev-lang-java

*EV3Dev-lang-Java* is a Java library designed to interact with hardware managed by [EV3Dev](http://www.ev3dev.org/) using the [LeJOS](http://www.lejos.org/) way.

ev3dev kernel support: uname -r (4.4.24-16-ev3dev-ev3)

# Features

* Regulated Motor Support
* Unregulated Motor Support
* Sensor Support (EV3 sensors)
* Sounds
* [LeJOS Sensor filter](http://sourceforge.net/p/lejos/wiki/Sensor%20Framework/) Support
* [OpenCV](http://opencv.org/) Computer Vision Support
* [eSpeak](http://espeak.sourceforge.net/) TTS (Text to speech) Support
* Java profiling tools Support ([VisualVM](https://visualvm.java.net/) & [JConsole](http://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html))

# Getting Started.

Check if your EV3Brick with EV3Dev need some upgrade:

``` bash
sudo apt-get update
sudo apt-get upgrade
sudo apt-get dist-upgrade
sudo echo LC_ALL="en_GB.utf8" > /etc/environment 
sudo reboot
```

Current development has been tested with this version:

``` bash
robot@ev3dev:~$ uname -r
4.4.24-16-ev3dev-ev3
```

## References:

* LeJOS: http://www.lejos.org/
* LeJOS Git: http://sourceforge.net/p/lejos/ev3/code/ci/master/tree/ 
* EV3Dev: http://www.ev3dev.org/
* EV3Dev // Getting Started: http://www.ev3dev.org/docs/getting-started/
