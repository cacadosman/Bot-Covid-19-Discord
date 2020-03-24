package com.cacadosman.infocovid19.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface PreventionCommand {
    void execute(MessageReceivedEvent event);
}
