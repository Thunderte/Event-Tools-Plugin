# Event-Tools-Plugin
 Event Tools is a plugin, developed for the community!
 
 ## How to use
 
 - :eha without typing the name of the event, the name of the room comes out. If you want a custom event name, just type :eha (event name).
  
After you run the :eha command it opens the room and after 7 seconds it locks. You can edit the time in the emulator_config table and in the `commands.cmd_eha.timestamp` key.

## How to install 
Download the compiled version [Compiled](https://github.com/Thunderte/Event-Tools-Plugin/releases/tag/1.0)

Paste the Event-Tools-1.0-jar-with-dependencies.jar file in your emulator's plugins folder!

Go to your database and in permissions give the permission in cmd_eha for each rank: 0 - deny, 1 - allow!

Restart your hotel emulator or type :update_permissions.


## Configuration

 Permissions:

| Key                  | Default Value |
|----------------------|---------------|
| cmd_eha              | 0             |


## Configuration Discord Webhook

![print](https://i.ibb.co/44g0wJR/eha.png)


Open the emulator_settings table in your database and edit the `eha_command.discord` key to 1

Go to the discord channel, Click the gear icon (Edit Channel) of the channel you want to post to. 

Click Webhooks in the left menu. 

Click the Create Webhook button. 

Enter a Name of your choice. 

Click the Copy button of the Webhook URL. 

Click the Save button. 

Paste the Webhook URL into emulator_settings, key `eha_command.discord-webhook-url`


## Contact
To contact me use discord: Thunderte#1527

