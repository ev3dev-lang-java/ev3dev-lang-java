package fake_ev3dev.ev3dev.utils.io;

/**
 * Counter of some event
 *
 * @author Jakub Vaněk
 * @since 2.4.7
 */
public interface ICounter extends IFile {
    /**
     * Reset event count.
     */
    void resetCount();
}
