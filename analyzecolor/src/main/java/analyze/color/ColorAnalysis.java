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
    public static final Scalar LEUKOCORIA_LEVEL_LOW [] = new Scalar[]{new Scalar(0,97,127),new Scalar(30,153,153)};
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
    public static Mat analizarColor(Mat imagen, Scalar level[]){
        Mat masck = new Mat();
        Mat imagenHSV = new Mat();
        Imgproc.cvtColor(imagen,imagenHSV,Imgproc.COLOR_BGR2HSV);
        Core.inRange(imagenHSV, level[0],level[1],masck);
        return masck;
    }
}
