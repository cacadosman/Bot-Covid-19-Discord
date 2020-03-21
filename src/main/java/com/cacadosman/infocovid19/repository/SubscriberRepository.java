package com.cacadosman.infocovid19.repository;

import com.cacadosman.infocovid19.entity.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SubscriberRepository extends MongoRepository<Subscriber, String> {
    Subscriber findByGuildId(long guildId);
}
