package com.examen.refactored.format;

import com.examen.refactored.Notification;
/*
    Se utiliza el Patron Strategy.
  Cada implementacion define una forma distinta de formatear el mensaje.
 */
public interface NotificationFormatter {
    String format(Notification notification);
}