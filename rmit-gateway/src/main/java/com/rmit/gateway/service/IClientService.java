package com.rmit.gateway.service;

import com.rmit.main.library.api.AddUsersRequest;
import com.rmit.main.library.api.AddUsersResponse;
import com.rmit.main.library.api.DisableUsersRequest;
import com.rmit.main.library.gateway.api.ServiceResponse;
import com.rmit.main.library.gateway.model.User;

import java.util.List;

public interface IClientService {
	
	AddUsersResponse addUsers(AddUsersRequest request) throws Exception;
	
	ServiceResponse disableUsers(DisableUsersRequest request) throws Exception;

	List<User> getClients(int index, int size);

}
