package remotify.client.profiles.audio;

import utils.OS;

import java.io.File;
import java.io.IOException;

/**
 * .
 * User: Ostap
 * Date: 5/1/14
 * Time: 2:16 PM
 */
public class Audio4Win implements IAudio {

    private File nirCmd = new File("res/bin/nircmd.exe");

    @Override
    public void volumeUp() {
        try {
            OS.exec(nirCmd.getAbsolutePath(),"changesysvolume","2000");
        } catch (IOException e) {
        }
    }

    @Override
    public void volumeDown() {
        try {
            OS.exec(nirCmd.getAbsolutePath(),"changesysvolume","-2000");
        } catch (IOException e) {
        }
    }

    @Override
    public void mute() {
        try {
            OS.exec(nirCmd.getAbsolutePath(),"mutesysvolume","1");
        } catch (IOException e) {
        }
    }

    @Override
    public void unmute() {
        try {
            OS.exec(nirCmd.getAbsolutePath(),"mutesysvolume","0");
        } catch (IOException e) {
        }
    }

    @Override
    public void changeVolume(int value) {
        try {
            OS.exec(nirCmd.getAbsolutePath(),"setsysvolume",value+"");
        } catch (IOException e) {
        }
    }

}
