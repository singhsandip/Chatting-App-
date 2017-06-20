package com.sandeep.forumappstask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sandeep on 16-06-2017.
 */

public class ResultGetUsers
{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("email")
    @Expose
    private String email;

    public ResultGetUsers(String id, String email, String token, String uniqueId) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.email = email;
        this.token = token;
    }

    @SerializedName("token")
    @Expose
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
