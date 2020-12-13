package ev3dev.utils;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FastCvtTest {
    @Test
    public void integerDecoder() {
        Assert.assertEquals(-1,
            FastCvt.parseInt(ByteBuffer.wrap(new byte[]{'-', '1'})));
        Assert.assertEquals(-1000,
            FastCvt.parseInt(ByteBuffer.wrap(new byte[]{'-', '1', '0', '0', '0'})));
        Assert.assertEquals(1,
            FastCvt.parseInt(ByteBuffer.wrap(new byte[]{'1', '\n', '0'})));
        Assert.assertEquals(1000,
            FastCvt.parseInt(ByteBuffer.wrap(new byte[]{'1', '0', '0', '0', '.', '9'})));
        Assert.assertEquals(0,
            FastCvt.parseInt(ByteBuffer.wrap(new byte[]{'h', 'e', 'l', 'l', 'o'})));
    }

    @Test(expected = IllegalStateException.class)
    public void integerDecoderRefusesDirectBuffer() {
        FastCvt.parseInt(ByteBuffer.allocateDirect(1));
    }

    @Test
    public void integerEncoder() {
        var buffer = ByteBuffer.allocate(4);

        Arrays.fill(buffer.array(), (byte) 0);
        Assert.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, '0'},
            FastCvt.serializeInt(0, buffer).array());

        Arrays.fill(buffer.array(), (byte) 0);
        Assert.assertArrayEquals(new byte[]{0x00, 0x00, '-', '1'},
            FastCvt.serializeInt(-1, buffer).array());

        Arrays.fill(buffer.array(), (byte) 0);
        Assert.assertArrayEquals(new byte[]{0x00, 0x00, '1', '0'},
            FastCvt.serializeInt(10, buffer).array());
    }

    @Test(expected = IllegalStateException.class)
    public void integerEncoderRefusesDirectBuffer() {
        FastCvt.serializeInt(1, ByteBuffer.allocateDirect(1));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void integerEncoderOverflow() {
        var buffer = ByteBuffer.allocate(4);
        FastCvt.serializeInt(10000, buffer);
    }

    @Test
    public void stringEncoder() {
        var buffer = ByteBuffer.allocate(4);
        CharsetEncoder enc = StandardCharsets.UTF_8.newEncoder();

        Arrays.fill(buffer.array(), (byte) 0);
        Assert.assertArrayEquals(new byte[]{'a', 'b', 'c', 0x00},
            FastCvt.serializeString("abc", buffer, enc).array());

        Arrays.fill(buffer.array(), (byte) 0);
        Assert.assertArrayEquals(new byte[]{'a', 'b', 'c', '\n'},
            FastCvt.serializeString("abc\n", buffer, enc).array());

        Arrays.fill(buffer.array(), (byte) 0);
        Assert.assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x00},
            FastCvt.serializeString("", buffer, enc).array());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void stringEncoderOverflow() {
        var buffer = ByteBuffer.allocate(4);
        CharsetEncoder enc = StandardCharsets.UTF_8.newEncoder();
        FastCvt.serializeString("abcde", buffer, enc);
    }

    @Test
    public void stringDecoder() {
        CharBuffer buf = CharBuffer.allocate(4);
        CharsetDecoder dec = StandardCharsets.UTF_8.newDecoder();

        Assert.assertEquals("abc",
            FastCvt.parseString(ByteBuffer.wrap(new byte[]{'a', 'b', 'c'}), buf, dec));
        Assert.assertEquals("abc",
            FastCvt.parseString(ByteBuffer.wrap(new byte[]{'a', 'b', 'c', '\n'}), buf, dec));
        Assert.assertEquals("",
            FastCvt.parseString(ByteBuffer.wrap(new byte[]{}), buf, dec));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void stringDecoderOverflow() {
        CharBuffer buf = CharBuffer.allocate(4);
        CharsetDecoder dec = StandardCharsets.UTF_8.newDecoder();

        FastCvt.parseString(ByteBuffer.wrap(new byte[]{'a', 'b', 'c', 'd', 'e'}), buf, dec);
    }
}
