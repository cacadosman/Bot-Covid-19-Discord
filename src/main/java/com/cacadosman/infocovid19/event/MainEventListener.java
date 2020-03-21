package com.cacadosman.infocovid19.event;

import com.cacadosman.infocovid19.model.discord.DiscordBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Component
public class MainEventListener {

    @Autowired
    DiscordEventListener discordEventListener;

    @Value("${bot.token}")
    private String token;

    @EventListener
    public void handleEvent(ContextRefreshedEvent event) throws LoginException {
        DiscordBot.createInstance(token, discordEventListener);
    }
}
