/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgranados.examenfinal.ayd2.legacy.external;


import java.util.HashMap;
import java.util.Map;

// ============================================================
// NO MODIFICAR ESTA CLASE - Simula un repositorio de dispositivos
// ============================================================

public class UserDeviceRepository {
    private static Map<String, String> userDevices = new HashMap<>();
    
    static {
        userDevices.put("user123", "device_abc123");
        userDevices.put("user456", null);
    }
    
    public static String getDeviceToken(String userId) {
        return userDevices.get(userId);
    }
}
