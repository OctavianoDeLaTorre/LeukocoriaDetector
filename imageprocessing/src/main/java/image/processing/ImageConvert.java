package image.processing;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

public class ImageConvert {
    /**
     * Convierte una imagen Bitmap a Mat.
     * @param bitmap Imagen a convertir.
     * @return Imagen Mat.
     */
    public Mat toMat(Bitmap bitmap){
        Mat mat = new Mat();
        Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32, mat);
        return mat;
    }

    /**
     * Convierte una imagen Mat a Bitmat.
     * @param mat Imagen a convertir.
     * @param bitmap
     * @return Imagen Bitmat.
     */
    public Bitmap toBitmap(Mat mat, Bitmap bitmap){
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }
}
