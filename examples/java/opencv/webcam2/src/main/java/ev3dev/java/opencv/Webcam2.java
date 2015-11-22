package ev3dev.java.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 * Webcam1
 *
 */
public class Webcam2 {

    public static void main( String[] args ) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        VideoCapture camera = new VideoCapture(0);
        if(!camera.isOpened()) {
            System.out.println("Camera Error");
        }

        Mat frame = new Mat();

        for(int i = 0; i <= 100; i++) {
            camera.read(frame);
            System.out.println("Frame Obtained");
            System.out.println("Captured Frame Width " + frame.width());

            Highgui.imwrite("camera.jpg", frame);
            
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.println("Iteration:" + i);
        }

        camera.release();
    }
}
