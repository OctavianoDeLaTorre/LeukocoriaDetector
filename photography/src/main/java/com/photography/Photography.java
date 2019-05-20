package com.photography;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Photography {
    /*Codigo de solicitud de galeria*/
    public static final int REQUEST_CODE_GALLERY = 1;
    /*Codigo de solicitud de camara*/
    public static final int REQUEST_CODE_CAMERA = 2;
    private Intent intent;
    private final Activity activity;
    private SimpleDateFormat df;
    private Bitmap fotografia;

    /**
     * COnstructor
     * @param activity Context de activity
     */
    public Photography(Activity activity) {
        this.activity = activity;
        df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
    }

    /**
     * Devuelve la imagen cargada
     * @return Bitmat
     */
    public Bitmap getFotografia() {
        return fotografia;
    }

    /**
     * Recibe como parametro la imagen
     * @param fotografia Bitmat
     */
    public void setFotografia(Bitmap fotografia) {
        this.fotografia = fotografia;
    }

    /**
     * Inicia activity para cargar una
     * imagen desde la galeria.
     */
    public void loadFromGallery() {
        intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(
                Intent.createChooser(intent, "Selecciona imagen:"),
                REQUEST_CODE_GALLERY);
    }

    /**
     * Inicia una activity para tomar una
     * fotografia con la camara.
     */
    public void loadFromCamera(){
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * Guarda una imagen en la galeria.
     * @return true en caso de que se haya
     * guardado o false en caso de que no.
     */
    public boolean save() {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Detector_retinoblastoma";
        File dir = new File(file_path);
        final boolean b = false;
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, getImageName() + currentDateAndTime() + ".jpg");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            fotografia.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            return true;
        } catch (FileNotFoundException e) {
            return b;
        } catch (IOException e1) {
            return b;
        }
    }

    /**
     * Obtinen un Bitmat almacenado en un objeto
     * Intent.
     * @param data Objeto Intent
     * @return true si el el objeto Intent almacenaba
     * un bitmat.
     */
    public boolean getBitmat(Intent data) {
        Uri selectedImageUri = null;
        Uri selectedImage;
        String filePath = null;
        selectedImage = data.getData();
        String selectedPath = selectedImage.getPath();
        if (selectedPath != null) {
            InputStream imageStream = null;
            try {
                imageStream = activity.getContentResolver().openInputStream(
                        selectedImage);
            } catch (FileNotFoundException e) {
                return false;
            }

            fotografia = BitmapFactory.decodeStream(imageStream);
        }
        return true;
    }

    /**
     * Asegúrese de que el archivo haya sido creado y
     * ponerlo a disposición.
     * @param file
     */
    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(activity,
                new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    /**
     * Obtiene la fecha y hora del sistema.
     * @return fecha y hora
     */
    private String currentDateAndTime() {
        Calendar c = Calendar.getInstance();
        return df.format(c.getTime());
    }

    /**
     * Genera nombre de la imagen qe se
     * guardara en galeria.
     * @return
     */
    private String getImageName(){
        return "Detector_retinoblastoma_"+currentDateAndTime();
    }
}
