package messages.device;

import messages.Message;

/**
 * .
 * User: Ostap
 * Date: 5/7/14
 * Time: 11:47 PM
 */
public class BrowserMessage extends CommandMessage {

    //in
    public String url;

    @Override
    public int getMessageId() {
        return Message.BROWSER_MESSAGE;
    }
}
