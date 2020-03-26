package com.cacadosman.infocovid19.service.feign;

import com.cacadosman.infocovid19.model.service.Prediction;
import com.cacadosman.infocovid19.model.service.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "analytics-api", url = "https://api-covid-19-analytics.herokuapp.com/api")
@RequestMapping(headers = {"User-Agent=Mozilla/5.0"})
public interface AnalyticsService {
    @RequestMapping(method = RequestMethod.GET, value = "/v1/predict/")
    Response<List<Prediction>> getPredictions();
}
