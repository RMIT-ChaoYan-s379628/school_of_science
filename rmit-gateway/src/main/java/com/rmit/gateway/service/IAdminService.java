package com.rmit.gateway.service;

import com.rmit.gateway.controller.request.DeleteAdminRequest;
import com.rmit.main.library.api.AddClientRequest;
import com.rmit.main.library.api.GetUserResponse;
import com.rmit.main.library.gateway.api.ServiceResponse;

public interface IAdminService {

    ServiceResponse addClient(AddClientRequest request) throws Exception;

    ServiceResponse deleteUser(DeleteAdminRequest request);

}
