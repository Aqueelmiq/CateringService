package com.aqueel.project.aTesting;

/**
 * Created by aqueelmiqdad on 4/24/16.
 */
public class ApiResponse {
    private int status;
    private String body;

    public ApiResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
