package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.ProvinsiCommand;
import com.cacadosman.infocovid19.helper.MessageHelper;
import com.cacadosman.infocovid19.model.command.CovidProvinceResult;
import com.cacadosman.infocovid19.model.service.CovidAllProvince;
import com.cacadosman.infocovid19.service.feign.CovidService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinsiCommandImpl implements ProvinsiCommand {

    @Autowired
    CovidService covidService;

    Logger logger = LoggerFactory.getLogger(ProvinsiCommandImpl.class);

    @Override
    public void execute(MessageReceivedEvent event) {
        List<CovidProvinceResult> covidProvinceResultList = parseDataFromApi();

        String message = event.getMessage().getContentRaw();
        if (MessageHelper.getParamCount(message) > 2) {
            String query = MessageHelper.sliceParamUntilEnd(message, 2).toLowerCase();
            for(CovidProvinceResult data: covidProvinceResultList) {
                if (data.getProvince().toLowerCase().contains(query)) {
                    sendProvinceDataMessage(event, data);
                    return;
                }
            }
            sendProvinceNotFoundMessage(event);
        } else {
            sendAllProvicesDataMessage(event, covidProvinceResultList);
        }
    }

    @Deprecated
    private List<CovidProvinceResult> parseData() {
        List<CovidProvinceResult> results = new ArrayList<>();
        try {
            Document document = Jsoup.connect("https://kawalcorona.com/").get();
            Element coronaDataPerProvinceTable = document.selectFirst("tbody");
            Elements provinces = coronaDataPerProvinceTable.children();

            for (Element province: provinces) {
                Elements details = province.children().next();

                CovidProvinceResult data = new CovidProvinceResult();
                data.setProvince(details.eachText().get(0));
                data.setPositive(Long.parseLong(details.eachText().get(1)));
                data.setRecovered(Long.parseLong(details.eachText().get(2)));
                data.setDeath(Long.parseLong(details.eachText().get(3)));
                results.add(data);
            }
        } catch (Exception e) {
            logger.error("parseData: " + e.toString());
        }

        return results;
    }

    private List<CovidProvinceResult> parseDataFromApi() {
        return covidService.getProvinces().stream()
                .map(this::mapCovidApiToCovidProviceResult)
                .collect(Collectors.toList());
    }

    private CovidProvinceResult mapCovidApiToCovidProviceResult(CovidAllProvince data) {
        CovidProvinceResult result = CovidProvinceResult
                .builder()
                .province(data.getAttributes().getProvinsi())
                .positive(data.getAttributes().getKasus_Posi())
                .recovered(data.getAttributes().getKasus_Semb())
                .death(data.getAttributes().getKasus_Meni())
                .build();
        return result;
    }

    private void sendAllProvicesDataMessage(
            MessageReceivedEvent event,
            List<CovidProvinceResult> dataList) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.CYAN);
        eb.setTitle("Data statistik semua provinsi");

        String descriptions = "";
        for(CovidProvinceResult data: dataList) {
            descriptions += "***" + data.getProvince() + "***\n";
            descriptions += ":cry: Positif **" + data.getPositive() + "**\n";
            descriptions += ":innocent: Sembuh **" + data.getRecovered() + "**\n";
            descriptions += ":sob: Meninggal **" + data.getDeath() + "**\n\n";
        }
        eb.setDescription(descriptions);

        event.getChannel().sendMessage(eb.build()).queue();
    }

    private void sendProvinceDataMessage(
            MessageReceivedEvent event, CovidProvinceResult data) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.CYAN);
        eb.setTitle("Data statistik di Provinsi " + data.getProvince());
        eb.addField(":cry: Positif", String.valueOf(data.getPositive()), true);
        eb.addField(":innocent: Sembuh", String.valueOf(data.getRecovered()), true);
        eb.addField(":sob: Meninggal", String.valueOf(data.getDeath()), true);

        event.getChannel().sendMessage(eb.build()).queue();
    }

    private void sendProvinceNotFoundMessage(MessageReceivedEvent event) {
        String message = "Provinsi tidak ditemukan.";
        event.getChannel().sendMessage(message).queue();
    }
}
