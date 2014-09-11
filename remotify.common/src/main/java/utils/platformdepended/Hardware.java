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
public class Hardware implements Dependable, IHardware{

    private IHardware instance;
    
    @Override
    public void init(OS.Name name) {
        switch (name) {
            case WINDOWS:
                instance = new Hardware4Win();
                break;
            case LINUX:
                instance = new Hardware4Nix();
                break;
            case MAC:
                instance = new Hardware4Mac();
                break;
        }
    }

    @Override
    public String getSerialNumber() {
        return instance.getSerialNumber();
    }
    
    

}
