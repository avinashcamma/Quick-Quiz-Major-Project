package com.example.ankit.quickquiz;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankit.quickquiz.DataModel.QuestionsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class BattleQuestionsFragment extends Fragment implements View.OnClickListener {

    public static final String LOG_TAG = BattleQuestionsFragment.class.getName();

    public static final int NEXT_QUESTION_PAUSE_TIME = 2000;

    public static final int QUESTIONS_DEFAULT_TIME = 10000;

    public static final int FRAGMENT_LIFETIME = 12000;


    Context mContext;

    ArrayList<QuestionsModel> list = new ArrayList<>();


    @BindView(R.id.tv_battle_question)
    TextView battleQuestionTextView;
    @BindView(R.id.bt_battle_option1)
    Button option1Button;
    @BindView(R.id.bt_battle_option2)
    Button option2Button;
    @BindView(R.id.bt_battle_option3)
    Button option3Button;
    @BindView(R.id.bt_battle_option4)
    Button option4Button;

    @BindView(R.id.tv_time)
    TextView timeLeftTextView;
    @BindView(R.id.tv_battle_player_1_correct)
    TextView correctAnsTextView;


    /**
     * Member Variable to temporary store Values of Post
     */
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    int answerOption;

    /**
     * AI ALGORITH PARAMETERS POSTED BACK AFTER THE GAME ENDS
     */
    int correctHits;
    int difficultyLevel;
    int effeciency;
    int wrongHits;
    String category;


    int questionsPositions = 0;
    int correctOption = 0;
    int markedOption = -1;
    boolean userCorrectAnswer = false;
    int numberOfCorrectAnswsers = 0;

    private int incrementTime = 0;
    private long decrementTime = 0;
    int timeLeft = 10;


    public BattleQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battle_questions, container, false);
       /* battleQuestionTextView = (TextView) view.findViewById(R.id.tv_battle_question);
        option1Button = (Button) view.findViewById(R.id.bt_battle_option1);
        option2Button = (Button) view.findViewById(R.id.bt_battle_option2);
        option3Button = (Button) view.findViewById(R.id.bt_battle_option3);
        option4Button = (Button) view.findViewById(R.id.bt_battle_option4);*/
        ButterKnife.bind(this, view);
        option1Button.setOnClickListener(this);
        option2Button.setOnClickListener(this);
        option3Button.setOnClickListener(this);
        option4Button.setOnClickListener(this);
        populateUiwithQuestion();
        return view;
    }


    /**
     * DO NOT ACCESS VIES FROM HERE LIKE SETTEXT() setText etc.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         *GET Questions Model Object from {@link DualBattleQuestionsActivity} at questionsPositions
         * and then launch getDataFromQuestionsModel(q) to get question and options for the fragment
         */
        list = ((DualBattleQuestionsActivity) getActivity()).getQuestionsArrayList();
        questionsPositions = ((DualBattleQuestionsActivity) getActivity()).getQuestionsPosition();

        QuestionsModel q = new QuestionsModel();
        q = list.get(questionsPositions);

        numberOfCorrectAnswsers = ((DualBattleQuestionsActivity) getActivity()).getUserCorrectQuestions();
        /**
         *GET Questions and it's related data first in OnCreate()
         * and then start time
         */
        getDataFromQuestionsModel(q);
        try {
            question = q.getQuestion();
            Log.d(LOG_TAG, question);
            option1 = q.getOption1();
            option2 = q.getOption2();
            option3 = q.getOption3();
            option4 = q.getOption4();
            category = q.getCategory();
            answerOption = q.getAnswerOption();


        } catch (Exception e) {
            e.printStackTrace();
        }
        //################ NEVER POPULATE DATA INSIDE FRAGMENT IN onCreate() method due to LifeCycle Callbacks
        //VIES ARE NOT ACCESSIBLE

    }

    private void getDataFromQuestionsModel(QuestionsModel q) {
        try {
            question = q.getQuestion();
            option1 = q.getOption1();
            option2 = q.getOption2();
            option3 = q.getOption3();
            option4 = q.getOption4();
            category = q.getCategory();
            answerOption = q.getAnswerOption();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateUiwithQuestion() {

        try {
            battleQuestionTextView.setText(question);
            option1Button.setText(option1);
            option2Button.setText(option2);
            option3Button.setText(option3);
            option4Button.setText(option4);
            startTime();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void startTime() {
        new CountDownTimer(FRAGMENT_LIFETIME, 1000) {

            @Override
            public void onTick(long l) {

                //decrementTime = l / 1000;
                incrementTime++;
                if (timeLeft > 0) {
                    timeLeft--;
                    timeLeftTextView.setText("TimeLeft: " + timeLeft);
                    correctAnsTextView.setText("Correct: " + numberOfCorrectAnswsers);
                } else {
                    /**
                     *CHECK if incrementTime==10 seconds or not, if yes then show
                     * the correct answer and mark everything unclickable #justInCase.
                     */
                    //Log.e(LOG_TAG, "Button COLOR" + incrementTime);
                    //markAllButtonsUnclickAble();
                    if (answerOption == 1) {
                        option1Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                        Log.e(LOG_TAG, "Button COLOR" + incrementTime);
                    } else if (answerOption == 2) {
                        option2Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                        Log.e(LOG_TAG, "Button COLOR" + incrementTime);
                    } else if (answerOption == 3) {
                        option3Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                        Log.e(LOG_TAG, "Button COLOR" + incrementTime);
                    } else if (answerOption == 4) {
                        option4Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                        Log.e(LOG_TAG, "Button COLOR" + incrementTime);
                    } else {
                        Toast.makeText(mContext, "OPSEEEE!!!!\n NO Button CLICKED ", Toast.LENGTH_LONG).show();
                        Log.e(LOG_TAG, " NO Button CLICKED");
                    }
                    timeLeftTextView.setText("0");
                }
            }

            @Override
            public void onFinish() {
                Log.d(LOG_TAG, "NEXT FRAGMENT");
                //Launch Next Question on Time Complete
                try {
                    ((DualBattleQuestionsActivity) getActivity()).updateUiwithQusttions();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }


    @Override
    public void onStart() {
        super.onStart();
        questionsPositions = ((DualBattleQuestionsActivity) getActivity()).getQuestionsPosition();
        Log.d(LOG_TAG, " QUESTION POSITION " + questionsPositions);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        /**
         *1. Mark choosed buttion as selected by changing it's color and disable changing option
         * and mark All other options unclickable
         * 2. Check this option against the solution after 10 seconds and mark the correct option
         * as Green COLOR
         */
        switch (id) {
            case R.id.bt_battle_option1:
                option1Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.battle_option_selected_clr));
                markAllButtonsUnclickAble();
                markedOption = 1;
                checkForNumberOfCorrectAns();
                break;
            case R.id.bt_battle_option2:
                option2Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.battle_option_selected_clr));
                markAllButtonsUnclickAble();
                markedOption = 2;
                checkForNumberOfCorrectAns();
                break;
            case R.id.bt_battle_option3:
                option3Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.battle_option_selected_clr));
                markAllButtonsUnclickAble();
                markedOption = 3;
                checkForNumberOfCorrectAns();

                break;
            case R.id.bt_battle_option4:
                option4Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.battle_option_selected_clr));
                markAllButtonsUnclickAble();
                markedOption = 4;
                checkForNumberOfCorrectAns();

                break;
            default:
                /**
                 *If user does not clicks so do call checkForNumberOfCorrectAns so that
                 * wrongAnswers can also be updated in {@link DualBattleQuestionsActivity}
                 */
                checkForNumberOfCorrectAns();
                break;
        }
    }

    private void checkForNumberOfCorrectAns() {
        /**
         * ######### THIS MUST ONLY BE CALLED ONCE PER QUESTION AND IF CLICK HAPPENS
         *
         *Check if user ans is correct or not
         * and then send this info back to the activity
         * 1. Basically just call a function in {@link DualBattleQuestionsActivity}
         * which increments the total user correct answers
         * and increment the total number of incorrect answers
         * if the answer is wrong
         */
        if (markedOption == answerOption) {
            userCorrectAnswer = true;
            /**
             * Call a function which just increment the number of questions user has corrected;
             */
            ((DualBattleQuestionsActivity) getActivity()).userCorrectQuestion();
        } else {
            /**
             * Call a function which just increment the number of questions user has done wrong
             */
            ((DualBattleQuestionsActivity) getActivity()).userInCorrectQuestions();
        }
    }

    private void markAllButtonsUnclickAble() {
        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);

    }


    /*public void markCorrectAnswer() {

        *//**
     * Mark CORRECT ANS as GREEN after 10 seconds QUESTIONS_DEFAULT_TIME
     *//*
        if (incrementTime >= 10) {
            if (markedOption == 1) {
                option1Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
            } else if (markedOption == 2) {
                option2Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
            } else if (markedOption == 3) {
                option3Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
            } else if (markedOption == 4) {
                option4Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
            }
        }

    }*/
}
