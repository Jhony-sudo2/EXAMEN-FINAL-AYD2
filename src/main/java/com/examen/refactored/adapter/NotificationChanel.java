package com.examen.refactored.adapter;

import com.examen.refactored.DeliveryResult;
import com.examen.refactored.Notification;

public interface NotificationChanel {
        DeliveryResult send(Notification notification, String formattedMessage);

}
