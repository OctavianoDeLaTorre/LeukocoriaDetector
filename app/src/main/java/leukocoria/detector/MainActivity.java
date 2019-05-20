package leukocoria.detector;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.photography.Photography;
import com.photography.SharePhotography;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Photography photography;
    private ImageView ivImage;
    private Bitmap imageBitmap;

    private static boolean initOpenCV = false;

    private final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA
    };

    static { initOpenCV = OpenCVLoader.initDebug(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        final Activity activity =  MainActivity.this;

        photography = new Photography(activity);
        ivImage = findViewById(R.id.image);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            photography.loadFromCamera();
        } else if (id == R.id.nav_gallery) {
            photography.loadFromGallery();
        }else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            if (photography.getFotografia() != null)
                new SharePhotography(MainActivity.this).execute(photography.getFotografia());
            else
                Toast.makeText(MainActivity.this,
                        R.string.imagen_null,
                        Toast.LENGTH_SHORT
                ).show();
        } else if (id == R.id.nav_save) {
            if (photography.getFotografia() != null) {
                if (photography.save())
                    Toast.makeText(MainActivity.this,
                            R.string.imagnen_guardada,
                            Toast.LENGTH_SHORT
                    ).show();
                else
                    Toast.makeText(MainActivity.this,
                            R.string.imagnen_no_guardada,
                            Toast.LENGTH_SHORT
                    ).show();
            } else {
                Toast.makeText(MainActivity.this,
                        R.string.imagen_null,
                        Toast.LENGTH_SHORT).show();
            }
        } else if(id == R.id.nav_ayuda){
            startActivity(new Intent(MainActivity.this,InfoRBActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if ( requestCode == Photography.REQUEST_CODE_GALLERY){
                if (photography.getBitmat(data)) {
                    imageBitmap = photography.getFotografia();

                    ivImage.setImageBitmap(imageBitmap);
                } else {
                    Toast.makeText(MainActivity.this,
                            R.string.imagen_no_cargada,
                            Toast.LENGTH_SHORT
                    ).show();
                }

            } else if (requestCode == Photography.REQUEST_CODE_CAMERA){
                Bundle extras = data.getExtras();
                photography.setFotografia((Bitmap) extras.get("data"));

                ivImage.setImageBitmap(photography.getFotografia());
            }
        }
    }

    public void requestPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA )
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,1);
        }
    }
}