package com.sandeep.forumappstask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sandeep.forumappstask.R;
import com.sandeep.forumappstask.holder.MessageViewholder;
import com.sandeep.forumappstask.model.Message;

import java.util.List;

/**
 * Created by sandeep on 16-06-2017.
 */


public class ConversationAdapter extends RecyclerView.Adapter<MessageViewholder> {

    private Context context;
    private List<Message> smsList;
    public static final int TYPE_INCOMING = 1;
    public static final int TYPE_OUTGOING = 0;

    public ConversationAdapter(Context context, List<Message> smsList) {
        this.context = context;
        this.smsList = smsList;
    }


    @Override
    public int getItemViewType(int position) {
        return smsList.get(position).getType();
    }

    @Override
    public MessageViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_INCOMING:
                View layoutViewIncomingSms = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation_incoming, null);
                return new MessageViewholder(layoutViewIncomingSms);

            case TYPE_OUTGOING:
                View layoutViewOutgoingSms = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation_outgoing, null);
                return new MessageViewholder(layoutViewOutgoingSms);

        }

        throw new IllegalArgumentException("unknown type: " + viewType);
    }

    @Override
    public void onBindViewHolder(MessageViewholder holder, int position) {
        Message sms = smsList.get(position);
        holder.tvMessage.setText(sms.getMessage());
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }
}