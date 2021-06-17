package project.akbaralzaini.btraf;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import project.akbaralzaini.btraf.models.Role;
import project.akbaralzaini.btraf.models.User;
import project.akbaralzaini.btraf.models.UserRegister;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.AuthInterface;
import project.akbaralzaini.btraf.utils.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {
    EditText edtEmail, edtFirstName, edtLastName, edtMobileNumber, edtPassword;
    Button btnRegister;
    AuthInterface authInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.edtEmailReg);
        edtFirstName = findViewById(R.id.edFirst_nameReg);
        edtLastName = findViewById(R.id.edtLast_nameReg);
        edtMobileNumber = findViewById(R.id.edtMobile_numberReg);
        edtPassword = findViewById(R.id.passwordReg);
        btnRegister = findViewById(R.id.btnRegister);
        authInterface = ApiClient.getClient().create(AuthInterface.class);

        btnRegister.setOnClickListener(v -> {

            ArrayList<String> role = new ArrayList<String>();
            role.add("user");
            UserRegister userRegister = new UserRegister(edtEmail.getText().toString(),
                    edtFirstName.getText().toString(),
                    edtLastName.getText().toString(),
                    edtMobileNumber.getText().toString(),
                    edtPassword.getText().toString(),role);
            Call<User> userCall = authInterface.register(userRegister);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user != null){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setTitle("information");
                        builder.setMessage("Registrasi Berhasil Dilakukan");
                        builder.setPositiveButton("Ok",(dialog, which) -> {
                            Intent home=new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(home);
                            finish();
                        });

                        builder.setCancelable(false);
                        builder.show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        });

    }
}