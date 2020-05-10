package ev3dev.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Command line wrapper
 *
 * @author Juan Antonio Breña Moral
 */
@Slf4j
public class Shell {

    public static String COMMAND_ERROR_MESSAGE = "COMMAND_ERROR";

    /**
     * Execute a command passed as a parameter
     *
     * @param command Command to execute in Linux
     * @return Result from the command
     */
    public static String execute(final String command) {

        LOGGER.debug("Command: {}", command);
        StringBuilder output = new StringBuilder();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            reader.close();
        } catch (IOException | InterruptedException e) {
            LOGGER.warn(e.getLocalizedMessage(), e);
            return COMMAND_ERROR_MESSAGE;
        }

        return output.toString();
    }

    /**
     * Execute a set of commands passed as a parameter
     *
     * @param command Command to execute in Linux
     * @return Result from the command
     */
    public static String execute(final String[] command) {

        for (String cmd : command) {
            LOGGER.info("Command chunk: {}", cmd);
        }
        StringBuilder output = new StringBuilder();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
        }

        return output.toString();
    }

}
