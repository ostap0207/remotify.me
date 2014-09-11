import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by User on 03.03.14.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.setSize(900, 900);
        JLabel label = new JLabel();
        frame.add(label);
        frame.setVisible(true);

        while(true){
            String data = readUrl("http://api.remotify.me/user/computer/6f8cc1d8-2472-4143-9027-c70bddfe2e9a/desktop/02035d2b-376f-41e5-aa88-b83f76f97d63");
            data = data.substring(23,data.length() - 2);
            label.setIcon(new ImageIcon(Base64.decode(data)));
            Thread.sleep(100);
        }

    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }

    }
}
