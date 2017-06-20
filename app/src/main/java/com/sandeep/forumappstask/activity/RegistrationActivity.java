package com.sandeep.forumappstask.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sandeep.forumappstask.R;
import com.sandeep.forumappstask.helper.Constants;
import com.sandeep.forumappstask.helper.PrefrenceManager;
import com.sandeep.forumappstask.helper.RetrofitInstance;
import com.sandeep.forumappstask.interfaces.ApiClass;
import com.sandeep.forumappstask.model.RegisterResponse;
import com.sandeep.forumappstask.model.RegisterResult;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegistrationActivity.class.getCanonicalName();
    private String strEmail, strPassword, strConfPass;
    private EditText etEmail, etPassword, etConfirmPassword;
    private String token = null;
    private RegisterResult registereduser = null;
    private ProgressDialog progressdialog;
    private TextView tvLogin, tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        progressdialog = new ProgressDialog(this);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfPass);

        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.tvLogin:

                Intent loginIntent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(loginIntent);
                finish();

                break;

            case R.id.tvRegister:

                token = PrefrenceManager.getInstance(this).getDeviceToken();
                strEmail = etEmail.getText().toString();
                strPassword = etPassword.getText().toString();
                strConfPass = etConfirmPassword.getText().toString();

                if (token.equalsIgnoreCase(Constants.STR_DEFAULT)) {
                    Toast.makeText(this, "token not generated", Toast.LENGTH_SHORT).show();
                } else {
                    if (strEmail.equalsIgnoreCase("")) {
                        etEmail.setError("Enter your name");
                    } else if (strPassword.equalsIgnoreCase("")) {
                        etPassword.setError("Enter your password");
                    } else if (strConfPass.equalsIgnoreCase("")) {
                        etConfirmPassword.setError("Enter your confirm password");
                    } else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {

                        Toast.makeText(RegistrationActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    } else if (strPassword.length() < 6 || strConfPass.length() < 6) {
                        etPassword.setError("You must have six characters in your password");
                    } else {

                        boolean check = ValidEmail();
                        if (check) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                            progressdialog.setCancelable(false);
                            progressdialog.setMessage("Registering...");
                            progressdialog.show();
                            //RestAdapter retrofit = new RestAdapter.Builder().setEndpoint("http://omninos.com").build();
                            ApiClass apiClass = RetrofitInstance.getRetrofit().create(ApiClass.class);
                            apiClass.registerUser(strEmail, token, strPassword, new Callback<RegisterResponse>() {
                                @Override
                                public void success(RegisterResponse registerResponse, Response response) {

                                    Log.d(TAG, "success: " + registerResponse);
                                    registereduser = registerResponse.getUser();
                                    registereduser.getToken();
                                    progressdialog.dismiss();
                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    progressdialog.dismiss();
                                    Toast.makeText(RegistrationActivity.this, "Failed to register" + error, Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    }

                }

                break;
        }

    }


    public boolean ValidEmail() {
        boolean check = false;
        String email11 = etEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email11.matches(emailPattern)) {
            check = true;
        } else {
            etEmail.setError("Invalid email address");
            Toast.makeText(RegistrationActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
        }
        return check;
    }
}
