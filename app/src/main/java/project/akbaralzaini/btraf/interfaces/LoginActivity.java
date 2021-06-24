package project.akbaralzaini.btraf.interfaces;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.models.trip.Stop;
import project.akbaralzaini.btraf.models.user.login.AuthLogin;
import project.akbaralzaini.btraf.models.user.login.LoginUser;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.AuthInterface;
import project.akbaralzaini.btraf.rest.StopInterface;
import project.akbaralzaini.btraf.utils.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    private String PasswordHolder, UsernameHolder;
    private Boolean CheckEditText;

    TextView txtRegister;
    EditText edtUsername,edtPassword;
    Button btnLogin;
    AuthInterface authInterface;
    StopInterface stopInterface;
    MySession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        session = new MySession(this);
        HashMap<String, String> sUsernya = session.getUserDetails();

        authInterface = ApiClient.getClient("").create(AuthInterface.class);
        stopInterface = ApiClient.getClient(sUsernya.get(MySession.KEY_TOKEN)).create(StopInterface.class);


        if (session.isLoggedIn()) {
            Call<List<Stop>> stopCall = stopInterface.getStop();
            stopCall.enqueue(new Callback<List<Stop>>() {
                @Override
                public void onResponse(Call<List<Stop>> call, Response<List<Stop>> response) {
                    Log.d("testt", String.valueOf(response.code()));
                    if (response.code() == 200){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        session.logoutUser();
                        Toast.makeText(LoginActivity.this, "Session anda telah habis", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Stop>> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "koneksi time out", Toast.LENGTH_SHORT).show();
                }
            });

        }

        txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(i);
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            cekLogin();
        });
    }

    //Check Edit Text
    public void CheckEditTextIsEmptyOrNot(){
        UsernameHolder = edtUsername.getText().toString();
        PasswordHolder = edtPassword.getText().toString();
        CheckEditText = !TextUtils.isEmpty(UsernameHolder) && !TextUtils.isEmpty(PasswordHolder);
    }

    private void cekLogin(){
        CheckEditTextIsEmptyOrNot();
        if(CheckEditText){
            SyncCek();
        }else {
            Toast.makeText(LoginActivity.this, "Data Masih Kosong !", Toast.LENGTH_LONG).show();
        }
    }

    //Loading
    private void SyncCek() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    if (progressDialog.isShowing()) progressDialog.dismiss();
                    callValidateTrue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void callValidateTrue() {
        try {
            runOnUiThread(() -> {
                LoginUser loginUser = new LoginUser(edtUsername.getText().toString(),edtPassword.getText().toString());
                Call<AuthLogin> authLoginCall = authInterface.login(loginUser);
                authLoginCall.enqueue(new Callback<AuthLogin>() {
                    @Override
                    public void onResponse(@NotNull Call<AuthLogin> call, @NotNull Response<AuthLogin> response) {
                        AuthLogin userData = response.body();
                        if (userData != null){
                            session.createLoginSession(userData.getId(),userData.getUsername(),userData.getFirstName(),userData.getLastName(),userData.getMobileNumber(),userData.getRoles().get(0),userData.getToken());
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Username atau Password salah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AuthLogin> call, @NotNull Throwable t) {
                        Toast.makeText(LoginActivity.this, "no internet Access", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.wtf("Error Exception : ",e.getMessage());
        }
    }


}