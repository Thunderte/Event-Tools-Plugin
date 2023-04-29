package com.plugin.thunder.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
public class SetPublicCommand extends Command{

    public SetPublicCommand(String permission, String[] keys) {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() == null)
            return false;

        Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();

        if(room.isPublicRoom()) {
            room.setPublicRoom(false);
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.keys.cmd_set_public.success2"));
        } else {
            room.setPublicRoom(true);
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.keys.cmd_set_public.success"));
        }

        return true;
    }
}
