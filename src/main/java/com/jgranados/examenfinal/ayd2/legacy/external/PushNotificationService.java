/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgranados.examenfinal.ayd2.legacy.external;

// ============================================================
// NO MODIFICAR ESTA CLASE - Simula un servicio externo de Push
// El estudiante debe crear un Adapter para integrar esta clase
// ============================================================

public class PushNotificationService {
    
    private String appKey;
    
    public PushNotificationService(String appKey) {
        this.appKey = appKey;
    }
    
    public void sendPush(String deviceToken, String title, String body, int priority) {
        if (appKey == null || deviceToken == null) {
            throw new IllegalArgumentException("Missing credentials");
        }
        System.out.println("Sending PUSH to " + deviceToken + " | " + title + ": " + body + " (priority: " + priority + ")");
    }
    
    public String registerDevice(String userId) {
        return "device_token_" + userId;
    }
}
