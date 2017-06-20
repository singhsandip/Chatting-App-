package com.sandeep.forumappstask.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.sandeep.forumappstask.model.LoginResponse;
import com.sandeep.forumappstask.model.User;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tvLogin, tvRegister;
    private String loginEmail, loginPassword;
    private EditText userNameET, passwordET;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        String email = PrefrenceManager.getInstance(this).getEmail();
        if (email != null && !email.isEmpty() && !email.equals(Constants.STR_DEFAULT)) {
            Intent intent = new Intent(MainActivity.this, UsersListActivity.class);
            startActivity(intent);
            finish();
        }

        progressdialog = new ProgressDialog(this);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        userNameET = (EditText) findViewById(R.id.userNameET);
        passwordET = (EditText) findViewById(R.id.passwordET);

        tvRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRegister:

                Intent registerIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(registerIntent);
                finish();
                break;

            case R.id.tvLogin:

                if (isNetworkAvailable(this)) {

                    loginEmail = userNameET.getText().toString();
                    loginPassword = passwordET.getText().toString();

                    if (loginEmail.equalsIgnoreCase("")) {
                        userNameET.setError("Enter UserName");
                    } else if (loginPassword.equalsIgnoreCase("")) {

                        passwordET.setError("Enter Password");
                    } else {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                        progressdialog.setCancelable(false);
                        progressdialog.setMessage("Loging In..");
                        progressdialog.show();

                        String token = PrefrenceManager.getInstance(this).getDeviceToken();

                        ApiClass apiClass = RetrofitInstance.getRetrofit().create(ApiClass.class);
                        apiClass.loginUser(loginEmail, loginPassword, new Callback<LoginResponse>() {

                            @Override
                            public void success(LoginResponse loginResponse, Response response) {
                                progressdialog.dismiss();

                                if (loginResponse.getError()) {
                                    Toast.makeText(MainActivity.this, "Could not login", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                String unique_id = loginResponse.getUid();
                                User user = loginResponse.getUser();
                                String mytoken = user.getToken();
                                String email = user.getEmail();

                                PrefrenceManager.getInstance(MainActivity.this).saveDeviceToken(mytoken);
                                PrefrenceManager.getInstance(MainActivity.this).saveEmail(email);
                                PrefrenceManager.getInstance(MainActivity.this).saveUniqueId(unique_id);

                                Intent intent = new Intent(MainActivity.this, UsersListActivity.class);
                                startActivity(intent);

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                progressdialog.dismiss();
                                Toast.makeText(MainActivity.this, "Network Issues", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } else {
                    Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
