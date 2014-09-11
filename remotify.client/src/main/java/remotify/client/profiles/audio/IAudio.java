package remotify.client.profiles.audio;

/**
 * .
 * User: Ostap
 * Date: 5/1/14
 * Time: 10:32 AM
 */
public interface IAudio {

    public void volumeUp();

    public void volumeDown();

    public void mute();

    public void unmute();

    public void changeVolume(int value);

}
