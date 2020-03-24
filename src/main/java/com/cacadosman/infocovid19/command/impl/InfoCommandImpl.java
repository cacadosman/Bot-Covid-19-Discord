package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.InfoCommand;
import com.cacadosman.infocovid19.helper.MessageHelper;
import com.cacadosman.infocovid19.model.command.CovidCountryResult;
import com.cacadosman.infocovid19.model.service.CovidAll;
import com.cacadosman.infocovid19.model.service.CovidSimple;
import com.cacadosman.infocovid19.service.feign.CovidService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class InfoCommandImpl implements InfoCommand {

    @Autowired
    CovidService covidService;

    @Override
    public void execute(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (MessageHelper.getParamCount(message) < 3) {
            return;
        }

        switch (MessageHelper.getParam(message, 2)) {
            case "global":
                sendGlobalData(event);
                break;
            default:
                sendCountryData(event);
        }
    }

    private void sendGlobalData(MessageReceivedEvent event) {
        CovidSimple positiveData = covidService.getPositive();
        CovidSimple recoveredData = covidService.getRecovered();
        CovidSimple deathData = covidService.getDeath();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN);
        eb.setTitle("Data Covid-19 Global");
        eb.addField(":cry: Positif", positiveData.getValue(), true);
        eb.addField(":innocent: Sembuh", recoveredData.getValue(), true);
        eb.addField(":sob: Meninggal", deathData.getValue(), true);

        event.getChannel().sendMessage(eb.build()).queue();
    }

    private CovidCountryResult getCountryData(MessageReceivedEvent event) {
        String country = MessageHelper.sliceParamUntilEnd(event.getMessage().getContentRaw(), 2);
        List<CovidAll> data = covidService.getAll();
        for(CovidAll item : data) {
            String itemCountry = item.getAttributes().getCountry_Region().toLowerCase();

            if (itemCountry.equals(country)) {
                CovidCountryResult result = CovidCountryResult
                        .builder()
                        .active(item.getAttributes().getActive())
                        .confirmed(item.getAttributes().getConfirmed())
                        .recovered(item.getAttributes().getRecovered())
                        .deaths(item.getAttributes().getDeaths())
                        .build();
                return result;
            }
        }
        return null;
    }

    private void sendCountryData(MessageReceivedEvent event) {
        String country = MessageHelper.sliceParamUntilEnd(event.getMessage().getContentRaw(), 2);
        CovidCountryResult result = getCountryData(event);
        if (result == null) {
            String messages = "Nama negara tidak ditemukan.";
            event.getChannel().sendMessage(messages).queue();
        } else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.GREEN);
            eb.setTitle("Data Covid-19 di " + country);
            eb.addField(":cry: Positif", String.valueOf(result.getConfirmed()), true);
            eb.addField(":innocent: Sembuh", String.valueOf(result.getRecovered()), true);
            eb.addField(":sob: Meninggal", String.valueOf(result.getDeaths()), true);

            event.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
