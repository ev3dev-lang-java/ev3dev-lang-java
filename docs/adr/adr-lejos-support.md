# Implement LeJOS API on top of EV3Dev (Nov 2015)

EV3 Brick is the first Lego Mindstorms Brick to run a Linux-based operating system.

LeJOS team designed a solution on top of a LEGO-provided Linux userspace &
Linux kernel with modules also provided by LEGO, although enhanced by leJOS hackers.
This architecture doesn't have proper USB support, so devices like USB LIDAR or an Arduino Board do not work.
Also, LEGO didn't provide any package manager, so the core system is effectively stuck in time.

As the time passed, the number of developers decreased and the project development slowed down.

In 2015 a project named EV3Dev was launched, am OSS project designed to provide a
Debian distribution for EV3 and RaspberryPi boards like BrickPi or PiStorms.
The project doesn't use the userspace nor the kernel modules from LEGO.
Rather, the sensor and actuator interfaces were reimplemented to provide
a better and easier-to-understand interface. This includes EV3 sensors
and also various other sensors.

The main reasons to implement LeJOS Interfaces on top of EV3Dev were:

- Lack of LeJOS releases. Last release was launched in November of 2015
- Lack of a complete Linux distro support,
- Lack of USB support
- Lack of support for modern JVM

That reasons motivated the creation of this project.
