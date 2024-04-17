package com.goulbamc.clientservice.service;

import com.goulbamc.clientservice.dto.ReminderRequest;
import com.goulbamc.clientservice.dto.ReminderResponse;
import com.goulbamc.clientservice.model.Reminder;
import com.goulbamc.clientservice.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserService userService;

    /****************************************************************************
     *                            Tests functions                               *
     ****************************************************************************/
    // Testing if data is available
    public boolean dataExists(Long id) {
        if(reminderRepository.existsById(id)){
            return true;
        } else {
            return false;
        }
    }

    // Retrieving data
    private ReminderResponse mapToReminderResponse(Reminder reminder) {
        return ReminderResponse.builder()
                .reminderId(reminder.getReminderId())
                .task(reminder.getTask())
                .reminder_date_time(reminder.getReminder_date_time())
                .contact_id(reminder.getContact_id())
                .user_id(reminder.getUser_id())
                .registration_date(reminder.getRegistration_date())
                .build();
    }

    /**************************************************************
     *                       CRUD Functions                       *
     **************************************************************/

    // Create a new reminder
    public String createReminder(ReminderRequest reminderRequest){
        String req[] = {};
        ArrayList<String> reqList = new ArrayList<>(Arrays.asList(req));

        String message = "Reminder created successfully";
        boolean status = true;

        if (reminderRequest.getUser_id() == null){
            reqList.add("User");
        }

        if (reminderRequest.getTask() == null){
            reqList.add("Task");
        }

        if (reminderRequest.getReminder_date_time() == null){
            reqList.add("Reminder Date");
        }

        req = reqList.toArray(new String[0]);

        if (reminderRequest.getUser_id() == null || reminderRequest.getTask() == null || reminderRequest.getReminder_date_time() == null){
            message = "Error : Field Empty " + Arrays.toString(req);
            status = false;
        } else if (userService.dataExist(reminderRequest.getUser_id()) == false){
            message = "User does not exist";
            status = false;
        }

        log.info("Creation status: " + status);

        if (status == true) {
            try {
                Reminder reminder = Reminder.builder()
                        .task(reminderRequest.getTask())
                        .registration_date(new Date())
                        .user_id(reminderRequest.getUser_id())
                        .reminder_date_time(reminderRequest.getReminder_date_time())
                        .build();

                reminderRepository.save(reminder);
                return message;
            } catch (DateTimeParseException e){
                return "Error: Invalid date format. Format example : 2024-01-23T12:17:27";
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        } else {
            return message;
        }
    }

    // Read all reminders
    public List<ReminderResponse> readReminders(){
        List<Reminder> reminders = reminderRepository.findAll();
        return reminders.stream().map(this::mapToReminderResponse).toList();
    }

    // Read single reminder
    public ReminderResponse readReminder(Long reminderId){
        if (dataExists(reminderId)){
            Reminder reminder = reminderRepository.findById(reminderId).get();
            return mapToReminderResponse(reminder);
        } else {
            return null;
        }
    }

    // Read user reminders
    public List<ReminderResponse> remindersByUser(Long userId) {
        List<Reminder> reminders = reminderRepository.findByUserId(userId);
        return reminders.stream().map(this::mapToReminderResponse).toList();
    }

    // Update reminder
    public String updateReminder(Long id, ReminderRequest updateReminderRequest) {
        boolean status = true;
        if (dataExists(id)){
            Reminder reminder = reminderRepository.findById(id).orElse(null);

            if (reminder != null){
                if (updateReminderRequest.getTask() != null){
                    reminder.setTask(updateReminderRequest.getTask());
                }

                if (updateReminderRequest.getReminder_date_time() != null){
                    reminder.setReminder_date_time(updateReminderRequest.getReminder_date_time());
                }

                reminderRepository.save(reminder);
                return "Reminder registered";
            }
        } else {
            return "The user does not exist";
        }
        return "An error occured";
    }

    // Delete Reminder
    public String deleteReminder(Long id){
        if (dataExists(id)){
            reminderRepository.deleteById(id);
            return "Data deleted";
        } else {
            return "The data does not exist";
        }
    }
}
