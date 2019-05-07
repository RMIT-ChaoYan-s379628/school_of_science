package com.rmit.engine.s3;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rmit.main.library.api.ApiResponseUtils;
import com.rmit.main.library.api.ServiceResponse;

@Service
public class S3ServicesImpl implements S3Services {

	private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${rmit.s3.bucket}")
	private String bucketName;

	@Override
	public ServiceResponse uploadFile(String keyName, MultipartFile file) {
		ServiceResponse response = new ServiceResponse();
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			String filename = file.getOriginalFilename();

			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename,
					file.getInputStream(), metadata);

			putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);

			s3client.putObject(putObjectRequest);
			
			response.setCode(ApiResponseUtils.SUCCESS_CODE);
			response.setMessage(s3client.getUrl(bucketName, filename).toString());

		} catch (IOException ioe) {
			logger.error("IOException: " + ioe.getMessage());
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(ioe.getMessage());
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(ase.getMessage());
			throw ase;
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
			response.setCode(ApiResponseUtils.ERROR_CODE);
			response.setMessage(ace.getMessage());
			throw ace;
		}

		return response;
	}

}
