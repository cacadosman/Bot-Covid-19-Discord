package com.cacadosman.infocovid19.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = CovidCase.COLLECTION_NAME)
public class CovidCase {

    public static final String COLLECTION_NAME = "simple_covid_cases";
    public static final String FIELD_ID = "id";
    public static final String FIELD_COUNTRY = "country";
    public static final String FIELD_POSITIVE = "positive";
    public static final String FIELD_RECOVERED = "recovered";
    public static final String FIELD_DEATH = "death";
    public static final String FIELD_LAST_UPDATE = "last_update";

    @Id
    @Field(value = FIELD_ID)
    private String id;

    @Field(value = FIELD_COUNTRY)
    private String country;

    @Field(value = FIELD_POSITIVE)
    private long positive;

    @Field(value = FIELD_RECOVERED)
    private long recovered;

    @Field(value = FIELD_DEATH)
    private long death;

    @Field(value = FIELD_LAST_UPDATE)
    private String lastUpdate;
}
