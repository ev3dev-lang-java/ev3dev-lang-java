package ev3dev.java.opencv;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class BlobDetector {
    // Lower and Upper bounds for range checking in HSV color space
    private Scalar mLowerBound = new Scalar(0);
    private Scalar mUpperBound = new Scalar(0);
    // Minimum contour area in percent for contours filtering
    private static double mMinContourArea = 0.35;
    // Color radius for range checking in HSV color space
    private Scalar mColorRadius = new Scalar(50,25,70);
    private Mat mSpectrum = new Mat();
    private List<MatOfPoint> mContours = new ArrayList<MatOfPoint>();

    // some temporary variables
    Mat mPyrDownMat = new Mat();
    Mat mHsvMat = new Mat();
    Mat mMask = new Mat();
    Mat mDilatedMask = new Mat();
    Mat mHierarchy = new Mat();

    public void setColorRadius(Scalar radius) {
        mColorRadius = radius; //setter for color radius
    }

    public void setHsvColor(Scalar hsvColor)
    {
     	
        double minH = (hsvColor.val[0] >= mColorRadius.val[0]) ? hsvColor.val[0]-mColorRadius.val[0] : 0;
        double maxH = (hsvColor.val[0]+mColorRadius.val[0] <= 255) ? hsvColor.val[0]+mColorRadius.val[0] : 255;

        //mLowerBound.val[0] = minH;
       // mUpperBound.val[0] = maxH;
        System.out.println("minH: " + minH + " and maxH:" + maxH);
        mLowerBound.val[0] = 0;
        mUpperBound.val[0] = 150;
        // mUpperBound.val[0] = maxH;
        System.out.println("(hardcoded) minH: " + mLowerBound.val[0] + " and maxH:" + mUpperBound.val[0]);
        
        mLowerBound.val[1] = hsvColor.val[1] - mColorRadius.val[1];
        mUpperBound.val[1] = hsvColor.val[1] + mColorRadius.val[1];

        mLowerBound.val[2] = hsvColor.val[2] - mColorRadius.val[2];
        mUpperBound.val[2] = hsvColor.val[2] + mColorRadius.val[2];

        mLowerBound.val[3] = 0;
        mUpperBound.val[3] = 255;

        Mat spectrumHsv = new Mat(1, (int)(maxH-minH), CvType.CV_8UC3);

        for (int j = 0; j < maxH-minH; j++) {
            byte[] tmp = {(byte)(minH+j), (byte)255, (byte)255};
            spectrumHsv.put(0, j, tmp);
        }

        //convert to RGB
        Imgproc.cvtColor(spectrumHsv, mSpectrum, Imgproc.COLOR_HSV2RGB_FULL, 4);
    }

    public Mat getSpectrum() {
        return mSpectrum;
    }

    public void setMinContourArea(double area) {
        mMinContourArea = area;
    }

    public void process(Mat rgbaImage) {
    	Mat tmp2 = new Mat(); //a temporary variable for processing things
    	
    	//slow bilateral filter (commented out)
    	//Imgproc.bilateralFilter(rgbaImage, tmp2, 5, 90, 90);
    	
    	Imgproc.GaussianBlur(rgbaImage, tmp2, new Size(15,15), 2); //gaussian kernel filter (blur)
    			
    	//my computer is fast enough to deal with full-size video
    	//you may need to use pyrDown once or twice otherwise
        //ie: Imgproc.pyrDown(rgbaImage, mPyrDownMat);
        
        Imgproc.cvtColor(tmp2, mHsvMat, Imgproc.COLOR_RGB2HSV_FULL); //convert RGB to hsv colorspace

        Core.inRange(mHsvMat, mLowerBound, mUpperBound, mMask); //threshold between lower/upper HSV bounds
        
        Imgproc.dilate(mMask, mDilatedMask, new Mat()); //dilate: reduce "holes" in matte
        
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>(); //initialize contour holding variable

        //find contours from dilated mask
        Imgproc.findContours(mDilatedMask, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        
        MatOfPoint largestContour = null; //temp variable for finding largest contour
        Imgproc.cvtColor(mMask, rgbaImage, Imgproc.COLOR_GRAY2BGR); //only needed if you want the "swatch" to be displayed later on.
        //OTHERWISE, it is redundant and pointless/inefficient
        
        //iterate through all contours, determine maximum area
        double maxArea = 0;
        Iterator<MatOfPoint> each = contours.iterator();
        while (each.hasNext()) {
            MatOfPoint wrapper = each.next();
            double area = Imgproc.contourArea(wrapper);
            if (area > maxArea)
            {
                maxArea = area;
                largestContour = wrapper;
            }
        }

        //iterate through contours and save all that meet area requirements
        mContours.clear();
        each = contours.iterator();
        while (each.hasNext())
        {
        	MatOfPoint contour = each.next();
            if (Imgproc.contourArea(contour) > mMinContourArea*maxArea)
            {
                mContours.add(contour); //add contour to mContours
            }
        }
        
        Moments moments = new Moments(); //initalize Moments holder
        if (largestContour != null) //sanity check?
        {
        	moments = org.opencv.imgproc.Imgproc.moments(largestContour,false); //compute moments
        	
        	//compute the mass center
        	Point2D.Float mass = new Point2D.Float();
        	mass = new Point2D.Float((float) (moments.get_m10()/moments.get_m00()), (float) (moments.get_m01()/moments.get_m00()));
        	org.opencv.core.Point intpoint = new org.opencv.core.Point((int) mass.getX(), (int) mass.getY());
        	
        	//draw a crosshair
        	Core.line(rgbaImage,new org.opencv.core.Point((int) intpoint.x-8,(int) intpoint.y), new org.opencv.core.Point((int) intpoint.x+8,(int) intpoint.y), new Scalar(0,0,255,255),2);
        	Core.line(rgbaImage,new org.opencv.core.Point((int) intpoint.x,(int) intpoint.y-8), new org.opencv.core.Point((int) intpoint.x,(int) intpoint.y+8), new Scalar(0,0,255,255),2);
        	Core.circle(rgbaImage, intpoint, 5, new Scalar(255,0,0,255));

    	}
    }

    public List<MatOfPoint> getContours() {
        return mContours; //getter for contours
    }
}