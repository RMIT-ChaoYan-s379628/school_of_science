package com.rmit.main.library.api;

import java.util.List;

public class GetUserResponse extends ServiceResponse{

	private static final long serialVersionUID = 1129934986790058023L;
	
	private List<UserDTO> userList;
	
	private long size;

	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
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
		return "GetUserResponse [userList=" + userList + ", size=" + size + "]";
	}

	

}
