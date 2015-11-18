package ev3dev.java.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 * Webcam1
 *
 */
public class Webcam1 {

    public static void main( String[] args ) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        VideoCapture camera = new VideoCapture(0);
        if(!camera.isOpened()) {
            System.out.println("Camera Error");
        }

        Mat frame = new Mat();

        camera.read(frame);
        System.out.println("Frame Obtained");
        System.out.println("Captured Frame Width " + frame.width());

        Highgui.imwrite("camera.jpg", frame);
        camera.release();
    }
}
