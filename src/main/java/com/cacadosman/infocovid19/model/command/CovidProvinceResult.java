package com.cacadosman.infocovid19.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CovidProvinceResult {
    private String province;
    private long positive;
    private long recovered;
    private long death;
}
