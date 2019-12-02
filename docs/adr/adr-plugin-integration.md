# Gradle-plugin integration (Dec 2019)

This ADR is about the ev3dev-lang-java's gradle plugin:
https://github.com/ev3dev-lang-java/gradle-plugin

## Dependency uploading

This plugin provides functionality for uploading the program's JAR dependencies
to the on-brick library directory. The purpose of this is to make first-time
setup easier while still using standard JARs (i.e. non-fat jar that does
not contain it's dependencies inside).

To achieve this, the Gradle plugin integrates with the dependency
system present in Gradle. It does not have an effect on dependency
resolution. Rather, it is a consumer of the runtimeClasspath API provided
by Gradle's Java plugin that collects the JAR dependencies that are
needed at runtime. The libraries that are returned by Gradle are
then uploaded from the local Maven repository to the brick.

For this reason, it is necessary to use [`implementation`][impl] or [`api`][api]
dependency configuration in `build.gradle` for runtime libraries.
While using `compileOnly` would not break the plugin, but it would
prevent it from seeing these dependencies (and their transitive
dependencies) and uploading them.

Lombok is an exception here, because its classes are not used at runtime,
they are used only during `javac` compilation for annotation processing.
It can therefore be included using just `compileOnly`

Now one has to choose between `api` and `implementation`.
For this, take a look at a take of `java-library` authors on this [here][api_vs_impl].


[impl]: https://docs.gradle.org/current/userguide/java_plugin.html#tab:configurations
[api]: https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_separation
[api_vs_impl]: https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_recognizing_dependencies
