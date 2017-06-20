package com.sandeep.forumappstask.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sandeep.forumappstask.helper.PrefrenceManager;
import com.sandeep.forumappstask.helper.RetrofitInstance;
import com.sandeep.forumappstask.interfaces.ApiClass;
import com.sandeep.forumappstask.model.RegisterResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sandeep on 16-06-2017.
 */

public class FireBaseInstanceService extends FirebaseInstanceIdService
{
    private static final String TAG = "MyFirebaseIIDService";


    @Override
    public void onTokenRefresh() {


        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + token);

        storeTokenInLocal(token);
        String email = PrefrenceManager.getInstance(getBaseContext()).getEmail();
        updateTokenAtServer(email,token);


    }

    private void updateTokenAtServer(String email ,String token)
    {
        ApiClass apiclasss = RetrofitInstance.getRetrofit().create(ApiClass.class);
        apiclasss.updateToken(email, token, new Callback<RegisterResponse>() {
            @Override
            public void success(RegisterResponse registerResponse, Response response)
            {

            }

            @Override
            public void failure(RetrofitError error)
            {

            }
        });

    }

    private void storeTokenInLocal(String token)
    {
        PrefrenceManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }
}
