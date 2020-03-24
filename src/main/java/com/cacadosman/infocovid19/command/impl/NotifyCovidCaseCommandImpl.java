package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.NotifyCovidCaseCommand;
import com.cacadosman.infocovid19.entity.Subscriber;
import com.cacadosman.infocovid19.model.discord.DiscordBot;
import com.cacadosman.infocovid19.model.event.CovidCaseData;
import com.cacadosman.infocovid19.repository.SubscriberRepository;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.List;

@Service
public class NotifyCovidCaseCommandImpl implements NotifyCovidCaseCommand {

    Logger logger = LoggerFactory.getLogger(NotifyCovidCaseCommandImpl.class);

    @Autowired
    SubscriberRepository subscriberRepository;

    @Value("${bot.token}")
    private String token;

    @Override
    public void execute(CovidCaseData covidCaseData) {
        List<Subscriber> subscriberList = subscriberRepository.findAll();
        subscriberList.forEach(subscriber -> notify(subscriber, covidCaseData));
    }

    private void notify(Subscriber subscriber, CovidCaseData covidCaseData) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.YELLOW);
        eb.setTitle("Update Otomatis Kasus Covid-19 di Indonesia");
        eb.addField(":cry: Positif", String.valueOf(covidCaseData.getPositive()), true);
        eb.addField(":innocent: Sembuh", String.valueOf(covidCaseData.getRecovered()), true);
        eb.addField(":sob: Meninggal", String.valueOf(covidCaseData.getDeath()), true);

        try {
            JDA jda = DiscordBot.getInstance();

            Guild guild = jda.getGuildById(subscriber.getGuildId());
            TextChannel textChannel = guild.getTextChannelById(subscriber.getTextChannelId());

            textChannel.sendMessage(eb.build()).queue();
        } catch (Exception e) {
            logger.error("notify: " + e.getMessage());
        }
    }
}
