/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.platformdepended;

import utils.OS;

/**
 *
 * @author Ostap
 */
public class Platform implements Dependable{

    public static Hardware HARDWARE;
    public static OS OS;

    @Override
    public void init(OS.Name name) {
        HARDWARE = new Hardware();
        HARDWARE.init(name);

        OS = new OS();
        OS.init(name);
    }
    
}
