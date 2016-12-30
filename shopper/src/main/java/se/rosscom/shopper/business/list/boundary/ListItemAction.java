package se.rosscom.shopper.business.list.boundary;
import org.opendolphin.core.comm.Command;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;
import org.opendolphin.core.server.comm.CommandHandler;

import java.util.List;
import se.rosscom.shopper.business.ILogService;
import se.rosscom.shopper.shared.ListItemConstants;


public class ListItemAction extends DolphinServerAction{
    final ILogService service;
    public ListItemAction(ILogService service) {
        this.service = service;
    }
    @Override
    public void registerIn(ActionRegistry actionRegistry) {
        actionRegistry.register(ListItemConstants.CMD_ADD_ITEM, new CommandHandler<Command>() {
            @Override
            public void handleCommand(Command command, List<Command> response) {
                System.out.println("Server reached.");
                service.log(getServerDolphin().getAt(ListItemConstants.PM_SHOPPER).getAt(ListItemConstants.ATT_ITEM).getValue());
                service.log(getServerDolphin().getAt(ListItemConstants.PM_SHOPPER).getAt(ListItemConstants.ATT_ITEM).getQualifier());
            }
        });
    }
}