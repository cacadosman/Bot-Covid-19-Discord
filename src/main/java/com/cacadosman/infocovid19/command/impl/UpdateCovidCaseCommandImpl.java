package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.NotifyCovidCaseCommand;
import com.cacadosman.infocovid19.command.UpdateCovidCaseCommand;
import com.cacadosman.infocovid19.entity.CovidCase;
import com.cacadosman.infocovid19.model.event.CovidCaseData;
import com.cacadosman.infocovid19.model.service.CovidIndonesia;
import com.cacadosman.infocovid19.repository.CovidCaseRepository;
import com.cacadosman.infocovid19.service.CovidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UpdateCovidCaseCommandImpl implements UpdateCovidCaseCommand {

    Logger logger = LoggerFactory.getLogger(UpdateCovidCaseCommandImpl.class);

    @Autowired
    CovidService covidService;
    @Autowired
    CovidCaseRepository covidCaseRepository;
    @Autowired
    NotifyCovidCaseCommand notifyCovidCaseCommand;

    @Override
    public void execute() {
        CovidCaseData data = getData();
        CovidCase covidCase = covidCaseRepository.findByCountry("indonesia");
        if (covidCase == null) {
            covidCase = new CovidCase();
            BeanUtils.copyProperties(data, covidCase);
            covidCase.setId(UUID.randomUUID().toString());
            covidCaseRepository.save(covidCase);
            logger.info("execute: covid data created");

            // Notify guild
            notifyCovidCaseCommand.execute(data);
            logger.info("execute: notify guild");
        } else {
            if (!covidCase.getLastUpdate().equals(data.getLastUpdate())) {
                covidCase.setLastUpdate(data.getLastUpdate());
                covidCaseRepository.save(covidCase);

                // Notify guild
                notifyCovidCaseCommand.execute(data);
                logger.info("execute: notify guild");
            }
        }
    }

    private CovidCaseData getData() {
        List<CovidIndonesia> covidCaseDataList = covidService.getIndonesia();
        return parseData(covidCaseDataList);
    }

    private CovidCaseData parseData(List<CovidIndonesia> covidIndonesiaList) {
        CovidCaseData covidCaseData = new CovidCaseData();
        covidIndonesiaList.forEach(data -> {
            if (data.getLastupdate() == null) {
                covidCaseData.setCountry(data.getName().toLowerCase());
                covidCaseData.setPositive(data.getPositif());
                covidCaseData.setRecovered(data.getSembuh());
                covidCaseData.setDeath(data.getMeninggal());
            } else {
                covidCaseData.setLastUpdate(data.getLastupdate());
            }
        });
        return covidCaseData;
    }
}
