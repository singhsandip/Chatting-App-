package com.sandeep.forumappstask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sandeep on 16-06-2017.
 */

public class ChatResponse
{
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("user")
    @Expose
    private ResultChatResponse user;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public ResultChatResponse getUser() {
        return user;
    }

    public void setUser(ResultChatResponse user) {
        this.user = user;
    }
}
