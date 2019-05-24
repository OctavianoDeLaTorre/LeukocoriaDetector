package image.processing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;

public class ImageConvert {
    /**
     * Convierte una imagen Bitmap a Mat.
     * @param bitmap Imagen a convertir.
     * @return Imagen Mat.
     */
    public static Mat toMat(Bitmap bitmap){
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
    public static Bitmap toBitmap(Mat mat, Bitmap bitmap){
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }

    public static byte[] compressBitmat(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap unCompressBitmat(byte[] compressBitmap){
       return BitmapFactory.decodeByteArray(compressBitmap, 0, compressBitmap.length);
    }


}
