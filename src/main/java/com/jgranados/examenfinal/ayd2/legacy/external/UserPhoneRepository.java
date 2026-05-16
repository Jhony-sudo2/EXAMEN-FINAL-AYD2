/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgranados.examenfinal.ayd2.legacy.external;

import java.util.HashMap;
import java.util.Map;

// ============================================================
// NO MODIFICAR ESTA CLASE - Simula un repositorio de telefonos
// ============================================================

public class UserPhoneRepository {
    private static Map<String, String> userPhones = new HashMap<>();
    
    static {
        userPhones.put("user123", "5551234567");
        userPhones.put("user456", "5559876543");
        userPhones.put("user789", null);
    }
    
    public static String getPhoneNumber(String userId) {
        return userPhones.getOrDefault(userId, null);
    }
}
