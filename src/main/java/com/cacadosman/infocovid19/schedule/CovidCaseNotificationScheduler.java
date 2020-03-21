package com.cacadosman.infocovid19.schedule;

import com.cacadosman.infocovid19.command.UpdateCovidCaseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CovidCaseNotificationScheduler {

    Logger logger = LoggerFactory.getLogger(CovidCaseNotificationScheduler.class);

    @Autowired
    UpdateCovidCaseCommand updateCovidCaseCommand;

    @Scheduled(fixedRate = 600000L)
    public void schedule() {
        logger.info("schedule executed");
        updateCovidCaseCommand.execute();
    }
}
