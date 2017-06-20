package com.sandeep.forumappstask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sandeep.forumappstask.R;
import com.sandeep.forumappstask.clicklisteners.UserEmailClickListener;
import com.sandeep.forumappstask.holder.UserViewHolder;
import com.sandeep.forumappstask.model.ResultGetUsers;

import java.util.List;

/**
 * Created by sandeep on 16-06-2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {


    private Context context;
    private List<ResultGetUsers> list;
    private LayoutInflater inflater;


    public UsersAdapter(Context context, List<ResultGetUsers> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position)
    {
        holder.tvUserEmail.setText(list.get(position).getEmail());
        String token = list.get(position).getToken().toString();
        String email = list.get(position).getEmail();
        holder.tvUserEmail.setOnClickListener(new UserEmailClickListener(context,position,token,email));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
