package com.examen.refactored;

/*
  Resultado del envio de una notificacion.
  Permite manejar errores de negocio, por ejemplo usuario sin telefono
  o sin dispositivo, sin romper el flujo con excepciones innecesarias.
 */
public class DeliveryResult {

    private final boolean successful;
    private final String detail;

    private DeliveryResult(boolean successful, String detail) {
        this.successful = successful;
        this.detail = detail;
    }

    public static DeliveryResult success(String detail) {
        return new DeliveryResult(true, detail);
    }

    public static DeliveryResult failure(String detail) {
        return new DeliveryResult(false, detail);
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getDetail() {
        return detail;
    }
}
