package com.android.q1learningapp.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.q1learningapp.R;

public class AllQuizActivity extends AppCompatActivity {

    RelativeLayout firstQuizBox;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_quiz);

        //Hooks
        firstQuizBox = findViewById(R.id.quiz_First);
        backBtn = findViewById(R.id.backButtonPress);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllQuizActivity.super.onBackPressed();
            }
        });

        firstQuizBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllQuizActivity.this, QuizQuestions.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AllQuizActivity.this, UserDashboard.class);
        startActivity(intent);
    }
}