package com.rmit.gateway.service;

import com.rmit.main.library.gateway.model.User;

public interface IUserService {

    User findUserByUserId(String userId);

}
