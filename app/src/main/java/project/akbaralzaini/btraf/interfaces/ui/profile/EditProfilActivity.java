package project.akbaralzaini.btraf.interfaces.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.interfaces.MainActivity;
import project.akbaralzaini.btraf.interfaces.ui.bus.BookingTicketActivity;
import project.akbaralzaini.btraf.models.user.UpdateUser;
import project.akbaralzaini.btraf.models.user.User;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.UserInterface;
import project.akbaralzaini.btraf.utils.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfilActivity extends AppCompatActivity {
    EditText edtFirstName,edtLastName,edtMobileNumber;
    Button btnUpdate;
    UserInterface userInterface;
    MySession session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        session = new MySession(EditProfilActivity.this);
        HashMap<String, String> sUsernya = session.getUserDetails();
        userInterface = ApiClient.getClient(""+sUsernya.get(MySession.KEY_TOKEN)).create(UserInterface.class);

        Intent i = getIntent();
        User user = (User) i.getSerializableExtra("user");

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtMobileNumber = findViewById(R.id.edtNumber);
        btnUpdate = findViewById(R.id.btnUpdateProfile);

        edtFirstName.setText(user.getFirstName());
        edtLastName.setText(user.getLastName());
        edtMobileNumber.setText(user.getMobileNumber());

        btnUpdate.setOnClickListener(v -> {
            UpdateUser updateUser = new UpdateUser(edtFirstName.getText().toString(),edtLastName.getText().toString(),edtMobileNumber.getText().toString());
            Call<User> userCall = userInterface.updateProfile(updateUser);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user1 = response.body();
                    if (user1 != null){
                        new AlertDialog.Builder(EditProfilActivity.this)
                                .setTitle("Information")
                                .setMessage("Update profile successfully")
                                .setCancelable(false)
                                .setPositiveButton("ok", (dialog, which) -> {
                                    Intent intent = new Intent(EditProfilActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        });
    }
}