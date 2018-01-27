package intive.grzegorzbaczek.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import intive.grzegorzbaczek.R;

public class SplashScreen extends AppCompatActivity {

    private static final int waitingTime = 5000;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAndRemoveTask();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActivityLoader loader = new ActivityLoader();
        loader.start();
    }

    private class ActivityLoader extends Thread {

        @Override
        public void run() {

            try {
                Thread.sleep(waitingTime);
            } catch (InterruptedException e) {
                Log.e("Splash screen", e.getMessage());
            }

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
