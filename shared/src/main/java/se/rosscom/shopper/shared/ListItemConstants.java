package se.rosscom.shopper.shared;

 /**
 * Place for shared information among client and server. Typically identifiers for models, attributes and actions.
 */
public class ListItemConstants {
    
    public static final String PM_SHOPPER = unique("shopper");
    public static final String CMD_ADD_ITEM = unique("AddItem");
    public static final String ATT_ITEM = "item";


    /**
     * Unify the identifier with the class name prefix.
     */
    private static String unique(String key) {
        return ListItemConstants.class.getName() + "." + key;
    }
}
