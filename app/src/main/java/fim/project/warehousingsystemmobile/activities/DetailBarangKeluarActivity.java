package fim.project.warehousingsystemmobile.activities;

import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.adapters.BarangKeluarItemAdapter;
import fim.project.warehousingsystemmobile.responses.ApiEndPoint;
import fim.project.warehousingsystemmobile.responses.DetailBarangKeluarListResponse;
import fim.project.warehousingsystemmobile.responses.HistoryLastIdResponse;
import fim.project.warehousingsystemmobile.responses.MainItemDataResponse;
import fim.project.warehousingsystemmobile.responses.StatusResponse;
import fim.project.warehousingsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBarangKeluarActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom_menu;
    List<MainItemDataResponse> mainItemList;

    @BindView(R.id.lv_barangkeluar)
    ListView lvBarangKeluarList;

    BarangKeluarItemAdapter barangKeluarItemAdapter;

    private SharedPreferences pref;
    private SharedPreferences prefSuccessOut;
    private SharedPreferences prefDetailBarangKeluar;
    private SharedPreferences prefCancelDisable;
    private SharedPreferences prefDetailBarangKeluarListView;

    private String mainItemCode;
    private String jumlahInput;
    private String suksesOut;
    private String cancelDisable;
    private String cancelOut;
    private String cancelItemCode;
    private String cancelQtyInput;
    private String minQuantity;
    private Button btnSelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang_keluar);

        ButterKnife.bind(this);

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
        prefCancelDisable = getSharedPreferences("prefCancelDisable",MODE_PRIVATE);
        prefSuccessOut = getSharedPreferences("successOutPref", MODE_PRIVATE);
        prefDetailBarangKeluar = getSharedPreferences("detail_barang_keluar",MODE_PRIVATE);
        prefDetailBarangKeluarListView = getSharedPreferences("detail_barang_keluar_list_view",MODE_PRIVATE);
        mainItemCode = prefDetailBarangKeluar.getString("mainItemCode",null);
        jumlahInput = prefDetailBarangKeluar.getString("mainItemJumlahInput",null);
        minQuantity = prefDetailBarangKeluar.getString("minQuantity","0");

        getMainItemBarangKeluar(mainItemCode, jumlahInput);

        btnSelesai = (Button)findViewById(R.id.lv_btnSelesai);
        btnSelesai.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (cancelDisable.equals("0")){
                    cancelDisableErrorMessage();
                }else{
                    confirmationMessageComplete();
                }
            }
        });

        //Masukkan adapter ke listView
        barangKeluarItemAdapter = new BarangKeluarItemAdapter(this, mainItemList);
        registerForContextMenu(lvBarangKeluarList);

        lvBarangKeluarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView status = (TextView)view.findViewById(R.id.txtStatusBarangKeluar);

                if(status.getText().toString().equals("1")){
                    new SweetAlertDialog(DetailBarangKeluarActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Peringatan")
                            .setContentText("Ingin membatalkan proses?")
                            .setConfirmText("Ya")
                            .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                            .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                    SharedPreferences.Editor editor = prefSuccessOut.edit();
                                    editor.putString("cancelOut","1");
                                    editor.commit();

                                    TextView mainItemCode = (TextView)view.findViewById(R.id.txtMainItemCodeBarangKeluar);
                                    TextView itemCode = (TextView)view.findViewById(R.id.txtItemCodeBarangKeluar);
                                    TextView idPalet = (TextView)view.findViewById(R.id.txtPaletIdBarangKeluar);
                                    TextView jumlah = (TextView)view.findViewById(R.id.txtJumlahBarangKeluar);

                                    cancelItemCode = itemCode.getText().toString();
                                    cancelQtyInput = jumlah.getText().toString();

                                    getLastIdHistory(mainItemCode.getText().toString(), itemCode.getText().toString(), jumlah.getText().toString());

                                }
                            })
                            .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }else{

                    TextView namaPalet = (TextView)view.findViewById(R.id.txtNamaPaletBarangKeluar);

                    SharedPreferences.Editor editor = prefDetailBarangKeluarListView.edit();
                    editor.clear();
                    editor.commit();

                    TextView itemCode = (TextView)view.findViewById(R.id.txtItemCodeBarangKeluar);
                    TextView itemName = (TextView)view.findViewById(R.id.txtItemNameBarangKeluar);
                    TextView jumlah = (TextView)view.findViewById(R.id.txtJumlahBarangKeluar);
                    TextView idPalet = (TextView)view.findViewById(R.id.txtPaletIdBarangKeluar);
                    TextView idBarang = (TextView)view.findViewById(R.id.txtIdBarangBarangKeluar);
                    TextView currentQty = (TextView)view.findViewById(R.id.txtCurrentQtyBarangKeluar);
                    TextView namaLemari = (TextView)view.findViewById(R.id.txtNamaLemariBarangKeluar);
                    TextView namaRak = (TextView)view.findViewById(R.id.txtNamaRakBarangKeluar);
                    TextView maxBarang = (TextView)view.findViewById(R.id.txtMaxBarangBarangKeluar);
                    TextView ipAddress = (TextView)view.findViewById(R.id.txtIpAddress);
                    TextView gpio1 = (TextView)view.findViewById(R.id.txtGpio1);
                    TextView gpio2 = (TextView)view.findViewById(R.id.txtGpio2);
                    TextView gpio3 = (TextView)view.findViewById(R.id.txtGpio3);
                    TextView gpioStatus = (TextView)view.findViewById(R.id.txtGpioStatus);

                    editor.putString("lvIdBarang",idBarang.getText().toString());
                    editor.putString("lvItemCode",itemCode.getText().toString());
                    editor.putString("lvIdPalet",idPalet.getText().toString());
                    editor.putString("lvJumlah",jumlah.getText().toString());
                    editor.putString("lvItemName",itemName.getText().toString());
                    editor.putString("lvNamaPalet",namaPalet.getText().toString());
                    editor.putString("lvCurrentQty",currentQty.getText().toString());
                    editor.putString("lvNamaLemari",namaLemari.getText().toString());
                    editor.putString("lvNamaRak",namaRak.getText().toString());
                    editor.putString("lvMaxBarang",maxBarang.getText().toString());
                    editor.putString("lvIpAddress",ipAddress.getText().toString());
                    editor.putString("lvGpio1",gpio1.getText().toString());
                    editor.putString("lvGpio2",gpio2.getText().toString());
                    editor.putString("lvGpio3",gpio3.getText().toString());
                    editor.putString("lvGpioStatus",gpioStatus.getText().toString());
                    editor.commit();

                    if (Integer.parseInt(currentQty.getText().toString()) < Integer.parseInt(jumlah.getText().toString())){
                        outOfStockMessage();
                    }else{
                        Intent i = new Intent(DetailBarangKeluarActivity.this, KonfirmasiBarangKeluarActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            }
        });

        suksesOut = prefSuccessOut.getString("suksesOut","0");
        cancelDisable = prefCancelDisable.getString("cancelDisable","0");

        if (suksesOut.equals("1")){
            successMessage();

            SharedPreferences.Editor editor = prefSuccessOut.edit();
            editor.clear();
            editor.commit();
        }else{

        }

        cancelOut = prefSuccessOut.getString("cancelOut","0");

        if (cancelOut.equals("1")){
            cancelMessage();

            SharedPreferences.Editor editor = prefSuccessOut.edit();
            editor.clear();
            editor.commit();
        }else{

        }

        if(minQuantity.equals("0")){

        }else{
            if (Integer.parseInt(minQuantity) < Integer.parseInt(jumlahInput)){
                new SweetAlertDialog(DetailBarangKeluarActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Peringatan")
                        .setContentText("Terdapat quantity yang kurang pada item yang akan diambil, disarankan untuk membatalkan proses, ingin membatalkan proses?")
                        .setConfirmText("Ya")
                        .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                        .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                completeBarangKeluar(mainItemCode);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("batal", "1");
                                editor.commit();
                            }
                        })
                        .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                SharedPreferences.Editor editor = prefDetailBarangKeluar.edit();
                                editor.putString("minQuantity", "0");

                                editor.commit();
                            }
                        })
                        .show();
            }else{

            }
        }

        bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(this);
        bottom_menu.getMenu().getItem(1).setChecked(true);
    }

    public void getLastIdHistory(String mainItemCode, String itemCode, String jumlah) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<HistoryLastIdResponse> call = apiEndPoint.lastIdHistory(mainItemCode, itemCode, jumlah);

        call.enqueue(new Callback<HistoryLastIdResponse>() {
            @Override
            public void onResponse(Call<HistoryLastIdResponse> call, Response<HistoryLastIdResponse> response) {

                final HistoryLastIdResponse statusResponse = response.body();

                if (statusResponse.getResponses()) {

                    cancelBarangKeluar(cancelItemCode, cancelQtyInput, statusResponse.getLastIdHistory());

                    Intent i = new Intent(DetailBarangKeluarActivity.this, DetailBarangKeluarActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else {

                }
            }

            @Override
            public void onFailure(Call<HistoryLastIdResponse> call, Throwable t) {

            }
        });
    }

    public void cancelBarangKeluar(String itemCode, String qtyInput, String lastIdHistory) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.cancelBarangKeluar(itemCode, qtyInput, lastIdHistory);

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

    public void completeBarangKeluar(String mainItemCode) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.completeBarangKeluar(mainItemCode);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                final StatusResponse statusResponse = response.body();

                if (statusResponse.getResponses()) {

                    Intent i = new Intent(DetailBarangKeluarActivity.this, BarangKeluarActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else {
                    Toast.makeText(DetailBarangKeluarActivity.this, "Gagal melakukan proses !", Toast.LENGTH_LONG).show();

                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(DetailBarangKeluarActivity.this, DetailBarangKeluarActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }, 8000);
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(DetailBarangKeluarActivity.this, "Koneksi bermasalah !", Toast.LENGTH_LONG).show();

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(DetailBarangKeluarActivity.this, DetailBarangKeluarActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }, 8000);
            }
        });
    }

    public void successMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Berhasil").setContentText("Data berhasil discan !").show();
    }

    public void cancelMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Berhasil").setContentText("Behasil membatalkan proses !").show();
    }

    public void cancelDisableMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Gagal").setContentText("Anda sedang menjalankan proses !").show();
    }

    public void cancelDisableErrorMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Gagal").setContentText("Anda belum menjalankan proses !").show();
    }

    public void outOfStockMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Peringatan").setContentText("Quantity barang yang akan anda ambil kurang !").show();
    }

    public void confirmationMessageComplete() {
        new SweetAlertDialog(DetailBarangKeluarActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Konfirmasi")
                .setContentText("Ingin menyelesaikan proses?")
                .setConfirmText("Selesai")
                .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        btnSelesai.setClickable(false);
                        completeBarangKeluar(mainItemCode);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("sukses","1");
                        editor.commit();
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

    @Override
    protected void onResume() {
        super.onResume();
        getMainItemBarangKeluar(mainItemCode, jumlahInput);
        barangKeluarItemAdapter.notifyDataSetChanged();
    }

    public void getMainItemBarangKeluar(String mainItemCode, String jumlahInput) {

            ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
            Call<DetailBarangKeluarListResponse> call = apiEndPoint.getMainItemBarangKeluar(mainItemCode, jumlahInput);

            call.enqueue(new Callback<DetailBarangKeluarListResponse>() {
                @Override
                public void onResponse(Call<DetailBarangKeluarListResponse> call, Response<DetailBarangKeluarListResponse> response) {

                    final DetailBarangKeluarListResponse responseData = response.body();

                    if (responseData.getResponses()) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mainItemList = responseData.getResult();

                                lvBarangKeluarList.setAdapter(new BarangKeluarItemAdapter(getApplicationContext(),mainItemList));
                                barangKeluarItemAdapter.notifyDataSetChanged();
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Data tidak tersedia !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DetailBarangKeluarListResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Koneksi bermasalah !", Toast.LENGTH_SHORT).show();
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
                if (cancelDisable.equals("1")){
                    cancelDisableMessage();
                }else {

                    new SweetAlertDialog(DetailBarangKeluarActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Konfirmasi")
                            .setContentText("Ingin kembali ke halaman utama?")
                            .setConfirmText("Ya")
                            .setConfirmButtonBackgroundColor(Color.parseColor("#003c8f"))
                            .setCancelButtonBackgroundColor(Color.parseColor("#003c8f"))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    completeBarangKeluar(mainItemCode);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("batal", "1");
                                    editor.commit();
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
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setConfirmButtonBackgroundColor(Color.parseColor("#003c8f")).setTitleText("Peringatan").setContentText("Harap selesaikan proses pengeluaran barang terlebih dahulu !").setConfirmText("OK").show();
    }

    @Override
    public void onBackPressed()
    {
        warningMessage();
    }

}