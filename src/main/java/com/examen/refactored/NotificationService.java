package com.examen.refactored;

import java.util.HashMap;
import java.util.Map;

import com.examen.refactored.adapter.EmailNotificationAdapter;
import com.examen.refactored.adapter.NotificationChanel;
import com.examen.refactored.adapter.PushNotificationAdapter;
import com.examen.refactored.adapter.SmsNotificationAdapter;
import com.examen.refactored.format.NotificationFormatter;
import com.examen.refactored.format.NotificationFormatterRegister;
import com.examen.refactored.login.AudithService;
import com.examen.refactored.login.LegacyAudithService;
import com.jgranados.examenfinal.ayd2.legacy.external.PushNotificationService;
import com.jgranados.examenfinal.ayd2.legacy.external.ThirdPartySMSProvider;

public class NotificationService {
    private final NotificationFormatterRegister formatterRegistry;
    private final Map<String, NotificationChanel> channels;
    private final AudithService auditService;

    
    public NotificationService(NotificationFormatterRegister formatterRegistry,
            Map<String, NotificationChanel> channels, AudithService auditService) {
        if (formatterRegistry == null) {
            throw new IllegalArgumentException("Formatter registry cannot be null");
        }
        if (channels == null) {
            throw new IllegalArgumentException("Channels cannot be null");
        }
        if (auditService == null) {
            throw new IllegalArgumentException("AuditService cannot be null");
        }

        
        this.formatterRegistry = formatterRegistry;
        this.channels = channels;
        this.auditService = auditService;
    }

    public DeliveryResult send(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("Notification cannot be null");
        }

        NotificationChanel channel = channels.get(notification.getChannel());

        if (channel == null) {
            throw new IllegalArgumentException("Unsupported notification channel: " + notification.getChannel());
        }

        NotificationFormatter formatter = formatterRegistry.getFormatter(notification.getType());
        String formattedMessage = formatter.format(notification);

        DeliveryResult result = channel.send(notification, formattedMessage);
        auditService.log(notification, result);

        return result;
    }
    
    public static NotificationService createDefault() {
        NotificationFormatterRegister formatterRegistry = new NotificationFormatterRegister();
        Map<String, NotificationChanel> channels = new HashMap<>();

        channels.put(NotificationChannels.EMAIL, new EmailNotificationAdapter());
        channels.put(NotificationChannels.SMS, new SmsNotificationAdapter(new ThirdPartySMSProvider("sms-api-key")));
        channels.put(NotificationChannels.PUSH,
                new PushNotificationAdapter(new PushNotificationService("push-app-key")));
        return new NotificationService(
                formatterRegistry,
                channels,
                new LegacyAudithService());
    }
}
