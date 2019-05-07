package com.rmit.main.library.api;

import java.util.List;

public class GetDepartmentResponse extends ServiceResponse{

	
	private static final long serialVersionUID = -1268530279270579477L;
	
	private List<String> departments;

	public List<String> getDepartments() {
		return departments;
	}

	public void setDepartments(List<String> departments) {
		this.departments = departments;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "GetDepartmentResponse [departments=" + departments + "]";
	}
	

}
