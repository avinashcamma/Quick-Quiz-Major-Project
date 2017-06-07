package com.example.ankit.quickquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ankit.quickquiz.DataModel.QuestionsModel;
import com.example.ankit.quickquiz.Database.CustomContentProvider;
import com.example.ankit.quickquiz.HelperClasses.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;

import static com.example.ankit.quickquiz.HelperClasses.LogUtils.MY_SOCKET_TIMEOUT_MS;

public class DualBattleQuestionsActivity extends AppCompatActivity {

    private static final String LOG_TAG = DualBattleQuestionsActivity.class.getName();


    private static final String BATTLE_QUESTIONS_URL = "http://104.236.57.114:8080/quickquiz/v1/questions/battle/get_questions";

    private static final String CORRECT_QUESTIONS_KEY = "numberOfCorrectQuestions";
    private static final String INCORRECT_QUESTIONS_KEY = "numberOfInCorrectQuestions";


    Context mContext;
    CustomContentProvider mCustomContentProvider;

    RequestQueue mRequestQueue;


    int userCorrectQuestions = 0;
    int userInCorrectQuestions = 0;
    int questionsPositions = 0;
    int correctOption = 0;


    ArrayList<QuestionsModel> questionsArrayList;


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
    int categoryId;
    int questionId;
    private String message;


    ProgressBar spinner;

