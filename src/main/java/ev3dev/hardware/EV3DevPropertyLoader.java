package ev3dev.hardware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class EV3DevPropertyLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(EV3DevPropertyLoader.class);

    private final Properties ev3DevProperties;

    private static final String STRETCH_PROPERTY_FILENAME = "stretch.properties";
    private static final String JESSIE_PROPERTY_FILENAME = "jessie.properties";

    /**
     * Constructor
     */
    public EV3DevPropertyLoader() {

        String propertyName;
        if (EV3DevDistros.getInstance().getDistro().equals(EV3DevDistro.STRETCH)) {
            propertyName = STRETCH_PROPERTY_FILENAME;
        } else {
            propertyName = JESSIE_PROPERTY_FILENAME;
        }

        try {
            Properties prop = new Properties();
            prop.load(EV3DevPropertyLoader.class.getClassLoader().getResourceAsStream(propertyName));
            ev3DevProperties = prop;
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * Return the properties
     * @return Properties
     */
    public Properties getEV3DevProperties() {
        return ev3DevProperties;
    }
}
