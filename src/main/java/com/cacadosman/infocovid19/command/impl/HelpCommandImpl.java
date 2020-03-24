package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.HelpCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class HelpCommandImpl implements HelpCommand {
    @Override
    public void execute(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Daftar perintah Bot Covid-19");
        eb.setColor(Color.red);
        eb.addField("/covid info global", "Untuk mengetahui statistik global", false);
        eb.addField("/covid info <negara>", "Untuk mengetahui statistik global", false);
        eb.addField("/covid subscribe", "Untuk melakukan subscription agar mendapat " +
                    "push notification pada channel discord saat ada perubahan data", false);
        eb.addField("/covid unsubscribe", "Untuk membatalkan subscription", false);
        eb.addField("/covid help", "untuk melihat daftar perintah", false);
        eb.addField("/covid provinsi", "Untuk mengetahui statistik semua provinsi", false);
        eb.addField("/covid provinsi <nama provinsi>",
                "Untuk mengetahui statistik di provinsi tertentu", false);
        eb.addField("/covid pencegahan", "Informasi mengenai pencegahan Covid-19", false);
        eb.addBlankField(false);
        eb.addField(":heart: Made by love",
                "#DiamDirumahCuk\n" + "#SocialDistancing\n" + "#StaySafeIndonesia",
                false);

        event.getChannel().sendMessage(eb.build()).queue();
    }
}
