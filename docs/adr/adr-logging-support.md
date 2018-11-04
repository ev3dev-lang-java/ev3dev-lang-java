# Logging Support

The library move away from the idea to use `System.out.println("Your message")`
because it is a bad practice. You can search on the network many articles about 
this fact. 

The library follow some Best practices in the software industry and use
the dependency SLF4J (Simple Logging Facade for Java).

The Simple Logging Facade for Java (SLF4J) serves as 
a simple facade or abstraction for various logging frameworks 
(e.g. java.util.logging, logback, log4j) allowing 
the end user to plug in the desired logging framework at deployment time. 

The library uses SLF4J in the whole project and later the user has to
choose the final implementation. In the example, the development use 
Logback but any user could use any logging framework.

For testing purposes, enable traces is a good practice but for 
production projects try to disable some levels. 
Generate many traces in your logs impact in your performance.

## Links

https://javarevisited.blogspot.com/2016/06/why-use-log4j-logging-vs.html
https://www.slf4j.org/
https://logback.qos.ch/