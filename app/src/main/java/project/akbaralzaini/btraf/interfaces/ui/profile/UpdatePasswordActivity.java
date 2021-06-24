package project.akbaralzaini.btraf.interfaces.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.interfaces.LoginActivity;
import project.akbaralzaini.btraf.interfaces.MainActivity;
import project.akbaralzaini.btraf.models.user.UpadatePassword;
import project.akbaralzaini.btraf.models.user.User;
import project.akbaralzaini.btraf.models.user.login.AuthLogin;
import project.akbaralzaini.btraf.models.user.login.LoginUser;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.AuthInterface;
import project.akbaralzaini.btraf.rest.UserInterface;
import project.akbaralzaini.btraf.utils.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {
    UserInterface userInterface;
    AuthInterface authInterface;
    EditText edtPassword1, edtPassword2;
    TextView validator;
    Button btnUpdate;
    MySession session;
    Boolean valid = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        btnUpdate = findViewById(R.id.btnUpdatePassword);
        validator = findViewById(R.id.validator);

        session = new MySession(UpdatePasswordActivity.this);
        HashMap<String, String> sUsernya = session.getUserDetails();
        userInterface = ApiClient.getClient(""+sUsernya.get(MySession.KEY_TOKEN)).create(UserInterface.class);
        authInterface = ApiClient.getClient(""+sUsernya.get(MySession.KEY_TOKEN)).create(AuthInterface.class);
        edtPassword1 = findViewById(R.id.edtPassword);
        edtPassword2 = findViewById(R.id.edtPassword2);

        edtPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valid = edtPassword1.getText().toString().equals(edtPassword2.getText().toString());
                if (!valid) validator.setVisibility(View.VISIBLE);
                else validator.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if (valid){
            btnUpdate.setOnClickListener(v -> {
                Log.d("drtyu", String.valueOf(valid));
                UpadatePassword upadatePassword = new UpadatePassword(edtPassword1.getText().toString());
                Call<User> userCall = userInterface.updatePassword(upadatePassword);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        if (user != null){
                            new AlertDialog.Builder(UpdatePasswordActivity.this)
                                    .setTitle("Information")
                                    .setMessage("Update password successfully")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", (dialog, which) -> {
                                        Intent intent = new Intent(UpdatePasswordActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(UpdatePasswordActivity.this, "internet connection", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
        else{
            btnUpdate.setOnClickListener(v -> Toast.makeText(this, "password must match", Toast.LENGTH_SHORT).show());
        }

    }


}