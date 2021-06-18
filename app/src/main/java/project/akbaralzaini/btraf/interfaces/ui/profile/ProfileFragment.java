package project.akbaralzaini.btraf.interfaces.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.interfaces.LoginActivity;
import project.akbaralzaini.btraf.interfaces.MainActivity;
import project.akbaralzaini.btraf.utils.MySession;

public class ProfileFragment extends Fragment {
    Button btnLogout;
    MySession session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        session = new MySession(getActivity());

        btnLogout = rootView.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            session.logoutUser();
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
            getActivity().finish();
        });

        return rootView;
    }
}