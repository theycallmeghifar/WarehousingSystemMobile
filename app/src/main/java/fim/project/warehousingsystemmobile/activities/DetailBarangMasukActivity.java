package fim.project.warehousingsystemmobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.responses.ApiEndPoint;
import fim.project.warehousingsystemmobile.responses.StatusResponse;
import fim.project.warehousingsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBarangMasukActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom_menu;
    private SharedPreferences pref;
    private SharedPreferences prefDetailBarangMasuk;
    private SharedPreferences prefLokasi;

    private String idBarang;
    private String itemCode;
    private String itemName;
    private String qtyInput;
    private String purchaseOrder;
    private String deliveryNote;
    private String qtyTotal;
    private String qtyMax;
    private String namaLemari;
    private String idRak;
    private String namaRak;
    private String idPalet;
    private String namaPalet;
    private String idPaletPref;
    private String ipAddress;
    private String gpio1;
    private String gpio2;
    private String gpio3;
    private String gpioStatus;
    private EditText etIdPalet;
    private Button btnKonfirmasiManual;
    private Button btnKonfirmasi;
    private Button btnScanRak;
    private CardView btnLokasi;
    private TextView txtError;
    private TextView txtErrorManual;
    private WebView wvGPIO1;
    private WebView wvGPIO2;
    private WebView wvGPIO3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang_masuk);

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
        prefLokasi = getSharedPreferences("lokasi",MODE_PRIVATE);

        idBarang = prefDetailBarangMasuk.getString("idBarang",null);
        itemCode = prefDetailBarangMasuk.getString("itemCode",null);
        itemName = prefDetailBarangMasuk.getString("itemName",null);
        qtyInput = pref.getString("itemJumlah",null);
        purchaseOrder = pref.getString("purchaseOrder",null);
        deliveryNote = pref.getString("deliveryNote",null);
        qtyTotal = prefDetailBarangMasuk.getString("jumlahBarang",null);
        qtyMax = prefDetailBarangMasuk.getString("maxBarang",null);
        namaLemari = prefDetailBarangMasuk.getString("deskripsiLemari",null);
        idRak = prefDetailBarangMasuk.getString("idRak",null);
        namaRak = prefDetailBarangMasuk.getString("deskripsiRak",null);
        idPalet = prefDetailBarangMasuk.getString("idPalet",null);
        namaPalet = prefDetailBarangMasuk.getString("deskripsiPalet",null);
        ipAddress = prefDetailBarangMasuk.getString("ipAddress",null);
        gpio1 = prefDetailBarangMasuk.getString("gpio1","0");
        gpio2 = prefDetailBarangMasuk.getString("gpio2","0");
        gpio3 = prefDetailBarangMasuk.getString("gpio3","0");
        gpioStatus = prefDetailBarangMasuk.getString("gpioStatus",null);

        idPaletPref = pref.getString("idPaletPref","0");

        wvGPIO1 = (WebView) findViewById(R.id.in_wvGPIO1);
        wvGPIO1.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        wvGPIO2 = (WebView) findViewById(R.id.in_wvGPIO2);
        wvGPIO2.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        wvGPIO3 = (WebView) findViewById(R.id.in_wvGPIO3);
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

        etIdPalet = findViewById(R.id.id_palet);

        TextView tvItemCode = (TextView) findViewById(R.id.barangMasuk_itemCodeValue);
        tvItemCode.setText(String.valueOf(itemCode));

        TextView tvItemName = (TextView) findViewById(R.id.barangMasuk_itemNameValue);
        tvItemName.setText(String.valueOf(itemName));

        TextView tyQtyInput = (TextView) findViewById(R.id.barangMasuk_qtyInputValue);
        tyQtyInput.setText(String.valueOf(qtyInput));

        if (Integer.parseInt(qtyTotal) >= Integer.parseInt(qtyMax)){
            TextView tvQtyTotal = (TextView) findViewById(R.id.barangMasuk_totalQtyValue);
            tvQtyTotal.setText(String.valueOf(qtyTotal + " (Overload Max " + qtyMax + ")"));
            tvQtyTotal.setTextColor(Color.parseColor("#ff3030"));
        }else{
            TextView tvQtyTotal = (TextView) findViewById(R.id.barangMasuk_totalQtyValue);
            tvQtyTotal.setText(String.valueOf(qtyTotal + " (Max " + qtyMax + ")"));
            tvQtyTotal.setTextColor(Color.parseColor("#616161"));
        }

        TextView tvNamaLemari = (TextView) findViewById(R.id.barangMasuk_namaLemariValue);
        tvNamaLemari.setText(String.valueOf(namaLemari));

        TextView tvNamaRak = (TextView) findViewById(R.id.barangMasuk_namaRakValue);
        tvNamaRak.setText(String.valueOf(namaRak));

        TextView tvNamaPalet = (TextView) findViewById(R.id.barangMasuk_namaPaletValue);
        tvNamaPalet.setText(String.valueOf(namaPalet));

        txtError = (TextView) findViewById(R.id.barangMasuk_txt_error);
        txtError.setVisibility(View.INVISIBLE);

        txtErrorManual = (TextView) findViewById(R.id.txt_error_input_manual);
        txtErrorManual.setVisibility(View.INVISIBLE);

        btnScanRak = (Button)findViewById(R.id.barangMasuk_btnDetailScan);
        btnScanRak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DetailBarangMasukActivity.this, ScanRakBarangMasukActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnKonfirmasi = (Button)findViewById(R.id.barangMasuk_btnDetailKonfirmasi);
        btnKonfirmasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                confirmationMessageComplete();
            }
        });

        btnKonfirmasiManual = (Button)findViewById(R.id.btn_konfirmasi_manual);
        btnKonfirmasiManual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                idPaletManualCheck();
            }
        });

        btnLokasi = (CardView)findViewById(R.id.barangMasuk_btnLokasi);
        btnLokasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefLokasi.edit();
                editor.putString("namaLemari",namaLemari);
                editor.commit();

                Intent i = new Intent(DetailBarangMasukActivity.this, LokasiActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        if (idPaletPref.equals("1")){
            btnKonfirmasi.setVisibility(View.VISIBLE);
            btnScanRak.setVisibility(View.GONE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("Rak telah discan !");
            txtError.setTextColor(Color.parseColor("#8bc34a"));
            txtErrorManual.setVisibility(View.GONE);
            btnKonfirmasiManual.setVisibility(View.GONE);
            etIdPalet.setVisibility(View.GONE);
        }else{
            btnKonfirmasi.setVisibility(View.GONE);
            btnScanRak.setVisibility(View.VISIBLE);
            txtError.setText("Rak belum discan !");
            txtError.setTextColor(Color.parseColor("#ff3030"));
            txtError.setVisibility(View.VISIBLE);
        }

        bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(this);
        bottom_menu.getMenu().getItem(0).setChecked(true);
    }

    public void confirmationMessageComplete() {
        new SweetAlertDialog(DetailBarangMasukActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Konfirmasi")
                .setContentText("Ingin menyimpan data?")
                .setConfirmText("Simpan")
                .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        btnKonfirmasi.setClickable(false);
                        updatePaletGpioOffBarangMasuk(idBarang, idPalet, itemCode, qtyInput, purchaseOrder, deliveryNote);
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

    public void idPaletManualCheck() {
        if (etIdPalet != null) {
            String idPaletInput = etIdPalet.getText().toString().trim();

            txtErrorManual.setVisibility(View.VISIBLE); // Set visible sebelum menampilkan teks

            if (TextUtils.isEmpty(idPaletInput)) {
                txtErrorManual.setText("Input Id palet terlebih dahulu !");
                txtErrorManual.setTextColor(Color.parseColor("#ff3030"));
                txtError.setText("Rak belum discan !");
                txtError.setTextColor(Color.parseColor("#ff3030"));
            } else if (idPalet != null && idPalet.equals(idPaletInput)) {
                txtErrorManual.setVisibility(View.INVISIBLE);
                txtError.setText("Rak Sesuai !");
                txtError.setTextColor(Color.parseColor("#8bc34a"));

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("idPaletPref","1");
                editor.commit();

                SharedPreferences.Editor editorDetail = prefDetailBarangMasuk.edit();
                editorDetail.putString("gpioStatus","0");
                editorDetail.commit();

                confirmationMessageComplete();
            } else {
                txtErrorManual.setText("Rak tidak sesuai !");
                txtErrorManual.setTextColor(Color.parseColor("#ff3030"));
                txtError.setText("Rak belum discan !");
                txtError.setTextColor(Color.parseColor("#ff3030"));
            }
        }
    }

    public void confirmationMessageCancel() {
        new SweetAlertDialog(DetailBarangMasukActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Peringatan")
                .setContentText("Ingin membatalkan proses?")
                .setConfirmText("Ya")
                .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                            wvGPIO1.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio1+"&status=dl");
                            wvGPIO2.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio2+"&status=dl");
                            wvGPIO3.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio3+"&status=dl");
                            completeBarangMasuk(itemCode);

                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("itemCode","");
                            editor.putString("itemName","");
                            editor.putString("purchaseOrder","");
                            editor.putString("deliveryNote","");
                            editor.putString("itemJumlah","");

                            editor.commit();

                            startActivity(new Intent(DetailBarangMasukActivity.this, DetailScanBarangMasukActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                })
                .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public void updatePaletGpioOffBarangMasuk(String idBarang, String idPalet, String itemCode, String qtyInput, String purchaseOrder, String deliveryNote) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.updatePaletGpioOffBarangMasuk(idBarang, idPalet, itemCode, qtyInput, purchaseOrder, deliveryNote);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                final StatusResponse statusResponse = response.body();

                if (statusResponse.getResponses()) {

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("onceCompleted","1");
                    editor.putString("itemCode","");
                    editor.putString("itemName","");
                    editor.putString("itemJumlah","");
                    editor.putString("idPaletPref","0");
                    editor.putString("sukses","1");
                    editor.commit();

                    wvGPIO1.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio1+"&status=dl");
                    wvGPIO2.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio2+"&status=dl");
                    wvGPIO3.loadUrl("http://"+ipAddress+"/gpio.php?pin="+gpio3+"&status=dl");

                    Intent i = new Intent(DetailBarangMasukActivity.this, DetailScanBarangMasukActivity.class);
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
                            Intent i = new Intent(DetailBarangMasukActivity.this, DetailBarangMasukActivity.class);
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
                        Intent i = new Intent(DetailBarangMasukActivity.this, DetailBarangMasukActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }, 8000);
            }
        });
    }

    public void completeBarangMasuk(String itemCode) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.completeBarangMasuk(itemCode);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                final StatusResponse statusResponse = response.body();

                if (statusResponse.getResponses()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {

            }
        });
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
                    confirmationMessageCancel();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Peringatan").setContentText("Harap selesaikan proses penyimpanan barang terlebih dahulu !").setConfirmText("OK").show();
    }

    @Override
    public void onBackPressed()
    {
        warningMessage();
    }
}