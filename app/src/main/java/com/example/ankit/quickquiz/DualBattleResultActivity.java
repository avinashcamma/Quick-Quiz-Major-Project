package com.example.ankit.quickquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DualBattleResultActivity extends AppCompatActivity {

    private static final String CORRECT_QUESTIONS_KEY = "numberOfCorrectQuestions";
    private static final String INCORRECT_QUESTIONS_KEY = "numberOfInCorrectQuestions";
    public static final String SKIPPED_QUESTIONS_KEY = "numberOfSkippedQuestions";

    @BindView(R.id.tv_name_battle_result)
    TextView playerNameTextView;
    @BindView(R.id.tv_correct_battle_result)
    TextView playerNuberOfCorrectQuestioonsTextView;
    @BindView(R.id.tv_incorrect_battle_result)
    TextView playerNuberOfInCorrectQuestioonsTextView;
    @BindView(R.id.tv_skipped_battle_result)
    TextView playerNumberOfSkippedQuestionsTextView;

    int numberOfCorrectQuestions = 0;
    int numberOfInCorrectQuestions = 0;
    int numberOfSkippedQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_battle_result);

        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            numberOfCorrectQuestions = bundle.getInt(CORRECT_QUESTIONS_KEY);
            numberOfInCorrectQuestions = bundle.getInt(INCORRECT_QUESTIONS_KEY);
        }
        numberOfSkippedQuestions = 10 - numberOfCorrectQuestions - numberOfInCorrectQuestions;
        setBattleResultData();
    }

    private void setBattleResultData() {
        playerNameTextView.setText("Ankit");
        playerNuberOfCorrectQuestioonsTextView.setText("Correct: " + numberOfCorrectQuestions);
        playerNuberOfInCorrectQuestioonsTextView.setText("Incorrect: " + numberOfInCorrectQuestions);
        playerNumberOfSkippedQuestionsTextView.setText("Skipped: " + numberOfSkippedQuestions);
    }


}
