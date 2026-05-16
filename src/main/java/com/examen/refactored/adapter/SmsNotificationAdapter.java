package com.examen.refactored.adapter;

import com.examen.refactored.DeliveryResult;
import com.examen.refactored.Notification;
import com.jgranados.examenfinal.ayd2.legacy.external.ThirdPartySMSProvider;
import com.jgranados.examenfinal.ayd2.legacy.external.UserPhoneRepository;
/*
 Adapter para integrar la libreria externa ThirdPartySMSProvider.
 */
public class SmsNotificationAdapter implements NotificationChanel{
    private final ThirdPartySMSProvider thirdPartySMSProvider;

    public SmsNotificationAdapter(ThirdPartySMSProvider thirdPartySMSProvider){
        if (thirdPartySMSProvider == null) {
            throw new IllegalArgumentException("Sms provder cannot be null");
        }
        this.thirdPartySMSProvider = thirdPartySMSProvider;
    }

    
    @Override
    public DeliveryResult send(Notification notification, String formattedMessage) {
        String phoneNumber = UserPhoneRepository.getPhoneNumber(notification.getUserId());

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return DeliveryResult.failure("User " + notification.getUserId() + " does not have a phone number registered");
        }

        boolean sent = thirdPartySMSProvider.dispatchSMS(phoneNumber, formattedMessage);

        if (!sent) {
            return DeliveryResult.failure("SMS could not be sent to " + phoneNumber);
        }

        return DeliveryResult.success("SMS sent to " + phoneNumber);
    }
    
}
