# Implement LeJOS API on top of EV3Dev (Nov 2015)

EV3 Brick is the first Lego Mindstorms Brick with capacity to Linux. 

LeJOS team designed a solution on top of Busybox a non complete Linux solution.
this architecture don't have USB support to connect devices like a LIDAR or an Arduino Board.

With the time, the number of developers decreased and the project launched few releases.

In 2015 appeared a project named EV3Dev, a OSS project designed to provide a 
Debian distribution for EV3 and RaspberryPi boards like BrickPi or PiStorms. 
The project included an interface to operate with EV3 sensors/actuators and I2C devices.

The main reasons to implement LeJOS Interfaces on top of EV3Dev were:

- Lack of LeJOS releases. Last release was launched on November of 2015
- Lack of a complete Linux distro support
- Lack of USB support
- Lack of support for modern JVM 

That reasons motivated the creation of this project.
