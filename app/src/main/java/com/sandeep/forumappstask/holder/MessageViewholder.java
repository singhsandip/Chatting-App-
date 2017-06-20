package com.sandeep.forumappstask.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sandeep.forumappstask.R;

/**
 * Created by maninder on 18/6/17.
 */

public class MessageViewholder extends RecyclerView.ViewHolder{

   public TextView tvMessage;

    public MessageViewholder(View itemView) {
        super(itemView);
        tvMessage= (TextView) itemView.findViewById(R.id.tvMessage);
    }
}
