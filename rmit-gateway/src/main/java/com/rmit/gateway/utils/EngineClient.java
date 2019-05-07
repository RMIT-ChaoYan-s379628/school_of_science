package com.rmit.gateway.utils;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rmit.main.library.api.AddClientRequest;
import com.rmit.main.library.api.AddUsersRequest;
import com.rmit.main.library.api.ApiRequestUtils;
import com.rmit.main.library.api.DisableUsersRequest;
import com.rmit.main.library.api.DisableUsersResponse;
import com.rmit.main.library.api.ServiceResponse;

@FeignClient(name = "rmit-engine")
public interface EngineClient {

	@RequestMapping(value = "/admin/addClient", method = RequestMethod.POST)
	public ServiceResponse addFaculty(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody AddClientRequest request);

	@RequestMapping(value = "/engine/verifyUser", method = RequestMethod.GET)
    public ServiceResponse verifyUser(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
    		@RequestParam(value = ApiRequestUtils.USER_TYPE) String userType);
	
	@RequestMapping(value = "/faculty/addUsers", method = RequestMethod.POST)
    public ServiceResponse addUsers(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId, 
    		@RequestBody AddUsersRequest request) ;

	@RequestMapping(value = "/admin/disableClient", method = RequestMethod.POST)
	public DisableUsersResponse disableUsers(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody DisableUsersRequest request);
	
	@RequestMapping(value = "/faculty/disableClient", method = RequestMethod.POST)
	public DisableUsersResponse disableClientUsers(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
			@RequestBody DisableUsersRequest request);

}
