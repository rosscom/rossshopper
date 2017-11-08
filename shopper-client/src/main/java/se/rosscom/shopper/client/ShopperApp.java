package se.rosscom.shopper.client;
import se.rosscom.shopper.client.login.LoginView;
import com.airhacks.afterburner.injection.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * ShopperApp Application. This class handles navigation and user session.
 */
public class ShopperApp extends Application {
    
    private Stage stage;
    private Scene sceneLogin;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(ShopperApp.class, (java.lang.String[])null);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        stage = primaryStage;
        LoginView appView = new LoginView();
        sceneLogin = new Scene(appView.getView());

        stage.setTitle("Ross shopper");
        final String uri = getClass().getResource("shopper.css").toExternalForm();
        sceneLogin.getStylesheets().add(uri);
        stage.setScene(sceneLogin);
        stage.show();

    }
    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }
}
