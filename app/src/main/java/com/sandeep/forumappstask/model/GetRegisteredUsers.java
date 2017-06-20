package com.sandeep.forumappstask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sandeep on 16-06-2017.
 */

public class GetRegisteredUsers
{
    @SerializedName("result")
    @Expose
    private List<ResultGetUsers> result = null;

    public List<ResultGetUsers> getResult() {
        return result;
    }

    public void setResult(List<ResultGetUsers> result) {
        this.result = result;
    }
}
