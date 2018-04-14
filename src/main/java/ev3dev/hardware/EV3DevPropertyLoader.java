package ev3dev.hardware;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EV3DevPropertyLoader {

    public Properties getPropertyFile() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("stretch.properties");

            // load a properties file
            prop.load(input);

            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new RuntimeException("");
    }

}
