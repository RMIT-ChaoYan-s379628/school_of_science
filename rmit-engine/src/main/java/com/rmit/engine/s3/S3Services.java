package com.rmit.engine.s3;

import org.springframework.web.multipart.MultipartFile;

import com.rmit.main.library.api.ServiceResponse;

public interface S3Services {
	public ServiceResponse uploadFile(String keyName, MultipartFile file);
}