    @BindView(R.id.activity_dual_battle_questions)
    CoordinatorLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_battle_questions);
        mContext = this;
        spinner = (ProgressBar) findViewById(R.id.pb_loading_questions);
        loadBattleQuestions();


    }




   /* private void showCorrectAnswer() {
        //Get question position from the Array List and the correct ans position
        QuestionsModel q = new QuestionsModel();
        q = questionsArrayList.get(questionsPositions);
        correctOption = q.getAnswerOption();
        if (correctOption == 1) {
            option1Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
        } else if (correctOption == 2) {
            option2Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
        } else if (correctOption == 3) {
            option3Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
        } else if (correctOption == 4) {
            option4Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
        } else {
            Toast.makeText(mContext, "OPSEEEE!!!!", Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "OPTION GALAT BHAI");
        }

    }*/


    private void loadBattleQuestions() {
        mRequestQueue = MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(BATTLE_QUESTIONS_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(LOG_TAG, response.toString());
                        //Here start Intent Service that'll parse JsonPostResponse on background Thread

                        /*Intent parseIntent = new Intent(DualBattleQuestionsActivity.this, ParseJsonResponse.class);
                        *//**
                         * Also pass the result JSONObject as Intent extra
                         *//*
                        parseIntent.putExtra(getString(R.string.json_response_battle_response_key), response.toString());
                        startService(parseIntent);*/
                        /**
                         * Currently No {@link android.app.IntentService}
                         */
                        parseJsonResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        //Set Retry Policy for MY_SOCKET_TIMEOUT_MS = 10 SECONDS
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);

    }

    public void parseJsonResponse(JSONObject response) {
        questionsArrayList = new ArrayList<>();

        /**
         *Start Parsing the list
         */
        try {
            JSONArray jsonArray = response.getJSONArray("questionList");
            int lenght = jsonArray.length();
            JSONObject singleQuestionJsonObject;
            for (int i = 0; i < lenght; i++) {
                QuestionsModel questionsObject = new QuestionsModel();

                singleQuestionJsonObject = jsonArray.getJSONObject(i);

                question = singleQuestionJsonObject.getString("question");
                option1 = singleQuestionJsonObject.getString("optionOne");
                option2 = singleQuestionJsonObject.getString("optionTwo");
                option3 = singleQuestionJsonObject.getString("optionThree");
                option4 = singleQuestionJsonObject.getString("optionFour");
                answerOption = singleQuestionJsonObject.getInt("answer");
                questionId = singleQuestionJsonObject.getInt("questionId");


                correctHits = singleQuestionJsonObject.getInt("correctHits");
                wrongHits = singleQuestionJsonObject.getInt("wrongHits");
                difficultyLevel = singleQuestionJsonObject.getInt("difficultyLevel");
                effeciency = singleQuestionJsonObject.getInt("efficiency");

                //Get Category of the question using categoryJsonObject
                JSONObject categoryJsonObject = singleQuestionJsonObject.getJSONObject("category");
                category = categoryJsonObject.getString("category");
                categoryId = categoryJsonObject.getInt("categoryId");

                /**
                 *Set questions Data Object
                 */
                questionsObject.setQuestion(question);
                questionsObject.setOption1(option1);
                questionsObject.setOption2(option2);
                questionsObject.setOption3(option3);
                questionsObject.setOption4(option4);
                questionsObject.setAnswerOption(answerOption);

                questionsObject.setCorrectHits(correctHits);
                questionsObject.setWrongHits(wrongHits);
                questionsObject.setDifficultyLevel(difficultyLevel);
                questionsObject.setEffeciency(effeciency);
                questionsObject.setCategory(category);
                questionsObject.setCategoryId(categoryId);
                questionsArrayList.add(questionsObject);


            }
            try {
                Log.d(LOG_TAG, response.toString());
                //message = questionsArrayList.get(1).getQuestion() + "\n" + questionsArrayList.get(1).getAnswerOption();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Log.d(LOG_TAG, message);
            //showMesssageOnUiThread(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        spinner.setVisibility(View.GONE);
        updateUiwithQusttions();
        //Add Questions to Database after parsing
        addQuestionToDb();

    }

    public void updateUiwithQusttions() {
        /**
         *Send question and position of the question to the {@link BattleQuestionsFragment}
         * and update the posititon of the question in the questionsArrayList
         */
        try {
            /**If questions position is>=10 then
             * Game Over Launch Game Over Screenions
             */
            if (questionsPositions >= 10) {
                //CREATE GAME OVER SCREEN TO DISPLAY RESULT and send Info to display
                Toast.makeText(mContext, getString(R.string.game_over), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DualBattleQuestionsActivity.this, DualBattleResultActivity.class);
                intent.putExtra(CORRECT_QUESTIONS_KEY, userCorrectQuestions);
                intent.putExtra(INCORRECT_QUESTIONS_KEY, userInCorrectQuestions);
                startActivity(intent);
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_dual_battle_questions, new BattleQuestionsFragment())
                        .commit();

                Log.d(LOG_TAG, "FRAGMENT STARTED N0--" + questionsPositions);
                questionsPositions++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addQuestionToDb() {
        try {
            int questionId = 0;
            String categoryString;
            mCustomContentProvider = new CustomContentProvider(mContext);
            int size = questionsArrayList.size();
            QuestionsModel questionsModel = new QuestionsModel();
            for (int i = 0; i < size; i++) {
                questionsModel = questionsArrayList.get(i);
                questionId = questionsModel.getQuestionId();
                categoryString = questionsModel.getCategory();
                mCustomContentProvider.addQuestion(questionId, categoryString);
            }
            mCustomContentProvider.addQuestionsToDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * THIS Method used in Fragment to get Question At questionsPositions
     * which is incremented after each fragmentTransition
     *
     * @return QuestionsModel
     */
    public QuestionsModel questionAtPosition() {
        QuestionsModel q = new QuestionsModel();
        q = questionsArrayList.get(questionsPositions - 1);
        return q;
    }

    public void userCorrectQuestion() {
        userCorrectQuestions++;
    }

    public void userInCorrectQuestions() {
        userInCorrectQuestions++;
    }

    public int getUserCorrectQuestions() {
        return userCorrectQuestions;
    }

    public int getUserInCorrectQuestions() {
        return userInCorrectQuestions;
    }

    public ArrayList<QuestionsModel> getQuestionsArrayList() {
        return questionsArrayList;
    }

    public int getQuestionsPosition() {
        /**
         * -1 Because after the start of {@link BattleQuestionsFragment}
         * questionsPositions is incremented, so 1st question will never be dislayed
         */
        return questionsPositions - 1;
    }


    private boolean checkCorrectOption(int correctOption, int clickedOttion) {
        if (correctOption == clickedOttion) {
            return true;
        } else {
            return false;
        }
    }

    private int opponentPlayerTime() {
        Random random = new Random();
        //Generate random number from 0 to 10
        int num = random.nextInt(10) + 1;
        return num;
    }

}
