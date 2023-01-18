package com.plugin.thunder;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadItemsManagerEvent;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.plugin.thunder.commands.EhaCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class main extends HabboPlugin implements EventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(main.class);
    public void onEnable() throws Exception {
        Emulator.getPluginManager().registerEvents(this, this);
        LOGGER.info("[ Event Tools ] Loaded! by: Thunderte#1527");
    }

    public void onDisable() throws Exception {

        LOGGER.error("[ Event Tools ] Shutdown!");
   
    }

    @Override
    public boolean hasPermission(Habbo habbo, String s){
        return false;
    }
    private boolean registerPermission(String name, String options, String defaultValue, boolean defaultReturn)
    {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement("ALTER TABLE  `permissions` ADD  `" + name +"` ENUM(  " + options + " ) NOT NULL DEFAULT  '" + defaultValue + "'"))
            {
                statement.execute();
                return true;
            }
        }
        catch (SQLException e)
        {}

        return defaultReturn;
    }



    @EventHandler
    public void onEmulatorLoaded(EmulatorLoadedEvent event) throws Exception {

        boolean reloadPermissions = false;
        reloadPermissions = this.registerPermission("cmd_eha", "'0', '1'", "1", reloadPermissions);

        if (reloadPermissions)
        {
            Emulator.getGameEnvironment().getPermissionsManager().reload();
        }


        //Register texts
        Emulator.getTexts().register("commands.keys.cmd_eha", "eha");
        Emulator.getTexts().register("commands.cmd_eha.open_room", "Room opened with successfully!");
        Emulator.getTexts().register("commands.cmd_eha.lock_room", "Room locked with successfully!");
        Emulator.getTexts().register("eha_command.webhook-message", "A new event is happening in the %ROOM% room, enter the hotel to play!");
        Emulator.getTexts().register("eha_command.webhook.title", "New Event!");
        Emulator.getTexts().register("eha_command.webhook-footer","The event staff is %STAFF%");
        //Register configs
        Emulator.getConfig().register("commands.cmd_eha.timestamp", String.valueOf(7000));
        Emulator.getConfig().register("eha_command.discord", String.valueOf(1));
        Emulator.getConfig().register("eha_command.discord-webhook-url", "https://discord.com/api/webhooks/916743155821465650/azMTYnVeCtzj6hE6RSONYz6cN_r6HZuxcArCVvb2V1yyD73ghze-OP_F8el-Wz-IVSzm");
        Emulator.getConfig().register("eha_command.webhook-thumbnail", "https://3.bp.blogspot.com/-Vh9RmdNqiBU/XK0oYm7tW9I/AAAAAAABOsg/il6h-uQVdzMOY255ktG9JCdh4cj0gyusgCKgBGAs/s1600/Image%2B363.png");
        Emulator.getConfig().register("eha_command.webhook-hotel-url", "https://habbo.com");

        //Commands
        CommandHandler.addCommand(new EhaCommand("cmd_eha", Emulator.getTexts().getValue("commands.keys.cmd_eha").split(";")));
    }



    @EventHandler
    public void onEmulatorLoadedEvent(final EmulatorLoadedEvent e) {

    }

    @EventHandler
    public void onLoadItemsManager(final EmulatorLoadItemsManagerEvent e) {

    }

}
