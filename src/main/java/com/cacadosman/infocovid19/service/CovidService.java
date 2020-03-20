package com.cacadosman.infocovid19.service;

import com.cacadosman.infocovid19.model.service.CovidAll;
import com.cacadosman.infocovid19.model.service.CovidSimple;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "covid-api", url = "https://api.kawalcorona.com")
@RequestMapping(headers = {"User-Agent=Mozilla/5.0"})
public interface CovidService {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    List<CovidAll> getAll();

    @RequestMapping(method = RequestMethod.GET, value = "/positif/")
    CovidSimple getPositive();

    @RequestMapping(method = RequestMethod.GET, value = "/sembuh")
    CovidSimple getRecovered();

    @RequestMapping(method = RequestMethod.GET, value = "/meninggal")
    CovidSimple getDeath();
}
