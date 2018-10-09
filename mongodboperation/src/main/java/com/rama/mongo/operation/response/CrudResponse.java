package com.rama.mongo.operation.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect
public class CrudResponse {
    String exception;
	String message;
}
