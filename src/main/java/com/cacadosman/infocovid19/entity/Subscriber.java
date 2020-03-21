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
@Document(collection = Subscriber.COLLECTION_NAME)
public class Subscriber {

    public static final String COLLECTION_NAME = "subscribers";
    public static final String FIELD_ID = "id";
    public static final String FIELD_GUILD_ID = "guild_id";
    public static final String FIELD_CHANNEL_NAME = "text_channel_id";

    @Id
    @Field(value = FIELD_ID)
    private String id;

    @Field(value = FIELD_GUILD_ID)
    private long guildId;

    @Field(value = FIELD_CHANNEL_NAME)
    private long textChannelId;
}
