package com.examen.refactored;

/**
 Constantes de canales conocidos.
  Se usan String y no enum para que se puedan registrar nuevos canales
  sin modificar el codigo existente.
 */
public final class NotificationChannels {

    public static final String EMAIL = "email";
    public static final String SMS = "sms";
    public static final String PUSH = "push";

    private NotificationChannels() {
    }   
    
}
