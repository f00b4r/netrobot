package cz.playfx.netrobot.net;

import java.net.DatagramPacket;

/**
 *
 * @author Felix
 */
public interface ServerRecieve extends ServerListener {

    public void recieve(DatagramPacket packet);

}
