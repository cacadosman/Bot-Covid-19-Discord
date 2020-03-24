package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.PreventionCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
public class PreventionCommandImpl implements PreventionCommand {

    private final String URL = "https://simpan.ugm.ac.id/s/psR4V5dtC0HPlrS/download";
    private final String FILENAME = "pencegahan.mp4";

    private final String POSTER_PREVENTION_URL =    "https://www.unicef.org/indonesia/sites/unicef.org.indonesia/files" +
                                                    "/styles/media_large_image/public/WhatsApp%20Image%202020-03-02%20" +
                                                    "at%2018.07.17_3.jpeg?itok=hOZfqY3Q";

    @Override
    public void execute(MessageReceivedEvent event) {
        try {
            event.getChannel().sendMessage("Mohon tunggu, sedang mengupload video.").queue();
            File video = getFile();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.PINK);
            eb.setTitle("Pencegahan Covid-19");
            eb.setImage(POSTER_PREVENTION_URL);
            eb.setFooter("UNICEF/2020");
            event.getChannel().sendMessage(eb.build()).addFile(video).queue();
        } catch (IOException e) {
            event.getChannel().sendMessage("Terjadi sebuah kesalahan.").queue();
        }
    }

    public File getFile() throws IOException {
        File video = new File(FILENAME);
        if (!video.exists()) {
            FileUtils.copyURLToFile(
                    new URL(URL),
                    video
            );
        }
        return video;
    }
}
