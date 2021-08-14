package com.example.quizapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import  java.util.*;

public class QuizActivity extends AppCompatActivity {

    public static final String Extra_Score = "extraScore";
    private static  final long Countdown_In_Milis=120000;
    private static  final String Key_score = "keyscore";
    private static final String Key_Question_Count = "keyQuestionCount";
    private static final String Key_Milis_Left = "keyMilisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCategory;
    private TextView textViewDifficulty;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3,rb4;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilis;

    private ArrayList<Question> questionArrayList;
    private int questionCounter;
    private  int questionCountTotal;
    private  Question currentQuestion;

    private int score;
    private boolean answered;
    private long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewCategory = findViewById(R.id.text_view_category);
        textViewDifficulty=findViewById(R.id.text_view_difficulty);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        buttonConfirmNext=findViewById(R.id.button_confirm_next);

        textColorDefaultRb= rb1.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();

        Intent intent = getIntent();
        int categoryID = intent.getIntExtra(StartingScreenActivity.Extra_Category_ID,0);
        String categoryName = intent.getStringExtra(StartingScreenActivity.Extra_Category_Name);
        String difficulty = intent.getStringExtra(StartingScreenActivity.Extra_Difficulty);

        textViewCategory.setText("Category: " + categoryName);
        textViewDifficulty.setText("Difficulty: " + difficulty);

        if(savedInstanceState == null)
        {
            QuizDb quizDb = QuizDb.getInstance(this);
            questionArrayList = quizDb.getQuestions(categoryID,difficulty);
            questionCountTotal=questionArrayList.size();
            Collections.shuffle(questionArrayList);
            showNextQuestion();

        }

        else
        {
            questionArrayList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal =questionArrayList.size();
            questionCounter = savedInstanceState.getInt(Key_Question_Count);
            currentQuestion = questionArrayList.get(questionCounter - 1);
            score = savedInstanceState.getInt(Key_score);
            timeLeftInMilis = savedInstanceState.getLong(Key_Milis_Left);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if(!answered)
                startCountDown();
            else
            {
                updateCountDownText();
                showSolution();
            }
        }
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else
                    showNextQuestion();

            }
        });
    }
    private  void showNextQuestion()
    {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionArrayList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");

            timeLeftInMilis = Countdown_In_Milis;
            startCountDown();
        }
        else
            finishQuiz();

    }

    private void startCountDown()
    {
        countDownTimer = new CountDownTimer(timeLeftInMilis , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timeLeftInMilis=0;
                updateCountDownText();
                checkAnswer();

            }
        }.start();
    }

    private void updateCountDownText()
    {
        int min = (int) (timeLeftInMilis / 1000) /60 ;
        int sec = (int) (timeLeftInMilis / 1000 ) % 60;

        String timeformatted = String.format(Locale.getDefault(),"%02d:%02d",min,sec);

        textViewCountDown.setText(timeformatted);

        if (timeLeftInMilis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }

    }

    private void checkAnswer()
    {
        answered = true;
        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }

        showSolution();

    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Option 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Option 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Option 3 is correct");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Option 4 is correct");
                break;
        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }

    private  void finishQuiz()
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Extra_Score,score);
        setResult(RESULT_OK,resultIntent);
        finish();
    }
    @Override
    public void onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressed = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(Key_score,score);
        outState.putInt(Key_Question_Count,questionCounter);
        outState.putLong(Key_Milis_Left,timeLeftInMilis);
        outState.putBoolean(KEY_ANSWERED,answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST,questionArrayList);
    }




}
