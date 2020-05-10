package ev3dev.hardware.display.spi;

import com.sun.jna.LastErrorException;
import ev3dev.hardware.display.DisplayInterface;
import ev3dev.hardware.display.JavaFramebuffer;
import ev3dev.utils.io.NativeFramebuffer;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ServiceLoader;

/**
 * Framebuffer factory service provider
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public interface FramebufferProvider {

    /**
     * Initialize system framebuffer
     *
     * @param fb Framebuffer device.
     * @return Initialized framebuffer for the specified path.
     * @throws RuntimeException if no suitable framebuffer is found
     */
    static JavaFramebuffer load(@NonNull NativeFramebuffer fb, DisplayInterface disp) throws AllImplFailedException {

        final Logger LOGGER = LoggerFactory.getLogger(FramebufferProvider.class);

        LOGGER.debug("Loading framebuffer");
        ServiceLoader<FramebufferProvider> loader = ServiceLoader.load(FramebufferProvider.class);
        for (FramebufferProvider provider : loader) {
            try {
                JavaFramebuffer ok = provider.createFramebuffer(fb, disp);
                LOGGER.debug("Framebuffer '{}' is compatible", provider.getClass().getSimpleName());
                return ok;
            } catch (IllegalArgumentException ex) {
                LOGGER.debug("Framebuffer '{}' is not compatible", provider.getClass().getSimpleName());
            } catch (LastErrorException e) {
                LOGGER.warn("Framebuffer '{}' threw IOException", provider.getClass().getSimpleName());
                LOGGER.warn("Message: {}", e.getLocalizedMessage());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try (PrintStream chos = new PrintStream(bos)) {
                    e.printStackTrace(chos);
                }
                LOGGER.warn(new String(bos.toByteArray(), StandardCharsets.UTF_8));
            }
        }
        LOGGER.error("All framebuffer implementations failed");
        throw new AllImplFailedException("No suitable framebuffer found");
    }

    /**
     * Create and initialize a new framebuffer.
     *
     * @param fb   The framebuffer device (e.g. /dev/fb0)
     * @param disp Display manager (e.g. /dev/tty)
     * @throws IllegalArgumentException When this framebuffer is not compatible with this device.
     * @throws LastErrorException       When there was an error accessing the device.
     */
    JavaFramebuffer createFramebuffer(@NonNull NativeFramebuffer fb, DisplayInterface disp)
            throws LastErrorException, IllegalArgumentException;

}
