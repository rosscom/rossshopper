
package se.rosscom.shopper.client.login;

import java.util.HashMap;
import java.util.Map;

public class Account {

    private static final Map<String, Account> USERS = new HashMap<String, Account>();

    public static Account of(String id) {
        Account user = USERS.get(id);
        if (user == null) {
            user = new Account(id);
            USERS.put(id, user);
        }
        return user;
    }

    private Account(String id) {
        this.id = id;
    }
    private String id;

    public String getId() {
        return id;
    }
    private String email = "";
    private boolean subscribed;

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

 
    /**
     * @return the subscribed
     */
    public boolean isSubscribed() {
        return subscribed;
    }

    /**
     * @param subscribed the subscribed to set
     */
    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}
