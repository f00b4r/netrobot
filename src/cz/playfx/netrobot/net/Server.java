package cz.playfx.netrobot.net;

import cz.playfx.netrobot.data.Config;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Felix
 */
public class Server {

    // Vars - static
    private static int packets = 0;

    // Vars - final
    private final InetAddress address;
    private final int port;
    private final EventListenerList listeners;

    // Vars
    private boolean running;
    private Thread thread;

    public Server(String ip, Integer port) throws UnknownHostException {
        this.address = InetAddress.getByName(ip);
        this.port = port;
        this.listeners = new EventListenerList();
    }

    public void stop() {
        running = false;
    }

    public void listen() {
        running = true;
        thread = new ServerThread();
        thread.setDaemon(true);
        thread.start();
    }

    public void onRecieve(ServerRecieve listener) {
        listeners.add(ServerRecieve.class, listener);
    }

    private void doOnRecieve(DatagramPacket packet) {
        Arrays.asList(listeners.getListeners(ServerRecieve.class)).forEach(l -> l.recieve(packet));
    }

    public void onError(ServerError listener) {
        listeners.add(ServerError.class, listener);
    }

    private void doOnError(IOException ex) {
        Arrays.asList(listeners.getListeners(ServerError.class)).forEach(l -> l.error(ex));
    }

    public void onConnect(ServerConnect listener) {
        listeners.add(ServerConnect.class, listener);
    }

    private void doOnConnect() {
        Arrays.asList(listeners.getListeners(ServerConnect.class)).forEach(l -> l.connect());
    }

    public void onDisconnect(ServerDisconnect listener) {
        listeners.add(ServerDisconnect.class, listener);
    }

    private void doOnDisconnect() {
        Arrays.asList(listeners.getListeners(ServerDisconnect.class)).forEach(l -> l.disconnect());
    }

    private class ServerThread extends Thread {

        @Override
        public void run() {
            try {
                // Construct the socket
                DatagramSocket socket = new DatagramSocket(port);
                socket.setSoTimeout(1000);

                // Create a packet
                DatagramPacket packet;
                System.out.println("Server: ready to listen...");

                // Trigger events
                doOnConnect();

                while (running) {
                    packet = new DatagramPacket(new byte[Config.PACKET_SIZE], Config.PACKET_SIZE);
                    try {
                        // Receive a packet (blocking)
                        socket.receive(packet);
                    } catch (SocketTimeoutException ex) {
                        continue;
                    }

                    // Only if it real recieved packet, not just timeout
                    System.out.println("Server: recieved packet #" + (packets++));

                    // Trigger events
                    doOnRecieve(packet);
                }

                System.out.println("Server: closing...");

                // Close server
                socket.close();

                // Trigger events
                doOnDisconnect();
            } catch (IOException ex) {
                System.err.println("Server: error = " + ex.getMessage());

                // Trigger events
                doOnError(ex);
            }
        }

    }

}
