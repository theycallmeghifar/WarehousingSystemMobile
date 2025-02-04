package fim.project.warehousingsystemmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.responses.ApiEndPoint;
import fim.project.warehousingsystemmobile.responses.StatusResponse;
import fim.project.warehousingsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences prefHistory;
    private SharedPreferences prefBarang;
    private SharedPreferences prefLogin;
    private SharedPreferences prefDetailBarangKeluarListView;
    private SharedPreferences prefDetailBarangMasuk;
    private SharedPreferences prefDetailBarangKeluar;

    private String ipAddress_in;
    private String gpio_in1;
    private String gpio_in2;
    private String gpio_in3;
    private String ipAddress_out;
    private String gpio_out1;
    private String gpio_out2;
    private String gpio_out3;
    private String itemCode;
    private String loginStatus;
    private String mainItemCode;
    private WebView wvGPIO_in1;
    private WebView wvGPIO_in2;
    private WebView wvGPIO_in3;
    private WebView wvGPIO_out1;
    private WebView wvGPIO_out2;
    private WebView wvGPIO_out3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefLogin = getSharedPreferences("loginPref",MODE_PRIVATE);
        prefHistory = getSharedPreferences("historyPref",MODE_PRIVATE);
        prefBarang = getSharedPreferences("barangPref",MODE_PRIVATE);
        prefDetailBarangMasuk = getSharedPreferences("detail_barang_masuk",MODE_PRIVATE);
        prefDetailBarangKeluar = getSharedPreferences("detail_barang_keluar",MODE_PRIVATE);
        prefDetailBarangKeluarListView = getSharedPreferences("detail_barang_keluar_list_view", MODE_PRIVATE);

        loginStatus = prefLogin.getString("login","0");
        itemCode = prefDetailBarangMasuk.getString("itemCode","0");
        ipAddress_in = prefDetailBarangMasuk.getString("ipAddress","0");
        gpio_in1 = prefDetailBarangMasuk.getString("gpio1","0");
        gpio_in2 = prefDetailBarangMasuk.getString("gpio2","0");
        gpio_in3 = prefDetailBarangMasuk.getString("gpio3","0");
        mainItemCode = prefDetailBarangKeluar.getString("mainItemCode","0");
        ipAddress_out = prefDetailBarangKeluarListView.getString("lvIpAddress","0");
        gpio_out1 = prefDetailBarangKeluarListView.getString("lvGpio1","0");
        gpio_out2 = prefDetailBarangKeluarListView.getString("lvGpio2","0");
        gpio_out3 = prefDetailBarangKeluarListView.getString("lvGpio3","0");

        wvGPIO_in1 = (WebView) findViewById(R.id.splashIn_wvGPIO1);
        wvGPIO_in1.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        wvGPIO_in2 = (WebView) findViewById(R.id.splashIn_wvGPIO2);
        wvGPIO_in2.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        wvGPIO_in3 = (WebView) findViewById(R.id.splashIn_wvGPIO3);
        wvGPIO_in3.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });

        wvGPIO_out1 = (WebView) findViewById(R.id.splashOut_wvGPIO1);
        wvGPIO_out1.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        wvGPIO_out2 = (WebView) findViewById(R.id.splashOut_wvGPIO2);
        wvGPIO_out2.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
        wvGPIO_out3 = (WebView) findViewById(R.id.splashOut_wvGPIO3);
        wvGPIO_out3.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });

        wvGPIO_in1.loadUrl("http://"+ipAddress_in+"/gpio.php?pin="+gpio_in1+"&status=dl");
        wvGPIO_in2.loadUrl("http://"+ipAddress_in+"/gpio.php?pin="+gpio_in2+"&status=dl");
        wvGPIO_in3.loadUrl("http://"+ipAddress_in+"/gpio.php?pin="+gpio_in3+"&status=dl");
        wvGPIO_out1.loadUrl("http://"+ipAddress_out+"/gpio.php?pin="+gpio_out1+"&status=dl");
        wvGPIO_out2.loadUrl("http://"+ipAddress_out+"/gpio.php?pin="+gpio_out2+"&status=dl");
        wvGPIO_out3.loadUrl("http://"+ipAddress_out+"/gpio.php?pin="+gpio_out3+"&status=dl");

        completeBarangMasuk(itemCode);
        completeBarangKeluar(mainItemCode);

        SharedPreferences.Editor editorHistory = prefHistory.edit();
        editorHistory.clear();
        editorHistory.commit();

        SharedPreferences.Editor editorBarang = prefBarang.edit();
        editorBarang.clear();
        editorBarang.commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loginStatus.equals("1")){
                    Intent intent = new Intent(SplashActivity.this, BarangMasukActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else{
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        }, 1700);
    }

    public void completeBarangMasuk(String itemCode) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.completeBarangMasuk(itemCode);

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

                } else {

                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}