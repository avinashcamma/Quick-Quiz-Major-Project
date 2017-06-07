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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ankit.quickquiz.HelperClasses.PrefrenceConstants.MY_PREFRENCE;
import static com.example.ankit.quickquiz.HelperClasses.PrefrenceConstants.SIGN_UP_OR_NOT_KEY;
import static com.example.ankit.quickquiz.HelperClasses.PrefrenceConstants.USER_ID_PREFRENCE;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignUpActivity.class.getName();


    public static final String REGISTRATION_URL = "http://104.236.57.114:8080/quickquiz/v1/user/register";

    private static final String KEY_USERNAME = "userName";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL_ID = "emailId";
    private static final int MY_SOCKET_TIMEOUT_MS = 5000;


    RequestQueue mRequestQueue;
    Context mContext;


    @BindView(R.id.et_sign_up_email)
    TextInputEditText emailSignUpEditText;
    @BindView(R.id.et_signup_password)
    TextInputEditText passwordSignUpEditText;
    @BindView(R.id.et_signup_username)
    TextInputEditText userNameSignUpEditText;


    @BindView(R.id.btn_sign_up)
    Button signUpButton;

    @BindView(R.id.tv_link_login)
    TextView logInTextView;

    String email = "", password = "", userName = "";


    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mContext = this;
        ButterKnife.bind(this);

        mSharedPreferences = getSharedPreferences(MY_PREFRENCE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

    }


    @Override
    protected void onStart() {
        super.onStart();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *GET USER EMAIL AND PASSORD AND USERNAME AND SEND VIA VOLLEY TO API
                 */
                email = emailSignUpEditText.getText().toString().trim();
                password = passwordSignUpEditText.getText().toString().trim();
                userName = userNameSignUpEditText.getText().toString().trim();
                if (email.equals("") || password.equals("") || userName.equals("")) {
                    Snackbar snackbar = Snackbar.make(view, getString(R.string.empty_fields), Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                    snackbar.show();
                } else {
                    registerNewUser(view);
                }
            }
        });


        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerNewUser(final View view) {

        mRequestQueue = MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                REGISTRATION_URL, new JSONObject(postParams()),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String message = "Succesfully Registered\n" + response;
                        LogUtils.T(mContext, message);
                        Log.d(LOG_TAG, message);
                        saveUserId(response, view);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "Somethings is not right";
                Snackbar snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                snackbar.show();
                Log.d(LOG_TAG, errorMessage);

                /*NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        Log.d(LOG_TAG, res);
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        String message = "Yauzaaa Somethings is not right\n" + error;
                        LogUtils.T(mContext, message);
                        LogUtils.L(message);
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        String message = "Yauzaaa Somethings is not right\n" + error;
                        LogUtils.T(mContext, message);
                        LogUtils.L(message);
                        e2.printStackTrace();
                    }
                }*/
            }
        });
        //Set Retry Policy for MY_SOCKET_TIMEOUT_MS = 10 SECONDS
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(mContext).addToRequestQueue(jsonObjReq);
    }

    /**
     * Called to save userId for the profile
     * 1. Parse response to get userId
     * and then save to SharedPreferences
     *
     * @param response
     * @param view
     */
    private void saveUserId(JSONObject response, View view) {
        String errorMessage = "Something went wrong\nTry again";
        int userId = -1;

        /**
         * If the response contains response Code then there is some error
         * BECAUSE responseCode is only present in JSON response if there
         * some error
         * while registering the user and registrations is not successful
         * and then show {@link Snackbar} showing this information
         * else do not show {@link Snackbar}
         */
        try {
            if (response.has("responseCode")) {
                if (response.has("responseMessage")) {
                    errorMessage = response.getString("responseMessage");
                }
                Snackbar snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                snackbar.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         *If Successfully registered parse UserId from response
         * If the user is successfully registered then there is no need for {@link Snackbar}
         * and showing this information as {@link MainActivity}  is launched
         *
         * Change SIGN_UP_OR_NOT_KEY boolean variable
         * Add USERID TO THE SHARED PREFRENCE FOR PROFILE AND OTHER THINGS
         * Then launch {@link MainActivity}
         */
        try {
            if (response.has("userId")) {
                userId = response.getInt("userId");
                mEditor.putBoolean(SIGN_UP_OR_NOT_KEY, true);
                mEditor.putInt(USER_ID_PREFRENCE, userId);
                mEditor.commit();
                Log.d(LOG_TAG, " USERID - " + userId + " Signed Up Key Prefrence" + SIGN_UP_OR_NOT_KEY);
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected Map<String, String> postParams() {
        Map<String, String> params = new HashMap<>();
        params.put(KEY_EMAIL_ID, email);
        params.put(KEY_PASSWORD, password);
        params.put(KEY_USERNAME, userName);
        return params;
    }
}








/*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTRATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String message = "Succesfully Registered\n" + response;
                        LogUtils.T(mContext, message);
                        LogUtils.L(message);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "Yauzaaa Somethings is not right\n" + error;
                LogUtils.T(mContext, message);
                LogUtils.L(message);
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);*/
