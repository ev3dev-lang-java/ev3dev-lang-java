package ev3dev.hardware;

import ev3dev.utils.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EV3DevDistros {

    private static final Logger LOGGER = LoggerFactory.getLogger(EV3DevDistros.class);

    private static final String DEBIAN_DISTRO_DETECTION_QUERY = "cat /etc/os-release";
    private static final String STRETCH_DISTRO_DETECTION_QUERY = DEBIAN_DISTRO_DETECTION_QUERY + " | grep stretch";
    private static final String JESSIE_DISTRO_DETECTION_QUERY = DEBIAN_DISTRO_DETECTION_QUERY + " | grep jessie";

    private static final EV3DevDistro CURRENT_DISTRO = retrieveDistro();

    private static EV3DevDistro retrieveDistro() {

        final String jessieResult = Shell.execute(JESSIE_DISTRO_DETECTION_QUERY);
        if (jessieResult.length() > 0) {
            LOGGER.debug("Debian Jessie detected");
            return EV3DevDistro.JESSIE;
        }

        LOGGER.debug("Debian Stretch detected");
        return EV3DevDistro.STRETCH;
    }

    public static EV3DevDistro getDistro() {
        return CURRENT_DISTRO;
    }

}