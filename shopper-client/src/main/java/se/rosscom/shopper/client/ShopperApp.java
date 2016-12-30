package se.rosscom.shopper.client;
import se.rosscom.shopper.client.login.LoginView;
import com.airhacks.afterburner.injection.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opendolphin.core.client.ClientDolphin;

/**
 * ShopperApp Application. This class handles navigation and user session.
 */
public class ShopperApp extends Application {
    
    public static ClientDolphin clientDolphin;
    private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 300.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(ShopperApp.class, (java.lang.String[])null);
    }
    
    @Override
    public void start(Stage stage) throws Exception {

        LoginView appView = new LoginView();
        Scene scene = new Scene(appView.getView());
        stage.setTitle("Ross shopper");
        final String uri = getClass().getResource("shopperApp.css").toExternalForm();
        scene.getStylesheets().add(uri);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

}
