package com.rmit.main.library.api;

import java.util.List;

public class GetFacultyResponse extends ServiceResponse {

	private static final long serialVersionUID = -203209094693643644L;
	
	private List<FacultyDTO> facultyList;
	
	private long size;

	public List<FacultyDTO> getFacultyList() {
		return facultyList;
	}

	public void setFacultyList(List<FacultyDTO> facultyList) {
		this.facultyList = facultyList;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "GetFacultyResponse [facultyList=" + facultyList + ", size=" + size + "]";
	}

}
