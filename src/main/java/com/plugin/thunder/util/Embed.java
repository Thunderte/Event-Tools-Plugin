package com.plugin.thunder.util;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.AllowedMentions;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;

import static com.eu.habbo.Emulator.getConfig;
import static com.eu.habbo.Emulator.getTexts;


public class Embed {

    public static void DiscordEmbed(Room room, Habbo staff) {
        if (getConfig().getBoolean("eha_command.discord", true)) {
            WebhookClient client = WebhookClient.withUrl(Emulator.getConfig().getValue("eha_command.discord-webhook-url"));
            WebhookMessageBuilder message = new WebhookMessageBuilder()
                    .setAllowedMentions(AllowedMentions.none());
            WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setColor(0x03A9F4)
                    .setAuthor(new WebhookEmbed.EmbedAuthor(getConfig().getValue("hotel.name"), null, getConfig().getValue("eha_command.webhook-hotel-url")))
                    .setTitle(new WebhookEmbed.EmbedTitle(getTexts().getValue("eha_command.webhook.title"), null))
                    .setDescription(getTexts().getValue("eha_command.webhook-message").replace("%ROOM%", room.getName()))
                    .setThumbnailUrl(getConfig().getValue("eha_command.webhook-thumbnail"))
                    .setFooter(new WebhookEmbed.EmbedFooter(getTexts().getValue("eha_command.webhook-footer").replace("%STAFF%", staff.getHabboInfo().getUsername()), null))
                    .build();
            message.addEmbeds(embed);
            client.send(message.build());
        }

    }
}