/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgranados.examenfinal.ayd2.legacy.external;

// ============================================================
// NO MODIFICAR ESTA CLASE - Simula una libreria externa de SMS
// El estudiante debe crear un Adapter para integrar esta clase
// ============================================================

public class ThirdPartySMSProvider {
    
    private String apiKey;
    private int retryAttempts = 3;
    
    public ThirdPartySMSProvider(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public boolean dispatchSMS(String phoneNumber, String content) {
        if (apiKey == null || apiKey.isEmpty()) {
            return false;
        }
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            return false;
        }
        if (content == null || content.length() > 160) {
            return false;
        }
        System.out.println("Sending SMS to " + phoneNumber + ": " + content);
        return true;
    }
    
    public void setRetryAttempts(int attempts) {
        this.retryAttempts = attempts;
    }
}