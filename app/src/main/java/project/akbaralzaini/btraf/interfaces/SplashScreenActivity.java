package project.akbaralzaini.btraf.interfaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import project.akbaralzaini.btraf.R;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed((Runnable) () -> {
            Intent home=new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(home);
            finish();

        },2000);
    }
}