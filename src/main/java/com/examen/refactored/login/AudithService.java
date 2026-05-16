package com.examen.refactored.login;

import com.examen.refactored.DeliveryResult;
import com.examen.refactored.Notification;
/*
    Abstraccion para registrar auditoria sin acoplar el servicio principal
  directamente a AuditLogger.
 */
public interface AudithService {
    void log(Notification notification,DeliveryResult result);
}
