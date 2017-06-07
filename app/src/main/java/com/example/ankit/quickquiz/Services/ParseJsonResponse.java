package com.example.ankit.quickquiz.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.ankit.quickquiz.DataModel.QuestionsModel;
import com.example.ankit.quickquiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ankit on 4/4/2017.
 */

public class ParseJsonResponse extends IntentService {

    ArrayList<QuestionsModel> questionsArrayList;

    public static final String LOG_TAG = ParseJsonResponse.class.getName();

    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */


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
    private String message;

    public ParseJsonResponse() {

        super(LOG_TAG);
        questionsArrayList = new ArrayList<>();

    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        /**
         * Get Intent Extra and
         * GET JSONObject from it
         */
        String jsonResponseString = intent.getStringExtra(getString(R.string.json_response_battle_response_key));

        //Convert back jsonResponseString to JSONObject
        JSONObject response = null;
        try {
            response = new JSONObject(jsonResponseString);
            //Log.d(LOG_TAG, response.toString());
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.toString());
            e.printStackTrace();
        }

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


                correctHits = singleQuestionJsonObject.getInt("correctHits");
                wrongHits = singleQuestionJsonObject.getInt("wrongHits");
                difficultyLevel = singleQuestionJsonObject.getInt("difficultyLevel");
                effeciency = singleQuestionJsonObject.getInt("efficiency");

                //Get Category of the question using categoryJsonObject
                JSONObject categoryJsonObject = singleQuestionJsonObject.getJSONObject("category");
                category = categoryJsonObject.getString("category");

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
                questionsArrayList.add(questionsObject);


            }
            try {
                Log.d(LOG_TAG, response.toString());
                message = questionsArrayList.get(1).getQuestion() + "\n" + questionsArrayList.get(1).getAnswerOption();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(LOG_TAG, message + "Yahoooo");
            //showMesssageOnUiThread(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showMesssageOnUiThread(String message) {

        final String msg = message;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

            }
        });
    }
}
