package com.example.ankit.quickquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ankit.quickquiz.HelperClasses.LogUtils;
import com.example.ankit.quickquiz.HelperClasses.MySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ankit.quickquiz.HelperClasses.LogUtils.MY_SOCKET_TIMEOUT_MS;
import static com.example.ankit.quickquiz.HelperClasses.PrefrenceConstants.MY_PREFRENCE;
import static com.example.ankit.quickquiz.HelperClasses.PrefrenceConstants.SIGN_UP_OR_NOT_KEY;


public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getName();

    public static final String LOGIN_URL = "http://104.236.57.114:8080/quickquiz/v1/user/authenticate";

    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL_ID = "emailId";


    Context mContext;
    RequestQueue mRequestQueue;

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    @BindView(R.id.input_email)
    TextInputEditText emailEditText;
    @BindView(R.id.input_password)
    TextInputEditText passwordEditText;

    @BindView(R.id.btn_login)
    Button loginButton;

    @BindView(R.id.link_signup)
    TextView signUpTextView;
    @BindView(R.id.tv_forget_pass)
    TextView forgetPassTextView;

    private String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        mContext = this;
        mSharedPreferences = getSharedPreferences(MY_PREFRENCE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }


    @Override
    protected void onStart() {
        super.onStart();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(view);
            }
        });


        /**
         *Launch {@link SignUpActivity} if user is not registered
         */
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });


        /**
         * DIALOG
         *when clicked on forgetPassTextView Launch Dialog and enter email to set password for email
         */
        forgetPassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                /*String message = "Forget Password";
                LogUtils.showSnackBar(mContext, view, message);
                LogUtils.T(mContext, message);*/
            }
        });
    }

    private void loginUser(final View view) {
        /**
         *Get Data From emailEditText and passwordEditText and post data using Volley to server
         */
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();

        if (email.equals("") || password.equals("")) {
            Snackbar snackbar = Snackbar.make(view, getString(R.string.empty_fields), Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
            snackbar.show();
        } else {
            mRequestQueue = MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    LOGIN_URL, new JSONObject(postLoginDetails()),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String message = "Login Succesfully\n" + response;
                            /**
                             *Update SharedPreferences so for {@link SplashScreen}
                             * to make decision  so as to which activity to open
                             * {@link MainActivity} OR
                             * {@link SignUpActivity} OR
                             * {@link LoginActivity}
                             * and also for the adding Questions To DB
                             */
                            updateSharedPrefrence();
                            Log.d(LOG_TAG, message);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String message = "Login ERROR - " + error;
                    Snackbar snackbar = Snackbar.make(view, getString(R.string.login_error), Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                    snackbar.show();
                    LogUtils.L(message);
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
    }

    private void updateSharedPrefrence() {
        mEditor.putBoolean(SIGN_UP_OR_NOT_KEY, Boolean.TRUE);
        mEditor.commit();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        Log.d(LOG_TAG, " Shared Prefrence Successfully Updated");
    }

    protected Map<String, String> postLoginDetails() {
        Map<String, String> params = new HashMap<>();
        params.put(KEY_EMAIL_ID, email);
        params.put(KEY_PASSWORD, password);
        return params;
    }
}
