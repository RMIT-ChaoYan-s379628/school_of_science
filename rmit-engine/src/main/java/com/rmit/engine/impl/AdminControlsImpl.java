package com.rmit.engine.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rmit.engine.inf.IAdminControls;
import com.rmit.main.library.api.AddCalenderEventDTO;
import com.rmit.main.library.api.AddClientRequest;
import com.rmit.main.library.api.ApiResponseUtils;
import com.rmit.main.library.api.DisableUsersRequest;
import com.rmit.main.library.api.DisableUsersResponse;
import com.rmit.main.library.api.FacultyDTO;
import com.rmit.main.library.api.FeedAnalyticsResponse;
import com.rmit.main.library.api.GetActiveUsers;
import com.rmit.main.library.api.GetFacultyResponse;
import com.rmit.main.library.api.ServiceResponse;
import com.rmit.main.library.enums.Category;
import com.rmit.main.library.enums.Department;
import com.rmit.main.library.enums.UserStatus;
import com.rmit.main.library.feed.model.CalenderEvents;
import com.rmit.main.library.feed.model.Faculty;
import com.rmit.main.library.repository.AUserRepository;
import com.rmit.main.library.repository.CalenderRepository;
import com.rmit.main.library.repository.ContactsRepository;
import com.rmit.main.library.repository.FacultyRepository;
import com.rmit.main.library.repository.FeedRepository;

@Service
public class AdminControlsImpl implements IAdminControls {

    Logger logger = LoggerFactory.getLogger(AdminControlsImpl.class);
    @Autowired
    FeedRepository feedRepo;

    @Autowired
    ContactsRepository contactRepo;

    @Autowired
    FacultyRepository facultyRepo;

    @Autowired
    AUserRepository userRepo;

    @Autowired
    CalenderRepository calRepo;

    @Override
    public FeedAnalyticsResponse getNewsCount() {
        FeedAnalyticsResponse response = new FeedAnalyticsResponse();

        Map<String, Long> data = new HashMap();
        response.setData(data);

        for (Category news : Category.values()) {

            long count = feedRepo.countByCategory(news);

            response.getData().put(news.getValue(), count);

        }
        if (response.getData().isEmpty() || response.getData() == null) {
            response.setCode(ApiResponseUtils.SUCCESS_CODE);
            response.setMessage("Data is not present");
            return response;
        }
        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
        return response;
    }

    @Override
    public ServiceResponse addFaculty(AddClientRequest request) {

        ServiceResponse response = new ServiceResponse();
        Faculty faculty = new Faculty();
        String facultyId = "faculty_" + UUID.randomUUID().toString();
        faculty.setId(facultyId);
        faculty.setCreatedDate(new Date());
        faculty.setDepartment(Department.valueOf(request.getDepartment()));
        faculty.setStatus(UserStatus.UNVERIFIED);
        faculty.setName(request.getName());
        faculty.setUserId(request.getUserId());
        faculty.setUpdatedDate(new Date());
        facultyRepo.save(faculty);
        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
        return response;

    }

    @Override
    public boolean disableFaculty(String userId) {

//        logger.info("Recieved Request to disable Faculty : {}", Arrays.toString(request.getUserIds().toArray()));
//
//        DisableUsersResponse response = new DisableUsersResponse();
//        List<String> disabledUserIds = new ArrayList<>();
//        for (String userId : request.getUserIds()) {
//            Faculty faculty = facultyRepo.findOneByUserIdAndStatus(userId, UserStatus.ACTIVE);
//            logger.info("fetched user {}", faculty.toString());
//            if (faculty != null) {
//                faculty.setStatus(UserStatus.DISABLED);
//                faculty.setUpdatedDate(new Date());
//                facultyRepo.save(faculty);
//                disabledUserIds.add(userId);
//            }
//        }
//        response.setDisabledUserIds(disabledUserIds);
//        response.setCode(ApiResponseUtils.SUCCESS_CODE);
//        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
//        return response;

        Faculty faculty = facultyRepo.findOneByUserId(userId);
        if (faculty != null)
        {
            faculty.setStatus(UserStatus.DISABLED);
            faculty.setUpdatedDate(new Date());
            facultyRepo.save(faculty);
            return true;
        }
        return false;

    }

    @Override
    public ServiceResponse getFaculty(String userId, int index, int size) {

        GetFacultyResponse response = new GetFacultyResponse();

        if (size == 0) {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage("Size cannot be 0");
            return response;
        }
        Pageable pageable = new PageRequest(index, size);
        Page<Faculty> facultyBo;
        facultyBo = facultyRepo.findAllByOrderByCreatedDateDesc(pageable);

        if (facultyBo == null || !facultyBo.hasContent()) {
            response.setCode(ApiResponseUtils.SUCCESS_CODE);
            response.setMessage("Faculty is Empty");
            return response;
        }
        long totalSize = getFacultySize();
        logger.info("Converted Faculty {}", Arrays.toString(facultyBo.getContent().toArray()));
        List<FacultyDTO> facultyList = convertToFacultyDTO(facultyBo);
        logger.info("Converted Faculty {}", Arrays.toString(facultyList.toArray()));
        response.setFacultyList(facultyList);
        response.setSize(totalSize);
        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);

