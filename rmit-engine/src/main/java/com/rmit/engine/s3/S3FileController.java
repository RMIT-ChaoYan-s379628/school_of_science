package com.rmit.engine.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rmit.main.library.api.ServiceResponse;

@RestController
public class S3FileController {
	
	@Autowired
	S3Services s3Services;
	 	
	@PostMapping("/api/file/upload")
    public ServiceResponse uploadMultipartFile(@RequestParam("file") MultipartFile file) {
    	String keyName = file.getOriginalFilename();
		return s3Services.uploadFile(keyName, file);
    }   
}
