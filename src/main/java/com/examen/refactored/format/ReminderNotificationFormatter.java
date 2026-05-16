package com.examen.refactored.format;

import com.examen.refactored.Notification;

public class ReminderNotificationFormatter implements NotificationFormatter{

    @Override
    public String format(Notification notification) {
        return "[REMINDER] " + notification.getMessage() + " - Don't forget!";
    }

    
}
