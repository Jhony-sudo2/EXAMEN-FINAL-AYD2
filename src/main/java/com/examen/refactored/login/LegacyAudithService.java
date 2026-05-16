package com.examen.refactored.login;

import com.examen.refactored.DeliveryResult;
import com.examen.refactored.Notification;
import com.jgranados.examenfinal.ayd2.legacy.AuditLogger;

/* 
    adaptador para reutilizar el logger legayc
*/
public class LegacyAudithService implements AudithService{

    @Override
    public void log(Notification notification, DeliveryResult result) {
        AuditLogger.log(
                "Notification channel: " + notification.getChannel()
                        + ", type: " + notification.getType()
                        + ", user: " + notification.getUserId()
                        + ", success: " + result.isSuccessful()
                        + ", detail: " + result.getDetail()
        );
    }
    
}
