package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.ProvinsiCommand;
import com.cacadosman.infocovid19.helper.MessageHelper;
import com.cacadosman.infocovid19.model.command.CovidProvinceResult;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinsiCommandImpl implements ProvinsiCommand {

    Logger logger = LoggerFactory.getLogger(ProvinsiCommandImpl.class);

    @Override
    public void execute(MessageReceivedEvent event) {
        List<CovidProvinceResult> covidProvinceResultList = parseData();

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

    private void sendAllProvicesDataMessage(
            MessageReceivedEvent event,
            List<CovidProvinceResult> dataList) {
        String messages = "[Data statistik semua provinsi]\n";
        for(CovidProvinceResult data: dataList) {
            messages += data.getProvince() + ":\n";
            messages += "- Positif: " + data.getPositive() + "\n";
            messages += "- Sembuh: " + data.getRecovered() + "\n";
            messages += "- Meninggal: " + data.getDeath() + "\n\n";
        }

        event.getChannel().sendMessage(messages).queue();
    }

    private void sendProvinceDataMessage(
            MessageReceivedEvent event, CovidProvinceResult data) {
        String messages = "Data statistik di provinsi " + data.getProvince() + ":\n";
        messages += "- Positif: " + data.getPositive() + "\n";
        messages += "- Sembuh: " + data.getRecovered() + "\n";
        messages += "- Meninggal: " + data.getDeath() + "\n";

        event.getChannel().sendMessage(messages).queue();
    }

    private void sendProvinceNotFoundMessage(MessageReceivedEvent event) {
        String message = "Provinsi tidak ditemukan.";
        event.getChannel().sendMessage(message).queue();
    }
}
