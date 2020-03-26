package com.cacadosman.infocovid19.model.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Prediction {
    private String date;
    private long value;
}
