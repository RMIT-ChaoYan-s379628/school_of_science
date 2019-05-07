
package com.rmit.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping(value = "/admin/test", method = RequestMethod.GET)
    public @ResponseBody String testAdmin() {
        return "testAdmin returned successfully";
    }

    @RequestMapping(value = "/client/test", method = RequestMethod.GET)
    public @ResponseBody String testClient() {
        return "testClient returned successfully";
    }

    @RequestMapping(value = "/restaurant/test", method = RequestMethod.GET)
    public @ResponseBody String testRestaurant() {
        return "testRestaurant returned successfully";
    }

    @RequestMapping(value = "/user/test", method = RequestMethod.GET)
    public @ResponseBody String testUser() {
        return "testUser returned successfully";
    }

    @RequestMapping(value = "/support/test", method = RequestMethod.GET)
    public @ResponseBody String testSupport() {
        return "testSupport returned successfully";
    }
}
