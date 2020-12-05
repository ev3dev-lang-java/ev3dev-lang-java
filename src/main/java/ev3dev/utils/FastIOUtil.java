package ev3dev.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

/**
 * Helper class for IO and string coding.
 */
public class FastIOUtil {
    private static final FastIOUtil instance = new FastIOUtil(StandardCharsets.ISO_8859_1);
    private final CharsetDecoder stringDecoder;
    private final CharsetEncoder stringEncoder;

    /**
     * Initialize a new instance of IO helper.
     *
     * @param cs Charset to use for decoding. <b>WARNING: the encoding must be single-byte.</b>
     */
    public FastIOUtil(Charset cs) {
        this.stringEncoder = cs.newEncoder();
        this.stringDecoder = cs.newDecoder();
        this.stringEncoder.onMalformedInput(CodingErrorAction.REPLACE);
        this.stringDecoder.onMalformedInput(CodingErrorAction.REPLACE);
        this.stringEncoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
        this.stringDecoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
    }

    /**
     * Get a new thread-local instance of fast string converter.
     *
     * @return String converter local to this thread. Do not store outside of methods!
     */
    public static FastIOUtil getInstance() {
        return instance;
    }

    /**
     * Optimized string encoding routine.
     *
     * @param value String for encoding.
     * @return Temporary ByteBuffer ready for reading.
     */
    public synchronized ByteBuffer encodeString(String value) {
        // wrap source buffer (required by CharsetEncoder)
        CharBuffer chars = CharBuffer.wrap(value);

        // destination buffer needs to be large enough
        // note: this is single-byte encoding, so we can equate bytes to chars
        ByteBuffer bytes = ByteBuffer.allocate(value.length());

        // perform encoding process
        // no error handling: malformed input is replaced, underflow is OK, overflow shouldn't happen (see above)
        stringEncoder.reset();
        stringEncoder.encode(chars, bytes, true);
        stringEncoder.flush(bytes);
        bytes.flip();
        return bytes;
    }

    /**
     * Optimized string decoding routine.
     *
     * @param bytes Input buffer with character data from ev3dev sysfs.
     * @return Decoded string.
     */
    public synchronized String decodeString(final ByteBuffer bytes) {
        // set correct limit (strip '\n')
        bytes.limit(scanForEnd(bytes));

        // destination buffer needs to be large enough
        // note: this is single-byte encoding, so we can equate bytes to chars
        CharBuffer chars = CharBuffer.allocate(bytes.remaining());

        stringDecoder.reset();
        stringDecoder.decode(bytes, chars, true);
        stringDecoder.flush(chars);
        chars.flip();
        return chars.toString();
    }

    /**
     * Find a new buffer limit for a sysfs-originated char string.
     *
     * <p>ev3dev sysfs strings end with '\n' and that would interfere
     * with integer parsing. This method tries to find one of the following: </p>
     * <ul>
     *     <li>End of buffer, in which case the current limit is returned.</li>
     *     <li>Null termination/0x00 byte, in which case the index of this byte is returned.</li>
     *     <li>Newline/0x0A byte, in which case the index of this byte is returned.</li>
     * </ul>
     *
     * @param in ByteBuffer to scan.
     * @return New limit for the buffer.
     */
    private int scanForEnd(final ByteBuffer in) {
        int pos = in.position();

        if (in.hasArray()) {
            // we can access the array directly :)
            byte[] array = in.array();
            for (; pos < in.limit(); pos++) {
                if (array[pos] == 0x0A || array[pos] == 0x00) {
                    return pos;
                }
            }

        } else {
            // we have to use a function call :(
            for (; pos < in.limit(); pos++) {
                byte b = in.get(pos);
                if (b == 0x0A || b == 0x00) {
                    return pos;
                }
            }
        }
        return in.limit();
    }
}
