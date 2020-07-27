package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
// class for user submissions result
public class UserSubmissionsResult {

    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private ArrayList<Submission> result;

    public UserSubmissionsResult(String status, ArrayList<Submission> result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Submission> getResult() {
        return result;
    }

    public void setResult(ArrayList<Submission> result) {
        this.result = result;
    }
}
