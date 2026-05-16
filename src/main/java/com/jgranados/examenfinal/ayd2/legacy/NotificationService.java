/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgranados.examenfinal.ayd2.legacy;

/**
 * NO MODIFICAR ESTA CLASE - Es el codigo legacy a refactorizar El estudiante
 * debe escribir pruebas para esta clase y luego crear una nueva version
 * refactorizada en otro paquete
 *
 * @author jose
 */
public class NotificationService {

    public void sendNotification(String userId, String message, String type) {
        // 1. Obtener email del usuario
        String userEmail = getUserEmail(userId);

        // 2. Validar mensaje
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        // 3. Formatear segun tipo
        String formattedMessage = message;
        if (type.equals("alert")) {
            formattedMessage = "[ALERT] " + message + " - Action required!";
        } else if (type.equals("reminder")) {
            formattedMessage = "[REMINDER] " + message + " - Don't forget!";
        } else if (type.equals("promotion")) {
            formattedMessage = "[PROMO] " + message + " - Limited offer!";
        }

        // 4. Enviar email
        EmailSender.send(userEmail, formattedMessage);

        // 5. Guardar log
        AuditLogger.log("Email sent to " + userEmail + " with type: " + type);
    }

    private String getUserEmail(String userId) {
        return userId + "@company.com";
    }
}
