package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.UnsubscribeCommand;
import com.cacadosman.infocovid19.entity.Subscriber;
import com.cacadosman.infocovid19.service.permission.PermissionService;
import com.cacadosman.infocovid19.repository.SubscriberRepository;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UnsubscribeCommandImpl implements UnsubscribeCommand {

    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    @Qualifier("subscriptionPermissionService")
    PermissionService subscriptionPermissionService;

    @Override
    public void execute(MessageReceivedEvent event) {
        long guildId = event.getGuild().getIdLong();
        long channelId = event.getChannel().getIdLong();
        if (subscriptionPermissionService.isEligible(event)) {
            Subscriber subscriber = Subscriber.builder()
                    .guildId(guildId)
                    .textChannelId(channelId)
                    .build();
            unsubscribe(event, subscriber);
        } else {
            subscriptionPermissionService.permissionDenied(event);
        }
    }

    private void unsubscribe(MessageReceivedEvent event, Subscriber subscriber) {
        Subscriber result = subscriberRepository.findByGuildId(subscriber.getGuildId());
        if (result == null) {
            sendNotYetSubscribedMessage(event);
        } else {
            subscriberRepository.delete(result);
            sendUnsubscribedMessage(event);
        }
    }

    private void sendNotYetSubscribedMessage(MessageReceivedEvent event) {
        String message = "Anda belum melakukan subscription.";
        event.getChannel().sendMessage(message).queue();
    }

    private void sendUnsubscribedMessage(MessageReceivedEvent event) {
        String message = "Anda telah berhasil membatalkan subscription.";
        event.getChannel().sendMessage(message).queue();
    }
}
