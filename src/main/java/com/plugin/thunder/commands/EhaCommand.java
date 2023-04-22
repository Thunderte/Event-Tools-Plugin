package com.plugin.thunder.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomState;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import gnu.trove.map.hash.THashMap;
import com.plugin.thunder.util.Embed;

import java.util.Arrays;

public class EhaCommand extends Command {

    public EhaCommand(String permission, String[] keys) {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() == null)
            return false;

        HabboInfo habboInfo = gameClient.getHabbo().getHabboInfo();
        String message = String.join(" ", Arrays.copyOfRange(strings, 1, strings.length));
        THashMap<String, String> codes = new THashMap<>();
        codes.put("ROOMNAME", habboInfo.getCurrentRoom().getName());
        codes.put("ROOMID", habboInfo.getCurrentRoom().getId() + "");
        codes.put("USERNAME", habboInfo.getUsername());
        codes.put("LOOK", habboInfo.getLook());
        codes.put("TIME", Emulator.getDate().toString());
        codes.put("MESSAGE", strings.length >= 2 ? message.toString() : habboInfo.getCurrentRoom().getName());

        ServerMessage msg = new BubbleAlertComposer("hotel.event", codes).compose();
        Room room = habboInfo.getCurrentRoom();
        Embed.DiscordEmbed(habboInfo.getCurrentRoom(), gameClient.getHabbo());
        if(Emulator.getConfig().getBoolean("eha_command.automatic_close_room", true)) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.cmd_eha.open_room"));
            room.setState(RoomState.OPEN);
        }
        Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values().stream().filter(
                habbo -> habbo != null && !habbo.getHabboStats().blockStaffAlerts)
                .forEach(habbo -> habbo.getClient().sendResponse(msg));

        Emulator.getThreading().run(() -> {
            if(Emulator.getConfig().getBoolean("eha_command.automatic_close_room", true)) {
                room.setState(RoomState.LOCKED);
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.cmd_eha.lock_room"));
            }
        }, Long.parseLong(Emulator.getConfig().getValue("commands.cmd_eha.timestamp")));

        return true;
    }
}
