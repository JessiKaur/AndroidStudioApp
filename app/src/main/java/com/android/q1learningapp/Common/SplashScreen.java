package com.android.q1learningapp.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.q1learningapp.R;

public class SplashScreen extends AppCompatActivity {

    //Variable to hold animation time for seconds
    private static int SPLASH_TIMER = 5000;

    ImageView splash_bgImage;
    TextView poweredByLine;

    Animation sideAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);


        //Hooks
        splash_bgImage = findViewById(R.id.splash_bgImage);
        poweredByLine = findViewById(R.id.poweredByLine);

        //Animations
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //Set Animations on Elements
        splash_bgImage.setAnimation(sideAnim);
        poweredByLine.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, FrontScreen.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);
    }
}