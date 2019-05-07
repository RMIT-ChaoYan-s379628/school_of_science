package com.rmit.gateway.controller.request;

public class DeleteAdminRequest {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public boolean isValid() {
        return this.userId != null && !this.userId.isEmpty();
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
