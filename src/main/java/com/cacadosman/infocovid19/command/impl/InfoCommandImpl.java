package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.InfoCommand;
import com.cacadosman.infocovid19.helper.MessageHelper;
import com.cacadosman.infocovid19.model.command.CovidCountryResult;
import com.cacadosman.infocovid19.model.service.CovidAll;
import com.cacadosman.infocovid19.model.service.CovidSimple;
import com.cacadosman.infocovid19.service.CovidService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

        String messages = "Data Covid-19 Global:\n";
        messages += "- Total Positif: " + positiveData.getValue() + "\n";
        messages += "- Total Sembuh: " + recoveredData.getValue() + "\n";
        messages += "- Total Meninggal: " + deathData.getValue();

        event.getChannel().sendMessage(messages).queue();
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
            String messages = "Data Covid-19 di " + country + ":\n";
            messages += "- Total Terkonfirmasi: " + result.getConfirmed() + "\n";
            messages += "- Total Kasus Aktif: " + result.getActive() + "\n";
            messages += "- Total Sembuh: " + result.getRecovered() + "\n";
            messages += "- Total Meninggal: " + result.getDeaths();

            event.getChannel().sendMessage(messages).queue();
        }
    }
}
