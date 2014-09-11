/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages.device;

import messages.Message;

/**
 *
 * @author Ostap
 */
public abstract class CommandMessage implements Message{

    public static final long serialVersionUID = 1L;

    public String computerUID;
    public String deviceUID;
}
