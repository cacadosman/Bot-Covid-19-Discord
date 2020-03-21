package com.cacadosman.infocovid19.model.discord;

import com.cacadosman.infocovid19.event.DiscordEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class DiscordBot {

    private static JDA instance = null;

    public static void createInstance(String token, DiscordEventListener discordEventListener) {
        try {
            JDABuilder jdaBuilder = new JDABuilder();
            jdaBuilder.setToken(token);
            jdaBuilder.addEventListeners(discordEventListener);
            instance = jdaBuilder.build();
        }catch (Exception e) {

        }
    }

    public static JDA getInstance() {
        return instance;
    }
}
