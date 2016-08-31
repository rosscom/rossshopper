package se.rosscom.shopper.business;


import org.opendolphin.core.server.DefaultServerDolphin;
import org.opendolphin.server.adapter.DolphinServlet;
import se.rosscom.shopper.business.list.boundary.ListItemDirector;

/**
 * For real server mode, this servlet acts as entry point for all communication.
 */
public class ApplicationServlet extends DolphinServlet{
    @Override
    protected void registerApplicationActions(DefaultServerDolphin serverDolphin) {
        serverDolphin.register(new ListItemDirector());
    }
}

