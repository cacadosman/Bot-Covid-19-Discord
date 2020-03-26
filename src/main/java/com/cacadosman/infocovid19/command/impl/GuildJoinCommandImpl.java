package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.GuildJoinCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class GuildJoinCommandImpl implements GuildJoinCommand {
    @Override
    public void execute(GuildJoinEvent event) {
        sendHelpMessage(event);
    }

    private void sendHelpMessage(GuildJoinEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.CYAN);
        eb.setTitle("Bot Info Covid-19");
        eb.setDescription("Halo, saya adalah Bot Info Covid 19.\n" +
                "Saya di sini bertugas untuk memberikan informasi mengenai Covid-19.\n" +
                "Teman-teman dapat menginputkan perintah berikut:\n");
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
        eb.addField("/covid prediksi", "Prediksi total kasus Covid-19 di Indonesia lima hari kedepan", false);
        eb.addBlankField(false);
        eb.addField("Kontak:", "- FB: cacadosman23\n" + "- Discord: cacadosman#3356\n\n" +
                ":heart: Terima Kasih.", false);

        event.getGuild().getDefaultChannel().sendMessage(eb.build()).queue();
    }
}
