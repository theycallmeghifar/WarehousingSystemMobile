package fim.project.warehousingsystemmobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fim.project.warehousingsystemmobile.R;

public class DetailBarangActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom_menu;
    private SharedPreferences prefBarang;
    private SharedPreferences prefLokasi;

    private String itemCode, itemName, jumlahItem, netQuantity, namaLemari, namaRak, namaPalet, suksesUbahJumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

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
        prefBarang = getSharedPreferences("barangPref",MODE_PRIVATE);
        itemCode = prefBarang.getString("itemCodeLvBarang","0");
        itemName = prefBarang.getString("itemNameLvBarang","0");
        jumlahItem = prefBarang.getString("jumlahItemLvBarang","0");
        netQuantity = prefBarang.getString("netQuantityLvBarang","0");
        namaLemari = prefBarang.getString("lemariLvBarang","0");
        namaRak = prefBarang.getString("rakLvBarang","0");
        namaPalet = prefBarang.getString("paletLvBarang","0");
        suksesUbahJumlah = prefBarang.getString("suksesUbahJumlah","0");

        if (suksesUbahJumlah.equals("1")){
            successMessage();

            SharedPreferences.Editor editor = prefBarang.edit();
            editor.putString("suksesUbahJumlah", "0");
            editor.commit();
        }else{

        }

        RelativeLayout rv_lemari = (RelativeLayout) findViewById(R.id.detail_barang_lemari_rv);
        RelativeLayout rv_rak = (RelativeLayout) findViewById(R.id.detail_barang_rak_rv);
        RelativeLayout rv_palet = (RelativeLayout) findViewById(R.id.detail_barang_palet_rv);

        TextView tv_itemCode = (TextView) findViewById(R.id.detail_barang_itemCode);
        tv_itemCode.setText(String.valueOf(itemCode));

        TextView tv_itemName = (TextView) findViewById(R.id.detail_barang_itemName);
        tv_itemName.setText(String.valueOf(itemName));

        TextView tv_jumlahItem = (TextView) findViewById(R.id.detail_barang_jumlah_item);
        tv_jumlahItem.setText(String.valueOf(jumlahItem));

        TextView tv_netQuantity = (TextView) findViewById(R.id.detail_barang_netQuantity);
        tv_netQuantity.setText(String.valueOf(netQuantity));

        TextView tv_namaPalet = (TextView) findViewById(R.id.detail_barang_namaPalet);
        tv_namaPalet.setText(String.valueOf(namaPalet));

        TextView tv_namaLemari = (TextView) findViewById(R.id.detail_barang_namaLemari);
        tv_namaLemari.setText(String.valueOf(namaLemari));

        TextView tv_namaRak = (TextView) findViewById(R.id.detail_barang_namaRak);
        tv_namaRak.setText(String.valueOf(namaRak));

        CardView detail_barang_btnUbahQty = (CardView) findViewById(R.id.detail_barang_btnUbahQty);
        detail_barang_btnUbahQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(DetailBarangActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Peringatan")
                        .setContentText("Anda akan mengubah Qty yang sudah ada dalam sistem, mohon dipastikan dengan benar jumlah aktual yang ada di gudang !")
                        .setConfirmText("Ubah")
                        .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                        .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                Intent i = new Intent(DetailBarangActivity.this, DetailBarangUpdateActivity.class);
                                startActivity(i);
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

        Button btnKembali = (Button) findViewById(R.id.detail_barang_btnKembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBarangActivity.this, BarangActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        CardView btnLokasi = (CardView)findViewById(R.id.detail_barang_btnLokasi);
        btnLokasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefLokasi.edit();
                editor.putString("namaLemari",namaLemari);
                editor.commit();

                Intent i = new Intent(DetailBarangActivity.this, LokasiActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        if (namaLemari.equals("") | namaLemari.equals("0")){
            btnLokasi.setVisibility(View.GONE);
            rv_lemari.setVisibility(View.GONE);
            rv_rak.setVisibility(View.GONE);
            rv_palet.setVisibility(View.GONE);
        }else{

        }

        bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(this);
        bottom_menu.getMenu().getItem(3).setChecked(true);
    }

    public void successMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Berhasil").setContentText("Ubah jumlah berhasil !").show();
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
        Intent c = new Intent(this, BarangActivity.class);
        startActivity(c);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}