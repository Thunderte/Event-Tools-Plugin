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


        //Register sex command
        Emulator.getTexts().register("commands.keys.cmd_eha", "eha");
        Emulator.getTexts().register("commands.cmd_eha.open_room", "Room opened with successfully!");
        Emulator.getTexts().register("commands.cmd_eha.lock_room", "Room locked with successfully!");
        Emulator.getTexts().register("commands.cmd_eha.timestamp", String.valueOf(7000));



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
