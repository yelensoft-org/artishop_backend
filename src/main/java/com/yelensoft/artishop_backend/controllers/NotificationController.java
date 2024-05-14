package com.yelensoft.artishop_backend.controllers;
import com.yelensoft.artishop_backend.configuration.ResponseHandler;
import com.yelensoft.artishop_backend.entities.Notification;
import com.yelensoft.artishop_backend.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/add")
    public ResponseEntity<Object> addNotification(@Valid @RequestBody Notification notification){
        return ResponseHandler.generateResponse("success", HttpStatus.OK, notificationService.createNotification(notification));
    }

    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllNotifications() {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, notificationService.getAllNotifications());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateNotification(@PathVariable Long id, @Valid @RequestBody Notification notification) {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, notificationService.updateNotification(id, notification));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseHandler.generateResponse("success", HttpStatus.OK, null);
    }
}
