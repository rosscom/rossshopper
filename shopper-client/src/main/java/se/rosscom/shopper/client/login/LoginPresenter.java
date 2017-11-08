
package se.rosscom.shopper.client.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.inject.Inject;
import se.rosscom.shopper.client.PingServer;
import se.rosscom.shopper.client.item.ItemView;

/**
 * Login Controller.
 */
public class LoginPresenter extends AnchorPane implements Initializable {

   
    @FXML
    TextFieldExtended userId;
    @FXML
    PasswordFieldExtended password;
    @FXML
    Hyperlink loginLink ;    
    @FXML
    Label errorMessage;
        
    
    
    private Account loggedUser;
    @Inject
    private Authenticator authenticator;
    
    @Inject 
    private PingServer serverConnection;
    
    Stage stage; 
    Parent root;
    private Scene sceneItem;   
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        errorMessage.setText("");
        userId.setPromptText("Användare");
        password.setPromptText("Lösenord");
        if (serverConnection.connectToServer()) {
            authenticator.initClient();
        } else {
            errorMessage.setText("no connection");
        }
    }
    
    
    public void processLogin(ActionEvent event) {
        if (!userLogging(userId.getText(), password.getText())){
            errorMessage.setText("Username/Password is incorrect");
        }
    }
    
    public boolean userLogging(String userId, String password){
        if (authenticator.validate(userId, password)) {
            loggedUser = Account.of(userId);
            errorMessage.setText(loggedUser.getId());
            
            itemScene();
            return true;
        } else {
            return false;
        }
    }
    
    private void itemScene() {
        stage = (Stage) loginLink.getScene().getWindow();
        ItemView itemView = new ItemView();
        sceneItem = new Scene(itemView.getView());
        stage.setScene(sceneItem);
        stage.show();
    }

}
