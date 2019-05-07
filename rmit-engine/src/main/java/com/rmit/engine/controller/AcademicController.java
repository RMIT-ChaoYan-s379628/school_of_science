package com.rmit.engine.controller;

import java.util.Set;

import com.rmit.main.library.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmit.engine.inf.IStudentControls;

@RestController
@RequestMapping(value = "/student")
public class AcademicController {

    Logger logger = LoggerFactory.getLogger(AcademicController.class);

    @Autowired
    IStudentControls studentControls;

    @RequestMapping(value = "/getFeeds", method = RequestMethod.GET)
    public GetFeedResponseDTO getFeed(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
                                      @RequestParam(value = ApiRequestUtils.DEPARTMENT, required = false) Set<String> department,
                                      @RequestParam(value = ApiRequestUtils.FEED_TYPE, required = false) String feedType,
                                      @RequestParam(value = ApiRequestUtils.INDEX, required = true) int index,
                                      @RequestParam(value = ApiRequestUtils.SIZE, required = true) int size) {

        GetFeedResponseDTO response = new GetFeedResponseDTO();

        try {

            response = studentControls.getFeed(userId, department, feedType, index, size);

        } catch (Exception e) {
            logger.error("Some error occured while getting menu items");
            logger.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
            return response;
        }

        return response;

    }

    @RequestMapping(value = "/getFeedsforMobile", method = RequestMethod.GET)
    public GetFeedResponseDTO getFeedForMobile(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
                                                       @RequestParam(value = ApiRequestUtils.DEPARTMENT, required = false) Set<String> department,
                                                       @RequestParam(value = ApiRequestUtils.FEED_TYPE, required = true) String feedType) {

        GetFeedResponseDTO response = new GetFeedResponseDTO();

        try {

            response = studentControls.getFeedforMobile(userId, department, feedType);

        } catch (Exception e) {
            logger.error("Some error occured while getting menu items");
            logger.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
            return response;
        }

        return response;

    }

    @RequestMapping(value = "/getContacts", method = RequestMethod.GET)
    public GetContactResponseDTO getContact(@RequestHeader(ApiRequestUtils.USER_ID) String userId,
                                            @RequestParam(value = ApiRequestUtils.DEPARTMENT, required = false) Set<String> department,
                                            @RequestParam(value = ApiRequestUtils.INDEX, required = true) int index,
                                            @RequestParam(value = ApiRequestUtils.SIZE, required = true) int size) {

        GetContactResponseDTO response = new GetContactResponseDTO();

        try {

            response = studentControls.getContact(userId, department, index, size);

        } catch (Exception e) {
            logger.error("Some error occured while getting menu items");
            logger.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
            return response;
        }

        return response;

    }

    @RequestMapping(value = "/getCalender", method = RequestMethod.GET)
    public GetCalenderResponse getCalender(@RequestHeader(ApiRequestUtils.USER_ID) String userId) {

        GetCalenderResponse response = new GetCalenderResponse();

        try {

            response = studentControls.getCalender(userId);

        } catch (Exception e) {
            logger.error("Some error occured while getting menu items");
            logger.error(ApiResponseUtils.EXCEPTION, e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
            return response;
        }

        return response;

    }

    @RequestMapping(value = "/share", method = RequestMethod.POST)
    public ServiceResponse shareFeed(@RequestHeader(value = ApiRequestUtils.USER_ID) String userId,
                                     @RequestParam(value = "feedid") String feedId) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = studentControls.shareFeed(userId, feedId);
        } catch (Exception e) {
            logger.error("Some error occurred while verifying user");
            logger.error("Exception: ", e);
            response.setCode(ApiResponseUtils.ERROR_CODE);
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
