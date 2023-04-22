package com.plugin.thunder.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomState;
public class CloseRoomCommand extends Command{

    public CloseRoomCommand(String permission, String[] keys) {
        super(permission, keys);
    }
    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() == null)
            return false;

        Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();

        gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.cmd_eha.lock_room"));
        room.setState(RoomState.LOCKED);

        return true;
    }
}
