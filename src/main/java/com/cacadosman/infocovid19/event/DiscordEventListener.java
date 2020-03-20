package com.cacadosman.infocovid19.event;

import com.cacadosman.infocovid19.command.GuildJoinCommand;
import com.cacadosman.infocovid19.command.MessageReceiverCommand;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class DiscordEventListener extends ListenerAdapter {

    @Autowired
    MessageReceiverCommand messageReceiverCommand;

    @Autowired
    GuildJoinCommand guildJoinCommand;

    Logger logger = LoggerFactory.getLogger(DiscordEventListener.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        logger.info("onMessageReceived");
        messageReceiverCommand.execute(event);
    }

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        logger.info("onGuildJoin");
        guildJoinCommand.execute(event);
    }
}
