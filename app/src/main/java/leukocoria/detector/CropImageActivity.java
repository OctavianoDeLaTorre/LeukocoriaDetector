package leukocoria.detector;


import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.photography.Photography;


public class CropImageActivity extends AppCompatActivity implements SpeedDialView.OnActionSelectedListener{

    private CropImageView mCropView;
    private
    LoadCallback loadCallback = new LoadCallback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(Throwable e) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        mCropView = findViewById(R.id.cropImageView);

        final Uri uriImagen = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ojo);



        mCropView.load(uriImagen).execute(loadCallback);


        SpeedDialView speedDialView = findViewById(R.id.speedDial);


        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_action_crop,
                R.drawable.crop_image)
                .setLabel(R.string.crop)
                .setTheme(R.style.AppTheme_FloatActionItem)
                .create());

        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_action_cancel_crop,
                R.drawable.cancel)
                .setLabel(R.string.cancel)
                .setTheme(R.style.AppTheme_FloatActionItem)
                .create());

        speedDialView.setOnActionSelectedListener(this);

    }


    @Override
    public boolean onActionSelected(SpeedDialActionItem actionItem) {
        switch (actionItem.getId()){
            case R.id.fab_action_crop:

                mCropView.cropAsync(new CropCallback() {
                    @Override public void onSuccess(Bitmap cropped) {
                        Photography photography = new Photography(CropImageActivity.this);
                        photography.setFotografia(cropped);
                        photography.save();
                        Toast.makeText(CropImageActivity.this,"Croped",Toast.LENGTH_LONG).show();
                    }

                    @Override public void onError(Throwable e) {
                    }
                });

                return false;

            case R.id.fab_action_cancel_crop:

                return false;

            default:
                 return false;
        }

    }

}

