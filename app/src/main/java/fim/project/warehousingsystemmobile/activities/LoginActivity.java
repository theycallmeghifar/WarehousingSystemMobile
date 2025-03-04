package fim.project.warehousingsystemmobile.activities;

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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.responses.ApiEndPoint;
import fim.project.warehousingsystemmobile.responses.StatusResponse;
import fim.project.warehousingsystemmobile.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefLogin;
    private Button btnLogin;
    private EditText etUsername, etPassword;
    private TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefLogin = getSharedPreferences("loginPref",MODE_PRIVATE);

        etUsername = (EditText) findViewById(R.id.login_username);
        etPassword = (EditText)findViewById(R.id.login_password);
        txtError = (TextView) findViewById(R.id.login_txtError);
        txtError.setVisibility(View.INVISIBLE);

        btnLogin = (Button)findViewById(R.id.login_btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loginProcess(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    public void loginProcess(String username, String password) {

        if (etUsername.getText().toString().equals("") && etPassword.getText().toString().equals("")){
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("Username dan password tidak boleh kosong !");
        }else if(etUsername.getText().toString().equals("")){
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("Username tidak boleh kosong !");
        }else if(etPassword.getText().toString().equals("")){
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("Password tidak boleh kosong !");
        }else {
            ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
            Call<StatusResponse> call = apiEndPoint.loginProcess(username, password);

            call.enqueue(new Callback<StatusResponse>() {
                @Override
                public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                    final StatusResponse statusResponse = response.body();

                    if (statusResponse.getResponses()) {

                        SharedPreferences.Editor editor = prefLogin.edit();
                        editor.putString("login", "1");
                        editor.putString("username", etUsername.getText().toString());
                        editor.commit();

                        Intent i = new Intent(LoginActivity.this, BarangMasukActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    } else {
                        txtError.setText("Username atau password salah !");
                        txtError.setTextColor(Color.parseColor("#ff3030"));
                        txtError.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<StatusResponse> call, Throwable t) {
                    Log.e("LoginError", "Error: " + t.getMessage());
                    txtError.setText("Koneksi bermasalah !");
                    txtError.setTextColor(Color.parseColor("#ff3030"));
                    txtError.setVisibility(View.VISIBLE);
                }
            });
        }
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

    @Override
    public void onBackPressed()
    {
        finishAffinity();
    }
}