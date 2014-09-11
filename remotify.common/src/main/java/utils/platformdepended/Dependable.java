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
public interface Dependable {
    public void init(OS.Name name);
}
