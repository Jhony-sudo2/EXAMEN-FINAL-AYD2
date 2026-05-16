package com.examen.refactored;

public class Main {
    public static void main(String[] args) {
        NotificationService service = NotificationService.createDefault();

        Notification emailNotification = Notification.builder()
                .userId("jhony")
                .message("System error")
                .type(NotificationTypes.ALERT)
                .channel(NotificationChannels.EMAIL)
                .build();

        service.send(emailNotification);

        Notification smsNotification = Notification.builder()
                .userId("user123")
                .message("Meeting at 10")
                .type(NotificationTypes.REMINDER)
                .channel(NotificationChannels.SMS)
                .build();

        service.send(smsNotification);

        Notification pushNotification = Notification.builder()
                .userId("user123")
                .title("Important notification")
                .message("Discount available")
                .type(NotificationTypes.PROMOTION)
                .channel(NotificationChannels.PUSH)
                .priority(2)
                .build();

        service.send(pushNotification);
    }
}
