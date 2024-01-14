package app.commands.normaluser;

import app.commands.Command;
import app.common.Output;
import app.entities.Library;
import app.entities.userside.normaluser.NormalUser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GetNotifications extends Command {
    /**
     * Returns a string representation of this GetNotifications object, including the superclass
     * string representation.
     *
     * @return A string representation of this GetNotifications object.
     */
    @Override
    public String toString() {
        return super.toString() + "GetNotifications{}";
    }

    /**
     * Executes the GetNotifications command, retrieving and clearing notifications for the user.
     * This method retrieves the user's notifications, adds them to the output, and
     * clears the notifications.
     *
     * @param outputs  The ArrayNode where the output will be added.
     * @param library  The Library object containing user data.
     * @param offline  A boolean indicating whether the user is offline.
     */
    @Override
    public void execute(final ArrayNode outputs, final Library library, final boolean offline) {
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
