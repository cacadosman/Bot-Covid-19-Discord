package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.NotifyCovidCaseCommand;
import com.cacadosman.infocovid19.entity.Subscriber;
import com.cacadosman.infocovid19.model.discord.DiscordBot;
import com.cacadosman.infocovid19.model.event.CovidCaseData;
import com.cacadosman.infocovid19.repository.SubscriberRepository;
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
        String messages = "[Update Otomatis Bot Covid-19]\n";
        messages += "Informasi Covid-19 di Indonesia:\n";
        messages += "- Total Positif: " + covidCaseData.getPositive() + "\n";
        messages += "- Total Sembuh: " + covidCaseData.getRecovered() + "\n";
        messages += "- Total Meninggal: " + covidCaseData.getDeath() + "\n";
        messages += "Update terakhir: " + covidCaseData.getLastUpdate();

        try {
            JDA jda = DiscordBot.getInstance();

            Guild guild = jda.getGuildById(subscriber.getGuildId());
            TextChannel textChannel = guild.getTextChannelById(subscriber.getTextChannelId());

            textChannel.sendMessage(messages).queue();
        } catch (Exception e) {
            logger.error("notify: " + e.getMessage());
        }
    }
}
