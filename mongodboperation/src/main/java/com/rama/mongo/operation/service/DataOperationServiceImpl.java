package com.rama.mongo.operation.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rama.mongo.operation.document.Employee;
import com.rama.mongo.operation.exception.ProcessException;
import com.rama.mongo.operation.response.CrudResponse;
import com.rama.mongo.operation.response.FileUploadResponse;

@Service
public class DataOperationServiceImpl implements DataOperationService {

	private MongoTemplate mongoTemplate;

	public DataOperationServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public FileUploadResponse uploadFileProcess(MultipartFile[] multipartFiles, String name) throws ProcessException {
		FileUploadResponse uploadFileResponse = new FileUploadResponse();
		List<String> exception = new ArrayList<>();
		int counter = 0;
		List<Document> documents = new ArrayList<>();
		for (MultipartFile multipartFile : multipartFiles) {
			String fileName = multipartFile.getOriginalFilename();
			String fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
			InputStream inputStream;
			try {
				inputStream = multipartFile.getInputStream();
				String jsonTxt = IOUtils.toString(inputStream, "UTF-8");
				Document doc = Document.parse(jsonTxt);
				documents.add(doc);
				mongoTemplate.insert(documents, fileNameWithoutExtension);
			} catch (UnsupportedEncodingException ex) {
				exception.add(ex.getMessage());
				ex.printStackTrace();
			} catch (IOException ex) {
				exception.add(ex.getMessage());
				ex.printStackTrace();
			}
			counter++;

		}
		uploadFileResponse.setFilecount(counter);
		uploadFileResponse.setException(exception);
		return uploadFileResponse;
	}

	@Override
	public Employee createData(Employee employee) {

		employee.set_id(ObjectId.get());
		mongoTemplate.insert(employee);
		return employee;
	}

	@Override
	public List<Employee> readAllData() {
		return mongoTemplate.findAll(Employee.class);
	}

	@Override
	public Employee updateData(Employee employee) {
		mongoTemplate.save(employee);
		return employee;
	}

	@Override
	public void deleteData(ObjectId  id) {
		mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Employee.class);
	}
}
