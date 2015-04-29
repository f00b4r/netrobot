package cz.playfx.netrobot.net;

import cz.playfx.netrobot.gui.ClientControl;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felix
 */
public class Client {

    // Vars - final
    private final InetAddress address;
    private final int port;

    public Client(String ip, Integer port) throws UnknownHostException {
        this.address = InetAddress.getByName(ip);
        this.port = port;
    }

    public void send(String message) {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
