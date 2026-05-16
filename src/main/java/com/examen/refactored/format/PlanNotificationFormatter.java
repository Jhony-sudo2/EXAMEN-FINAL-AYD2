package com.examen.refactored.format;

import com.examen.refactored.Notification;

public class PlanNotificationFormatter implements NotificationFormatter{

    @Override
    public String format(Notification notification) {
        return notification.getMessage();
    }
    
}
