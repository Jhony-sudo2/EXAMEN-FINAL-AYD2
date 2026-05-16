package com.examen.refactored;

/**
  Constantes de formatos conocidos.
  Se usan String y no enum para respetar Open/Closed Principle:
  un nuevo formato se registra, no se modifica el servicio.
 */
public final class NotificationTypes {

    public static final String PLAIN = "plain";
    public static final String ALERT = "alert";
    public static final String REMINDER = "reminder";
    public static final String PROMOTION = "promotion";

    private NotificationTypes() {
    }
}
