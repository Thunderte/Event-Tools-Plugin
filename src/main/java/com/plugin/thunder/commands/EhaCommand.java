package com.plugin.thunder.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomState;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import gnu.trove.map.hash.THashMap;

import java.util.Map;

public class EhaCommand extends Command {

    public EhaCommand(String permission, String[] keys) {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() != null) {
                StringBuilder message = new StringBuilder();

                for (int i = 1; i < strings.length; i++) {
                    message.append(strings[i]);
                    message.append(" ");
                }
                THashMap<String, String> codes = new THashMap<>();
                codes.put("ROOMNAME", gameClient.getHabbo().getHabboInfo().getCurrentRoom().getName());
                codes.put("ROOMID", gameClient.getHabbo().getHabboInfo().getCurrentRoom().getId() + "");
                codes.put("USERNAME", gameClient.getHabbo().getHabboInfo().getUsername());
                codes.put("LOOK", gameClient.getHabbo().getHabboInfo().getLook());
                codes.put("TIME", Emulator.getDate().toString());
                if(strings.length >= 2) {
                    codes.put("MESSAGE", message.toString());
                } else {
                    codes.put("MESSAGE", gameClient.getHabbo().getHabboInfo().getCurrentRoom().getName());
                }

                ServerMessage msg = new BubbleAlertComposer("hotel.event", codes).compose();

                for (Map.Entry<Integer, Habbo> set : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
                    Habbo habbo = set.getValue();

                    Habbo habbo2 = gameClient.getHabbo();
                    if (habbo.getHabboStats().blockStaffAlerts)
                        continue;

                    Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();

                    room.setState(RoomState.OPEN);

                    habbo2.whisper(Emulator.getTexts().getValue("commands.cmd_eha.open_room"));

                    habbo.getClient().sendResponse(msg);

                    Emulator.getThreading().run(new Runnable() {
                        @Override
                        public void run() {
                            room.setState(RoomState.LOCKED);
                            habbo2.whisper(Emulator.getTexts().getValue("commands.cmd_eha.lock_room"));
                        }
                    }, Long.parseLong(Emulator.getTexts().getValue("commands.cmd_eha.timestamp")));



                }

                return true;
            }

        return true;
    }
}
