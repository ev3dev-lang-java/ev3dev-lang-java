package ev3dev.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * Fast string/integer conversion routines for Sysfs.
 *
 * @author Jakub Vanek
 */
class FastCvt {
    /**
     * Quickly decode an integer from an ASCII ByteBuffer.
     *
     * @param buffer Buffer to parse the integer from (will not be updated),
     * @return Decoded integer.
     */
    public static int parseInt(ByteBuffer buffer) {
        if (!buffer.hasArray()) {
            throw new IllegalStateException("cannot parse direct buffers");
        }

        int result = 0;
        boolean negative = false;
        byte[] arr = buffer.array();

        int i = buffer.position();
        if (arr[i] == 0x2D /* - */) {
            negative = true;
            i++;
        }
        for (; i < buffer.limit(); i++) {
            if (arr[i] < 0x30 || arr[i] > 0x39) {
                break;
            }
            result = (result * 10) + (arr[i] & 0x0F);
        }
        if (negative) {
            return -result;
        } else {
            return result;
        }
    }

    /**
     * Quickly encode an integer to an ASCII ByteBuffer.
     *
     * @param value  Integer to encode.
     * @param buffer Destination buffer (integer will be written at its end).
     */
    public static ByteBuffer serializeInt(int value, ByteBuffer buffer) {
        if (!buffer.hasArray()) {
            throw new IllegalStateException("cannot parse direct buffers");
        }

        buffer.limit(buffer.capacity());
        byte[] arr = buffer.array();

        int remainder = value;
        if (remainder < 0) {
            remainder = -remainder;
        }

        int i = arr.length - 1;
        if (remainder == 0) {
            arr[i] = '0';
            i--;
        } else {
            for (; remainder != 0; i--) {
                arr[i] = (byte) ((remainder % 10) + 0x30 /* 0 */);
                remainder /= 10;
            }
        }

        if (value < 0) {
            arr[i--] = '-';
        }

        buffer.position(i + 1);
        return buffer;
    }

    /**
     * Quickly decode a String from an ASCII ByteBuffer.
     *
     * @param buffer    Source buffer (correct position/limit is required)
     * @param tmp       Temporary character buffer for conversion output.
     * @param stringDec Cached string decoder.
     * @return Decoded string.
     */
    public static String parseString(ByteBuffer buffer, CharBuffer tmp, CharsetDecoder stringDec) {
        tmp.clear();
        stringDec.reset();

        CoderResult res = stringDec.decode(buffer, tmp, true);
        if (res.isOverflow()) {
            throw new ArrayIndexOutOfBoundsException("string too long to decode");
        }

        res = stringDec.flush(tmp);
        if (res.isOverflow()) {
            throw new ArrayIndexOutOfBoundsException("string too long to encode");
        }

        int pos = tmp.position();
        if (pos > 0 && tmp.get(pos - 1) == '\n') {
            tmp.position(pos - 1);
        }

        tmp.flip();
        return tmp.toString();
    }

    /**
     * Quickly encode a String from an ASCII ByteByffer.
     *
     * @param value     String to encode.
     * @param buffer    Buffer to decode.
     * @param stringEnc Cached string encoder.
     */
    public static ByteBuffer serializeString(String value, ByteBuffer buffer, CharsetEncoder stringEnc) {
        buffer.clear();
        stringEnc.reset();

        CoderResult res = stringEnc.encode(CharBuffer.wrap(value), buffer, true);
        if (res.isOverflow()) {
            throw new ArrayIndexOutOfBoundsException("string too long to encode");
        }

        res = stringEnc.flush(buffer);
        if (res.isOverflow()) {
            throw new ArrayIndexOutOfBoundsException("string too long to encode");
        }

        buffer.flip();
        return buffer;
    }
}
