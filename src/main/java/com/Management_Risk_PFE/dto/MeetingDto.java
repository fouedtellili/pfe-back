package com.Management_Risk_PFE.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class MeetingDto {
    private String type;
    private String title;
    private LocalDate date;
    private List<Long> attendeeIds;
    private String details;
    private String summary;
}
