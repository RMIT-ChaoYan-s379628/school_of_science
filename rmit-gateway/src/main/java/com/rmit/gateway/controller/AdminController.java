package com.rmit.gateway.controller;

import com.rmit.gateway.controller.request.DeleteAdminRequest;
import com.rmit.main.library.api.GetUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmit.gateway.service.IAdminService;
import com.rmit.main.library.api.AddClientRequest;
import com.rmit.main.library.gateway.api.ApiResponseUtils;
import com.rmit.main.library.gateway.api.ServiceResponse;

import javax.servlet.http.HttpServletResponse;

import static com.rmit.main.library.api.ApiResponseUtils.SUCCESS_CODE;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;


@Controller
public class AdminController {

    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private IAdminService adminService;

    @RequestMapping(value = "/admin/addClient", method = RequestMethod.POST)
    public @ResponseBody
    ServiceResponse addClient(@RequestBody AddClientRequest request) {
        LOG.info("Adding Client");
        ServiceResponse response = new ServiceResponse();
        if (request == null || !request.isValid()) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }
        try {
            response = adminService.addClient(request);
        } catch (Exception e) {
            LOG.error("Error occured while adding client.");
            LOG.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/admin/deleteUser", method = RequestMethod.POST)
    public @ResponseBody
    ServiceResponse deleteUser(@RequestBody DeleteAdminRequest request, HttpServletResponse httpServletResponse) {

        if (request == null || !request.isValid()) {
            httpServletResponse.setStatus(SC_BAD_REQUEST);
            ServiceResponse response = new ServiceResponse();
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }

        ServiceResponse response = adminService.deleteUser(request);
        if (response.getCode() != SUCCESS_CODE) {
            httpServletResponse.setStatus(SC_BAD_REQUEST);
        }
        return response;
    }


}
