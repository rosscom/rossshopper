package se.rosscom.shopper.business.list.boundary;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;
import se.rosscom.shopper.business.ILogService;
import se.rosscom.shopper.business.LogServiceImpl;

public class ListItemDirector extends DolphinServerAction {
    private ILogService logService;

    public ListItemDirector(ILogService logService) {
        this.logService = logService;
    }
   public ListItemDirector() {

        this(new LogServiceImpl());
    }
   public void registerIn(ActionRegistry registry) {
        // register all your actions here.
        // make sure account has logged in and choosed a home
        getServerDolphin().register(new ListItemAction(logService));
    }
}
