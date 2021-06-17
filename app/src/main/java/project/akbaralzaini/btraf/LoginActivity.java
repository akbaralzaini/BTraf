package project.akbaralzaini.btraf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project.akbaralzaini.btraf.models.AuthLogin;
import project.akbaralzaini.btraf.models.LoginUser;
import project.akbaralzaini.btraf.models.User;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.AuthInterface;
import project.akbaralzaini.btraf.utils.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    TextView txtRegister;
    EditText edtEmail,edtPassword;
    Button btnLogin;
    AuthInterface authInterface;
    MySession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        authInterface = ApiClient.getClient().create(AuthInterface.class);
        session = new MySession(this);

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

    private void cekLogin() {
        LoginUser loginUser = new LoginUser(edtEmail.getText().toString(),edtPassword.getText().toString());
        Call<AuthLogin> authLoginCall = authInterface.login(loginUser);
        authLoginCall.enqueue(new Callback<AuthLogin>() {
            @Override
            public void onResponse(Call<AuthLogin> call, Response<AuthLogin> response) {
                AuthLogin userData = response.body();
                if (userData != null){
                    User user = userData.getUser();
                    session.createLoginSession(user.getId(),user.getEmail(),user.getFirstName(),userData.accessToken);
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Email atau Password salah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "no internet Access", Toast.LENGTH_SHORT).show();
            }
        });
    }


}