package ev3dev.hardware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class EV3DevPropertyLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(EV3DevPropertyLoader.class);

    private static final Properties ev3DevProperties = retrieveEV3DevProperties();

    private static final String STRETCH_PROPERTY_FILENAME = "stretch.properties";
    private static final String JESSIE_PROPERTY_FILENAME = "jessie.properties";

    private static Properties retrieveEV3DevProperties() {

        EV3DevDistro ev3DevDistro = EV3DevDistros.getDistro();

        String propertyName;
        if(ev3DevDistro.equals(EV3DevDistro.STRETCH)) {
            propertyName = STRETCH_PROPERTY_FILENAME;
        }else {
            propertyName = JESSIE_PROPERTY_FILENAME;
        }

        try {
            Properties prop = new Properties();
            prop.load(EV3DevPropertyLoader.class.getClassLoader().getResourceAsStream(propertyName));
            return prop;
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    public static Properties getEV3DevProperties(){
        return ev3DevProperties;
    }
}
