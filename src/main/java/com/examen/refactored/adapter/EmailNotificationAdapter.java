package com.examen.refactored.adapter;

import com.examen.refactored.DeliveryResult;
import com.examen.refactored.Notification;
import com.jgranados.examenfinal.ayd2.legacy.EmailSender;

/*
     Adapter para el envio de email existente.
 */
public class EmailNotificationAdapter implements NotificationChanel {

    @Override
    public DeliveryResult send(Notification notification, String formattedMessage) {
        String email = getUserEmail(notification.getUserId());
        EmailSender.send(email, formattedMessage);
        return DeliveryResult.success("Email sent to " + email);
    }

    private String getUserEmail(String userId) {
        return userId + "@company.com";
    }
}
