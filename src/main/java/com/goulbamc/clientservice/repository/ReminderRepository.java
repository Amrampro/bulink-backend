package com.goulbamc.clientservice.repository;

import com.goulbamc.clientservice.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    @Query("SELECT r FROM Reminder r WHERE r.user_id = :userId")
    List<Reminder> findByUserId(@Param("userId") Long userId);
}
