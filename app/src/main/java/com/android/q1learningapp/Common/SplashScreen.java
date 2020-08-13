package com.android.q1learningapp.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.q1learningapp.Databases.SessionManager;
import com.android.q1learningapp.HelperClasses.LocaleHelper;
import com.android.q1learningapp.HelperClasses.QuestionsClass;
import com.android.q1learningapp.R;
import com.android.q1learningapp.Users.QuizQuestions;
import com.android.q1learningapp.Users.UserDashboard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    //Variable to hold animation time for seconds
    private static int SPLASH_TIMER = 5000;

    ImageView splash_bgImage;
    TextView poweredByLine;

    Animation sideAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // your language
        changeLanguage(LocaleHelper.getLanguage(SplashScreen.this));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        //Toast.makeText(SplashScreen.this,LocaleHelper.getLanguage(SplashScreen.this), Toast.LENGTH_SHORT).show();


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
                //Start Session
                SessionManager sessionManager = new SessionManager(SplashScreen.this);
                Boolean sessioncheck =  sessionManager.checkLogin();
                if (sessioncheck) {
                    //User Already Signed In
                    startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                } else {
                    //User Signed Out
                    Intent intent = new Intent(SplashScreen.this, FrontScreen.class);
                    startActivity(intent);
                    finish();
                }
                // your language
                changeLanguage(LocaleHelper.getLanguage(SplashScreen.this));

            }
        }, SPLASH_TIMER);

        insertRootNodes();

    }

    private void insertRootNodes() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Scores").child("+4674239999");

        myRef.setValue("Hello, World!");
    }


    public void changeLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}