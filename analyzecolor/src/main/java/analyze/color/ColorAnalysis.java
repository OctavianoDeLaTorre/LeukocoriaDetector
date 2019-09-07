package analyze.color;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.BitSet;

public class ColorAnalysis {
    /**
     * Escala de colores segun el nivel el grado de la
     * leucocoria.
     */
    public static final Scalar LEUKOCORIA_LEVEL_LOW[] = new Scalar[]{new Scalar(0, 97, 77), new Scalar(30, 153, 153)};
    public static final Scalar LEUKOCORIA_LEVEL_MEDIUM [] = new Scalar[]{new Scalar(0,51,153), new Scalar(30,97,204)};
    public static final Scalar LEUKOCORIA_LEVEL_HIGH [] = new Scalar[]{new Scalar(0,0,204), new Scalar(30,51,255)};

    /**
     * Devuelve una máscara binaria, donde los píxeles blancos
     * representan los píxeles que se encuentran dentro del
     * rango de colores y los píxeles negros no lo hacen.
     * @param imagen Imagen a analizar.
     * @param level Rango de colores.
     * @return Máscara binaria.
     */
    public static Mat analyze(Mat imagen, Scalar level[]){
        Mat masck = new Mat();
        Mat imagenHSV = new Mat();
        Imgproc.cvtColor(imagen,imagenHSV,Imgproc.COLOR_BGR2HSV);
        Core.inRange(imagenHSV, level[0],level[1],masck);
        return masck;
    }

    public Bitmap deleteBackColor(Bitmap image) {
        Bitmap imageResult = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());
        int pixel;
        int pixelColors[];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pixel = image.getPixel(x, y);
                pixelColors = getPixelColors(pixel);
                if (pixelColors[0] == 0 && pixelColors[1] == 0 && pixelColors[2] == 0) {
                    imageResult.setPixel(x, y, Color.argb(0, pixelColors[0], pixelColors[1], pixelColors[2]));
                } else {
                    imageResult.setPixel(x, y, Color.argb(pixelColors[3], pixelColors[0], pixelColors[1], pixelColors[2]));
                }
            }
        }

        return imageResult;
    }

    private int[] getPixelColors(int pixel) {
        return new int[]{Color.red(pixel), Color.green(pixel), Color.blue(pixel), Color.alpha(pixel)};
    }
}
