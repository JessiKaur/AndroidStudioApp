package com.android.q1learningapp.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.q1learningapp.HelperClasses.QuestionsClass;
import com.android.q1learningapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestions extends AppCompatActivity {

    private TextView queCounter, quizQuestion;
    private RelativeLayout optionsSet;
    private Button nextBtn, optionOne, optionTwo, optionThree, optionFour;
    List<QuestionsClass> questionList;
    private int count = 0;
    private int position = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        queCounter = findViewById(R.id.q_question_Count);
        quizQuestion = findViewById(R.id.q_quiz_Questions);
        optionsSet = findViewById(R.id.q_options_container);
        nextBtn = findViewById(R.id.q_next_question);

        optionOne = findViewById(R.id.q_option_1);
        optionTwo = findViewById(R.id.q_option_2);
        optionThree = findViewById(R.id.q_option_3);
        optionFour = findViewById(R.id.q_option_4);

        //Get Questions List from Model
        getQuestionsList();
        setQuestion();

        //Option Click Functionality
        for (int j = 0; j< 4 ; j++){
            optionsSet.getChildAt(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer((Button) view);
                }
            });
        }

        playAnim(quizQuestion, 0, questionList.get(position).getQuestion());
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextBtn.setEnabled(false);
                nextBtn.setAlpha(0.7f);
                enableOption(true);
                position++;
                /*Toast.makeText(QuizQuestions.this, position + "-" + questionList.size(), Toast.LENGTH_SHORT).show();*/
                if(position == questionList.size()){
                    // No More Questions Left - Go to Score Activity
                    Intent intent = new Intent(QuizQuestions.this, ScoresActivity.class);
                    String _totalQuestions = String.valueOf(questionList.size());
                    String _totalScores = String.valueOf(score);
                    //Pass all fields to the next activity
                    intent.putExtra("totalScores", _totalScores);
                    intent.putExtra("totalQues", _totalQuestions);
                    startActivity(intent);
                }
                count = 0;
                if(position < questionList.size()){
                    playAnim(quizQuestion, 0, questionList.get(position).getQuestion());
                }
            }
        });


    }


    private void getQuestionsList() {
        questionList = new ArrayList<>();

        questionList.add(new QuestionsClass("If an accompanying driver is older than 22, their blood alcohol level must be less than", "0.07", "0.08", "0.00", "0.04", "0.04"));
        questionList.add(new QuestionsClass("Question 2", "A", "B", "C", "D", "C"));
        questionList.add(new QuestionsClass("Question 3", "A", "B", "C", "D", "D"));
        questionList.add(new QuestionsClass("Question 4", "A", "B", "C", "D", "A"));
        questionList.add(new QuestionsClass("Question 5", "A", "B", "C", "D", "D"));
        questionList.add(new QuestionsClass("Question 6", "A", "B", "C", "D", "D"));

    }

    private void setQuestion() {
        quizQuestion.setText(questionList.get(0).getQuestion());
        optionOne.setText(questionList.get(0).getOptionA());
        optionTwo.setText(questionList.get(0).getOptionB());
        optionThree.setText(questionList.get(0).getOptionC());
        optionFour.setText(questionList.get(0).getOptionD());

    }

    private void playAnim(final View view, final int value, final String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(400)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        if (value == 0 && count < 4) {
                            String option = "";
                            if(count == 0){
                                //Option A
                                option = questionList.get(position).getOptionA();
                            }else if(count == 1){
                                option = questionList.get(position).getOptionB();
                            }
                            else if(count == 2){
                                option = questionList.get(position).getOptionC();
                            }
                            else if(count == 3){
                                option = questionList.get(position).getOptionD();
                            }
                            playAnim(optionsSet.getChildAt(count), 0 , option);
                            count++;
                        }
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        //Data change
                        if (value == 0) {
                            try {
                                ((TextView)view).setText(data);
                                queCounter.setText(position+1+"/"+questionList.size());
                            }catch (ClassCastException ex){
                                ((Button)view).setText(data);
                            }
                            view.setTag(data);
                            playAnim(view, 1, data);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
    }

    @SuppressLint("NewApi")
    private void checkAnswer(Button selectedOption){
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if(selectedOption.getText().toString().equals(questionList.get(position).getCorrectAnswer())){
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF478C4A")));
            score++;
        }
        else{
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            Button correctOption = (Button) optionsSet.findViewWithTag(questionList.get(position).getCorrectAnswer());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF6CBF6F")));
        }
    }

    @SuppressLint("NewApi")
    private void enableOption(boolean enable){
        for (int i = 0; i< 4 ; i++){
            optionsSet.getChildAt(i).setEnabled(enable);
            if(enable){
                optionsSet.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            }
        }
    }
}