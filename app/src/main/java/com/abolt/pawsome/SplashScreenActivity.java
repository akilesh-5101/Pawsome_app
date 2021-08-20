package com.abolt.pawsome;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler wait;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wait = new Handler();

        wait.postDelayed(new Runnable() {
            @Override
            public void run() {

                try{
                    Intent i = new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                catch (Exception ignored){
                    ignored.printStackTrace();
                }
            }
        }, 300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wait.removeCallbacksAndMessages(null);
    }

}
