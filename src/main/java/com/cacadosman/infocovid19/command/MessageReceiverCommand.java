package com.cacadosman.infocovid19.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface MessageReceiverCommand {
    void execute(MessageReceivedEvent event);
}
