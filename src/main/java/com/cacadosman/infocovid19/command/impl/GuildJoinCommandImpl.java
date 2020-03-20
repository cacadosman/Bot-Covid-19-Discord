package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.GuildJoinCommand;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import org.springframework.stereotype.Service;

@Service
public class GuildJoinCommandImpl implements GuildJoinCommand {
    @Override
    public void execute(GuildJoinEvent event) {
        sendHelpMessage(event);
    }

    private void sendHelpMessage(GuildJoinEvent event) {
        String messages = "Halo, saya adalah Bot Info Covid 19.\n";
        messages += "Saya di sini bertugas untuk memberikan informasi mengenai Covid-19.\n";
        messages += "Teman-teman dapat menginputkan perintah berikut:\n";
        messages += "- /covid info global\n";
        messages += "- /covid info <negara>\n";
        messages += "Contoh: /covid info indonesia\n\n";
        messages += "Kontak:\n";
        messages += "- FB: cacadosman23\n";
        messages += "- Discord: cacadosman#3356\n\n";
        messages += "Terima kasih.";

        event.getGuild().getDefaultChannel().sendMessage(messages).queue();
    }
}
