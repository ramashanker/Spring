package com.rama.mongo.operation.service;

import org.springframework.web.multipart.MultipartFile;
import com.rama.mongo.operation.exception.FileProcessException;
import com.rama.mongo.operation.response.FileUploadResponse;

public interface DataOperationService {
	FileUploadResponse uploadFileProcess(MultipartFile[] multipartFile, String name) throws FileProcessException;
}
