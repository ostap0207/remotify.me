/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import utils.platformdepended.Dependable;
import utils.platformdepended.IOS;
import utils.platformdepended.WindowsOS;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Ostap
 */
public class OS implements IOS,Dependable {
    
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static String USER_NAME = System.getProperty("user.name");
    public static Name NAME = getName();

    private IOS instance;

    @Override
    public void init(Name name) {
        instance = new WindowsOS();
    }


    public enum Name{
        WINDOWS, MAC, LINUX, UNKNOWN
    }
    
    private static Name getName(){
        if (isWindows()) {
            return Name.WINDOWS;
        } else if (isMac()) {
            return Name.MAC;
        } else if (isUnix()) {
            return Name.LINUX;
        } else if (isSolaris()) {
            return Name.LINUX;
        } else {
            return Name.UNKNOWN;
        }
    }
    
    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

    }

    public static boolean isSolaris() {

        return (OS.indexOf("sunos") >= 0);

    }

    private static String[] prepareShellCommands(String params){
        return isWindows() ? new String[]{"cmd.exe", "/c", params} : new String[]{"/bin/sh", "-c", params};
    }

    public static void openFile(File file) throws IOException{
        String[] commands = prepareShellCommands(file.getPath());
        Runtime.getRuntime().exec(commands);
    }

    public static void exec(String ... args) throws IOException{
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        String[] commands = prepareShellCommands(sb.toString());
        Runtime.getRuntime().exec(commands);
    }

    public static void exec(String command) throws IOException{
        String[] commands = prepareShellCommands(command);
        Runtime.getRuntime().exec(commands);
    }

    public void shutdown(){
        instance.shutdown();
    }
    
}
