package ev3dev.hardware.display;

import fake_ev3dev.ev3dev.utils.io.CountingFile;
import fake_ev3dev.ev3dev.utils.io.EmulatedFramebuffer;
import fake_ev3dev.ev3dev.utils.io.EmulatedLibc;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class BaseFramebufferTest {
    protected EmulatedLibc eLibc;
    protected CountingFile eCtr;
    protected EmulatedFramebuffer eFb;

    public void TestAdapter(JavaFramebuffer adapter, int width, int height, int stride) throws IOException {
        Assert.assertEquals(adapter.getWidth(), width);
        Assert.assertEquals(adapter.getHeight(), height);
        Assert.assertEquals(adapter.getStride(), stride);

        adapter.close();

        Assert.assertEquals(eCtr.getCountOpen(), 1);
        Assert.assertEquals(eCtr.getCountClose(), 1);
        Assert.assertEquals(eCtr.getCountMmap(), 1);
        Assert.assertEquals(eCtr.getCountMunmap(), 1);
        Assert.assertEquals(eCtr.getCountMsync(), 0);
        Assert.assertEquals(eCtr.getCountRead(), 0);
        Assert.assertEquals(eCtr.getCountWrite(), 0);
        Assert.assertEquals(eCtr.getCountIoctl_int(), 0);
        Assert.assertThat(eCtr.getCountIoctl_ptr(), Matchers.greaterThanOrEqualTo(3));
        Assert.assertEquals(eCtr.getCountFcntl(), 0);
        Assert.assertEquals(eFb.getCountMmapBadFlags(), 0);
        Assert.assertEquals(eFb.getCountMmapBadProt(), 0);
    }

    public void TestDraw(JavaFramebuffer adapter) throws IOException {
        BufferedImage img = adapter.createCompatibleBuffer(adapter.getWidth(), adapter.getHeight());
        drawRectangle(img);
        adapter.flushScreen(img);
        adapter.close();

        Assert.assertEquals(eCtr.getCountOpen(), 1);
        Assert.assertEquals(eCtr.getCountClose(), 1);
        Assert.assertEquals(eCtr.getCountMmap(), 1);
        Assert.assertEquals(eCtr.getCountMunmap(), 1);
        Assert.assertEquals(eCtr.getCountMsync(), 1);
        Assert.assertEquals(eCtr.getCountRead(), 0);
        Assert.assertEquals(eCtr.getCountWrite(), 0);
        Assert.assertEquals(eCtr.getCountIoctl_int(), 0);
        Assert.assertThat(eCtr.getCountIoctl_ptr(), Matchers.greaterThanOrEqualTo(3));
        Assert.assertEquals(eCtr.getCountFcntl(), 0);
        Assert.assertEquals(eFb.getCountMmapBadFlags(), 0);
        Assert.assertEquals(eFb.getCountMmapBadProt(), 0);


        BufferedImage last = getLastRender();
        Assert.assertFalse(allWhite(last));
    }

    protected void drawRectangle(BufferedImage img) {
        int xLen = img.getWidth() / 3;
        int yLen = img.getHeight() / 3;
        Graphics2D gfx = img.createGraphics();
        gfx.setColor(Color.BLACK);
        gfx.fillRect(xLen, yLen, xLen, yLen);
        gfx.dispose();
    }

    public void TestDrawBan(JavaFramebuffer adapter) throws IOException {
        adapter.setFlushEnabled(false);
        BufferedImage img = adapter.createCompatibleBuffer(adapter.getWidth(), adapter.getHeight());
        drawRectangle(img);
        adapter.flushScreen(img);
        adapter.close();

        Assert.assertEquals(eCtr.getCountOpen(), 1);
        Assert.assertEquals(eCtr.getCountClose(), 1);
        Assert.assertEquals(eCtr.getCountMmap(), 1);
        Assert.assertEquals(eCtr.getCountMunmap(), 1);
        Assert.assertEquals(eCtr.getCountMsync(), 0);
        Assert.assertEquals(eCtr.getCountRead(), 0);
        Assert.assertEquals(eCtr.getCountWrite(), 0);
        Assert.assertEquals(eCtr.getCountIoctl_int(), 0);
        Assert.assertThat(eCtr.getCountIoctl_ptr(), Matchers.greaterThanOrEqualTo(3));
        Assert.assertEquals(eCtr.getCountFcntl(), 0);
        Assert.assertEquals(eFb.getCountMmapBadFlags(), 0);
        Assert.assertEquals(eFb.getCountMmapBadProt(), 0);
    }

    public void TestClean(JavaFramebuffer adapter) throws IOException {
        adapter.clear();
        adapter.close();

        Assert.assertEquals(eCtr.getCountOpen(), 1);
        Assert.assertEquals(eCtr.getCountClose(), 1);
        Assert.assertEquals(eCtr.getCountMmap(), 1);
        Assert.assertEquals(eCtr.getCountMunmap(), 1);
        Assert.assertEquals(eCtr.getCountMsync(), 1);
        Assert.assertEquals(eCtr.getCountRead(), 0);
        Assert.assertEquals(eCtr.getCountWrite(), 0);
        Assert.assertEquals(eCtr.getCountIoctl_int(), 0);
        Assert.assertThat(eCtr.getCountIoctl_ptr(), Matchers.greaterThanOrEqualTo(3));
        Assert.assertEquals(eCtr.getCountFcntl(), 0);
        Assert.assertEquals(eFb.getCountMmapBadFlags(), 0);
        Assert.assertEquals(eFb.getCountMmapBadProt(), 0);

        BufferedImage last = getLastRender();
        Assert.assertTrue(allWhite(last));
    }

    public void TestRestore(JavaFramebuffer adapter) throws IOException {
        adapter.clear();
        adapter.storeData();
        BufferedImage img = adapter.createCompatibleBuffer(adapter.getWidth(), adapter.getHeight());
        drawRectangle(img);
        adapter.flushScreen(img);
        adapter.restoreData();
        adapter.close();

        Assert.assertEquals(eCtr.getCountOpen(), 1);
        Assert.assertEquals(eCtr.getCountClose(), 1);
        Assert.assertEquals(eCtr.getCountMmap(), 1);
        Assert.assertEquals(eCtr.getCountMunmap(), 1);
        Assert.assertEquals(eCtr.getCountMsync(), 3);
        Assert.assertEquals(eCtr.getCountRead(), 0);
        Assert.assertEquals(eCtr.getCountWrite(), 0);
        Assert.assertEquals(eCtr.getCountIoctl_int(), 0);
        Assert.assertThat(eCtr.getCountIoctl_ptr(), Matchers.greaterThanOrEqualTo(3));
        Assert.assertEquals(eCtr.getCountFcntl(), 0);
        Assert.assertEquals(eFb.getCountMmapBadFlags(), 0);
        Assert.assertEquals(eFb.getCountMmapBadProt(), 0);

        BufferedImage last = getLastRender();
        Assert.assertTrue(allWhite(last));
    }

    private boolean allWhite(BufferedImage img) {
        int whiteRgb = Color.WHITE.getRGB();

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int rgb = img.getRGB(x, y);
                if (rgb != whiteRgb) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private BufferedImage getLastRender() {
        return eFb.getSnapshots().get(eFb.getSnapshots().size() - 1);
    }
}
