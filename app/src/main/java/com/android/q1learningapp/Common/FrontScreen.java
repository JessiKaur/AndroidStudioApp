package com.android.q1learningapp.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.android.q1learningapp.Common.LoginSignup.LoginActivity;
import com.android.q1learningapp.Common.LoginSignup.SignUpActivity;
import com.android.q1learningapp.R;

public class FrontScreen extends AppCompatActivity {

    Button login_button;
    Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_front_screen);


        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginView();
            }
        } );

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterView();
            }
        });

    }

    public void LoginView(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void RegisterView(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}