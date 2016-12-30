
package se.rosscom.shopper.client.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;

/**
 * Login Controller.
 */
public class LoginController extends AnchorPane implements Initializable {

    @FXML
    TextFieldExtended userId;
    @FXML
    PasswordFieldExtended password;
    @FXML
    Button login;
    @FXML
    Label errorMessage;
    
    private Account loggedUser;
    @Inject
    private Authenticator authenticator;
       
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        userId.setPromptText("Användare");
        password.setPromptText("Lösenord");
        authenticator.initClient();
        
    }
    
    
    public void processLogin(ActionEvent event) {
        if (!userLogging(userId.getText(), password.getText())){
            errorMessage.setText("Username/Password is incorrect");
        }
    }
    
    public boolean userLogging(String userId, String password){
        if (authenticator.validate(userId, password)) {
            loggedUser = Account.of(userId);
            errorMessage.setText("");
//            profileScene();
            return true;
        } else {
            return false;
        }
    }

}
