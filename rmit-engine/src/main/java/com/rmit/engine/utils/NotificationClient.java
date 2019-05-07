package com.rmit.engine.utils;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rmit.main.library.api.ServiceResponse;

@FeignClient(name="rmit-notification")
public interface NotificationClient {
	
	@PostMapping("/send/notification/push")
	public ServiceResponse pushNotification(@RequestParam(value = "department") String department,
			@RequestParam(value="title") String title, @RequestParam(value="category") String category);
	
	@PostMapping("/send/notification/all")
	public ServiceResponse pushNotificationToAll(@RequestParam(value="title") String title, @RequestParam(value="category") String category);
	
	@PostMapping("/notification/unregister")
	public ServiceResponse unregister(@RequestParam("department") String department, @RequestParam("token") String token,@RequestParam("userId") String userId);

}
