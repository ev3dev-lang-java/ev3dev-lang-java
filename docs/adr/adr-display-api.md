# ev3dev Display API (October 2018)

## Reason

In Linux 4.14 for EV3, there was a change in the LCD framebuffer code
which required a redesign of our display architecture. The change was
that kernel no longer accepted 1bpp black-and-white bitmap. Now a
32bpp XRGB bitmap was required. To allow for simultaneous support of
these two formats and also to address a few other issues, a new
display API was designed.

## API Design

The code has roughly these interfaces:
* leJOS LCD emulation in `LCD` - This reimplements some parts of leJOS
  `GraphicsLCD` API on top of Java2D Graphics2D. The `BufferedImage` is
  obtained from and the refresh is forwarded to `JavaFramebuffer`.
  The `JavaFramebuffer` itself is obtained from `SystemDisplay`.
* Glue in `SystemDisplay` - This provides a factory for a system
  display manager - either a native one or a remote one, and
  a JavaFramebuffer above the display manager.
* `JavaFramebuffer` interface - This interface abstracts away
  a framebuffer. The interface provides:
  * basic information about the display geometry,
  * methods for creating offscreen `BufferedImage` for drawing,
  * writing full-size offscreen buffers to the display,
  * clearing the display,
  * saving and restoring a single snapshot,
  * getter for the underlying `DisplayInterface`
* `DisplayInterface` interface - This class manages the underlying
  display output. It provides:
  * switching between text mode (Linux console is displayed) and
    graphics mode (user graphics is displayed),
  * framebuffer creation - this class creates the appropriate
    `JavaFramebuffer` implementation for the system display.
  * framebuffer lifetime management - this class stores the opened framebuffer.
* `FramebufferProvider` interface with `AllImplFailedException` -
  These classes provide Java SPI for framebuffer formats.
  The `DisplayInterface` uses this SPI to instantiate the correct
  `JavaFramebuffer` implementation for the system framebuffer.

Then, we have the following implementations:
* `ImageUtils` class - This class implements BufferedImage creation
  for 32bpp XRGB and 1bpp BW framebuffers.
* `LinuxFramebuffer` class - This class implements `JavaFramebuffer`
  for native Linux framebuffer device.
* `BitFramebuffer`, `RGBFramebuffer` - These classes specialize
  `LinuxFramebuffer` for 1bpp BW and 32bpp XRGB formats.
* `BitFramebufferProvider`, `RGBFramebufferProvider` - These classes wrap
  `BitFramebuffer` and `RGBFramebuffer` for usage in `FramebufferProvider`.
* `OwnedDisplay` class - This class implements `DisplayInterface`
  for standard Linux console. The Java process must have access to
  the system console.
* `StolenDisplay` class - This class implements `DisplayInterface` when
  the standard Linux console is unavailable. It ignores text/graphics mode
  switching. However it disables brickman process when instantiated.

Under all of this is an extension of leJOS Native* APIs:
* `NativeFile` class - this class wraps ILibc file system calls.
* `NativeDevice` class - this class wraps ILibc system calls
  for Linux devices.
* `NativeFramebuffer` class - this class wraps ILibc system calls
  for Linux framebuffer devices.
* `NativeTTY` class - this class wraps ILibc system calls
  for Linux console devices.
* `NativeConstants` class - contains basic C macros, like errno values and
  ioctl arguments.
* `ILibc` interface - abstraction of basic system calls.
  This is useful for testing the interface code.
* `NativeLibc` interface - class which is used for JNA binding to system libc.

`ILibc` interface had been mocked for purposes of unit testing:
* `EmulatedLibc` - class which provides a virtual file system to which
  fake files and devices can be mounted.
* `IFile` - interface for fake files,
* `ICounter` - interface for event counters,
* `CountingFile` - this class wraps another IFile and provides
  counting of system call invocations,
* `EmulatedFramebuffer` - this class provides a mocked version of system
  framebuffer device.
* `EmulatedFramebufferBuilder` - this class helps with `EmulatedFramebuffer`
  construction.

This design makes it possible to implement new framebuffer formats
(through subclassing JavaFramebuffer and FramebufferProvider) and
new output devices (through subclassing DisplayInterface and modifying
SystemDisplay). It also allows for unit testing the interfacing code
through abstracted system call interface.

For example, it should be possible to relatively easily implement a Swing-backed
display output. This feature might actually be implemented in a future
version of the display code.

## Running programs

There is also a change in how the programs have to be launched. Until now,
simply running the JAR on the console was sufficient. However this wasn't
compliant with how ev3dev handles console (the old method is still possible
through current StolenDisplay).

For this reason, we introduced the recommended usage of `brickrun`
to our project too. This program allocates a virtual terminal
for the application and suspends Brickman. Then, it launches there
the application it was given in its arguments.

When you run the application from Brickman menu, using brickrun is not
necessary, because Brickman automatically suspends itself.
