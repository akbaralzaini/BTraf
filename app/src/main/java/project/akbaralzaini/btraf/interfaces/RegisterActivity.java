package project.akbaralzaini.btraf.interfaces;

import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.models.user.User;
import project.akbaralzaini.btraf.models.user.register.UserRegister;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.AuthInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {
    EditText edtUsername, edtFirstName, edtLastName, edtMobileNumber, edtPassword;
    String username, firstName, lastName, mobileNumber, password;
    Button btnRegister;
    private Boolean CheckEditText = false;
    AuthInterface authInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.edtUsernameReg);
        edtFirstName = findViewById(R.id.edFirst_nameReg);
        edtLastName = findViewById(R.id.edtLast_nameReg);
        edtMobileNumber = findViewById(R.id.edtMobile_numberReg);
        edtPassword = findViewById(R.id.passwordReg);
        btnRegister = findViewById(R.id.btnRegister);
        authInterface = ApiClient.getClient("").create(AuthInterface.class);

        btnRegister.setOnClickListener(v -> { register(); });


    }

    private void cekRegister(){
        CheckEditTextIsEmptyOrNot();
        if(CheckEditText){
            SyncCek();
        }else {
            Toast.makeText(RegisterActivity.this, "Data Masih Kosong !", Toast.LENGTH_LONG).show();
        }
    }

    private void SyncCek() {
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                    if (progressDialog.isShowing()) progressDialog.dismiss();
                    register();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void CheckEditTextIsEmptyOrNot(){
        username = edtUsername.getText().toString();
        firstName = edtFirstName.getText().toString();
        lastName = edtLastName.getText().toString();
        mobileNumber = edtMobileNumber.getText().toString();
        password = edtPassword.getText().toString();
        CheckEditText = !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(mobileNumber)
                && !TextUtils.isEmpty(password) ;
    }

    public void register(){
        try {
            runOnUiThread(() -> {
                ArrayList<String> role = new ArrayList<String>();
                role.add("user");
                UserRegister userRegister = new UserRegister(edtUsername.getText().toString(),
                        edtFirstName.getText().toString(),
                        edtLastName.getText().toString(),
                        edtMobileNumber.getText().toString(),
                        edtPassword.getText().toString(), role);
                Call<User> userCall = authInterface.register(userRegister);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        if (user != null) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setTitle("information");
                            builder.setMessage("Registrasi Berhasil Dilakukan");
                            builder.setPositiveButton("Ok", (dialog, which) -> {
                                Intent home = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(home);
                                finish();
                            });

                            builder.setCancelable(false);
                            builder.show();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "pendaftaran gagal, username sudah digunakan", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "pendaftaran gagal, periksa koneksi", Toast.LENGTH_LONG).show();
                    }
                });
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.wtf("Error Exception : ",e.getMessage());
        }
    }
}