package ev3dev.hardware;

import org.slf4j.Logger;

import java.util.Objects;

/**
 * The class responsible to interact with EV3Dev file system
 *
 * @author Juan Antonio Breña Moral
 *
 */
public abstract class EV3DevFileSystem {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EV3DevFileSystem.class);

    public static final String EV3DEV_TESTING_KEY = "EV3DEV_TESTING_KEY";
    public static final String EV3DEV_ROOT_PATH = "/sys/class";

    protected final String ROOT_PATH;

    protected EV3DevFileSystem(){
        if(Objects.nonNull(System.getProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY))){
            ROOT_PATH = System.getProperty(EV3DEV_TESTING_KEY);
            LOGGER.debug("ROOT_PATH modified: {}", ROOT_PATH);
        }else {
            ROOT_PATH = EV3DEV_ROOT_PATH;
        }
        LOGGER.debug("Root Path: {}", ROOT_PATH);
    }

    protected String getROOT_PATH() {
        return ROOT_PATH;
    }
}
