package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.PredictionCommand;
import com.cacadosman.infocovid19.model.service.Prediction;
import com.cacadosman.infocovid19.service.feign.AnalyticsService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class PredictionCommandImpl implements PredictionCommand {

    @Autowired
    AnalyticsService analyticsService;

    @Override
    public void execute(MessageReceivedEvent event) {
        List<Prediction> data = getPredictions();
        sendMessages(event, data);
    }

    private List<Prediction> getPredictions() {
        return analyticsService.getPredictions().getData();
    }

    private void sendMessages(MessageReceivedEvent event, List<Prediction> data) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.ORANGE);
        eb.setTitle("Prediksi total kasus Covid-19 di Indonesia");
        eb.setDescription("Data berikut ini merupakan data hasil prediksi yang belum tentu akurat untuk lima hari kedepan.");
        for (Prediction item: data) {
            eb.addField("Tanggal " + item.getDate(),
                    "Hasil prediksi: " + item.getValue() + " kasus", false);
        }

        event.getChannel().sendMessage(eb.build()).queue();
    }
}
