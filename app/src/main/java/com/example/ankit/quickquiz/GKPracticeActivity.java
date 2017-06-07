package com.example.ankit.quickquiz;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ankit.quickquiz.Database.CustomContentProvider;
import com.example.ankit.quickquiz.HelperClasses.MySingleton;

import org.json.JSONObject;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ankit.quickquiz.HelperClasses.LogUtils.MY_SOCKET_TIMEOUT_MS;

public class GKPracticeActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = GKPracticeActivity.class.getName();

    public static String QUESTION_ID_URL = "http://104.236.57.114:8080/quickquiz/v1/questions/question_detail/";

    Context mContext;

    private RequestQueue mRequestQueue;

    CustomContentProvider mCustomContentProvider;

    int[] gkQuestionIdsArray;//= new int[];

    @BindView(R.id.tv_question_gkpa)
    TextView questionTextView;
    @BindView(R.id.bt_button1_gkpa)
    Button option1Button;
    @BindView(R.id.bt_button2_gkpa)
    Button option2Button;
    @BindView(R.id.bt_button3_gkpa)
    Button option3Button;
    @BindView(R.id.bt_button4_gkpa)
    Button option4Button;
    @BindView(R.id.bt_next_gkpa)
    Button nextButton;
    @BindView(R.id.bt_see_ans_gkpa)
    Button answerAnsButton;

//    private int size;
//    int questionIdsPosition = 0;


    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    int answerOption;

    private int buttonClicked;

    @BindView(R.id.pb_loading_questions_gkpa)
    ProgressBar spinner;
    @BindView(R.id.gkpa_cl_questions_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gkpractice);
        ButterKnife.bind(this);
        mContext = this;
        mCustomContentProvider = new CustomContentProvider(mContext);
        gkQuestionIdsArray = mCustomContentProvider.getGkQuestions();

        option1Button.setOnClickListener(this);
        option2Button.setOnClickListener(this);
        option3Button.setOnClickListener(this);
        option4Button.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        answerAnsButton.setOnClickListener(this);

        /**
         *Load 1st question from the database
         */
        loadQuestions();
    }

    private void loadQuestions() {
        int id = getRandomInt();
//        QUESTION_ID_URL += id;
        String URL = QUESTION_ID_URL + id;
        Log.d(LOG_TAG, URL);

        mRequestQueue = MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

    private void parseJsonResponse(JSONObject response) {
        /**
         *Parse the question
         */
        try {
            question = response.getString("question");
            answerOption = response.getInt("answer");
            option1 = response.getString("optionOne");
            option2 = response.getString("optionTwo");
            option3 = response.getString("optionThree");
            option4 = response.getString("optionFour");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDataToViews();
    }

    private void setDataToViews() {
        spinner.setVisibility(View.GONE);
        mCoordinatorLayout.setVisibility(View.VISIBLE);

        option1Button.setBackgroundResource(android.R.drawable.btn_default);
        option2Button.setBackgroundResource(android.R.drawable.btn_default);
        option3Button.setBackgroundResource(android.R.drawable.btn_default);
        option4Button.setBackgroundResource(android.R.drawable.btn_default);

        questionTextView.setText(question);
        option1Button.setText(option1);
        option2Button.setText(option2);
        option3Button.setText(option3);
        option4Button.setText(option4);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bt_next_gkpa:
                mCoordinatorLayout.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
                loadQuestions();
                break;
            case R.id.bt_see_ans_gkpa:
                if (answerOption == 1) {
                    option1Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                } else if (answerOption == 2) {
                    option2Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                } else if (answerOption == 3) {
                    option3Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                } else if (answerOption == 4) {
                    option4Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                } else {
                    //Toast.makeText(mContext, "OPSEEEE!!!!", Toast.LENGTH_LONG).show();
                    Log.e(LOG_TAG, " NO Button CLICKED");
                }
                break;
            case R.id.bt_button1_gkpa:
                option1Button.setBackgroundResource(android.R.drawable.btn_default);
                option2Button.setBackgroundResource(android.R.drawable.btn_default);
                option3Button.setBackgroundResource(android.R.drawable.btn_default);
                option4Button.setBackgroundResource(android.R.drawable.btn_default);
                option1Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.battle_option_selected_clr));
                break;
            case R.id.bt_button2_gkpa:
                option1Button.setBackgroundResource(android.R.drawable.btn_default);
                option2Button.setBackgroundResource(android.R.drawable.btn_default);
                option3Button.setBackgroundResource(android.R.drawable.btn_default);
                option4Button.setBackgroundResource(android.R.drawable.btn_default);
                option2Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.battle_option_selected_clr));
                break;
            case R.id.bt_button3_gkpa:
                option1Button.setBackgroundResource(android.R.drawable.btn_default);
                option2Button.setBackgroundResource(android.R.drawable.btn_default);
                option3Button.setBackgroundResource(android.R.drawable.btn_default);
                option4Button.setBackgroundResource(android.R.drawable.btn_default);
                option3Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.battle_option_selected_clr));
                break;
            case R.id.bt_button4_gkpa:
                option1Button.setBackgroundResource(android.R.drawable.btn_default);
                option2Button.setBackgroundResource(android.R.drawable.btn_default);
                option3Button.setBackgroundResource(android.R.drawable.btn_default);
                option4Button.setBackgroundResource(android.R.drawable.btn_default);
                option4Button.setBackgroundColor(ContextCompat.getColor(mContext, R.color.battle_option_selected_clr));
                break;
            default:
                option1Button.setBackgroundResource(android.R.drawable.btn_default);
                option2Button.setBackgroundResource(android.R.drawable.btn_default);
                option3Button.setBackgroundResource(android.R.drawable.btn_default);
                option4Button.setBackgroundResource(android.R.drawable.btn_default);
                break;
        }

    }

    private int getRandomInt() {
        Random random = new Random();
        return gkQuestionIdsArray[random.nextInt(gkQuestionIdsArray.length - 0)];
    }
}
