package project.akbaralzaini.btraf.interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.interfaces.ui.agency.AgencyFragment;
import project.akbaralzaini.btraf.interfaces.ui.bus.BusFragment;
import project.akbaralzaini.btraf.interfaces.ui.home.HomeFragment;
import project.akbaralzaini.btraf.interfaces.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.bottomNavigationView);
        navView.setOnNavigationItemSelectedListener(item -> {

            Fragment fragment = null;
            int id = item.getItemId();
            if (id == R.id.tab_home){
                fragment = new HomeFragment();
            }
            else if (id == R.id.tab_profile){
                fragment = new ProfileFragment();
            }else if (id == R.id.tab_bus){
                fragment = new BusFragment();
            }else if (id == R.id.tab_agency){
                fragment = new AgencyFragment();
            }
            return loadFragment(fragment);
        });

        loadFragment(new HomeFragment());

    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}