package com.rmit.main.library.api;

import java.util.List;

public class GetContactResponseDTO extends ServiceResponse {

	private static final long serialVersionUID = -4873258435834560850L;

	private List<ContactDTO> contacts;

	private long size;

	public List<ContactDTO> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactDTO> contacts) {
		this.contacts = contacts;
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
		return "GetContactResponseDTO [contacts=" + contacts + ", size=" + size + "]";
	}

}
