package com.rmit.gateway.controller;

import com.rmit.gateway.controller.response.GetClientsResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.rmit.gateway.service.IClientService;
import com.rmit.main.library.api.AddUsersRequest;
import com.rmit.main.library.api.AddUsersResponse;
import com.rmit.main.library.api.ApiResponseUtils;
import com.rmit.main.library.api.DisableUsersRequest;
import com.rmit.main.library.gateway.api.ServiceResponse;

@Controller
@RequestMapping("/client")
public class ClientController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);
	
	@Autowired
	private IClientService clientService;
	
	@RequestMapping(value = "/addUsers", method = RequestMethod.POST)
    public @ResponseBody AddUsersResponse addUsers(@RequestBody AddUsersRequest request) {
        AddUsersResponse response = new AddUsersResponse();
        if (request == null || !request.isValid()) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }
        try {
            response = clientService.addUsers(request);
        } catch (Exception e) {
            LOG.error("Some error occurred while inviting users");
            LOG.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }
	
	@RequestMapping(value = "/disableUsers", method = RequestMethod.POST)
	public @ResponseBody ServiceResponse disableUsers(@RequestBody DisableUsersRequest request) {
		ServiceResponse response = new ServiceResponse();
		if(request == null || !request.isValid()) {
			response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
			response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
			return response;
		}
		try {
			response = clientService.disableUsers(request);
		} catch(Exception e) {
			LOG.error("Some error occurred while disabling users");
			LOG.error(ApiResponseUtils.EXCEPTION, e);
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/getClients", method = RequestMethod.GET)
	public @ResponseBody
	GetClientsResponseDTO getClients(@RequestHeader(required = false) String userId,
									 @RequestParam( required = true) int index,
									 @RequestParam(required = true) int size) {
		GetClientsResponseDTO response=new GetClientsResponseDTO();
		response.setUserList(clientService.getClients(index,size));
		response.setSize(size);
		return response;
	}

}re
