package remotify.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * .
 * User: Ostap
 * Date: 5/1/14
 * Time: 3:27 PM
 */
public enum Resources {
    INSTANCE;

    public static final String RESOURCES_DIR = "res";
    public static final String ICON_PATH = "/icon-64.png";
    public static final String CONFIG_PATH = "/conf/config.properties";
    public static final String WIN_START_BAT = "/bin/autostart/remotify.bat";

    private static final String[] resourcesToExtract = new String[]{
            ICON_PATH,
            CONFIG_PATH,
            "/bin/nircmd.exe",
            WIN_START_BAT
    };


    public void extractFile(String fileName)
            throws IOException {
        String fullPath = RESOURCES_DIR + fileName;
        String dirName = fullPath.substring(0,fullPath.lastIndexOf("/"));
        File dir = new File(dirName);
        if (!dir.exists()){
            dir.mkdirs();
        }

        if (!new File(fullPath).exists()) {
            InputStream io = getClass().getResourceAsStream(fileName);
            try (FileOutputStream fos = new FileOutputStream(fullPath)) {
                byte[] buf = new byte[256];
                int read = 0;
                while ((read = io.read(buf)) > 0) {
                    fos.write(buf, 0, read);
                }
            }
        }
    }

    private void extractResources(){
        for (String resource : resourcesToExtract) {
            try{
                extractFile(resource);
            }catch (IOException ex){

            }
        }
    }

    public void init(){
        extractResources();
    }

    public String getPath(String resource){
        return RESOURCES_DIR + resource;
    }

    public String getAbsolutePath(String resource){
        return RESOURCES_DIR + resource;
    }

    public File getFile(String resource){
        return new File(RESOURCES_DIR + resource);
    }

}
