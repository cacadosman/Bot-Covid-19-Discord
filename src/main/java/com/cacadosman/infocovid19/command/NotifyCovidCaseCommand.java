package com.cacadosman.infocovid19.command;

import com.cacadosman.infocovid19.model.event.CovidCaseData;

public interface NotifyCovidCaseCommand {
    void execute(CovidCaseData covidCaseData);
}
