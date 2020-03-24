package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.PreventionCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
public class PreventionCommandImpl implements PreventionCommand {

    private final String URL = "https://simpan.ugm.ac.id/s/psR4V5dtC0HPlrS/download";
    private final String FILENAME = "pencegahan.mp4";

    @Override
    public void execute(MessageReceivedEvent event) {
        try {
            event.getChannel().sendMessage("Mohon tunggu, sedang mengupload video.").queue();
            File video = getFile();
            event.getChannel().sendMessage("[Pencegahan Covid-19]").addFile(video).queue();
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
