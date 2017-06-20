package com.sandeep.forumappstask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sandeep on 16-06-2017.
 */

public class RegisterResponse
{
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("user")
    @Expose
    private RegisterResult registerResult;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public RegisterResult getUser() {
        return registerResult;
    }

    public void setUser(RegisterResult registerResult) {
        this.registerResult = registerResult;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "error=" + error +
                ", uid='" + uid + '\'' +
                ", registerResult=" + registerResult +
                '}';
    }
}
