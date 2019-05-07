package com.rmit.engine.controller;

import com.rmit.engine.controller.request.DeleteClientRequestDTO;
import com.rmit.engine.controller.request.DisableClientRequestDTO;
import com.rmit.engine.controller.request.EnableClientRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmit.engine.inf.IAdminControls;
import com.rmit.main.library.api.AddCalenderEventDTO;
import com.rmit.main.library.api.AddClientRequest;
import com.rmit.main.library.api.ApiRequestUtils;
import com.rmit.main.library.api.ApiResponseUtils;
import com.rmit.main.library.api.DisableUsersRequest;
import com.rmit.main.library.api.DisableUsersResponse;
import com.rmit.main.library.api.EditCalenderEventDTO;
import com.rmit.main.library.api.FeedAnalyticsResponse;
import com.rmit.main.library.api.GetActiveUsers;
import com.rmit.main.library.api.ServiceResponse;

@RestController
@RequestMapping("/admin")
public class ProductOwnerController {

    Logger logger = LoggerFactory.getLogger(ProductOwnerController.class);

    @Autowired
    IAdminControls adminControls;

    @RequestMapping(value = "/analytics/getFeedDetails", method = RequestMethod.GET)
    public FeedAnalyticsResponse getFeedAnalytics(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId) {
        FeedAnalyticsResponse response = new FeedAnalyticsResponse();
        try {
            response = adminControls.getNewsCount();
        } catch (Exception e) {
            logger.error("Some error occured while adding News");
            logger.error("Exception: ", e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/analytics/getActiveAdmins", method = RequestMethod.GET)
    public ServiceResponse getActiveAdmins(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId) {
        GetActiveUsers response = new GetActiveUsers();

        try {
            response = adminControls.getAdminsCount();
        } catch (Exception e) {
            logger.error("Some error occured while getting admins");
            logger.error("Exception: ", e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @RequestMapping(value = "/analytics/getActiveUsers", method = RequestMethod.GET)
    public ServiceResponse getActiveUsers(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId) {
        GetActiveUsers response = new GetActiveUsers();
        try {
            response = adminControls.getUsersCount();
        } catch (Exception e) {
            logger.error("Some error occured while getting users");
            logger.error("Exception: ", e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/addClient", method = RequestMethod.POST)
    public ServiceResponse addFaculty(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
                                      @RequestBody AddClientRequest request) {
        ServiceResponse response = new ServiceResponse();
        if (request == null || !request.isValid()) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }
        try {
            response = adminControls.addFaculty(request);
        } catch (Exception e) {
            logger.error("Some error occured while adding client");
            logger.error("Exception: ", e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/getClient", method = RequestMethod.GET)
    public ServiceResponse getUsers(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
                                    @RequestParam(value = ApiRequestUtils.INDEX, required = true) int index,
                                    @RequestParam(value = ApiRequestUtils.SIZE, required = true) int size) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = adminControls.getFaculty(userId, index, size);
        } catch (Exception e) {
            logger.error("Some error occurred while disabling users");
            logger.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/deleteClient", method = RequestMethod.POST)
    public ServiceResponse deleteClient(@RequestHeader(value = ApiRequestUtils.USER_ID, required = false) String userId,
                                        @RequestBody DeleteClientRequestDTO deleteClientRequest) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = adminControls.deleteClient(deleteClientRequest.getUserId());
        } catch (Exception e) {
            logger.error("Some error occurred while deleting users");
            logger.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/searchClient", method = RequestMethod.GET)
    public ServiceResponse getUsers(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
                                    @RequestParam(value = "user", required = true) String user) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = adminControls.searchFaculty(userId, user);
        } catch (Exception e) {
            logger.error("Some error occurred while disabling users");
            logger.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/disableClient", method = RequestMethod.POST)
    public ServiceResponse disableUsers(@RequestHeader(value = ApiRequestUtils.USER_ID, required = false) String userId,
                                        @RequestBody DisableClientRequestDTO disableClientRequestDTO) {
        ServiceResponse response = new ServiceResponse();
        boolean success = adminControls.disableFaculty(disableClientRequestDTO.getUserId());
        if (success == false) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }
        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
        return response;
    }

    @RequestMapping(value = "/enableClient", method = RequestMethod.POST)
    public ServiceResponse enableUser(@RequestHeader(value = "userId", required = false) String userId,
                                      @RequestBody EnableClientRequestDTO enableClientRequestDTO) {
        ServiceResponse response = new ServiceResponse();
        boolean success = adminControls.enableFaculty(enableClientRequestDTO.getUserId());
        if (success == false) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }
        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
        return response;
    }

    @RequestMapping(value = "/setCalender", method = RequestMethod.POST)
    public ServiceResponse setCalender(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
                                       @RequestBody AddCalenderEventDTO request) {
        ServiceResponse response = new ServiceResponse();
        if (request == null || !request.isValid()) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }
        try {
            response = adminControls.addCalender(request);
        } catch (Exception e) {
            logger.error("Some error occurred while disabling users");
            logger.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/editCalender", method = RequestMethod.POST)
    public ServiceResponse editCalender(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
                                        @RequestBody EditCalenderEventDTO request) {
        ServiceResponse response = new ServiceResponse();
        if (request == null) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }
        try {
            response = adminControls.editCalender(request.getEvent(), request.getDate());
        } catch (Exception e) {
            logger.error("Some error occurred while disabling users");
            logger.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/deleteCalender", method = RequestMethod.POST)
    public ServiceResponse deleteCalender(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
                                          @RequestBody String request) {
        ServiceResponse response = new ServiceResponse();
        if (request == null) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage(ApiResponseUtils.INVALID_REQUEST_MESSAGE);
            return response;
        }
        try {
            response = adminControls.deleteCalender(request);
        } catch (Exception e) {
            logger.error("Some error occurred while disabling users");
            logger.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    // @RequestMapping(value = "/getContactDetails", method = RequestMethod.GET)
    // public FeedAnalyticsResponse getContactAnalytics(@RequestHeader(value =
    // ApiRequestUtils.USER_ID) String userId) {
    // FeedAnalyticsResponse response = new FeedAnalyticsResponse();
    // try {
    // response = adminControls.getContactsCount();
    // } catch (Exception e) {
    // logger.error("Some error occured while adding News");
    // logger.error("Exception: ", e);
    // response.setCode(ApiResponseUtils.ERROR_CODE);
    // response.setMessage(e.getMessage());
    // }
    // return response;
    // }

}
