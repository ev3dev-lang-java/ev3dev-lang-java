package ev3dev.hardware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EV3DevDistros {

    private static final Logger LOGGER = LoggerFactory.getLogger(EV3DevDistros.class);

    private static final String DEBIAN_DISTRO_DETECTION_QUERY = "cat /etc/os-release";
    private static final String JESSIE_DISTRO_DETECTION_PATTERN = "ev3dev-jessie";
    private static final String STRETCH_DISTRO_DETECTION_PATTERN = "ev3dev-stretch";
    private static final String DEBIAN_DISTRO_DETECTION_KEY = "EV3DEV_DISTRO";
    private static final String DEBIAN_DISTRO_DETECTION_JESSIE = "jessie";
    private static final String DEBIAN_DISTRO_DETECTION_STRETCH = "stretch";

    private EV3DevDistro CURRENT_DISTRO;

    public EV3DevDistros() {

        setStretch();
    }

    private void setStretch() {
        LOGGER.debug("Debian Stretch detected");
        CURRENT_DISTRO = EV3DevDistro.STRETCH;
    }

    public EV3DevDistro getDistro() {
        return CURRENT_DISTRO;
    }

}