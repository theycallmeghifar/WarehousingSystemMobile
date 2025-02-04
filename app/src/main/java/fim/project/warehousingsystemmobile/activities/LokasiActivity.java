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

import fim.project.warehousingsystemmobile.R;

public class LokasiActivity extends AppCompatActivity {

    String lemari[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
    private TextView A, B, C, D, E, F, G, H, I, J, K, L;
    private String namaLemari;
    private Button btnKembali;
    private SharedPreferences prefLokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);

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
        namaLemari = prefLokasi.getString("namaLemari",null);

        A = (TextView) findViewById(R.id.lokasi_A);
        B = (TextView) findViewById(R.id.lokasi_B);
        C = (TextView) findViewById(R.id.lokasi_C);
        D = (TextView) findViewById(R.id.lokasi_D);
        E = (TextView) findViewById(R.id.lokasi_E);
        F = (TextView) findViewById(R.id.lokasi_F);
        G = (TextView) findViewById(R.id.lokasi_G);
        H = (TextView) findViewById(R.id.lokasi_H);
        I = (TextView) findViewById(R.id.lokasi_I);
        J = (TextView) findViewById(R.id.lokasi_J);
        K = (TextView) findViewById(R.id.lokasi_K);
        L = (TextView) findViewById(R.id.lokasi_L);

        for (String lemari_value: lemari) {
            if (lemari_value.equals(namaLemari)){
                if (lemari_value.equals("A")){
                    A.setBackgroundColor(Color.parseColor("#1565c0"));
                }else if (lemari_value.equals("B")){
                    B.setBackgroundColor(Color.parseColor("#1565c0"));
                }else if (lemari_value.equals("C")){
                    C.setBackgroundColor(Color.parseColor("#1565c0"));
                }else if (lemari_value.equals("D")){
                    D.setBackgroundColor(Color.parseColor("#1565c0"));
                }else if (lemari_value.equals("E")){
                    E.setBackgroundColor(Color.parseColor("#1565c0"));
                }else if (lemari_value.equals("F")){
                    F.setBackgroundColor(Color.parseColor("#1565c0"));
                }else if (lemari_value.equals("G")){
                    G.setBackgroundColor(Color.parseColor("#1565c0"));
                }else if (lemari_value.equals("H")){
                    H.setBackgroundColor(Color.parseColor("#1565c0"));
                }else if (lemari_value.equals("I")){
                    I.setBackgroundColor(Color.parseColor("#1565c0"));
                }else if (lemari_value.equals("J")){
                    J.setBackgroundColor(Color.parseColor("#1565c0"));
                }else if (lemari_value.equals("K")){
                    K.setBackgroundColor(Color.parseColor("#1565c0"));
                }else if (lemari_value.equals("L")){
                    L.setBackgroundColor(Color.parseColor("#1565c0"));
                }else{

                }
            }
        }

        btnKembali = (Button)this.findViewById(R.id.lokasi_btnKembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefLokasi.edit();
                editor.clear();
                editor.commit();

                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}