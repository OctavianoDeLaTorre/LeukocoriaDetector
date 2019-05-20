package analyze.color;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ColorAnalysis {
    /**
     * Escala de colores segun el nivel el grado de la
     * leucocoria.
     */
    public static final Scalar LEUKOCORIA_LEVEL_LOW [] = new Scalar[]{new Scalar(110,50,50),new Scalar(130,255,255)};
    public static final Scalar LEUKOCORIA_LEVEL_MEDIUM [] = new Scalar[]{new Scalar(0,20,61), new Scalar(60,37,80)};
    public static final Scalar LEUKOCORIA_LEVEL_HIGH [] = new Scalar[]{new Scalar(0,0,81), new Scalar(60,19,100)};

    /**
     * Devuelve una máscara binaria, donde los píxeles blancos
     * representan los píxeles que se encuentran dentro del
     * rango de colores y los píxeles negros no lo hacen.
     * @param imagen Imagen a analizar.
     * @param level Rango de colores.
     * @return Máscara binaria.
     */
    public static Mat analizarColor(Mat imagen, Scalar level[]){
        Mat masck = new Mat();
        Mat imagenHSV = new Mat();
        Imgproc.cvtColor(imagen,imagenHSV,Imgproc.COLOR_BGR2HSV);
        Core.inRange(imagenHSV, level[0],level[1],masck);
        return masck;
    }
}
