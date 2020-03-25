package com.cacadosman.infocovid19.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CovidCountryResult {
    private String country;
    private long confirmed;
    private long active;
    private long recovered;
    private long deaths;
}
