package commands.player;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import entities.Library;
import entities.UserPlayer;
import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repeat extends Command {
    @Override
    public String toString() {
        return super.toString() +
                "Repeat{" +
                '}';
    }

    @Override
    public void execute(ArrayNode outputs, Library lib) {
        ObjectNode out = outputs.addObject();

        out.put("command", "repeat");
        out.put("user", getUsername());
        out.put("timestamp", getTimestamp());

        UserPlayer userPlayer = lib.getUserWithUsername(getUsername()).getPlayer();

        if (!userPlayer.playingIndexIsValid()) {
            out.put("message", "Please load a source before setting the repeat status.");
        } else {
            switch (userPlayer.changeRepeatState()) {
                case NO_REPEAT ->
                    out.put("message", "Repeat mode changed to no repeat.");
                case REPEAT_ONCE ->
                    out.put("message", "Repeat mode changed to repeat once.");
                case REPEAT_ALL ->
                        out.put("message", "Repeat mode changed to repeat all.");
                case REPEAT_CURRENT_SONG ->
                        out.put("message", "Repeat mode changed to repeat current song.");
                case REPEAT_INFINITE -> out.put("message", "Repeat mode changed to repeat infinite.");
            }
        }
    }
}
