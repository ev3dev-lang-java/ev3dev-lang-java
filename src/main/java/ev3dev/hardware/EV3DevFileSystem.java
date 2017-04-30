package ev3dev.hardware;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * The class responsible to interact with EV3Dev file system
 *
 * @author Juan Antonio Breña Moral
 *
 */
public @Slf4j abstract class EV3DevFileSystem {

    public static final String EV3DEV_TESTING_KEY = "EV3DEV_TESTING_KEY";
    public static final String EV3DEV_ROOT_PATH = "/sys/class";

    @Getter
    protected final String ROOT_PATH;

    protected EV3DevFileSystem(){
        if(Objects.nonNull(System.getProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY))){
            ROOT_PATH = System.getProperty(EV3DEV_TESTING_KEY);
        }else {
            ROOT_PATH = EV3DEV_ROOT_PATH;
        }
    }

}
