package cz.playfx.netrobot.net;

import java.io.IOException;

/**
 *
 * @author Felix
 */
public interface ServerError extends ServerListener {

    public void error(IOException ex);
}
