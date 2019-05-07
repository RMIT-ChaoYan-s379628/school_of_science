package com.rmit.main.library.api;

import java.util.List;


public class AddUsersRequest extends ServiceRequest {

    private static final long   serialVersionUID = -3094916869058793968L;

    private List<AddUserDTO> users;

    @Override
    public boolean isValid() {
        if (users == null || users.isEmpty())
            return false;
        for (AddUserDTO user : users) {
            if (!user.isValid())
                return false;
        }
        return true;
    }

	public List<AddUserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<AddUserDTO> users) {
		this.users = users;
	}

}
