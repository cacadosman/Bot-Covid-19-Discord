package com.cacadosman.infocovid19.command.impl;

import com.cacadosman.infocovid19.command.SubscribeCommand;
import com.cacadosman.infocovid19.entity.Subscriber;
import com.cacadosman.infocovid19.repository.SubscriberRepository;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubscribeCommandImpl implements SubscribeCommand {

    @Autowired
    SubscriberRepository subscriberRepository;

    @Override
    public void execute(MessageReceivedEvent event) {
        long guildId = event.getGuild().getIdLong();
        long channelId = event.getChannel().getIdLong();
        if (isEligible(event)) {
            Subscriber subscriber = Subscriber.builder()
                    .guildId(guildId)
                    .textChannelId(channelId)
                    .build();
            subscribe(event, subscriber);
        } else {
            permissionDenied(event);
        }
    }

    private void subscribe(MessageReceivedEvent event, Subscriber subscriber) {
        Subscriber result = subscriberRepository.findByGuildId(subscriber.getGuildId());
        if (result == null) {
            subscriber.setId(UUID.randomUUID().toString());
            subscriberRepository.save(subscriber);
            sendSubscribedChannelMessage(event);
        } else {
            subscriber.setId(result.getId());
            subscriberRepository.save(subscriber);
            sendChangeSubscribedChannelMessage(event);
        }
    }

    private void permissionDenied(MessageReceivedEvent event) {
        String message = "Anda tidak memiliki akses di channel ini.";
        event.getChannel().sendMessage(message).queue();
    }

    private void sendChangeSubscribedChannelMessage(MessageReceivedEvent event) {
        String message = "Subscription telah diubah ke channel ini.";
        event.getChannel().sendMessage(message).queue();
    }

    private void sendSubscribedChannelMessage(MessageReceivedEvent event) {
        String message = "Terima kasih telah mensubscribe info Covid-19 di channel ini.";
        event.getChannel().sendMessage(message).queue();
    }

    private boolean isEligible(MessageReceivedEvent event) {
        boolean CAN_MANAGE_CHANNEL = event.getMember().hasPermission(Permission.MANAGE_CHANNEL);
        boolean IS_BOT_CREATOR = event.getAuthor().getAsTag().contains("3356");
        if (CAN_MANAGE_CHANNEL || IS_BOT_CREATOR) {
            return true;
        }
        return false;
    }
}
