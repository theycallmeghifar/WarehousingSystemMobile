package fim.project.warehousingsystemmobile.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.responses.ApiEndPoint;
import fim.project.warehousingsystemmobile.responses.PaletTempResponse;
import fim.project.warehousingsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanRakBarangMasukActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences prefDetailBarangMasuk;
    private SurfaceView surfaceView;
    private TextView txtBarcodeValue;
    private TextView txtError;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private String intentData = "";

    private String idPaletPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_rak_barang_masuk);
        initViews();

        //CUSTOM ACTION BAR FONT
        ActionBar actionBar = getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());
        Typeface typeface = ResourcesCompat.getFont(this, R.font.bebasneue);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText("FIM WAREHOUSING"); // ActionBar title text
        tv.setTextSize(32);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.LEFT);
        tv.setTypeface(typeface, typeface.NORMAL);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);

        pref = getSharedPreferences("scan_barang_masuk",MODE_PRIVATE);
        prefDetailBarangMasuk = getSharedPreferences("detail_barang_masuk",MODE_PRIVATE);

        idPaletPref = prefDetailBarangMasuk.getString("idPalet",null);

        txtError = (TextView) findViewById(R.id.txtErrorScanRakBarangMasuk);
        txtError.setVisibility(View.INVISIBLE);
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtQRRakBarangMasuk);
        surfaceView = findViewById(R.id.surfaceViewRakBarangMasuk);
    }

    private void initialiseDetectorsAndSources() {

//        Toast.makeText(getApplicationContext(), "Silahkan Scan Barang", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScanRakBarangMasukActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScanRakBarangMasukActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
//                Toast.makeText(getApplicationContext(), "Berhasil discan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {
                            intentData = barcodes.valueAt(0).displayValue;
                            getPalet(intentData, idPaletPref);
                        }
                    });
                }
            }
        });
    }

    public void getPalet(String idPalet, String idPaletCompare) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<PaletTempResponse> call = apiEndPoint.getPalet(idPalet, idPaletCompare);

        call.enqueue(new Callback<PaletTempResponse>() {
            @Override
            public void onResponse(Call<PaletTempResponse> call, Response<PaletTempResponse> response) {

                final PaletTempResponse itemResponse = response.body();

                if (itemResponse.getResponses()) {

                    if (idPaletPref.equals(itemResponse.getIdPalet())){
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("idPaletPref","1");
                        editor.commit();

                        SharedPreferences.Editor editorDetail = prefDetailBarangMasuk.edit();
                        editorDetail.putString("gpioStatus","0");
                        editorDetail.commit();

                        Intent i = new Intent(ScanRakBarangMasukActivity.this, DetailBarangMasukActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }else{
                        txtError.setVisibility(View.VISIBLE);
                        txtError.setText("RAK TIDAK SESUAI");

                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                txtError.setVisibility(View.INVISIBLE);
                            }
                        }, 6000);
                    }

                } else {
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("DATA TIDAK DITEMUKAN");

                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtError.setVisibility(View.INVISIBLE);
                        }
                    }, 6000);
                }
            }

            @Override
            public void onFailure(Call<PaletTempResponse> call, Throwable t) {
                txtError.setVisibility(View.VISIBLE);
                txtError.setText("KONEKSI BERMASALAH");

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtError.setVisibility(View.INVISIBLE);
                    }
                }, 6000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        initialiseDetectorsAndSources();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}