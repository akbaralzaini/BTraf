package project.akbaralzaini.btraf.interfaces.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.interfaces.LoginActivity;
import project.akbaralzaini.btraf.interfaces.MainActivity;
import project.akbaralzaini.btraf.models.user.User;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.TripScheduleInterface;
import project.akbaralzaini.btraf.rest.UserInterface;
import project.akbaralzaini.btraf.utils.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    Button btnLogout;
    MySession session;
    UserInterface userInterface;
    TextView tvName,tvUsername, tvMobileNumber;
    Button btnUpdateProfile,btnUpdatePassword;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        session = new MySession(getActivity());
        HashMap<String, String> sUsernya = session.getUserDetails();
        userInterface = ApiClient.getClient(""+sUsernya.get(MySession.KEY_TOKEN)).create(UserInterface.class);
        tvName = rootView.findViewById(R.id.tvName);
        tvUsername = rootView.findViewById(R.id.tvUsername);
        tvMobileNumber = rootView.findViewById(R.id.tvNumber);
        btnUpdateProfile = rootView.findViewById(R.id.btnUpdateProfile);
        btnUpdatePassword = rootView.findViewById(R.id.btnUpdatePassword);

        btnLogout = rootView.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            session.logoutUser();
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
            getActivity().finish();
        });

        Call<User> callUser = userInterface.getUserDetail();
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                if (user != null){
                    String fullName = user.getFirstName()+" "+user.getLastName();
                    tvName.setText(fullName);
                    tvUsername.setText(user.getUsername());
                    tvMobileNumber.setText(user.getMobileNumber());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        btnUpdateProfile.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(),EditProfilActivity.class);
            i.putExtra("user",user);
            startActivity(i);
        });

        btnUpdatePassword.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(),UpdatePasswordActivity.class);
            startActivity(i);
        });

        return rootView;
    }
}