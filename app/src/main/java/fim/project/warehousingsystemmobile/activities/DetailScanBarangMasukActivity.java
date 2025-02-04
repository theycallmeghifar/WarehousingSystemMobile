package fim.project.warehousingsystemmobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.responses.ApiEndPoint;
import fim.project.warehousingsystemmobile.responses.DetailBarangMasukDataResponse;
import fim.project.warehousingsystemmobile.responses.ItemInTempResponse;
import fim.project.warehousingsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailScanBarangMasukActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom_menu;
    private SharedPreferences pref;
    private SharedPreferences prefDetailBarangMasuk;

    private String scanned;
    private String sukses;
    private String onceCompleted;
    private String id_barang;
    private String jumlah;
    private String purchase_order;
    private String delivery_note;
    private String nama_barang;
    private EditText etJumlah;
    private EditText etIdBarang;
    private EditText etPurchaseOrder;
    private EditText etDeliveryNote;
    private EditText etNamaBarang;
    private TextInputLayout etNamaBarangParent;
    private Button btnKonfirmasi;
    private TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scan_barang_masuk);

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
        id_barang = pref.getString("itemCode","");
        sukses = pref.getString("sukses","0");
        onceCompleted = pref.getString("onceCompleted","");
        jumlah = pref.getString("itemJumlah","");
        purchase_order = pref.getString("purchaseOrder","");
        delivery_note = pref.getString("deliveryNote","");
        nama_barang = pref.getString("itemName","");
        scanned = pref.getString("scanned","0");

        etIdBarang = (EditText) findViewById(R.id.detail_id_barang_masuk);
        etIdBarang.setText(String.valueOf(id_barang));
        etIdBarang.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        etPurchaseOrder = (EditText) findViewById(R.id.purchase_order_barang_masuk);
        etPurchaseOrder.setText(String.valueOf(purchase_order));

        etJumlah = (EditText) findViewById(R.id.detail_jumlah_barang_masuk);
        etJumlah.setText(String.valueOf(jumlah));

        etDeliveryNote = (EditText) findViewById(R.id.delivery_note_barang_masuk);
        etDeliveryNote.setText(String.valueOf(delivery_note));

        etNamaBarang = (EditText) findViewById(R.id.detail_nama_barang_masuk);
        etNamaBarang.setText(String.valueOf(nama_barang));

        etNamaBarangParent = (TextInputLayout) findViewById(R.id.detail_nama_barang_masuk_parent);

        Button btnSelesai = (Button) findViewById(R.id.btn_selesai_barang_masuk);
        Button btnScanItem = (Button) findViewById(R.id.btn_scan_item_barang_masuk);
        Button btnScanPurchaseOrder = (Button) findViewById(R.id.btn_scan_purchase_order_barang_masuk);
        Button btnScanDeliveryNote = (Button) findViewById(R.id.btn_scan_delivery_note_barang_masuk);
        Button btnCheckItem = (Button) findViewById(R.id.btn_check_item_barang_masuk);

        if (sukses.equals("1")){
            successMessage();

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("sukses","0");
            editor.commit();
        }else{

        }

        etIdBarang.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    btnCheckItem.setVisibility(View.VISIBLE);
                    btnScanItem.setVisibility(View.GONE);
                }else{
                    btnCheckItem.setVisibility(View.GONE);
                    btnScanItem.setVisibility(View.VISIBLE);
                }
                etNamaBarangParent.setVisibility(View.GONE);
            }
        });

        if (scanned.equals("1")){
            etIdBarang.setText(id_barang);
            etNamaBarangParent.setVisibility(View.VISIBLE);
        }else{

        }

        if (onceCompleted.equals("1")){
            btnSelesai.setVisibility(View.VISIBLE);
        }else{
            btnSelesai.setVisibility(View.GONE);
        }

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(DetailScanBarangMasukActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Ingin menyelesaikan proses?")
                        .setConfirmText("Selesai")
                        .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                        .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("sukses","1");
                                editor.commit();

                                startActivity(new Intent(DetailScanBarangMasukActivity.this, BarangMasukActivity.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

        btnScanItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("deliveryNote", etDeliveryNote.getText().toString());
                editor.putString("purchaseOrder", etPurchaseOrder.getText().toString());
                editor.putString("scanned", "0");
                editor.commit();

                startActivity(new Intent(DetailScanBarangMasukActivity.this, ScanBarangMasukActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnCheckItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem(etIdBarang.getText().toString());
            }
        });

        btnScanPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("deliveryNote", etDeliveryNote.getText().toString());
                editor.putString("purchaseOrder", etPurchaseOrder.getText().toString());
                editor.commit();

                startActivity(new Intent(DetailScanBarangMasukActivity.this, ScanPurchaseOrderBarangMasukActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnScanDeliveryNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("deliveryNote", etDeliveryNote.getText().toString());
                editor.putString("purchaseOrder", etPurchaseOrder.getText().toString());
                editor.commit();

                startActivity(new Intent(DetailScanBarangMasukActivity.this, ScanDeliveryNoteBarangMasukActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnKonfirmasi = (Button)findViewById(R.id.btn_detail_konfirmasi_barang_masuk);
        btnKonfirmasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("purchaseOrder", etPurchaseOrder.getText().toString());
                editor.putString("deliveryNote", etDeliveryNote.getText().toString());
                editor.putString("itemJumlah", etJumlah.getText().toString());
                editor.commit();

                updatePaletGpioOnBarangMasuk(etIdBarang.getText().toString());
            }
        });

        txtError = (TextView) findViewById(R.id.txt_error_barangMasuk);
        txtError.setVisibility(View.INVISIBLE);

        bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(this);
        bottom_menu.getMenu().getItem(0).setChecked(true);
    }

    public void getItem(String itemCode) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<ItemInTempResponse> call = apiEndPoint.getItem(itemCode);

        call.enqueue(new Callback<ItemInTempResponse>() {
            @Override
            public void onResponse(Call<ItemInTempResponse> call, Response<ItemInTempResponse> response) {

                final ItemInTempResponse itemInTempResponse = response.body();

                if (itemInTempResponse.getResponses()) {

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("itemCode", itemInTempResponse.getItemCode());
                    editor.putString("itemName", itemInTempResponse.getItemName());
                    editor.putString("scanned", "1");
                    editor.commit();

                    etNamaBarang.setText(itemInTempResponse.getItemName());
                    etNamaBarangParent.setVisibility(View.VISIBLE);

                } else {
                    txtError.setVisibility(View.VISIBLE);
                    txtError.setText("Item tidak ditemukan !");

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
            public void onFailure(Call<ItemInTempResponse> call, Throwable t) {
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

    public void updatePaletGpioOnBarangMasuk(String itemCode) {

        if (etPurchaseOrder.getText().toString().equals("")){
            txtError.setText("Data tidak boleh ada yang kosong !");
            txtError.setVisibility(View.VISIBLE);
        }else if (etDeliveryNote.getText().toString().equals("")){
            txtError.setText("Data tidak boleh ada yang kosong !");
            txtError.setVisibility(View.VISIBLE);
        }else if (etIdBarang.getText().toString().equals("")){
            txtError.setText("Data tidak boleh ada yang kosong !");
            txtError.setVisibility(View.VISIBLE);
        }else if (etJumlah.getText().toString().equals("")){
            txtError.setText("Data tidak boleh ada yang kosong !");
            txtError.setVisibility(View.VISIBLE);
        }else if (etJumlah.getText().toString().equals("0")){
            txtError.setText("Data tidak boleh ada yang kosong !");
            txtError.setVisibility(View.VISIBLE);
        }else{
            ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
            Call<DetailBarangMasukDataResponse> call = apiEndPoint.updatePaletGpioOnBarangMasuk(itemCode);

            call.enqueue(new Callback<DetailBarangMasukDataResponse>() {
                @Override
                public void onResponse(Call<DetailBarangMasukDataResponse> call, Response<DetailBarangMasukDataResponse> response) {

                    final DetailBarangMasukDataResponse responseData = response.body();

                    if (responseData.getResponses()) {

                        SharedPreferences.Editor editor = prefDetailBarangMasuk.edit();
                        editor.clear();
                        editor.putString("idBarang",responseData.getIdBarang());
                        editor.putString("itemCode",responseData.getItemCode());
                        editor.putString("itemName",responseData.getItemName());
                        editor.putString("jumlahBarang",responseData.getJumlahItem());
                        editor.putString("maxBarang",responseData.getMaxBarang());
                        editor.putString("tanggalMasuk",responseData.getTanggalMasuk());
                        editor.putString("idPalet",responseData.getIdPalet());
                        editor.putString("deskripsiPalet",responseData.getDeskripsiPalet());
                        editor.putString("idRak",responseData.getIdRak());
                        editor.putString("deskripsiRak",responseData.getDeskripsiRak());
                        editor.putString("idLemari",responseData.getIdLemari());
                        editor.putString("deskripsiLemari",responseData.getDeskripsiLemari());
                        editor.putString("ipAddress",responseData.getIpAddress());
                        editor.putString("gpio1",responseData.getGpio1());
                        editor.putString("gpio2",responseData.getGpio2());
                        editor.putString("gpio3",responseData.getGpio3());
                        editor.putString("gpioStatus",responseData.getGpioStatus());

                        editor.commit();

                        Intent i = new Intent(DetailScanBarangMasukActivity.this, DetailBarangMasukActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    } else {
                        txtError.setText("Data tidak ditemukan / Data belum terdaftar pada rak !");
                        txtError.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<DetailBarangMasukDataResponse> call, Throwable t) {
                    txtError.setText("Koneksi Bermasalah !");
                    txtError.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void confirmationMessageCancel() {
        new SweetAlertDialog(DetailScanBarangMasukActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Peringatan")
                .setContentText("Ingin membatalkan proses?")
                .setConfirmText("Ya")
                .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("batal","1");
                        editor.commit();

                        startActivity(new Intent(DetailScanBarangMasukActivity.this, BarangMasukActivity.class));
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
                if (onceCompleted.equals("1")){
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
        switch (menuItem.getItemId()) {
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

    public void successMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Berhasil").setContentText("Barang masuk berhasil disimpan, silahkan lanjutkan proses penyimpanan atau memilih tombol selesai jika keseluruhan proses telah dilaksanakan.").show();
    }

    public void warningMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Peringatan").setContentText("Harap selesaikan proses penyimpanan barang terlebih dahulu !").setConfirmText("OK").show();
    }

    @Override
    public void onBackPressed()
    {
        if (onceCompleted.equals("1")){
            warningMessage();
        }else{
            confirmationMessageCancel();
        }
    }
}