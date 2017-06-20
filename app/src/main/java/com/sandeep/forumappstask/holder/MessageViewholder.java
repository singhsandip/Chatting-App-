package com.sandeep.forumappstask.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sandeep.forumappstask.R;

/**
 * Created by sandeep on 16-06-2017.
 */


public class MessageViewholder extends RecyclerView.ViewHolder{

   public TextView tvMessage;

    public MessageViewholder(View itemView) {
        super(itemView);
        tvMessage= (TextView) itemView.findViewById(R.id.tvMessage);
    }
}
