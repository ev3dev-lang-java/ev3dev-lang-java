# lejos API changelog

## GraphicsLCD

- Added method drawOval 08/12/2018

## leJOS Sensor API changes (Nov 2015)

In this project, we reimplemented most of the leJOS sensor API.
However, there are some incompatibilities and changes to enhance
performance and decrease the complexity of the sensor code
that break previously established promises:

* Using multiple `SensorMode`s in parallel doesn't work anymore.
  This is because for now, we have removed the kernel mode switching
  from `fetchSample()` of sensor modes. Instead, the mode switch
  is performed in the associated `get*Mode()` functions. This means
  that once you call one `get*Mode()` function, you cannot use
  `SensorMode`s from the other ones. This also means that once you
  reset the `EV3GyroSensor`, you have to call `get*Mode()` once more.

  In the future, if we find a way how to do this efficiently and
  without making it omplex, we might add support for this
  feature again. However, we might also keep it the way it is now,
  as this establishes a different promise - `fetchSample` only fetches
  data, and it doesn't touch the sensor mode.

* Most of the `BaseSensor` functions dealing with sensor modes
  are effectively broken now. This is caused by the previous point -
  sensor mode is not switched when using these functions.

* Waiting for multiple values does not work. We do not know how to
  wait for a new value in ev3dev. This means that IR sensor function
  reading multiple commands will read only one, otherwise it will
  throw an exception.

* Motor synchronization is not yet implemented.
