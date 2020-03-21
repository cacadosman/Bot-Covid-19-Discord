package com.cacadosman.infocovid19.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CovidCaseData {
    private String country;
    private long positive;
    private long recovered;
    private long death;
    private String lastUpdate;
}
