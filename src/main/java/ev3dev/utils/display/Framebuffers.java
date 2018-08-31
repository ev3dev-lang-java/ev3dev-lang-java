package ev3dev.utils.display;

import ev3dev.actuators.LCD;
import ev3dev.utils.display.spi.FramebufferProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

public final class Framebuffers {
    // logger
    private static final Logger log = LoggerFactory.getLogger(LCD.class);

    /**
     * Initialize system framebuffer
     *
     * @param path Path to the framebuffer.
     * @return Initialized framebuffer for the specified path.
     * @throws RuntimeException if no suitable framebuffer is found
     */
    public static JavaFramebuffer load(String path) {
        ServiceLoader<FramebufferProvider> loader = ServiceLoader.load(FramebufferProvider.class);
        for (FramebufferProvider provider : loader) {
            try {
                JavaFramebuffer ok = provider.createFramebuffer(path);
                log.info("Framebuffer '{}' is compatible", provider.getClass().getSimpleName());
                return ok;
            } catch (IllegalArgumentException ex) {
                log.info("Framebuffer '{}' is not compatible", provider.getClass().getSimpleName());
            }
        }
        throw new RuntimeException("No suitable framebuffer found");
    }
}
