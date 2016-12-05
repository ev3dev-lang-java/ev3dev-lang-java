package examples.hardware;

import ev3dev.hardware.Platform;

//gradle clean build
//java -cp ./build/libs/ev3-lang-java-0.3.0-SNAPSHOT.jar PlatformTest
public class PlatformTest {

	public static void main(String[] args) {
		Platform platform = new Platform();
		String value = platform.getPlatform();
		System.out.println(value);
		System.exit(0);
	}

}
