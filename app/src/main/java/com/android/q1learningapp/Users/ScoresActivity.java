package com.android.q1learningapp.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.q1learningapp.R;
import com.google.android.material.navigation.NavigationView;

public class ScoresActivity extends AppCompatActivity{

    TextView totalScoresText;
    ImageView backBtn;
    private String finalScores, totalQues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scores);

        totalScoresText = findViewById(R.id.total_scores_text);
        backBtn = findViewById(R.id.backButtonPress);

        //Get all the data from Intent
        totalQues = getIntent().getStringExtra("totalQues");
        finalScores = getIntent().getStringExtra("totalScores");

        totalScoresText.setText(finalScores + "/" + totalQues);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoresActivity.this, AllQuizActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ScoresActivity.this, AllQuizActivity.class);
        startActivity(intent);
    }

}