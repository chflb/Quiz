package com.example.quizgenerator;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity  implements View.OnClickListener {
    public static final String EXTRA_SCORE = "extraScore";
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;




    private TextView questionTextView;
    private TextView textViewScore;

    private Button b1;
    private Button b2;
    private Button b3;
    private Button nextBtn;

    private ColorStateList textColorDefaultRb;

    private List<Question> questionList;
    private int questionCounter;
    private int totalCountQuestion;
    private Question displayedQuestion;

    private int score;

    private long backPressedTime;
    private Button clickedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        questionTextView = findViewById(R.id.question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        nextBtn = findViewById(R.id.button_confirm_next);


        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);


        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        totalCountQuestion = questionList.size();

        Collections.shuffle(questionList);

        showNextQuestion();

       nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    nextBtn.setBackgroundResource(R.drawable.button_border_confirm_next_clicked);
                    if (v!=null) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }

                    showNextQuestion();
            }
        });
    }

    private void showNextQuestion() {
        b1.setCompoundDrawablesWithIntrinsicBounds( null, null, null, null);//clear drwable left image
        b2.setCompoundDrawablesWithIntrinsicBounds( null, null, null, null);
        b3.setCompoundDrawablesWithIntrinsicBounds( null, null, null, null);

        b1.setBackgroundResource(R.drawable.button_border);
        b2.setBackgroundResource(R.drawable.button_border);
        b3.setBackgroundResource(R.drawable.button_border);

        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);

        if (questionCounter < totalCountQuestion) {
            displayedQuestion = questionList.get(questionCounter);

            questionTextView.setText(displayedQuestion.getQuestion());
            b1.setText(displayedQuestion.getOption1());
            b2.setText(displayedQuestion.getOption2());
            b3.setText(displayedQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + totalCountQuestion);
        } else {
            sendFinalScoreToMAin();
        }
    }

    private void checkAnswer() {

        String answer= clickedButton.getText().toString();
        if(answer.equals(displayedQuestion.getReponse())){
            clickedButton.setBackgroundResource(R.drawable.button_border_true_answer);
            Drawable img = clickedButton.getContext().getResources().getDrawable( R.drawable.okk );
            img.setBounds(32,32,32,32);
            clickedButton.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null);//left drawable

            score++;
            textViewScore.setText("Score: " + score);


        } else{
            clickedButton.setBackgroundResource(R.drawable.button_border_false_answer);
           // clickedButton.setCompoundDrawablesWithIntrinsicBounds( 25, 0, 0, 0);
            Drawable img = clickedButton.getContext().getResources().getDrawable( R.drawable.faux );
            clickedButton.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null);
            clickedButton.setCompoundDrawablePadding(15);
        }
        if (questionCounter < totalCountQuestion) {
            nextBtn.setText("Next");
        } else {
            nextBtn.setText("Finish");
        }
    }






    private void sendFinalScoreToMAin() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            sendFinalScoreToMAin();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    public void onClick(View v) {
 /*   if(v.getId()==nextBtn.getId()) {
        checkAnswer();
        //} else {
        //    Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
        //}
        showNextQuestion();

    }else {

  */
            clickedButton = (Button) findViewById(v.getId());
            clickedButton.setBackgroundResource(R.drawable.button_border_clicked);
            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
       // }

    }

}