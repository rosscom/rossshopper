/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.rosscom.shopper.business.list.boundary;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Startup
@Singleton
@ServerEndpoint("/items")
public class ListItemEndpoint {
    
    private Session session;
    
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void messageArrived(String message) {
        try {
            this.session.getBasicRemote().sendText("echo from shopperserver " + message);
        } catch (IOException ex) {
            Logger.getLogger(ListItemEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public void sendMessage(String message) {
        System.out.println("--- shopper -" + message + " "+ new Date() );
        if (this.session != null) {
            try {
                this.session.getBasicRemote().sendText("adding listItem " + message);
            } catch (IOException ex) {
                Logger.getLogger(ListItemEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
