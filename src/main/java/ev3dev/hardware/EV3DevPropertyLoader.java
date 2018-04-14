package ev3dev.hardware;

import java.io.IOException;
import java.util.Properties;

public class EV3DevPropertyLoader {

    private static final Properties ev3DevProperties = retrieveEV3DevProperties();

    private static Properties retrieveEV3DevProperties() {

        EV3DevDistro ev3DevDistro = EV3DevDistros.getDistro();

        String propertyName;
        if(ev3DevDistro.equals(EV3DevDistro.STRETCH)) {
            propertyName = "stretch.properties";
        }else {
            propertyName = "jessie.properties";
        }

        try {
            Properties prop = new Properties();
            prop.load(EV3DevPropertyLoader.class.getClassLoader().getResourceAsStream(propertyName));
            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        throw new RuntimeException("");
    }

    public static Properties getEV3DevProperties(){
        return ev3DevProperties;
    }
}
