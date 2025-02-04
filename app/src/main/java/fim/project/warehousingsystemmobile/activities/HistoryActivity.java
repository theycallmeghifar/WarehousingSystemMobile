package fim.project.warehousingsystemmobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.adapters.HistoryAdapter;
import fim.project.warehousingsystemmobile.responses.ApiEndPoint;
import fim.project.warehousingsystemmobile.responses.HistoryDataResponse;
import fim.project.warehousingsystemmobile.responses.HistoryListResponse;
import fim.project.warehousingsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom_menu;
    List<HistoryDataResponse> mHistoryDataResponseList;

    @BindView(R.id.lv_history)
    ListView lvHistoryList;

    private int mYear, mMonth, mDay, mHour, mMinute;
    HistoryAdapter historyAdapter;
    private SharedPreferences prefHistory;
    private EditText etTanggalAwal;
    private EditText etTanggalAkhir;
    private ImageView btnFilter;
    private String filterAwal;
    private String filterAkhir;
    private ImageView imgHistory;
    private SharedPreferences prefLogin;

    private TextView txtErrorHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

        //CUSTOM ACTION BAR FONT
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
        prefHistory = getSharedPreferences("historyPref",MODE_PRIVATE);

        txtErrorHistory = (TextView) findViewById(R.id.txtErrorHistory);
        imgHistory = (ImageView) findViewById(R.id.imgHistory);

        btnFilter = (ImageView)findViewById(R.id.btn_filter_history);
        btnFilter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                txtErrorHistory.setVisibility(View.VISIBLE);
                txtErrorHistory.setText("");
                filterAwal = prefHistory.getString("filterAwal","");
                filterAkhir = prefHistory.getString("filterAkhir","");
                if (filterAwal.equals("")){
                    getHistory(etTanggalAwal.getText().toString(), etTanggalAkhir.getText().toString());
                }else{
                    etTanggalAwal.setText(filterAwal);
                    etTanggalAkhir.setText(filterAkhir);
                    getHistory(filterAwal, filterAkhir);
                }
            }
        });

        etTanggalAwal = (EditText) findViewById(R.id.tanggal_awal_filter_history);
        etTanggalAwal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(HistoryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            String fmonth, fDate;
                            int month;

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                try {
                                    if (monthOfYear < 10 && dayOfMonth < 10) {

                                        fmonth = "0" + monthOfYear;
                                        month = Integer.parseInt(fmonth) + 1;
                                        fDate = "0" + dayOfMonth;
                                        String paddedMonth = String.format("%02d", month);
                                        etTanggalAwal.setText(year + "-" + paddedMonth + "-" + fDate);

                                    } else {

                                        fmonth = "0" + monthOfYear;
                                        month = Integer.parseInt(fmonth) + 1;
                                        String paddedMonth = String.format("%02d", month);
                                        etTanggalAwal.setText(year + "-" + paddedMonth + "-" + dayOfMonth);

                                    }

                                    SharedPreferences.Editor editor = prefHistory.edit();
                                    editor.putString("filterAwal",etTanggalAwal.getText().toString());
                                    editor.putString("filterAkhir",etTanggalAkhir.getText().toString());
                                    editor.commit();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        etTanggalAkhir = (EditText) findViewById(R.id.tanggal_akhir_filter_history);
        etTanggalAkhir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(HistoryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            String fmonth, fDate;
                            int month;

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                try {
                                    if (monthOfYear < 10 && dayOfMonth < 10) {

                                        fmonth = "0" + monthOfYear;
                                        month = Integer.parseInt(fmonth) + 1;
                                        fDate = "0" + dayOfMonth;
                                        String paddedMonth = String.format("%02d", month);
                                        etTanggalAkhir.setText(year + "-" + paddedMonth + "-" + fDate);

                                    } else {

                                        fmonth = "0" + monthOfYear;
                                        month = Integer.parseInt(fmonth) + 1;
                                        String paddedMonth = String.format("%02d", month);
                                        etTanggalAkhir.setText(year + "-" + paddedMonth + "-" + dayOfMonth);

                                    }

                                    SharedPreferences.Editor editor = prefHistory.edit();
                                    editor.putString("filterAkhir",etTanggalAkhir.getText().toString());
                                    editor.commit();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        //Masukkan adapter ke listView
        historyAdapter = new HistoryAdapter(this, mHistoryDataResponseList);
        registerForContextMenu(lvHistoryList);

        lvHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView itemCode = view.findViewById(R.id.txtItemCodeHistory);
                TextView itemName = view.findViewById(R.id.txtItemNameHistory);
                TextView namaPalet = view.findViewById(R.id.txtNamaPaletHistory);
                TextView jumlahMasuk = view.findViewById(R.id.txtJumlahMasukHistory);
                TextView jumlahKeluar = view.findViewById(R.id.txtJumlahKeluarHistory);
                TextView namaLemari = view.findViewById(R.id.txtNamaLemariHistory);
                TextView namaRak = view.findViewById(R.id.txtNamaRakHistory);
                TextView tanggal = view.findViewById(R.id.txtTanggalHistory);
                TextView mainItemCode = view.findViewById(R.id.txtMainItemCodeHistory);
                TextView mainItemName = view.findViewById(R.id.txtMainItemNameHistory);
                TextView purchaseOrder = view.findViewById(R.id.txtPurchaseOrderHistory);
                TextView deliveryNote = view.findViewById(R.id.txtDeliveryNoteHistory);

                SharedPreferences.Editor editor = prefHistory.edit();
                editor.clear();
                editor.putString("filterAwal",etTanggalAwal.getText().toString());
                editor.putString("filterAkhir",etTanggalAkhir.getText().toString());
                editor.putString("itemCodeLv",itemCode.getText().toString());
                editor.putString("itemNameLv",itemName.getText().toString());
                editor.putString("namaPaletLv",namaPalet.getText().toString());
                editor.putString("jumlahMasukLv",jumlahMasuk.getText().toString());
                editor.putString("jumlahKeluarLv",jumlahKeluar.getText().toString());
                editor.putString("namaLemariLv",namaLemari.getText().toString());
                editor.putString("namaRakLv",namaRak.getText().toString());
                editor.putString("tanggalLv",tanggal.getText().toString());
                editor.putString("mainItemCodeLv",mainItemCode.getText().toString());
                editor.putString("mainItemNameLv",mainItemName.getText().toString());
                editor.putString("purchaseOrderLv",purchaseOrder.getText().toString());
                editor.putString("deliveryNoteLv",deliveryNote.getText().toString());
                editor.commit();

                Intent i = new Intent(HistoryActivity.this, DetailHistoryActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(this);
        bottom_menu.getMenu().getItem(2).setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        filterAwal = prefHistory.getString("filterAwal","");
        filterAkhir = prefHistory.getString("filterAkhir","");
        if (filterAwal.equals("")){
            getHistory(etTanggalAwal.getText().toString(), etTanggalAkhir.getText().toString());
        }else{
            etTanggalAwal.setText(filterAwal);
            etTanggalAkhir.setText(filterAkhir);
            getHistory(filterAwal, filterAkhir);
        }
        historyAdapter.notifyDataSetChanged();
    }

    public void getHistory(String tanggalAwal, String tanggalAkhir) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<HistoryListResponse> call = apiEndPoint.getHistory(tanggalAwal, tanggalAkhir);

        call.enqueue(new Callback<HistoryListResponse>() {
            @Override
            public void onResponse(Call<HistoryListResponse> call, Response<HistoryListResponse> response) {

                final HistoryListResponse responseData = response.body();

                if (responseData.getResponses()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mHistoryDataResponseList = responseData.getResult();
                            lvHistoryList.setAdapter(new HistoryAdapter(getApplicationContext(), mHistoryDataResponseList));
                            historyAdapter.notifyDataSetChanged();

                            if (mHistoryDataResponseList.size() == 0) {
                                txtErrorHistory.setText("Data tidak tersedia !");
                                imgHistory.setVisibility(View.VISIBLE);
                            } else {
                                txtErrorHistory.setVisibility(View.GONE);
                                txtErrorHistory.setText("");
                                imgHistory.setVisibility(View.GONE);
                            }

                        }
                    });

                } else {
                    txtErrorHistory.setText("Data tidak tersedia !");
                }
            }

            @Override
            public void onFailure(Call<HistoryListResponse> call, Throwable t) {
                txtErrorHistory.setText("Koneksi Bermasalah !");
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
                SharedPreferences.Editor editor = prefHistory.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(this, HistoryActivity.class));
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
        new SweetAlertDialog(HistoryActivity.this, SweetAlertDialog.WARNING_TYPE)
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

                        startActivity(new Intent(HistoryActivity.this, LoginActivity.class));
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