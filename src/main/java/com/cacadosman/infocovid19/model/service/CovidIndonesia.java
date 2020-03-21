package com.cacadosman.infocovid19.model.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CovidIndonesia {
    private String name;
    private long positif;
    private long sembuh;
    private long meninggal;
    private String lastupdate;
}
