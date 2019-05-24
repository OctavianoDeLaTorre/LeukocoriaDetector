package pupil.segmentation;

import android.graphics.Bitmap;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import image.processing.ImageConvert;

public class PupilSegmetation {


    public Bitmap segmentPupil(Bitmap imgBitmat){

        // Load an image
        Mat src = ImageConvert.toMat(imgBitmat);

        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.medianBlur(gray, gray, 5);
        Mat circles = new Mat();
        Imgproc.HoughCircles(gray, circles,  Imgproc.CV_HOUGH_GRADIENT, 2, gray.rows(), 100, 20, 20, 200);

        for (int x = 0; x < circles.cols(); x++) {
            double[] c = circles.get(0, x);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
            // circle center
            Imgproc.circle(src, center, 1, new Scalar(0,255,0), 3, 8, 0 );
            // circle outline
            int radius = (int) Math.round(c[2]);
            Imgproc.circle(src, center, radius, new Scalar(255,0,0), 2, 8, 0 );
        }

        return ImageConvert.toBitmap(src,imgBitmat);
    }
}
