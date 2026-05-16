package com.examen.refactored.adapter;

import com.examen.refactored.DeliveryResult;
import com.examen.refactored.Notification;
import com.jgranados.examenfinal.ayd2.legacy.external.PushNotificationService;
import com.jgranados.examenfinal.ayd2.legacy.external.UserDeviceRepository;
/*
    adapter para intergrar la libreria externa PushNotificationService
*/
public class PushNotificationAdapter implements NotificationChanel {

    private final PushNotificationService pushService;

    public PushNotificationAdapter(PushNotificationService pushService) {
        if (pushService == null) {
            throw new IllegalArgumentException("Push service cannot be null");
        }
        this.pushService = pushService;
    }

    @Override
    public DeliveryResult send(Notification notification, String formattedMessage) {
        String deviceToken = UserDeviceRepository.getDeviceToken(notification.getUserId());

        if (deviceToken == null || deviceToken.trim().isEmpty()) {
            return DeliveryResult.failure("User " + notification.getUserId() + " does not have a device registered");
        }

        try {
            pushService.sendPush(
                    deviceToken,
                    notification.getTitle(),
                    formattedMessage,
                    notification.getPriority());
            return DeliveryResult.success("Push notification sent to " + deviceToken);
        } catch (IllegalArgumentException exception) {
            return DeliveryResult.failure("Push notification could not be sent: " + exception.getMessage());
        }
    }

}