        return response;

    }

    @Override
    public ServiceResponse deleteClient(String userId) {
        ServiceResponse response = new ServiceResponse();
        Faculty fac = facultyRepo.findOneByUserId(userId);
        facultyRepo.delete(fac);
        if (facultyRepo.findOneByUserId(userId) == null) {
            response.setCode(ApiResponseUtils.SUCCESS_CODE);
            response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
        }
        return response;
    }

    @Override
    public boolean enableFaculty(String userId) {
        Faculty faculty = facultyRepo.findOneByUserId(userId);
        if (faculty != null)
        {
            faculty.setStatus(UserStatus.ACTIVE);
            faculty.setUpdatedDate(new Date());
            facultyRepo.save(faculty);
            return true;
        }
        return false;
    }

    @Override
    public ServiceResponse searchFaculty(String userId, String user) {
        GetFacultyResponse response = new GetFacultyResponse();
        if (userId == null || userId == "" || user == null || user == "") {
            response.setCode(ApiResponseUtils.INVALID_REQUEST_CODE);
            response.setMessage("Size cannot be 0");
            return response;
        }
        Faculty f = facultyRepo.findOneByUserId(user);

        if (f == null) {
            response.setCode(ApiResponseUtils.SUCCESS_CODE);
            response.setMessage("Faculty not present");
            return response;
        }
        List<FacultyDTO> userList = new ArrayList<>();
        FacultyDTO fu = new FacultyDTO();
        BeanUtils.copyProperties(f, fu);
        fu.setDepartment(f.getDepartment().getValue());
        fu.setStatus(f.getStatus().getValue());
        userList.add(fu);
        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);
        response.setFacultyList(userList);
        response.setSize(1);

        return response;
    }

    private List<FacultyDTO> convertToFacultyDTO(Page<Faculty> facultyBo) {
        List<FacultyDTO> userList = new ArrayList<>();
        for (Faculty u : facultyBo.getContent()) {
            FacultyDTO user = new FacultyDTO();
            BeanUtils.copyProperties(u, user);
            user.setDepartment(u.getDepartment().getValue());
            user.setStatus(u.getStatus().getValue());
            userList.add(user);
        }
        return userList;
    }

    private long getFacultySize() {
        long count = 0;
        count = facultyRepo.count();
        return count;
    }

    @Override
    public GetActiveUsers getAdminsCount() {

        GetActiveUsers response = new GetActiveUsers();

        long count = facultyRepo.countByStatus(UserStatus.ACTIVE);

        if (count <= 0) {
            response.setCode(ApiResponseUtils.SUCCESS_CODE);
            response.setMessage("There are no admins");
            return response;
        }
        response.setActive(count);
        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);

        return response;

    }

    @Override
    public GetActiveUsers getUsersCount() {
        GetActiveUsers response = new GetActiveUsers();

        long count = userRepo.countByStatus(UserStatus.ACTIVE);

        if (count <= 0) {
            response.setCode(ApiResponseUtils.SUCCESS_CODE);
            response.setMessage("There are no admins");
            return response;
        }
        response.setActive(count);
        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);

        return response;
    }

    @Override
    public ServiceResponse addCalender(AddCalenderEventDTO request) {

        ServiceResponse response = new ServiceResponse();

        List<CalenderEvents> e = calRepo.findAll();
        CalenderEvents event = null;
        if (e != null && !e.isEmpty()) {
            event = e.get(0);
        }

        if (event != null) {

            Map<String, Date> temp = event.getCalender();
            temp.putAll(request.getCalender());
            event.setCalender(temp);
            calRepo.save(event);

        } else {
            CalenderEvents ev = new CalenderEvents();
            ev.setCalender(request.getCalender());
            calRepo.save(ev);

        }
        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);

        return response;

    }

    @Override
    public ServiceResponse editCalender(String req, Date request) {
        ServiceResponse response = new ServiceResponse();

        CalenderEvents event = calRepo.findAll().get(0);

        Map<String, Date> temp = event.getCalender();
        temp.put(req, request);
        event.setCalender(temp);
        calRepo.save(event);

        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);

        return response;
    }

    @Override
    public ServiceResponse deleteCalender(String request) {
        ServiceResponse response = new ServiceResponse();

        CalenderEvents event = calRepo.findAll().get(0);

        Map<String, Date> temp = event.getCalender();
        temp.remove(request);
        event.setCalender(temp);
        calRepo.save(event);

        response.setCode(ApiResponseUtils.SUCCESS_CODE);
        response.setMessage(ApiResponseUtils.SUCCESS_MESSAGE);

        return response;
    }

}
