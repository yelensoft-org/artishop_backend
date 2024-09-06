package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.entities.Notification;
import com.yelensoft.artishop_backend.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.orElse(null);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification updateNotification(Long id, Notification notificationDetails) {
        Notification notification = getNotificationById(id);
        if (notification != null) {
            notification.setTittle(notificationDetails.getTittle());
            notification.setContent(notificationDetails.getContent());
            notification.setType(notificationDetails.getType());
            notification.setDeleted(notificationDetails.isDeleted());
            notification.setCustomer(notificationDetails.getCustomer());
            return notificationRepository.save(notification);
        } else {
            return null;
        }
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
