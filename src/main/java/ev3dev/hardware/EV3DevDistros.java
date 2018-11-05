package ev3dev.hardware;

import ev3dev.utils.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class EV3DevDistros {

    private static final Logger LOGGER = LoggerFactory.getLogger(EV3DevDistros.class);

    private static EV3DevDistros instance;

    private static final String DEBIAN_DISTRO_DETECTION_QUERY = "cat /etc/os-release";
    private static final String JESSIE_DISTRO_DETECTION_PATTERN = "ev3dev-jessie";
    private static final String STRETCH_DISTRO_DETECTION_PATTERN = "ev3dev-stretch";
    private static final String DEBIAN_DISTRO_DETECTION_KEY = "EV3DEV_DISTRO";
    private static final String DEBIAN_DISTRO_DETECTION_JESSIE = "jessie";
    private static final String DEBIAN_DISTRO_DETECTION_STRETCH = "stretch";

    private EV3DevDistro CURRENT_DISTRO;

    /**
     * Return a Instance of EV3DevDistros.
     *
     * @return A EV3DevDistros instance
     */
    public static EV3DevDistros getInstance() {

        if (Objects.isNull(instance)) {
            instance = new EV3DevDistros();
        }
        return instance;
    }

    private EV3DevDistros() {

        LOGGER.info("Providing an EV3DevDistros instance");

        final String osResult = Shell.execute(DEBIAN_DISTRO_DETECTION_QUERY);
        if (osResult.contains(DEBIAN_DISTRO_DETECTION_JESSIE)) {
            setJessie();
        } else if (osResult.contains(DEBIAN_DISTRO_DETECTION_STRETCH)) {
            setStretch();
        } else {
            //TODO Improve this flow
            LOGGER.warn("Failed to detect distro, falling back to Stretch.");
            setStretch();
        }
    }

    private void setStretch() {
        LOGGER.debug("Debian Stretch detected");
        CURRENT_DISTRO = EV3DevDistro.STRETCH;
    }

    private void setJessie() {
        LOGGER.debug("Debian Jessie detected");
        CURRENT_DISTRO = EV3DevDistro.JESSIE;
    }

    public EV3DevDistro getDistro() {
        return CURRENT_DISTRO;
    }

}