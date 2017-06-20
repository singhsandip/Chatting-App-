package com.sandeep.forumappstask.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sandeep.forumappstask.R;

/**
 * Created by sandeep on 16-06-2017.
 */

public class UserViewHolder extends RecyclerView.ViewHolder
{
    public TextView tvUserEmail;

    public UserViewHolder(View view) {
        super(view);

        tvUserEmail = (TextView) view.findViewById(R.id.userEmail);


    }
}
