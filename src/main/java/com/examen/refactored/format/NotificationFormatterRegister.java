package com.examen.refactored.format;

import java.util.HashMap;
import java.util.Map;

import com.examen.refactored.NotificationTypes;

/* 

    Registro de estrategis de formato 
    Con esto nos permite agregar formatos nuevos sin modificar NotificationService
 
*/
public class NotificationFormatterRegister {
    private final Map<String, NotificationFormatter> formatters = new HashMap<>();
    private final NotificationFormatter defaultFormatter = new PlanNotificationFormatter();

    public NotificationFormatterRegister() {
        register(NotificationTypes.PLAIN, defaultFormatter);
        register(NotificationTypes.ALERT, new AlertNotificationFormatter());
        register(NotificationTypes.REMINDER, new ReminderNotificationFormatter());
        register(NotificationTypes.PROMOTION, new PromotionNotificationFormater());
    }

    public void register(String type, NotificationFormatter formatter) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Notification type cannot be empty");
        }

        if (formatter == null) {
            throw new IllegalArgumentException("Formatter cannot be null");
        }

        formatters.put(type, formatter);
    }

    public NotificationFormatter getFormatter(String type) {
        return formatters.getOrDefault(type, defaultFormatter);
    }
}
