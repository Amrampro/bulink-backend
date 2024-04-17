package com.goulbamc.clientservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reminderId;

    @Column(nullable = false)
    private String task;

    @Column(nullable = false)
    private Date reminder_date_time;

//    @ManyToOne
//    @JoinColumn(name = "contact_id")
//    private Contact contact;
    private Long contact_id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
    private Long user_id;

    private Date registration_date;
}
