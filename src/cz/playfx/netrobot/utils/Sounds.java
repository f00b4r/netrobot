package cz.playfx.netrobot.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Felix
 */
public class Sounds {

    public static void recieveMessage() {
        Media media = new Media(Sounds.class.getClass().getResource("/cz/playfx/netrobot/gui/resources/message.mp3").toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
    }

    public static void connectServer() {
        Media media = new Media(Sounds.class.getClass().getResource("/cz/playfx/netrobot/gui/resources/connect.mp3").toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
    }

    public static void disconnectServer() {
        Media media = new Media(Sounds.class.getClass().getResource("/cz/playfx/netrobot/gui/resources/disconnect.mp3").toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
    }
}
