package com.rmit.gateway.controller.response;

import com.rmit.main.library.gateway.api.ServiceResponse;
import com.rmit.main.library.gateway.model.User;

import java.util.List;

public class GetClientsResponseDTO extends ServiceResponse {

    List<User> userList;
    long size;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
