/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotify.client.profiles.keyboard;

import com.sun.glass.events.KeyEvent;
import org.apache.log4j.Logger;

import java.awt.*;

/**
 *
 * @author Ostap
 */
public class Keyboard {

    private static Logger l = Logger.getLogger(Keyboard.class);
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public enum Action{
        HOLD, PRESS, RELEASE, TYPE
    }

    public interface RobotAction{
        public void exec(Robot robot);
    }

    public static void right(){
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_RIGHT);
        } catch (AWTException ex) {
        }
    }
    
    public static void left(){
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_LEFT);
            robot.keyRelease(KeyEvent.VK_LEFT);
        } catch (AWTException ex) {
        }
    }
    
    public static void up(){
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_UP);
            robot.keyRelease(KeyEvent.VK_UP);
        } catch (AWTException ex) {
        }
    }
    
    public static void down(){
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
        } catch (AWTException ex) {
        }
    }
    
    public static void space() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_SPACE);
        } catch (AWTException ex) {
        }
    }

    public static void enter() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException ex) {
        }
    }

    public static void f5() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_F5);
            robot.keyRelease(KeyEvent.VK_F5);
        } catch (AWTException ex) {
        }
    }

    public static void nextWindow() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_ALT);
        } catch (AWTException ex) {
        }
    }

    public static void closeWindow() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_F4);
            robot.keyRelease(KeyEvent.VK_F4);
            robot.keyRelease(KeyEvent.VK_ALT);
        } catch (AWTException ex) {
        }
    }

    public static void alt_enter(){
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException ex) {
        }
    }

    public static void exec(final Action action, final int key){
        robotExec(new RobotAction() {
            @Override
            public void exec(Robot robot) {
                l.info("Execute keyboard action : " + action.toString() + ", key = " + key);
                switch (action) {
                    case HOLD:
                        robot.keyPress(key);
                        break;
                    case TYPE:
                        robot.setAutoDelay(500);
                        robot.keyPress(key);
                        robot.keyRelease(key);
                        break;
                    case RELEASE:
                        robot.keyRelease(key);
                        break;
                    case PRESS:
                        robot.keyPress(key);
                        break;
                }
            }
        });
    }

    private static void robotExec(RobotAction action){
        l.info("Start robot action");
        action.exec(robot);
        l.info("Finish robot action");
    }

}
