package cz.playfx.netrobot.app;

import cz.playfx.netrobot.gui.ClientControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Felix
 */
public class Application extends javafx.application.Application {

    // Constants
    public static final String VERSION = "v1.0";
    public static final String RELEASED = "29-04-2015";

    @Override
    public void start(Stage stage) throws Exception {
        // Create loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/playfx/netrobot/gui/Client.fxml"));
        ClientControl controller = new ClientControl();
        loader.setController(controller);

        // Create scene
        Scene scene = new Scene((Parent) loader.load());
        controller.init(scene);
        controller.init(stage);

        // Add icons
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/cz/playfx/netrobot/gui/resources/logo-16.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/cz/playfx/netrobot/gui/resources/logo-32.png")));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/cz/playfx/netrobot/gui/resources/logo-64.png")));

        // Configure stage
        stage.setMinWidth(500);
        stage.setTitle("NetRobot " + VERSION + " (" + RELEASED + ")");
        stage.setScene(scene);
        stage.show();
    }

}
