package com.examen.refactored.format;

import com.examen.refactored.Notification;

public class AlertNotificationFormatter implements NotificationFormatter{
    @Override
    public String format(Notification notification) {
        return "[ALERT] " + notification.getMessage() + " - Action required!";
    }
}
