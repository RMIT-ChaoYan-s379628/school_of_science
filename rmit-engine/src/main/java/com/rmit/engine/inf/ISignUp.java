package com.rmit.engine.inf;

import com.rmit.main.library.api.ServiceResponse;

public interface ISignUp {
	
	public ServiceResponse verifyUser(String userId, String userType);

	public ServiceResponse getDepartment();
	
	
}
