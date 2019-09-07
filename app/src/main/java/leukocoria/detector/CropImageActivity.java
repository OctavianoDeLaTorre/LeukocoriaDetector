package leukocoria.detector;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import image.processing.ImageConvert;


public class CropImageActivity extends AppCompatActivity implements SpeedDialView.OnActionSelectedListener{

    private CropImageView mCropView;
    private SpeedDialView speedDialView;
    private LoadCallback loadCallback = new LoadCallback() {
        @Override
        public void onSuccess() {
            //Snackbar.make(speedDialView, "Es recomendable recortar la fotografía para facilitar el análisis", Snackbar.LENGTH_LONG).show();

        }

        @Override
        public void onError(Throwable e) {
            Snackbar.make(mCropView, "Ocurrió un error al cargar la fotografía.", Snackbar.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        Uri imageUri;
        if (getIntent() != null && getIntent().hasExtra(MainActivity.IMAGE_URI)) {
            imageUri = Uri.parse(getIntent().getStringExtra(MainActivity.IMAGE_URI));
        } else {
            imageUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ojo);
        }

        //Load image on CropView
        mCropView = findViewById(R.id.cropImageView);
        mCropView.setCropMode(CropImageView.CropMode.CIRCLE);
        mCropView.load(imageUri).execute(loadCallback);

        //Add options on FloatBotton
        speedDialView = findViewById(R.id.speedDial);
        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_action_turn_right,
                R.drawable.crop_image)
                .setLabel(R.string.turn_right)
                .setTheme(R.style.AppTheme_FloatActionItem)
                .create());

        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_action_turn_left,
                R.drawable.crop_image)
                .setLabel(R.string.turn_left)
                .setTheme(R.style.AppTheme_FloatActionItem)
                .create());

        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_action_crop,
                R.drawable.crop_image)
                .setLabel(R.string.crop)
                .setTheme(R.style.AppTheme_FloatActionItem)
                .create());

        speedDialView.setOnActionSelectedListener(this);

    }


    @Override
    public boolean onActionSelected(SpeedDialActionItem actionItem) {
        switch (actionItem.getId()){
            case R.id.fab_action_crop:
                //Crop image
                mCropView.cropAsync(new CropCallback() {
                    @Override public void onSuccess(Bitmap cropped) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(MainActivity.REQUEST_RESULT, ImageConvert.compressBitmat(cropped));
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    }

                    @Override public void onError(Throwable e) {
                        Snackbar.make(mCropView, "Ocurrió un error al recortar la fotografía.", Snackbar.LENGTH_LONG).show();
                    }
                });

                return false;

            case R.id.fab_action_turn_right:
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                return false;

            case R.id.fab_action_turn_left:
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
                return false;

            default:
                 return false;
        }

    }

}

