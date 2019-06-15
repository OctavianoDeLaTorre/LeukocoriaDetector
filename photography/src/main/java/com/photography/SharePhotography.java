package com.photography;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SharePhotography  extends AsyncTask<Bitmap, Void, Void> {
    private Context mContext;

    /**
     * Constructor
     * @param mContext
     */
    public SharePhotography(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Inicia un activity en segundo plano para
     * compartir un imagen por las redes sociales
     * disponinbles en el sistema.
     * @param params Imagen a compartir
     * @return Void
     */
    @Override
    protected Void doInBackground(Bitmap... params) {
        try {
            File cachePath = new File(mContext.getCacheDir(), "imagenes"); //path cache.
            cachePath.mkdirs(); // Crea directorio si no existe.
            FileOutputStream stream = new FileOutputStream(cachePath + "/imagen.jpg"); // Escribe imagen.
            params[0].compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File imagePath = new File(mContext.getCacheDir(), "imagenes"); //obtiene directorio.
        File newFile = new File(imagePath, "imagen.jpg"); //obtiene imagen.
        String PACKAGE_NAME = mContext.getApplicationContext().getPackageName() + ".providers.FileProvider";
        Uri contentUri = FileProvider.getUriForFile(mContext.getApplicationContext(), PACKAGE_NAME, newFile);
        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setDataAndType(contentUri, mContext.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            mContext.startActivity(Intent.createChooser(shareIntent, "Selleciona una aplicacion:"));
        }
        return null;
    }
}
