# Logging Support (Nov 2015)

We recommend to move away from using `System.out.println("Your message")`
because using `stdout` is not the same as using `stderr` or a logging interface.

The reason you shouldn't use `System.out` is that we depend on output redirection
provided by ev3dev's brickrun. `stderr` is redirected via SSH to the user
when running remotely. If you use `System.out`, the message will be
displayed on the brick display only. On the other hand, this might
be intentional, in which case you should use `System.out`.

Also, to align with the best practises in the software industry, we
integrated support for SLF4J (Simple Logging Facade for Java).

The Simple Logging Facade for Java (SLF4J) serves as
a simple facade or abstraction for various logging frameworks
(e.g. java.util.logging, logback, log4j) allowing
the end user to plug in the desired logging framework at deployment time.

The library uses SLF4J in the whole project, but the user has to
choose the final implementation. In the example, the development uses
Logback but users can choose any other compatible logging framework.

For testing purposes, enabling traces is a good practice, but for
production builds you might want to disable some levels.
Generating too many tracing output creates a measurable impact on the program performance.

## Links

https://javarevisited.blogspot.com/2016/06/why-use-log4j-logging-vs.html
https://www.slf4j.org/
https://logback.qos.ch/
