package app.commands.normaluser;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GetNotifications extends Command {
    @Override
    public String toString() {
        return super.toString() + "GetNotifications{}";
    }

    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
//        System.out.println(this.toString());
        ObjectNode out = outputs.addObject();

        printCommandInfo(out, Output.GET_NOTIFICATIONS);

        NormalUser user = library.getUserWithUsername(getUsername());

        ArrayNode notificationsNode = out.putArray(Output.NOTIFICATIONS);
        for (Notification notification : user.getNotifications()) {
            ObjectNode notificationNode = notificationsNode.addObject();
            notificationNode.put(Output.NAME, notification.getName());
            notificationNode.put(Output.DESCRIPTION, notification.getDescription());
        }

        user.clearNotifications();
    }
}
