package com.rama.mongo.operation.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import com.rama.mongo.operation.document.Employee;
import com.rama.mongo.operation.exception.ProcessException;
import com.rama.mongo.operation.response.FileUploadResponse;

public interface DataOperationService {
	FileUploadResponse uploadFileProcess(MultipartFile[] multipartFile, String name) throws ProcessException;
	Employee createData(Employee employee);
	List<Employee> readAllData();
	Employee updateData(Employee employee);
	void deleteData(ObjectId employee);
	
}
