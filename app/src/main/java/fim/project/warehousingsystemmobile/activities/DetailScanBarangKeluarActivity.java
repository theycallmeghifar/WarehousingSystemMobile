package fim.project.warehousingsystemmobile.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.responses.ApiEndPoint;
import fim.project.warehousingsystemmobile.responses.DetailScanBarangKeluarResponse;
import fim.project.warehousingsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailScanBarangKeluarActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom_menu;
    private SharedPreferences pref;
    private SharedPreferences prefDetailBarangKeluar;

    private String manual;
    private String id_barang;
    private String nama_barang;
    private EditText etJumlah;
    private Button btnKonfirmasi;
    private TextView txtError;
    private EditText etIdBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scan_barang_keluar);

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

        pref = getSharedPreferences("scan_barang_keluar",MODE_PRIVATE);
        prefDetailBarangKeluar = getSharedPreferences("detail_barang_keluar",MODE_PRIVATE);
        id_barang = pref.getString("mainItemCode","");
        nama_barang = pref.getString("mainItemName","");
        manual = pref.getString("manual","0");

        etIdBarang = (EditText) findViewById(R.id.detail_id_barang_keluar);
        etIdBarang.setText(String.valueOf(id_barang));
        etIdBarang.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        EditText etNamaBarang = (EditText) findViewById(R.id.detail_nama_barang_keluar);
        etNamaBarang.setText(String.valueOf(nama_barang));

        TextInputLayout etNamaBarangParent = (TextInputLayout) findViewById(R.id.detail_nama_barang_keluar_parent);

        if (manual.equals("1")){
            etNamaBarangParent.setVisibility(View.GONE);
        }else{
            etNamaBarangParent.setVisibility(View.VISIBLE);
            etIdBarang.setFocusable(false);
            etNamaBarang.setFocusable(false);
        }

        etJumlah = (EditText) findViewById(R.id.detail_jumlah_barang_keluar);

        btnKonfirmasi = (Button)findViewById(R.id.btn_detail_konfirmasi_barang_keluar);
        btnKonfirmasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("itemJumlah", etJumlah.getText().toString());
                editor.commit();

                updatePaletGpioOnBarangKeluar(etIdBarang.getText().toString());
            }
        });

        txtError = (TextView) findViewById(R.id.txt_error_barangKeluar);
        txtError.setVisibility(View.INVISIBLE);

        bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(this);
        bottom_menu.getMenu().getItem(1).setChecked(true);
    }

    public void updatePaletGpioOnBarangKeluar(String mainItemCode) {

        if (etJumlah.getText().toString().equals("") && etIdBarang.getText().toString().equals("")){
            txtError.setText("ID dan Jumlah tidak boleh kosong !");
            txtError.setVisibility(View.VISIBLE);
        }else if (etIdBarang.getText().toString().equals("")){
            txtError.setText("Masukan ID !");
            txtError.setVisibility(View.VISIBLE);
        }else if (etJumlah.getText().toString().equals("")){
            txtError.setText("Masukan Jumlah !");
            txtError.setVisibility(View.VISIBLE);
        }else if (etJumlah.getText().toString().equals("0")){
            txtError.setText("Jumlah tidak boleh angka 0 !");
            txtError.setVisibility(View.VISIBLE);
        }else{
            ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
            Call<DetailScanBarangKeluarResponse> call = apiEndPoint.updatePaletGpioOnBarangKeluar(mainItemCode);

            call.enqueue(new Callback<DetailScanBarangKeluarResponse>() {
                @Override
                public void onResponse(Call<DetailScanBarangKeluarResponse> call, Response<DetailScanBarangKeluarResponse> response) {

                    final DetailScanBarangKeluarResponse statusResponse = response.body();

                    if (statusResponse.getResponses()) {

                        SharedPreferences.Editor editor = prefDetailBarangKeluar.edit();
                        editor.putString("mainItemCode",etIdBarang.getText().toString());
                        editor.putString("mainItemJumlahInput",etJumlah.getText().toString());
                        editor.putString("minQuantity", statusResponse.getMinQuantity());

                        editor.commit();

                        Intent i = new Intent(DetailScanBarangKeluarActivity.this, DetailBarangKeluarActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    } else {
                        txtError.setText("Data tidak ditemukan !");
                        txtError.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<DetailScanBarangKeluarResponse> call, Throwable t) {
                    txtError.setText("Koneksi Bermasalah !");
                    txtError.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.bottombar_barangMasuk:
                Intent a = new Intent(this, BarangMasukActivity.class);
                startActivity(a);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.bottombar_barangKeluar:
                Intent c = new Intent(this, BarangKeluarActivity.class);
                startActivity(c);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.bottombar_history:
                Intent h = new Intent(this, HistoryActivity.class);
                startActivity(h);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.bottombar_barang:
                Intent b = new Intent(this, BarangActivity.class);
                startActivity(b);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        Intent c = new Intent(this, BarangKeluarActivity.class);
        startActivity(c);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}