package itinterview.arpan.com.itinterview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import itinterview.arpan.com.itinterview.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash_screen);
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                    Intent intent;
                    if(FirebaseAuth.getInstance().getCurrentUser()== null) {
                        intent = new Intent(getApplicationContext(), login.class);
                    }else{
                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                    }
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();


    }

};