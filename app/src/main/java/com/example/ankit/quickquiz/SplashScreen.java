package com.example.ankit.quickquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ankit.quickquiz.Database.CustomContentProvider;

import static com.example.ankit.quickquiz.HelperClasses.PrefrenceConstants.MY_PREFRENCE;
import static com.example.ankit.quickquiz.HelperClasses.PrefrenceConstants.SIGN_UP_OR_NOT_KEY;

public class SplashScreen extends AppCompatActivity {

    public static final String LOG_TAG = SplashScreen.class.getName();


    public static final int SPLASH_TIME_OUT = 3000;

    Context mContext;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    CustomContentProvider mCustomContentProvider;
    boolean signUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mContext = this;
        mCustomContentProvider = new CustomContentProvider(mContext);
        mSharedPreferences = getSharedPreferences(MY_PREFRENCE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        signUp = mSharedPreferences.getBoolean(SIGN_UP_OR_NOT_KEY, false);


        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                /**
                 *
                 *If User has signed Up Successfully earlier (check signUp variable)
                 * then Launch {@link MainActivity}
                 *
                 * else
                 * and addToDbFirstTime and continue with the registration
                 * so {@link SignUpActivity} will be called after Timer is Over
                 */
                if (signUp) {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    // close this activity SplashScreen
                    finish();
                } else {
                    //addToDbFirstTime();
                    /** and then call finsh to close this activity
                     */
                    startActivity(new Intent(SplashScreen.this, SignUpActivity.class));
                    // close this activity SplashScreen
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }

    /**
     * Method called for adding the questionIds for the first time
     * for all the sections and launch {@link SignUpActivity}
     */
    private void addToDbFirstTime() {

        String CATEGORY_TWO = "english_antonymous";
        String CATEGORY_THREE = "general_awareness";
        String CATEGORY_FOUR = "reasoning";

        try {
            //English questionIds
            int englishQuestionPracticeIds[] = {1, 2, 4, 5, 6, 7, 8, 81, 82, 83, 84, 85, 86, 87, 89, 90};
            for (int i = 0; i < englishQuestionPracticeIds.length; i++) {
                mCustomContentProvider.addQuestion(1, CATEGORY_TWO);
            }

            //GK Questions
            int gkQuestionPracticeIds[] = {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
            for (int i = 0; i < gkQuestionPracticeIds.length; i++) {
                mCustomContentProvider.addQuestion(2, CATEGORY_THREE);
            }

            //Reasoning Questions
            int reasoningQuestionPracticeIds[] = {60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72};
            for (int i = 0; i < reasoningQuestionPracticeIds.length; i++) {
                mCustomContentProvider.addQuestion(3, CATEGORY_FOUR);
            }
            mCustomContentProvider.addQuestionsToDB();
            Log.d("Splash Screen Activity", " Question Ids added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
