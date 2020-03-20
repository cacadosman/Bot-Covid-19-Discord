package com.cacadosman.infocovid19.model.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CovidAttributes {

    private int OBJECTID;
    private String Country_Region;
    private long Confirmed;
    private long Deaths;
    private long Recovered;
    private long Active;
}
