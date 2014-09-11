/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotify.client;

import javafx.application.Platform;
import remotify.client.profiles.audio.Audio;
import utils.OS;
import utils.platformdepended.Dependable;
import utils.platformdepended.Hardware;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * @author Ostap
 */
public enum Controller {
    INSTANCE;

    private RemotifyClient remotifyClient;
    private List<ConnectionListener> onConnectionEstablished = new ArrayList();
    private Audio audio;
    private Hardware hardware;

    public Audio getAudio() {
        return audio;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public static interface ConnectionListener extends EventListener {
        public void connected(Object channel);

        public void disconnected();
    }

    public void addConnectionListener(ConnectionListener listener) {
        onConnectionEstablished.add(listener);
    }

    public void removeConnectionListener(ConnectionListener listener) {
        onConnectionEstablished.remove(listener);
    }

    public void init(RemotifyClient remotifyClient) {
        this.remotifyClient = remotifyClient;

        List<Dependable> dependables = new ArrayList<Dependable>();
        audio = new Audio();
        hardware = new Hardware();
        dependables.add(audio);
        dependables.add(hardware);
        initDependables(dependables);
    }

    private final void initDependables(List<Dependable> dependables) {
        for (int i = 0; i < dependables.size(); i++) {
            Dependable dependable = dependables.get(i);
            dependable.init(OS.NAME);
        }
    }


    public synchronized void hide() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                remotifyClient.primaryStage.hide();
            }
        });
    }

    public synchronized boolean exceptionCaught(Throwable t) {
        if (t instanceof ConnectException) {
            connectionFinifhed();
            return true;
        }
        return false;
    }

    public synchronized void connectionEstablished(Object channel) {
        for (ConnectionListener eventListener : onConnectionEstablished) {
            eventListener.connected(channel);
        }
    }

    public synchronized void connectionFinifhed() {
        for (ConnectionListener eventListener : onConnectionEstablished) {
            eventListener.disconnected();
        }
    }

}
