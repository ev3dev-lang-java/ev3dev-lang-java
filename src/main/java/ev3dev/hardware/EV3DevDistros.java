package ev3dev.hardware;

import ev3dev.utils.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EV3DevDistros {

    private static final Logger LOGGER = LoggerFactory.getLogger(EV3DevDistros.class);

    private static final String DEBIAN_DISTRO_DETECTION_QUERY = "cat /etc/os-release";
    private static final String STRETCH_DISTRO_DETECTION_QUERY = DEBIAN_DISTRO_DETECTION_QUERY + " | grep stretch | wc -l";
    private static final String JESSIE_DISTRO_DETECTION_QUERY = DEBIAN_DISTRO_DETECTION_QUERY + " | grep jessie | wc -l";

    private static final EV3DevDistro CURRENT_DISTRO = retrieveDistro();

    private static EV3DevDistro retrieveDistro() {

        //Testing purposes
        if (!EV3DevFileSystem2.getRootPath().equals(EV3DevFileSystem2.EV3DEV_ROOT_PATH)) {
            return EV3DevDistro.STRETCH;
        }

        final String stretchResult = Shell.execute(STRETCH_DISTRO_DETECTION_QUERY);
        if (stretchResult.length() > 0) {
            return EV3DevDistro.STRETCH;
        } else {
            final String jessieResult = Shell.execute(JESSIE_DISTRO_DETECTION_QUERY);
            if (jessieResult.length() > 0) {
                return EV3DevDistro.JESSIE;
            }
        }
        LOGGER.error(Shell.execute(DEBIAN_DISTRO_DETECTION_QUERY));
        throw new RuntimeException("Not supported Distro");
    }

    private static EV3DevDistro getDistro() {
        return CURRENT_DISTRO;
    }

}
