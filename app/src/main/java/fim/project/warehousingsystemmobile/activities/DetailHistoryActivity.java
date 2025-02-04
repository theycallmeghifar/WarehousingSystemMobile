package fim.project.warehousingsystemmobile.activities;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fim.project.warehousingsystemmobile.R;

public class DetailHistoryActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom_menu;
    private SharedPreferences prefHistory;
    private SharedPreferences prefLokasi;

    private String itemCode;
    private String itemName;
    private String namaPalet;
    private String jumlahMasuk;
    private String jumlahKeluar;
    private String namaLemari;
    private String namaRak;
    private String tanggal;
    private String mainItemCode;
    private String mainItemName;
    private String purchaseOrder;
    private String deliveryNote;
    private Button btnKembali;
    private CardView btnLokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

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
        prefHistory = getSharedPreferences("historyPref",MODE_PRIVATE);
        itemCode = prefHistory.getString("itemCodeLv","");
        itemName = prefHistory.getString("itemNameLv","");
        namaPalet = prefHistory.getString("namaPaletLv","");
        jumlahMasuk = prefHistory.getString("jumlahMasukLv","0");
        jumlahKeluar = prefHistory.getString("jumlahKeluarLv","0");
        namaLemari = prefHistory.getString("namaLemariLv","");
        namaRak = prefHistory.getString("namaRakLv","");
        tanggal = prefHistory.getString("tanggalLv","");
        mainItemCode = prefHistory.getString("mainItemCodeLv","");
        mainItemName = prefHistory.getString("mainItemNameLv","");
        purchaseOrder = prefHistory.getString("purchaseOrderLv","");
        deliveryNote = prefHistory.getString("deliveryNoteLv","");

        TextView tv_itemCode = (TextView) findViewById(R.id.history_itemCode);
        tv_itemCode.setText(String.valueOf(itemCode));

        TextView tv_itemName = (TextView) findViewById(R.id.history_itemName);
        tv_itemName.setText(String.valueOf(itemName));

        TextView tv_namaPalet = (TextView) findViewById(R.id.history_namaPalet);
        tv_namaPalet.setText(String.valueOf(namaPalet));

        TextView tv_jumlahMasuk = (TextView) findViewById(R.id.history_jumlah_masuk);
        tv_jumlahMasuk.setText(String.valueOf(jumlahMasuk));

        TextView tv_jumlahKeluar = (TextView) findViewById(R.id.history_jumlah_keluar);
        tv_jumlahKeluar.setText(String.valueOf(jumlahKeluar));

        TextView tv_namaLemari = (TextView) findViewById(R.id.history_namaLemari);
        tv_namaLemari.setText(String.valueOf(namaLemari));

        TextView tv_namaRak = (TextView) findViewById(R.id.history_namaRak);
        tv_namaRak.setText(String.valueOf(namaRak));

        TextView tv_tanggal = (TextView) findViewById(R.id.history_tanggal);
        tv_tanggal.setText(String.valueOf(tanggal));

        TextView tv_mainItemCode = (TextView) findViewById(R.id.history_mainItemCode);
        tv_mainItemCode.setText(String.valueOf(mainItemCode));

        TextView tv_mainItemName = (TextView) findViewById(R.id.history_mainItemName);
        tv_mainItemName.setText(String.valueOf(mainItemName));

        TextView tv_purchaseOrder = (TextView) findViewById(R.id.history_purchaseOrder);
        tv_purchaseOrder.setText(String.valueOf(purchaseOrder));

        TextView tv_deliveryNote = (TextView) findViewById(R.id.history_deliveryNote);
        tv_deliveryNote.setText(String.valueOf(deliveryNote));

        btnKembali = (Button) findViewById(R.id.history_btnKembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailHistoryActivity.this, HistoryActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnLokasi = (CardView)findViewById(R.id.history_btnLokasi);
        btnLokasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefLokasi.edit();
                editor.putString("namaLemari",namaLemari);
                editor.commit();

                Intent i = new Intent(DetailHistoryActivity.this, LokasiActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        RelativeLayout rv_mainItemCode = (RelativeLayout) findViewById(R.id.history_mainItemCodeParent);
        RelativeLayout rv_mainItemName = (RelativeLayout) findViewById(R.id.history_mainItemNameParent);
        RelativeLayout rv_purchaseOrder = (RelativeLayout) findViewById(R.id.history_purchaseOrderParent);
        RelativeLayout rv_deliveryNote = (RelativeLayout) findViewById(R.id.history_deliveryNoteParent);
        RelativeLayout rv_jumlahMasuk = (RelativeLayout) findViewById(R.id.history_jumlahMasukParent);
        RelativeLayout rv_jumlahKeluar = (RelativeLayout) findViewById(R.id.history_jumlahKeluarParent);
        RelativeLayout rv_lemari = (RelativeLayout) findViewById(R.id.history_lemari_rv);
        RelativeLayout rv_rak = (RelativeLayout) findViewById(R.id.history_rak_rv);
        RelativeLayout rv_palet = (RelativeLayout) findViewById(R.id.history_palet_rv);

        if (jumlahKeluar.equals("0")){
            rv_mainItemCode.setVisibility(View.GONE);
            rv_mainItemName.setVisibility(View.GONE);
            rv_purchaseOrder.setVisibility(View.VISIBLE);
            rv_deliveryNote.setVisibility(View.VISIBLE);
            rv_jumlahMasuk.setVisibility(View.VISIBLE);
            rv_jumlahKeluar.setVisibility(View.GONE);
        }else{
            rv_purchaseOrder.setVisibility(View.GONE);
            rv_deliveryNote.setVisibility(View.GONE);
            rv_mainItemCode.setVisibility(View.VISIBLE);
            rv_mainItemName.setVisibility(View.VISIBLE);
            rv_jumlahMasuk.setVisibility(View.GONE);
            rv_jumlahKeluar.setVisibility(View.VISIBLE);
        }

        if (namaLemari.equals("") | namaLemari.equals("0")){
            btnLokasi.setVisibility(View.GONE);
            rv_lemari.setVisibility(View.GONE);
            rv_rak.setVisibility(View.GONE);
            rv_palet.setVisibility(View.GONE);
        }else{

        }

        bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(this);
        bottom_menu.getMenu().getItem(2).setChecked(true);
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
    public void onBackPressed() {
        Intent c = new Intent(this, HistoryActivity.class);
        startActivity(c);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}