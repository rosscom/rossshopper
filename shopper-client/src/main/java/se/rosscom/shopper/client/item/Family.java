
package se.rosscom.shopper.client.item;

import java.util.List;

public class Family {

    private Family(String id) {
        this.id = id;
    }
    private String id;

    public String getId() {
        return id;
    }
    private List<String> item;

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }
    
    
 
}
