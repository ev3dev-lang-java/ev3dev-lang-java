package ev3dev.java.opencv;

import java.awt.Color;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class BlobDemo {

	/*
    static String sourcePath = "c:/test/source.jpg";
    static String targetPath = "c:/test/target.jpg";

    public static void main (String args[]){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	
        Mat image = Highgui.imread(sourcePath);
        Mat grayImage = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
        cvCvtColor(image, grayImage, CV_BGR2GRAY);

        CvMemStorage mem;
        CvSeq contours = new CvSeq();
        CvSeq ptr = new CvSeq();
        cvThreshold(grayImage, grayImage, 150, 255, CV_THRESH_BINARY);
        mem = cvCreateMemStorage(0);

        cvFindContours(grayImage, mem, contours, sizeof(CvContour.class) , CV_RETR_CCOMP, CV_CHAIN_APPROX_SIMPLE, cvPoint(0,0));

        Random rand = new Random();
        for (ptr = contours; ptr != null; ptr = ptr.h_next()) {
            Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            Scalar color = CV_RGB( randomColor.getRed(), randomColor.getGreen(), randomColor.getBlue());
            cvDrawContours(image, ptr, color, CV_RGB(0,0,0), -1, CV_FILLED, 8, cvPoint(0,0));
        }
        cvSaveImage(targetPath, image);
    }
*/
}
