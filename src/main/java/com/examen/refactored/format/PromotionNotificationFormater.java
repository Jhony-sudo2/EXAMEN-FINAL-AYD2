package com.examen.refactored.format;

import com.examen.refactored.Notification;

public class PromotionNotificationFormater implements NotificationFormatter{

    @Override
    public String format(Notification notification) {
        return "[PROMO] " + notification.getMessage() + " - Limited offer!";
    }
    
}
