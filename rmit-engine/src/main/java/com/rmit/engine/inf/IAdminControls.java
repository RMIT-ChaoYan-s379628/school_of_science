package com.rmit.engine.inf;

import java.util.Date;

import com.rmit.main.library.api.AddCalenderEventDTO;
import com.rmit.main.library.api.AddClientRequest;
import com.rmit.main.library.api.DisableUsersRequest;
import com.rmit.main.library.api.DisableUsersResponse;
import com.rmit.main.library.api.FeedAnalyticsResponse;
import com.rmit.main.library.api.GetActiveUsers;
import com.rmit.main.library.api.ServiceResponse;

public interface IAdminControls {

    /*
     * 1. GetNewsCount
     * 2. GetContactsCount
     * 3. GetEventsCount
     * 4. AddStaff
     * 5. Remove Staff
     * 6. GetFeedback
     */

    public FeedAnalyticsResponse getNewsCount();

    public ServiceResponse addFaculty(AddClientRequest request);

    public GetActiveUsers getAdminsCount();

    public GetActiveUsers getUsersCount();

    public ServiceResponse addCalender(AddCalenderEventDTO request);

    public ServiceResponse editCalender(String req, Date request);

    public ServiceResponse deleteCalender(String request);

    boolean enableFaculty(String userId);
    //public FeedAnalyticsResponse getContactsCount();

//	public DisableUsersResponse disableFaculty(String userId,DisableUsersRequest request);

    boolean disableFaculty(String userId);

    public ServiceResponse getFaculty(String userId, int index, int size);

    public ServiceResponse deleteClient(String request);

    public ServiceResponse searchFaculty(String userId, String user);

}
