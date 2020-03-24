package com.cacadosman.infocovid19.model.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CovidAttributesProvince {

    private int FID;
    private int Kode_Provi;
    private String Provinsi;
    private long Kasus_Posi;
    private long Kasus_Semb;
    private long Kasus_Meni;
}
