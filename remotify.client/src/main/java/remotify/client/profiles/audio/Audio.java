package remotify.client.profiles.audio;

import utils.OS;
import utils.platformdepended.Dependable;

/**
 * .
 * User: Ostap
 * Date: 5/1/14
 * Time: 10:28 AM
 */
public class Audio implements IAudio, Dependable {

    private IAudio instance;

    @Override
    public void init(OS.Name name) {
        switch (name) {
            case WINDOWS:
                instance = new Audio4Win();
                break;
            case LINUX:
                break;
            case MAC:
                break;
        }
    }

    @Override
    public void volumeUp() {
        instance.volumeUp();
    }

    @Override
    public void volumeDown() {
        instance.volumeDown();
    }

    @Override
    public void mute() {
        instance.mute();
    }

    @Override
    public void unmute() {
        instance.unmute();
    }

    @Override
    public void changeVolume(int value) {
        instance.changeVolume(value);
    }

}
