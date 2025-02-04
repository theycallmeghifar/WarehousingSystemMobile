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
import android.text.InputFilter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.adapters.BarangAdapter;
import fim.project.warehousingsystemmobile.adapters.HistoryAdapter;
import fim.project.warehousingsystemmobile.responses.ApiEndPoint;
import fim.project.warehousingsystemmobile.responses.BarangDataResponse;
import fim.project.warehousingsystemmobile.responses.BarangListResponse;
import fim.project.warehousingsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom_menu;
    private SharedPreferences prefBarang;
    private SharedPreferences prefLogin;
    List<BarangDataResponse> barangDataResponsesList;
    BarangAdapter barangAdapter;

    @BindView(R.id.lv_barang)
    ListView lvBarangList;

    private String filter;
    private EditText etItemCode;
    private TextView txtErrorBarang;
    private ImageView btnFilter;
    private ImageView imgSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        ButterKnife.bind(this);

        // CUSTOM ACTION BAR FONT
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

            RelativeLayout layout = new RelativeLayout(this);
            layout.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));

            TextView titleTextView = new TextView(getApplicationContext());
            Typeface typeface = ResourcesCompat.getFont(this, R.font.bebasneue);
            titleTextView.setText("FIM WAREHOUSING");
            titleTextView.setTextSize(32);
            titleTextView.setTextColor(Color.WHITE);
            titleTextView.setTypeface(typeface, Typeface.NORMAL);

            RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            titleParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            titleTextView.setLayoutParams(titleParams);

            TextView versionTextView = new TextView(getApplicationContext());
            versionTextView.setText("versi 2.0");
            versionTextView.setTextSize(14);
            versionTextView.setTextColor(Color.WHITE);

            RelativeLayout.LayoutParams versionParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            versionParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            versionParams.addRule(RelativeLayout.CENTER_VERTICAL);
            versionTextView.setLayoutParams(versionParams);

            layout.addView(titleTextView);
            layout.addView(versionTextView);

            actionBar.setCustomView(layout);
        }

        prefLogin = getSharedPreferences("loginPref",MODE_PRIVATE);
        prefBarang = getSharedPreferences("barangPref",MODE_PRIVATE);

        btnFilter = (ImageView)findViewById(R.id.btn_filter_barang);
        btnFilter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                txtErrorBarang.setVisibility(View.VISIBLE);
                txtErrorBarang.setText("");
                filter = prefBarang.getString("filter","");
                if (filter.equals("")){
                    getBarang(etItemCode.getText().toString());
                    imgSearch.setVisibility(View.GONE);
                }else{
                    getBarang(etItemCode.getText().toString());
                }
            }
        });
        btnFilter.setClickable(false);

        txtErrorBarang = (TextView) findViewById(R.id.txtErrorBarang);
        imgSearch = (ImageView) findViewById(R.id.imgSearchBarang);
        etItemCode = (EditText) findViewById(R.id.item_code_filter_barang);
        etItemCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                btnFilter.setClickable(true);
                etItemCode.setFocusableInTouchMode(true);
                imgSearch.setVisibility(View.GONE);
                return false;
            }
        });
        etItemCode.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        //Masukkan adapter ke listView
        barangAdapter = new BarangAdapter(this, barangDataResponsesList);
        registerForContextMenu(lvBarangList);

        lvBarangList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView itemCode = view.findViewById(R.id.txtItemCodeBarang);
                TextView itemName = view.findViewById(R.id.txtItemNameBarang);
                TextView jumlahItem = view.findViewById(R.id.txtJumlahItemBarang);
                TextView netQuantity = view.findViewById(R.id.txtNetQuantityBarang);
                TextView lemari = view.findViewById(R.id.txtNamaLemariBarang);
                TextView rak = view.findViewById(R.id.txtNamaRakBarang);
                TextView palet = view.findViewById(R.id.txtNamaPaletBarang);

                SharedPreferences.Editor editor = prefBarang.edit();
                editor.clear();
                editor.putString("filter",etItemCode.getText().toString());
                editor.putString("itemCodeLvBarang",itemCode.getText().toString());
                editor.putString("itemNameLvBarang",itemName.getText().toString());
                editor.putString("jumlahItemLvBarang",jumlahItem.getText().toString());
                editor.putString("netQuantityLvBarang",netQuantity.getText().toString());
                editor.putString("lemariLvBarang",lemari.getText().toString());
                editor.putString("rakLvBarang",rak.getText().toString());
                editor.putString("paletLvBarang",palet.getText().toString());

                editor.commit();

                Intent i = new Intent(BarangActivity.this, DetailBarangActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(this);
        bottom_menu.getMenu().getItem(3).setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        filter = prefBarang.getString("filter","");
        if (filter.equals("")){
            getBarang(etItemCode.getText().toString());
        }else{
            imgSearch.setVisibility(View.GONE);
            etItemCode.setText(filter);
            getBarang(filter);
        }
        barangAdapter.notifyDataSetChanged();
    }

    public void getBarang(String itemCode) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<BarangListResponse> call = apiEndPoint.getBarang(itemCode);

        call.enqueue(new Callback<BarangListResponse>() {
            @Override
            public void onResponse(Call<BarangListResponse> call, Response<BarangListResponse> response) {

                final BarangListResponse responseData = response.body();

                if (responseData.getResponses()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            barangDataResponsesList = responseData.getResult();
                            lvBarangList.setAdapter(new BarangAdapter(getApplicationContext(), barangDataResponsesList));
                            barangAdapter.notifyDataSetChanged();

                            if (barangDataResponsesList.size() == 0) {
                                txtErrorBarang.setText("Data tidak tersedia !");
                            } else {
                                txtErrorBarang.setVisibility(View.GONE);
                                txtErrorBarang.setText("");
                            }
                        }
                    });

                } else {
                    txtErrorBarang.setText("Data tidak tersedia !");
                }
            }

            @Override
            public void onFailure(Call<BarangListResponse> call, Throwable t) {
                txtErrorBarang.setText("Koneksi bermasalah !");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int getId = item.getItemId();
        switch (getId) {
            case R.id.menu_refresh:
                SharedPreferences.Editor editor = prefBarang.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(this, BarangActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
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
        new SweetAlertDialog(BarangActivity.this, SweetAlertDialog.WARNING_TYPE)
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

                        startActivity(new Intent(BarangActivity.this, LoginActivity.class));
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