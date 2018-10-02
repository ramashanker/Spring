package com.rama.mongo.operation.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rama.mongo.operation.exception.FileProcessException;
import com.rama.mongo.operation.response.FileUploadResponse;
import com.rama.mongo.operation.response.RestResponse;
import com.rama.mongo.operation.service.DataOperationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@Api(value="Mongo DB operation controller api",description="manage mongo crud operation")
@RestController
@RequestMapping("/mongo")
public class OperationController {
	private final DataOperationService dataUploadService;

	public OperationController(final DataOperationService dataUploadService) {
		this.dataUploadService = dataUploadService;
	}
	@ApiOperation(value = "upload json file to mongodb", response = RestResponse.class)
	@RequestMapping(value = "entryfile", method = PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<FileUploadResponse> bulkEntryFileUpload(
			@ApiParam(name = "datafile", value = "json files to be uploaded", required = true) @RequestParam(value = "file", required = false) MultipartFile[] filesSubmitted,
			@RequestParam(value = "name", required = false) String name) throws IOException {

		if (filesSubmitted == null || filesSubmitted.length == 0) {
			throw new FileProcessException("No files selected for update entries");
		}
		if (name == null) {
			throw new FileProcessException("No name selected for update entries");
		}
		FileUploadResponse response = dataUploadService.uploadFileProcess(filesSubmitted, name);
		int count = response.getFilecount();
		if ( count == 0) {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
