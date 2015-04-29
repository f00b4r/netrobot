package cz.playfx.netrobot.gui;

import cz.playfx.netrobot.net.Client;
import cz.playfx.netrobot.net.Server;
import cz.playfx.netrobot.utils.Sounds;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author Felix
 */
public class ClientControl implements Initializable {

    // Vars
    private Client client;
    private Server server;

    // FXML
    @FXML
    private AnchorPane root;
    @FXML
    private TextField destIp;
    @FXML
    private TextField destPort;
    @FXML
    private TextField srcIp;
    @FXML
    private TextField srcPort;
    @FXML
    private TextArea message;
    @FXML
    private TextArea communication;
    @FXML
    private ToggleButton listenButton;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Toggling
        listenButton.selectedProperty().addListener((ob, ov, nv) -> {
            if (listenButton.isSelected()) {
                listenButton.setText("Listening...");
            } else {
                listenButton.setText("Listen");
            }
        });

        // Message key-bindings
        message.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> {
            // Shortcut for sending message
            if (e.getCode() == KeyCode.ENTER) {
                if (e.isShiftDown()) {
                    message.appendText("\n");
                } else {
                    actionSend();
                }
                e.consume();
            }
        });
    }
    
    public void init(Scene scene) {
    }
    
    public void init(Stage stage) {
        stage.setOnCloseRequest((e) -> {
            // Disconnect server on close
            if (server != null) {
                server.stop();
            }
        });
    }

    /**
     * *************************************************************************
     * ACTIONS *****************************************************************
     * *************************************************************************
     */
    @FXML
    private void actionListen() {
        // Stop current running server
        if (!listenButton.isSelected() && server != null) {
            server.stop();
            return;
        }
        
        try {
            // Create new server
            server = new Server(srcIp.getText(), Integer.valueOf(srcPort.getText()));
            
            server.onConnect(() -> {
                // Play sound
                Platform.runLater(() -> {
                    Sounds.connectServer();
                });
            });
            
            server.onRecieve(packet -> {
                // Append message
                communication.appendText("Recieved: " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " = " + new String(packet.getData()) + "\n");

                // Play sound
                Sounds.recieveMessage();
            });
            
            server.onDisconnect(() -> {
                // Play sound
                Platform.runLater(() -> {
                    Sounds.disconnectServer();
                });
            });

            // Listen
            server.listen();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void actionSend() {
        try {
            // Create new client
            client = new Client(destIp.getText(), Integer.valueOf(destPort.getText()));

            // Send message
            client.send(message.getText());

            // Clear textare
            message.clear();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
