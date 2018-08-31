package ev3dev.hardware.display;

import ev3dev.actuators.LCD;
import ev3dev.hardware.display.spi.FramebufferProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
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
            } catch (IOError e) {
                log.warn("Framebuffer '{}' threw IOError", provider.getClass().getSimpleName());
                log.warn("Message: {}", e.getLocalizedMessage());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try (PrintStream chos = new PrintStream(bos)) {
                    e.printStackTrace(chos);
                }
                log.warn(new String(bos.toByteArray(), StandardCharsets.UTF_8));
            }
        }
        throw new NoAvailableImplementationException("No suitable framebuffer found");
    }

    public static class NoAvailableImplementationException extends RuntimeException {
        public NoAvailableImplementationException() {
            super();
        }

        public NoAvailableImplementationException(String message) {
            super(message);
        }

        public NoAvailableImplementationException(Throwable cause) {
            super(cause);
        }

        public NoAvailableImplementationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
