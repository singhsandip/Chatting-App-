package com.sandeep.forumappstask.clicklisteners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.sandeep.forumappstask.activity.ChatActivity;
import com.sandeep.forumappstask.helper.Constants;

/**
 * Created by sandeep on 16-06-2017.
 */

public class UserEmailClickListener implements View.OnClickListener {
    private Context context;
    private int position;
    private String token;
    private String email;

    public UserEmailClickListener(Context context, int position, String token, String email) {
        this.context = context;
        this.position = position;
        this.token = token;
        this.email = email;

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Constants.TOKEN, token);
        intent.putExtra(Constants.EMAIL, email);
        context.startActivity(intent);

    }
}
