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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import fim.project.warehousingsystemmobile.R;

public class BarangKeluarActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

        private BottomNavigationView bottom_menu;
        private Button btnScanBarcode;
        private Button btnInputManual;
        private SharedPreferences pref;
        private SharedPreferences prefDetailBarangKeluar;
        private SharedPreferences prefCancelDisable;
        private SharedPreferences prefDetailBarangKeluarListView;
        private SharedPreferences prefLogin;

        private String sukses, batal;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_barang_keluar);

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

                bottom_menu = findViewById(R.id.bottom_menu);
                bottom_menu.setOnNavigationItemSelectedListener(this);
                bottom_menu.getMenu().getItem(1).setChecked(true);

                pref = getSharedPreferences("scan_barang_keluar",MODE_PRIVATE);
                prefLogin = getSharedPreferences("loginPref",MODE_PRIVATE);
                prefCancelDisable = getSharedPreferences("prefCancelDisable",MODE_PRIVATE);
                prefDetailBarangKeluar = getSharedPreferences("detail_barang_keluar",MODE_PRIVATE);
                prefDetailBarangKeluarListView = getSharedPreferences("detail_barang_keluar_list_view",MODE_PRIVATE);

                sukses = pref.getString("sukses","0");
                batal = pref.getString("batal","0");

                if (sukses.equals("1")){
                        successMessage();

                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                }else{

                }

                if (batal.equals("1")){
                        cancelMessage();

                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                }else{

                }

                btnScanBarcode = findViewById(R.id.btn_scan_barangkeluar);
                btnScanBarcode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                SharedPreferences.Editor editor = pref.edit();
                                editor.clear();
                                editor.commit();

                                SharedPreferences.Editor editorCancel = prefCancelDisable.edit();
                                editorCancel.clear();
                                editorCancel.commit();

                                SharedPreferences.Editor editor_detail = prefDetailBarangKeluar.edit();
                                editor_detail.clear();
                                editor_detail.commit();

                                SharedPreferences.Editor editor_lv = prefDetailBarangKeluarListView.edit();
                                editor_lv.putString("lvGpioStatus","0");
                                editor_lv.commit();

                                startActivity(new Intent(BarangKeluarActivity.this, ScanBarangKeluarActivity.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                });

                btnInputManual = findViewById(R.id.btn_inputManual_barangkeluar);
                btnInputManual.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                SharedPreferences.Editor editor = pref.edit();
                                editor.clear();
                                editor.putString("manual","1");
                                editor.commit();

                                SharedPreferences.Editor editorCancel = prefCancelDisable.edit();
                                editorCancel.clear();
                                editorCancel.commit();

                                SharedPreferences.Editor editor_detail = prefDetailBarangKeluar.edit();
                                editor_detail.clear();
                                editor_detail.commit();

                                SharedPreferences.Editor editor_lv = prefDetailBarangKeluarListView.edit();
                                editor_lv.putString("lvGpioStatus","0");
                                editor_lv.commit();

                                startActivity(new Intent(BarangKeluarActivity.this, DetailScanBarangKeluarActivity.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                });
        }

        public void successMessage() {
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Berhasil").setContentText("Proses barang keluar telah selesai !").show();
        }

        public void cancelMessage() {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Proses Batal").setContentText("Proses barang keluar dibatalkan !").show();
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
                new SweetAlertDialog(BarangKeluarActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Peringatan")
                        .setContentText("Apakah anda ingin sign out?")
                        .setConfirmText("Ya")
                        .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                        .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();

                                        SharedPreferences.Editor editor = prefLogin.edit();
                                        editor.putString("login","0");

                                        editor.commit();

                                        startActivity(new Intent(BarangKeluarActivity.this, LoginActivity.class));
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
}