package fim.project.warehousingsystemmobile.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.responses.ApiEndPoint;
import fim.project.warehousingsystemmobile.responses.StatusResponse;
import fim.project.warehousingsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiBarangKeluarActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom_menu;
    private SharedPreferences pref;
    private SharedPreferences prefDetailBarangKeluarListView;
    private SharedPreferences prefSuccessOut;
    private SharedPreferences prefLokasi;
    private SharedPreferences prefCancelDisable;

    private String idBarang;
    private String idPalet;
    private String mainItemCode;
    private String itemCode;
    private String jumlah;
    private String scanStatus;
    private String itemName;
    private String namaPalet;
    private String currentQty;
    private String namaLemari;
    private String namaRak;
    private String maxBarang;
    private String ipAddress;
    private String gpio1;
    private String gpio2;
    private String gpio3;
    private String gpioStatus;
    private TextView txtError;
    private Button btnKonfirmasi;
    private Button btnScanRak;
    private CardView btnLokasi;
    private WebView wvGPIO1;
    private WebView wvGPIO2;
    private WebView wvGPIO3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_barang_keluar);

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

        prefLokasi = getSharedPreferences("lokasi",MODE_PRIVATE);
        prefCancelDisable = getSharedPreferences("prefCancelDisable",MODE_PRIVATE);
        pref = getSharedPreferences("scan_barang_keluar",MODE_PRIVATE);
        prefSuccessOut = getSharedPreferences("successOutPref", MODE_PRIVATE);
        prefDetailBarangKeluarListView = getSharedPreferences("detail_barang_keluar_list_view", MODE_PRIVATE);
        idBarang = prefDetailBarangKeluarListView.getString("lvIdBarang","0");
        idPalet = prefDetailBarangKeluarListView.getString("lvIdPalet","0");
        itemCode = prefDetailBarangKeluarListView.getString("lvItemCode","0");
        jumlah = prefDetailBarangKeluarListView.getString("lvJumlah","0");
        itemName = prefDetailBarangKeluarListView.getString("lvItemName","0");
        namaPalet = prefDetailBarangKeluarListView.getString("lvNamaPalet","0");
        currentQty = prefDetailBarangKeluarListView.getString("lvCurrentQty","0");
        namaLemari = prefDetailBarangKeluarListView.getString("lvNamaLemari","0");
        namaRak = prefDetailBarangKeluarListView.getString("lvNamaRak","0");
        maxBarang = prefDetailBarangKeluarListView.getString("lvMaxBarang","0");
        ipAddress = prefDetailBarangKeluarListView.getString("lvIpAddress","0");
        gpio1 = prefDetailBarangKeluarListView.getString("lvGpio1","0");
        gpio2 = prefDetailBarangKeluarListView.getString("lvGpio2","0");
        gpio3 = prefDetailBarangKeluarListView.getString("lvGpio3","0");
        gpioStatus = prefDetailBarangKeluarListView.getString("lvGpioStatus","0");
        mainItemCode = pref.getString("mainItemCode","0");
        scanStatus = prefDetailBarangKeluarListView.getString("scanStatus","0");

        wvGPIO1 = (WebView) findViewById(R.id.confirm_wvGPIO1);
        wvGPIO1.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        wvGPIO2 = (WebView) findViewById(R.id.confirm_wvGPIO2);
        wvGPIO2.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        wvGPIO3 = (WebView) findViewById(R.id.confirm_wvGPIO3);
        wvGPIO3.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });

        if (gpioStatus.equals("1")){
            wvGPIO1.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio1+"&status=dh");
            wvGPIO2.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio2+"&status=dh");
            wvGPIO3.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio3+"&status=dh");
        }else if(gpioStatus.equals("0")){
            wvGPIO1.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio1+"&status=dl");
            wvGPIO2.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio2+"&status=dl");
            wvGPIO3.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio3+"&status=dl");
        }else{

        }

        TextView tv_itemCode = (TextView) findViewById(R.id.barangKeluar_itemCodeValue);
        tv_itemCode.setText(String.valueOf(itemCode));

        TextView tv_jumlah = (TextView) findViewById(R.id.barangKeluar_qtyInputValue);
        tv_jumlah.setText(String.valueOf(jumlah));

        TextView tv_itemName = (TextView) findViewById(R.id.barangKeluar_itemNameValue);
        tv_itemName.setText(String.valueOf(itemName));

        TextView tv_namaPalet = (TextView) findViewById(R.id.barangKeluar_namaPaletValue);
        tv_namaPalet.setText(String.valueOf(namaPalet));

        TextView tv_namaLemari = (TextView) findViewById(R.id.barangKeluar_namaLemariValue);
        tv_namaLemari.setText(String.valueOf(namaLemari));

        TextView tv_namaRak = (TextView) findViewById(R.id.barangKeluar_namaRakValue);
        tv_namaRak.setText(String.valueOf(namaRak));

        txtError = (TextView) findViewById(R.id.barangKeluar_txt_error);
        txtError.setVisibility(View.INVISIBLE);

        btnKonfirmasi = (Button) findViewById(R.id.barangKeluar_btnDetailKonfirmasi);
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(KonfirmasiBarangKeluarActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Ingin menyelesaikan proses?")
                        .setConfirmText("Selesai")
                        .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                        .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                btnKonfirmasi.setClickable(false);
                                if (maxBarang.equals("0") | maxBarang.equals("")){
                                    updateItemBarangKeluar(mainItemCode, itemCode, jumlah);
                                }else{
                                    updatePaletGpioOffBarangKeluar(idBarang, mainItemCode, itemCode, idPalet, jumlah);
                                }
                            }
                        })
                        .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        btnLokasi = (CardView)findViewById(R.id.barangKeluar_btnLokasi);
        btnLokasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefLokasi.edit();
                editor.putString("namaLemari",namaLemari);
                editor.commit();

                Intent i = new Intent(KonfirmasiBarangKeluarActivity.this, LokasiActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnScanRak = (Button)findViewById(R.id.barangKeluar_btnDetailScan);
        btnScanRak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (maxBarang.equals("0") | maxBarang.equals("")){
                    Intent i = new Intent(KonfirmasiBarangKeluarActivity.this, ScanItemBarangKeluarActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    Intent i = new Intent(KonfirmasiBarangKeluarActivity.this, ScanRakBarangKeluarActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        if (maxBarang.equals("0") | maxBarang.equals("")){
            btnScanRak.setText("SCAN ITEM");
            CardView cv_lokasi = (CardView) findViewById(R.id.barangKeluar_btnLokasi);
            cv_lokasi.setVisibility(View.GONE);
            RelativeLayout rv_lemari = (RelativeLayout) findViewById(R.id.barangKeluar_namaLemari_rv);
            rv_lemari.setVisibility(View.GONE);
            RelativeLayout rv_rak = (RelativeLayout) findViewById(R.id.barangKeluar_namaRak_rv);
            rv_rak.setVisibility(View.GONE);
            RelativeLayout rv_palet = (RelativeLayout) findViewById(R.id.barangKeluar_namaPalet_rv);
            rv_palet.setVisibility(View.GONE);

            TextView tv_currentQty = (TextView) findViewById(R.id.barangKeluar_totalQtyValue);
            tv_currentQty.setText(String.valueOf(currentQty));
            tv_currentQty.setTextColor(Color.parseColor("#616161"));
        }else{
            btnScanRak.setText("SCAN RAK");
            if (Integer.parseInt(currentQty) >= Integer.parseInt(maxBarang)) {
                TextView tv_currentQty = (TextView) findViewById(R.id.barangKeluar_totalQtyValue);
                tv_currentQty.setText(String.valueOf(currentQty + " (Overload Max " + maxBarang + ")"));
                tv_currentQty.setTextColor(Color.parseColor("#ff3030"));
            } else {
                TextView tv_currentQty = (TextView) findViewById(R.id.barangKeluar_totalQtyValue);
                tv_currentQty.setText(String.valueOf(currentQty + " (Max " + maxBarang + ")"));
                tv_currentQty.setTextColor(Color.parseColor("#616161"));
            }
        }

        if (scanStatus.equals("1")){
            btnKonfirmasi.setVisibility(View.VISIBLE);
            btnScanRak.setVisibility(View.GONE);
            txtError.setVisibility(View.VISIBLE);
            if (maxBarang.equals("0") | maxBarang.equals("")){
                txtError.setText("Item telah discan !");
            }else{
                txtError.setText("Rak telah discan !");
            }
            txtError.setTextColor(Color.parseColor("#8bc34a"));
        }else{
            btnKonfirmasi.setVisibility(View.GONE);
            btnScanRak.setVisibility(View.VISIBLE);
            if (maxBarang.equals("0") | maxBarang.equals("")){
                txtError.setText("Item belum discan !");
            }else{
                txtError.setText("Rak belum discan !");
            }
            txtError.setTextColor(Color.parseColor("#ff3030"));
            txtError.setVisibility(View.VISIBLE);
        }

        bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(this);
        bottom_menu.getMenu().getItem(1).setChecked(true);
    }

    public void updateItemBarangKeluar(String mainItemCode, String itemCode, String qtyInput) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.updateItemBarangKeluar(mainItemCode, itemCode, qtyInput);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                final StatusResponse statusResponse = response.body();

                if (statusResponse.getResponses()) {

                    SharedPreferences.Editor editor = prefSuccessOut.edit();
                    editor.putString("suksesOut","1");
                    editor.commit();

                    SharedPreferences.Editor editorCancel = prefCancelDisable.edit();
                    editorCancel.putString("cancelDisable","1");
                    editorCancel.commit();

                    SharedPreferences.Editor editorGpio = prefDetailBarangKeluarListView.edit();
                    editorGpio.putString("lvGpioStatus","0");
                    editorGpio.commit();

                    Intent i = new Intent(KonfirmasiBarangKeluarActivity.this, DetailBarangKeluarActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else {
                    txtError.setText("Gagal melakukan proses penyimpanan !");
                    txtError.setTextColor(Color.parseColor("#ff3030"));
                    txtError.setVisibility(View.VISIBLE);

                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(KonfirmasiBarangKeluarActivity.this, KonfirmasiBarangKeluarActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }, 8000);
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                txtError.setText("Koneksi bermasalah !");
                txtError.setTextColor(Color.parseColor("#ff3030"));
                txtError.setVisibility(View.VISIBLE);

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(KonfirmasiBarangKeluarActivity.this, KonfirmasiBarangKeluarActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }, 8000);
            }
        });
    }

    public void updatePaletGpioOffBarangKeluar(String idBarang, String mainItemCode, String itemCode, String idPalet, String qtyInput) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.updatePaletGpioOffBarangKeluar(idBarang, mainItemCode, itemCode, idPalet, qtyInput);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                final StatusResponse statusResponse = response.body();

                if (statusResponse.getResponses()) {

                    SharedPreferences.Editor editor = prefSuccessOut.edit();
                    editor.putString("suksesOut","1");
                    editor.commit();

                    SharedPreferences.Editor editorCancel = prefCancelDisable.edit();
                    editorCancel.putString("cancelDisable","1");
                    editorCancel.commit();

                    SharedPreferences.Editor editorGpio = prefDetailBarangKeluarListView.edit();
                    editorGpio.putString("lvGpioStatus","0");
                    editorGpio.commit();

                    Intent i = new Intent(KonfirmasiBarangKeluarActivity.this, DetailBarangKeluarActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else {
                    txtError.setText("Gagal melakukan proses penyimpanan !");
                    txtError.setTextColor(Color.parseColor("#ff3030"));
                    txtError.setVisibility(View.VISIBLE);

                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(KonfirmasiBarangKeluarActivity.this, KonfirmasiBarangKeluarActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }, 8000);
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                txtError.setText("Koneksi bermasalah !");
                txtError.setTextColor(Color.parseColor("#ff3030"));
                txtError.setVisibility(View.VISIBLE);

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(KonfirmasiBarangKeluarActivity.this, KonfirmasiBarangKeluarActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }, 8000);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.bottombar_barangMasuk:
                warningMessage();
                break;
            case R.id.bottombar_barangKeluar:
                warningMessage();
                break;
            case R.id.bottombar_history:
                warningMessage();
                break;
            case R.id.bottombar_barang:
                warningMessage();
                break;
        }
        return true;
    }

    public void warningMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Peringatan").setContentText("Harap selesaikan proses pengeluaran barang terlebih dahulu !").setConfirmText("OK").setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_cancel, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int getId = item.getItemId();
        switch (getId) {
            case R.id.nav_cancel:
                if (gpioStatus.equals("0")){
                    warningMessage();
                }else{
                    wvGPIO1.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio1+"&status=dl");
                    wvGPIO2.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio2+"&status=dl");
                    wvGPIO3.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio3+"&status=dl");

                    SharedPreferences.Editor editorGpio = prefDetailBarangKeluarListView.edit();
                    editorGpio.putString("lvGpioStatus","0");
                    editorGpio.commit();

                    Intent c = new Intent(this, DetailBarangKeluarActivity.class);
                    startActivity(c);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        warningMessage();
    }
}