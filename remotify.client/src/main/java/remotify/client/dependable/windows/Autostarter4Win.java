package remotify.client.dependable.windows;

import org.apache.log4j.Logger;
import remotify.client.dependable.IAutostarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * .
 * User: Ostap
 * Date: 5/2/14
 * Time: 11:02 PM
 */
public class Autostarter4Win implements IAutostarter {

    Logger l = Logger.getLogger(Autostarter4Win.class);

    @Override
    public void autostart() {
        try {
            WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER,
                    "Software\\Microsoft\\Windows\\CurrentVersion\\Run",
                    "Remotify",getExePath());
        } catch (IllegalAccessException|InvocationTargetException e) {
            l.warn("Can not add to autostart", e);
        } catch (IOException e) {
            l.warn("Startup exe not found", e);
        }
    }

    private String getExePath() throws IOException {
        File file = new File("../");
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File parent, String name) {
                return name.startsWith("remotify.client-") && name.endsWith(".exe");
            }
        });
        if (files.length > 0)
            return "\"" + files[0].getCanonicalPath() + "\"";

        throw new FileNotFoundException();
    }
}
