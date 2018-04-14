package ev3dev.hardware;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EV3DevPropertyLoader {

    private static Properties EV3DevProperties = retrieveEV3DevProperties();

    private static Properties retrieveEV3DevProperties() {

        EV3DevDistro ev3DevDistro = EV3DevDistros.getDistro();

        String propertyName;
        if(ev3DevDistro.equals(EV3DevDistro.STRETCH)) {
            propertyName = "stretch.properties";
        }else {
            propertyName = "jessie.properties";
        }

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(propertyName);
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

    public static Properties getEV3DevProperties(){
        return EV3DevProperties;
    }
}
