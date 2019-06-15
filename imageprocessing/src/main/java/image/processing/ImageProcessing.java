package image.processing;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *  Esta clase utiliza las funciones de OpenCV para realizar
 *  la escalación, recorte y conversión a escalas de grises etc...
 */
public class ImageProcessing {
    /**
     * Convierte una imagen a escala de grises.
     * @param mat Imagen a convertir a escala de grises.
     * @return Imagen es escala de grises.
     */
    public Mat getGrayScale(Mat mat){
        Mat matGray = new Mat();
        Imgproc.cvtColor(mat, matGray, Imgproc.COLOR_BGR2GRAY);
        return matGray;
    }

    /**
     * Aplica efecto Canny Edges.
     * @param mat imagen original.
     * @param matCanny imagen donde se aplicara el efecto.
     * @return Imagen con efecto Canny Edges.
     */
    public Mat getCannyEdges(Mat mat,Mat matCanny){
        Imgproc.Canny(mat, matCanny, 10, 100);
        return matCanny;
    }

    /**
     * Aplica efecto Gaussian Blur (desenfoque).
     * @param mat imagen original.
     * @param matGB imagen donde se aplicara el efecto.
     * @return Imagen con efecto Gaussian Blur.
     */
    public Mat getGaussianBlur(Mat mat,Mat matGB){
        Imgproc.GaussianBlur(mat, matGB, new Size(9,9), 3);
        return matGB;
    }

    /**
     * Dibuja circulos en una imagen Mat.
     * @param mat Imagen donde se diburaran los circulos.
     * @param circles Ciruclos que se dibujaran.
     * @return Imagen Mat
     */
    public Mat drawCircles(Mat mat,Mat circles){
        for (int i = 0; i < circles.cols(); i++) {
            double vCircle[] = circles.get(0, i);
            Point center = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
            int radius = (int) Math.round(vCircle[2]);
            Imgproc.circle(mat, center, 3, new Scalar(0, 255, 0), -1, 8, 0);
            // circle outline
            Imgproc.circle(mat, center, radius, new Scalar(0, 0, 255), 3, 8, 0);
        }
        return mat;
    }

    /**
     * Escala una imagen.
     * @param mat Imagen a escalar.
     * @param size Factor de escala.
     * @return Imagen escalada.
     */
    public Mat getScala(Mat mat, Size size){
        Mat matSala = new Mat();
        Imgproc.resize(mat,matSala, size);
        return matSala;
    }

    /**
     * Cuenta los pixeles blancos de una
     * mascara binaria.
     * @param bitmap
     * @return Num. de pixeles blancos
     */
    public int countWhitePixels(Bitmap bitmap) {
        int r, g, b;
        int colorPixel;
        int numPixels = 0;

        for(int x = 0; x <bitmap.getWidth(); x++){
            for(int y = 0; y <bitmap.getHeight(); y++){
                colorPixel = bitmap.getPixel(x,y);
                r = Color.red(colorPixel);
                g = Color.green(colorPixel);
                b = Color.blue(colorPixel);

                if (r == 255 && g == 255 && b == 255){
                    numPixels++;
                }
            }
        }
        return numPixels;
    }
}
