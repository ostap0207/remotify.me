package remotify.client.ws.processors;

import messages.device.BrowserMessage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * .
 * User: Ostap
 * Date: 5/8/14
 * Time: 12:13 AM
 */
public class BrowserProcessor implements Processor<BrowserMessage> {

    private static Logger l = Logger.getLogger(BrowserProcessor.class);

    @Override
    public BrowserMessage process(BrowserMessage message) {
        try {
            URL url = new URL(message.url);
            Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url.toString());
        } catch (MalformedURLException ex) {
            l.error("", ex);
        } catch (IOException ex) {
            l.error("", ex);
        }
        return message;
    }
}
