package com.goulbamc.clientservice.dto;

import com.goulbamc.clientservice.model.Contact;
import com.goulbamc.clientservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReminderRequest {
    private Long reminderId;
    private String task;
    private Date reminder_date_time;
    private Long contact_id;
    private Long user_id;
//    private Contact contact;
//    private User user;
    private Date registration_date;
}
