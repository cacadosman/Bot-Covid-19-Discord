package com.cacadosman.infocovid19.command;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;

public interface GuildJoinCommand {
    void execute(GuildJoinEvent event);
}
