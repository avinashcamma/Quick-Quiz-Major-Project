package com.example.ankit.quickquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ankit.quickquiz.HelperClasses.MySingleton;
import com.example.ankit.quickquiz.HelperClasses.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ankit.quickquiz.HelperClasses.LogUtils.MY_SOCKET_TIMEOUT_MS;

public class ForgetPasswordActivity extends AppCompatActivity {


    private static final String RESET_PASS_URL = "http://104.236.57.114:8080/quickquiz/v1/user/reset_password";
    private static final String VALIDATE_OTP_PASS_RESET_URL = "http://104.236.57.114:8080/quickquiz/v1/user/validate_code";


    private static final String KEY_EMAIL_ID = "emailId";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_OTP = "code";


    Context mContext;

    @BindView(R.id.til_otp)
    TextInputLayout otpTextInputLayout;
    @BindView(R.id.et_fp_otp)
    TextInputEditText otpEditText;

    @BindView(R.id.til_email)
    TextInputLayout emailTextInputLayout;
    @BindView(R.id.et_fp_email)
    TextInputEditText emailEditText;

    @BindView(R.id.til_password)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.et_fp_new_password)
    TextInputEditText passwordEditText;

    @BindView(R.id.til_confirm_password)
    TextInputLayout cpTextInputLayout;
    @BindView(R.id.et_fp_confirm_new_password)
    TextInputEditText confirmPasswordEditText;

    @BindView(R.id.btn_change_password)
    Button changePasswordButton;

    @BindView(R.id.change_password_message)
    TextView changePasswordMessage;


    @BindView(R.id.pb_loading_questions_fpa)
    Spinner mSpinner;

    @BindView(R.id.cl_forget_pass)
    CoordinatorLayout mCoordinatorLayout;

    private String email, password, password2;
    private String otp = "";
    private boolean isValid = true;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mContext = this;
        ButterKnife.bind(this);

        setUI();
    }

    private void setUI() {
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailEditText.setError(null);
                isValid = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setError(null);
                isValid = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmPasswordEditText.setError(null);
                isValid = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                password2 = confirmPasswordEditText.getText().toString().trim();

                if (!Utility.Utils.validateEmail(email)) {
                    emailEditText.setError("Please enter some valid email address");
                    isValid = false;
                }
                if (passwordEditText.getText().toString().length() < 6) {
                    passwordEditText.setError("Password must have at least 6 characters");
                    isValid = false;
                } else {
                    if (!password.equals(password2)) {
                        confirmPasswordEditText.setError("Passwords didn't match");
                        isValid = false;
                    }
                }

                if (isValid) {

                    sendEmailToServerToSendOTP(v);
                    /**
                     * Call changeLayout() only when the reponse from the server is Ok!
                     * i.e OTP SENT Succesfully
                     * else Show {@link android.support.design.widget.Snackbar} with the error from Volley
                     * Error Listener
                     */
                    //changeLayout();

                    // call server and display process dialog because sending mail will take some time
//                    ProgressDialog progressDialog = new ProgressDialog(mContext);
//                    progressDialog.show();

                    // dismiss dialog after receiving response
                    //validateOTP();
                }
            }
        });
    }

    private void sendEmailToServerToSendOTP(final View view) {
        mRequestQueue = MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(RESET_PASS_URL, null,
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
                        parseJsonResponse(response, view);
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

    private void parseJsonResponse(JSONObject response, View view) {
        int responseCode = 0;
        String responseMessage = "";
        try {
            responseCode = response.getInt("responseCode");
            responseMessage = response.getString("responseMessage");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (responseCode == 11113) {
            //E-MAIL id not registered
            Snackbar snackbar = Snackbar.make(view, responseMessage, Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
            snackbar.show();
        } else if (responseCode == 11134) {
            Toast.makeText(mContext, "Otp sent to your mail", Toast.LENGTH_LONG).show();
            validateOTP(view);
            changeLayout();
        }
    }

    private void changeLayout() {

        emailTextInputLayout.setVisibility(View.GONE);
        emailEditText.setVisibility(View.GONE);

        passwordTextInputLayout.setVisibility(View.GONE);
        passwordEditText.setVisibility(View.GONE);

        cpTextInputLayout.setVisibility(View.GONE);
        confirmPasswordEditText.setVisibility(View.GONE);

        otpTextInputLayout.setVisibility(View.VISIBLE);
        otpEditText.setVisibility(View.VISIBLE);

        changePasswordButton.setText("Submit");

        changePasswordMessage.setVisibility(View.GONE);
    }

    private void validateOTP(final View view) {
        otp = otpEditText.getText().toString().trim();
        if (otp.equals("")) {
            Snackbar snackbar = Snackbar.make(view, getString(R.string.empty_fields), Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
            snackbar.show();
        } else {
            /**
             * Send 3 things to the server for passord update
             * 1. OTP
             * 2. EMIALID
             * 3. PASS
             */
            mRequestQueue = MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    VALIDATE_OTP_PASS_RESET_URL, new JSONObject(postParams()),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String message = "";
                            try {
                                message = response.getString("responseMessage");
                                if (response.getInt("responseCode") == 11133) {
                                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(ForgetPasswordActivity.this, MainActivity.class));
                                } else if (response.getInt("responseCode") == 11132) {
                                    Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
                                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                                    snackbar.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
        // send otp to server
    }

    protected Map<String, String> postParams() {
        Map<String, String> params = new HashMap<>();
        params.put(KEY_EMAIL_ID, email);
        params.put(KEY_PASSWORD, password);
        params.put(KEY_OTP, otp);
        return params;
    }
}
