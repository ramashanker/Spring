package com.rama.mongo.operation.document;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Employee")
public class Employee {

	@Id
	public ObjectId _id;

	@Indexed(unique = true)
	@Field(value = "Emp_No")
	private String empNo;

	@Field(value = "Full_Name")
	private String fullName;

	@Field(value = "Age")
	private int age;

	@Field(value = "Release_Date")
	private Date releaseDate;

	// ObjectId needs to be converted to string
	public String get_id() {
		return _id.toHexString();
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "id:" + this._id + ", empNo: " + empNo //
				+ ", fullName: " + this.fullName + ", Age: " + this.age + ", releaseDate: "
				+ this.releaseDate;
	}
}