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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.responses.ApiEndPoint;
import fim.project.warehousingsystemmobile.responses.StatusResponse;
import fim.project.warehousingsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBarangUpdateActivity extends AppCompatActivity {

    private SharedPreferences prefBarang, prefLogin;

    private EditText et_ubahJumlahItem;
    private TextView txtError;
    private String itemCode, itemName, jumlahItem, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang_update);

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

        prefLogin = getSharedPreferences("loginPref",MODE_PRIVATE);
        prefBarang = getSharedPreferences("barangPref",MODE_PRIVATE);
        itemCode = prefBarang.getString("itemCodeLvBarang","0");
        itemName = prefBarang.getString("itemNameLvBarang","0");
        jumlahItem = prefBarang.getString("jumlahItemLvBarang","0");
        username = prefLogin.getString("username","0");

        TextView tv_itemCode = (TextView) findViewById(R.id.item_code_detail_barang_ubah);
        tv_itemCode.setText(String.valueOf(itemCode));

        TextView tv_itemName = (TextView) findViewById(R.id.item_name_detail_barang_ubah);
        tv_itemName.setText(String.valueOf(itemName));

        TextView tv_jumlahItem = (TextView) findViewById(R.id.jumlah_item_detail_barang_ubah);
        tv_jumlahItem.setText(String.valueOf(jumlahItem));

        txtError = (TextView) findViewById(R.id.txt_error_detail_barang_ubah);
        txtError.setVisibility(View.INVISIBLE);

        et_ubahJumlahItem = (EditText) findViewById(R.id.ubah_jumlah_detail_barang_ubah);

        Button btn_konfirmasi = (Button) findViewById(R.id.btn_konfirmasi_detail_barang_ubah);
        btn_konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_ubahJumlahItem.getText().toString().equals("")){
                    txtError.setText("Jumlah tidak boleh kosong !");
                    txtError.setVisibility(View.VISIBLE);
                }else {
                    confirmationMessageUpdate();
                }
            }
        });
    }

    public void confirmationMessageUpdate() {
        new SweetAlertDialog(DetailBarangUpdateActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Peringatan")
                .setContentText("Ingin mengubah jumlah?")
                .setConfirmText("Ya")
                .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        ubahJumlahItem(itemCode, jumlahItem, et_ubahJumlahItem.getText().toString(), username);

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
                    startActivity(new Intent(DetailBarangUpdateActivity.this, DetailBarangActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ubahJumlahItem(String itemCode, String jumlahSebelum, String jumlahSesudah, String username) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.ubahJumlahItem(itemCode, jumlahSebelum, jumlahSesudah, username);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                final StatusResponse statusResponse = response.body();

                if (statusResponse.getResponses()) {

                    SharedPreferences.Editor editor = prefBarang.edit();
                    editor.putString("jumlahItemLvBarang", et_ubahJumlahItem.getText().toString());
                    editor.putString("suksesUbahJumlah", "1");
                    editor.commit();

                    startActivity(new Intent(DetailBarangUpdateActivity.this, DetailBarangActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else {
                    txtError.setText("Gagal mengubah jumlah item !");
                    txtError.setTextColor(Color.parseColor("#ff3030"));
                    txtError.setVisibility(View.VISIBLE);

                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(DetailBarangUpdateActivity.this, DetailBarangUpdateActivity.class);
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
                        Intent i = new Intent(DetailBarangUpdateActivity.this, DetailBarangUpdateActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }, 8000);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent c = new Intent(this, DetailBarangActivity.class);
        startActivity(c);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}