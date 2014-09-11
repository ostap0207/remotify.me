/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.security.MessageDigest;
import java.util.UUID;

/**
 *
 * @author Ostap
 */
public class Crypt {
    
    public static String encrypt(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((salt + password).getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public static String generateSalt() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int rand = (int) (Math.random() * 10);
            sb.append(rand);
        }
        return sb.toString();
    }
    
    
    public static String generateKey(){
        return UUID.randomUUID().toString();
    }
}
