/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remotify.client;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entities.Computer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import messages.computer.ConnectClientMessage;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import remotify.client.dependable.Autostarter;
import remotify.client.ws.Client;
import utils.ImageUtils;
import utils.Network;
import utils.OS;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Ostap
 */
public class RemotifyClient extends Application {

    private static Logger l = Logger.getLogger(RemotifyClient.class);

    private static final String CONFIG_SHOW_ON_START_UP = "showOnStartUp";

    private static final int ICON_SIZE = 16;
    private static final String TITLE = "Remotify";
    private static final int WIDTH = 400;
    private static final int HEIGHT = 475;
    private Resources resources;

    private AtomicBoolean connected = new AtomicBoolean(false);
    private volatile boolean closing = false;

    private ConnectClientMessage resp;
    private Thread connectionThread;
    private Computer computer;

//    private static final String HOST = "ws://107.170.154.103:8080/ws";
    private static final String HOST = "ws://api.remotify.me/ws";
//        private static final String HOST = "ws://localhost:8081/ws";

    private TrayIcon trayIcon;
    protected Client client;
    protected Stage primaryStage;
    private BorderPane mainGroup;
    private Properties config;


    private String getResource(String path) {
        return resources.getPath(path);
    }

    @Override
    public void start(final Stage primaryStage) {
        this.primaryStage = primaryStage;

        configure();
        createTray();
        initGUI();
        connect();
    }

    private void configure() {
        BasicConfigurator.configure();
        Resources.INSTANCE.init();
        resources = Resources.INSTANCE;

        config = loadConfig();
        computer = initComputer(config);
        client = new Client(HOST, computer);

        Controller.INSTANCE.init(this);
        Controller.INSTANCE.addConnectionListener(new OnConnectionListener());

        Autostarter autostarter = new Autostarter();
        autostarter.init(OS.NAME);
        autostarter.autostart();
    }

    private void initGUI(){
        mainGroup = new BorderPane();

        ProgressIndicator pb = new ProgressIndicator();
        pb.setProgress(-1.0);
        pb.setMaxWidth(50);
        pb.setMaxHeight(50);
        mainGroup.setCenter(pb);


        HBox showOnStartUpBox = new HBox();
        showOnStartUpBox.setPadding(new Insets(0,0,20,20));
        CheckBox showOnStartUpCheckBox = new CheckBox("Show this windows on start up");
        showOnStartUpCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                config.setProperty(CONFIG_SHOW_ON_START_UP,aBoolean2.toString());
                saveConfig(config);
            }
        });

        boolean showOnStartUp = Boolean.parseBoolean(config.getProperty(CONFIG_SHOW_ON_START_UP, "true"));
        showOnStartUpCheckBox.setSelected(showOnStartUp);
        showOnStartUpBox.getChildren().add(showOnStartUpCheckBox);
        mainGroup.setBottom(showOnStartUpBox);

        Scene scene = new Scene(mainGroup);
        Platform.setImplicitExit(false);
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(new File(getResource(resources.ICON_PATH)).toURI().toString()));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                hide();
            }
        });

        if (showOnStartUp)
            primaryStage.show();
    }

    private Properties loadConfig() {
        Properties properties = new Properties();
        File file = Resources.INSTANCE.getFile(resources.CONFIG_PATH);
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file)) {
                properties.load(in);
            } catch (IOException ex) {
                l.error("Error while loading config", ex);
            }
        }
        return properties;
    }

    private void saveConfig(Properties config) {
        File file = Resources.INSTANCE.getFile(Resources.CONFIG_PATH);
        try (FileOutputStream out = new FileOutputStream(file)) {
            config.store(out, "");
        } catch (Exception ex) {
            l.error("Error while saving config", ex);
        }
    }

    protected void connect() {
        if (connectionThread != null && connectionThread.isAlive()){
            connectionThread.interrupt();
            connectionThread = null;
        }
        if (closing)
            return;
        connectionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                l.info("Connection thread started");
                boolean connected = false;
                while (!connected) {

                    boolean started = false;
                    while (!started) {
                        started = client.start();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                        }
                        l.info("Client started - " + started);
                    }

                    while (true) {
                        try {
                            resp = client.connect();
                            if (resp == null)
                                continue;
                            break;
                        } catch (IOException ex) {
                            l.error("Can not connect to remote server", ex);
                            continue;
                        }
                    }
                    l.info("Client connected");
                    computer = resp.getComputer();
                    config.setProperty("connectionKey",computer.getConnectionKey());
                    saveConfig(config);

                    try {
                        Image image = createBarCode(computer.getConnectionKey());
                        final ImageView qrCode = new ImageView(image);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mainGroup.setCenter(qrCode);
                            }
                        });
                    } catch (WriterException | IOException ex) {
                        l.error("", ex);
                        client.stop();
                        continue;
                    }
                    connected = true;
                    l.info("Connection thread finished");
                }
            }
        });
        connectionThread.start();
    }

    private Image createBarCode(String data) throws WriterException, IOException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 400, 400);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", out);
        Image image = new Image(new ByteArrayInputStream(out.toByteArray()));
        return image;
    }

    private Computer initComputer(Properties config) {
        Computer computer = new Computer();
        computer.setIp(Network.IP);
        computer.setName(OS.USER_NAME);
        computer.setConnectionKey(config.getProperty("connectionKey"));
        computer.setOs(OS.NAME.toString());
        return computer;
    }

    public class OnConnectionListener implements Controller.ConnectionListener {

        @Override
        public void connected(Object channel) {
            connected.getAndSet(true);
        }

        @Override
        public void disconnected() {
            connected.getAndSet(false);
            connect();
        }

    }

    private void createTray() {
        if (SystemTray.isSupported()) {

            try {
                SystemTray tray = SystemTray.getSystemTray();
                PopupMenu popup = new PopupMenu();
                BufferedImage image = ImageUtils.resize(ImageIO.read(new FileInputStream(getResource(resources.ICON_PATH))), ICON_SIZE, ICON_SIZE);

                trayIcon = new TrayIcon(image, TITLE, popup);
                ActionListener listenerTray = new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent arg0) {
                        show();
                    }
                };
                trayIcon.addActionListener(listenerTray);

                MenuItem exitItem = new MenuItem("Exit");
                popup.add(exitItem);
                exitItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent arg0) {
                        closing = true;
                        try {
                            client.disconnect();
                        } catch (IOException ex) {
                            l.error("Can not disconnect properly", ex);
                        }
                        if (trayIcon != null) {
                            SystemTray.getSystemTray().remove(trayIcon);
                        }
                        exit();
                    }
                });

                MenuItem openItem = new MenuItem("Open");
                popup.add(openItem);
                openItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        show();
                    }
                });

                popup.add(openItem);
                popup.add(exitItem);

                try {
                    tray.add(trayIcon);
                } catch (Exception ex) {
                    l.error("Can't add to tray", ex);
                }
            } catch (IOException ex) {
                l.error("", ex);
            }
        }
    }

    protected void hide(){
        Platform.runLater(new Runnable() {
            public void run() {
                primaryStage.hide();
            }
        });
    }

    protected void show(){
        Platform.runLater(new Runnable() {
            public void run() {
                primaryStage.show();
            }
        });
    }

    protected void exit(){
        Platform.runLater(new Runnable() {
            public void run() {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        launch(args);
    }
}
