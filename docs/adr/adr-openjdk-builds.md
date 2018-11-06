# Support for newer Java versions (April 2018)

In December 2017, we noticed that Oracle had released their HotSpot port
for ARM32. Until that moment, there were only a few options of JVMs
which would run on the EV3:
* Debian-provided Zero build, which is a lot slower than HotSpot JIT.
* Oracle-provided EJDK7 build, which was used in leJOS.
* Oracle-provided EJDK8 build, which was used in leJOS as well.
* Some alternative JVMs, but we haven't finished investigating these options.

The problem with EJDK8 from Oracle was that it was the last supported
Java release for EV3. At that time, Java 9 was already available and
Java 10 was coming as well.

So, now that we knew OpenJDK 9 had the necessary bits from EJDK8, we
tried to create a build of OpenJDK for EV3. This resulted in creation
of [ev3dev-lang-java/openjdk-ev3][1] repository.
Over time, we adopted Docker for the builds. The next big thing was
that after some communication, [AdoptOpenJDK][2] Group
allowed us to use their [CI infrastructure][2] to do our builds. From that time,
instead of doing the releases on developer's computers, they are performed
on their Linux Jenkins slaves.

To make OpenJDK work on EV3, we maintain a set of patches on top of
OpenJDK tree. They do these things:
* ARMv5 support - there are some memory barriers that work only on ARMv6+. They are disabled on ARMv5 now.
* Additional optimizations - GCC flags `-mcpu` is used to compile for the CPU in EV3.
* SoftFloat support - previously, a SoftFloat-2 library was injected into `arm-sflt` builds
  to provide floating point emulation with higher precision for some code paths. This patch
  adds support for a modified version of SoftFloat-3e to be used.
* Debian library path - this patch adds support for Debian-style `/usr/lib/jni` path and others.

Also, with the new features in Java 9 and onwards, we changed Java packaging:
* JRI - Java Runtime Image - provides very small runtime environment suitable for ev3dev.
* JDK - Java Development Kit - provides full Java JDK build for EV3.
* JMODs - Java MODules - with these a curious user can build larger
  subsets of full Java than our JRI is.

There is a small problem with using Java HotSpot, though. ARMv5/softfp
is no longer a supported configuration. We depend on that Java developers
do not break or remove the support for this platform in future releases.
If such a thing happens, we will have to either use the Zero JVM, or to
use an alternative JVM altogether.

[1]: https://github.com/ev3dev-lang-java/openjdk-ev3
[2]: https://adoptopenjdk.net/
[3]: https://ci.adoptopenjdk.net/
