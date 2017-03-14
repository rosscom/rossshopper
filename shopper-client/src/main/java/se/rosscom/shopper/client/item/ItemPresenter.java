
package se.rosscom.shopper.client.item;

import se.rosscom.shopper.client.login.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Login Controller.
 */
public class ItemPresenter extends AnchorPane implements Initializable {

    
    @FXML
    Label userid;

    private Account loggedUser;    
    private Client clientPing;
    private WebTarget pingTarget;


       
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userid.setText("");
        
    }

    public Account getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Account loggedUser) {
        this.loggedUser = loggedUser;
    }
    
    
    
    
    
}
