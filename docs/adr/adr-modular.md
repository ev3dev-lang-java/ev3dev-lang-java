# Modular design (Nov 2015)

The project has been designed like a set of libraries stackables 
to offer different solutions for different use cases with the Brick.

## Libraries

**Core**

The main library offers a Integration solution with EV3Dev.
Using this library, it is possible to interact with
Sensors & Actuators from Lego Mindstorms & third party manufacturers
like MindSensors or Dexter Industries.

This library depends of `lejos-commons` & `jna`

**LeJOS // Commons**

`lejos-commons` manage a set of Interfaces in order to maintain the 
same way to develop than LeJOS but in this case using the `EV3Dev` runtime.


**LeJOS // Navigation**

`lejos-navigation` is a library to use the local navigation stack from LeJOS.

**USB-devices**

This library has been designed to manage different usb devices like
Arduino boards, LIDARs, IMUs, Microphones, GPS

