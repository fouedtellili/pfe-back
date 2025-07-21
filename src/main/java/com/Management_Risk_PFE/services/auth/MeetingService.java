package com.Management_Risk_PFE.services.auth;

import com.Management_Risk_PFE.dto.MeetingDto;
import com.Management_Risk_PFE.entity.Meeting;

import java.util.List;

public interface MeetingService {
    Meeting createMeeting(MeetingDto meetingDto);
    List<Meeting> getAllMeetings();

    void deleteMeeting(Long id);

    Meeting updateMeetingSummary(Long id, String summary);
}
