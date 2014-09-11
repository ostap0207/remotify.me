/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Ostap
 */
public class Network {
    
    public static String MAC_ADDRESS;
    public static String IP;
    
    static{
        try {
            MAC_ADDRESS = Network.getMacAddres();
        } catch (SocketException | UnknownHostException ex) {
        }

        try {
            IP = Network.getIP();
        } catch (UnknownHostException ex) {
        }
        
    }

    public static String getMacAddres() throws SocketException, UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("Current IP address : " + ip.getHostAddress());

        NetworkInterface network = NetworkInterface.getByInetAddress(ip);

        byte[] mac = network.getHardwareAddress();

        System.out.print("Current MAC address : ");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        System.out.println(InetAddress.getLocalHost().getHostName());
        return InetAddress.getLocalHost().getHostName();
    }

    public static String getIP() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }
    
}
