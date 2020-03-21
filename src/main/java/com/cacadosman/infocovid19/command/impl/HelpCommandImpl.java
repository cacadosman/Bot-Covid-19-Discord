package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.HelpCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

@Service
public class HelpCommandImpl implements HelpCommand {
    @Override
    public void execute(MessageReceivedEvent event) {
        String messages = "Daftar perintah Bot Covid-19:\n";
        messages += "- /covid info global (untuk mengetahui statistik global)\n";
        messages += "- /covid info <negara> (untuk mengetahui statistik di negara tertentu)\n";
        messages += "- /covid subscribe (untuk melakukan subscription agar mendapat " +
                    "push notification pada channel discord saat ada perubahan data)\n";
        messages += "- /covid unsubscribe (untuk membatalkan subscription)\n";
        messages += "- /covid help (untuk melihat daftar perintah)\n";
        messages += "- /covid provinsi (untuk mengetahui statistik semua provinsi)\n";
        messages += "- /covid provinsi <nama provinsi> (Untuk mengetahui statistik di provinsi tertentu)";

        event.getChannel().sendMessage(messages).queue();
    }
}
