package com.Management_Risk_PFE.services.auth;

import com.Management_Risk_PFE.dto.MeetingDto;
import com.Management_Risk_PFE.entity.Meeting;
import com.Management_Risk_PFE.entity.User;
import com.Management_Risk_PFE.repository.MeetingRepository;
import com.Management_Risk_PFE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public Meeting createMeeting(MeetingDto meetingDto) {
        Meeting meeting = new Meeting();
        meeting.setType(meetingDto.getType());
        meeting.setTitle(meetingDto.getTitle());
        meeting.setDate(meetingDto.getDate());
        meeting.setDetails(meetingDto.getDetails());
        meeting.setSummary(meetingDto.getSummary());

        Set<User> attendees = new HashSet<>();
        if (meetingDto.getAttendeeIds() != null) {
            meetingDto.getAttendeeIds().forEach(userId -> {
                userRepository.findById(userId).ifPresent(attendees::add);
            });
        }
        meeting.setAttendees(attendees);

        Meeting savedMeeting = meetingRepository.save(meeting);

        // Envoi des mails aux participants
        attendees.forEach(user -> {
            String subject = "Invitation to Meeting: " + savedMeeting.getTitle();
            String body = "Hello  " + user.getName() + ",\n\n" +
                    "You are invited to the following meeting :\n" +
                    "Type : " + savedMeeting.getType() + "\n" +
                    "Title : " + savedMeeting.getTitle() + "\n" +
                    "Date : " + savedMeeting.getDate() + "\n" +
                    "Details : " + savedMeeting.getDetails() + "\n\n" +
                    "Thank you for your participation.\n\n" +
                    "Best regards,\\nThe Management Team";
            emailService.sendSimpleEmail(user.getEmail(), subject, body);
        });

        return savedMeeting;
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }

    @Override
    public Meeting updateMeetingSummary(Long id, String summary) {
        return meetingRepository.findById(id).map(meeting -> {
            meeting.setSummary(summary);
            return meetingRepository.save(meeting);
        }).orElse(null);
    }
}
