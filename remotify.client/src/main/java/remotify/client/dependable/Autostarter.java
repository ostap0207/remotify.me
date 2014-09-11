package remotify.client.dependable;

import remotify.client.dependable.windows.Autostarter4Win;
import utils.OS;
import utils.platformdepended.Dependable;

/**
 * .
 * User: Ostap
 * Date: 5/2/14
 * Time: 11:03 PM
 */
public class Autostarter implements IAutostarter, Dependable {

    IAutostarter instance;

    @Override
    public void init(OS.Name name) {
        instance = new Autostarter4Win();
    }

    @Override
    public void autostart() {
        instance.autostart();
    }
}
