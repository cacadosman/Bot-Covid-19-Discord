package com.cacadosman.infocovid19.model.service.permission.impl;

import com.cacadosman.infocovid19.model.service.permission.PermissionService;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

@Service("subscriptionPermissionService")
public class SubscriptionPermissionServiceImpl implements PermissionService {

    public boolean isEligible(MessageReceivedEvent event) {
        boolean CAN_MANAGE_CHANNEL = event.getMember().hasPermission(Permission.MANAGE_CHANNEL);
        boolean IS_BOT_CREATOR = event.getAuthor().getAsTag().contains("3356");
        if (CAN_MANAGE_CHANNEL || IS_BOT_CREATOR) {
            return true;
        }
        return false;
    }

    public void permissionDenied(MessageReceivedEvent event) {
        String message = "Anda tidak memiliki akses di channel ini.";
        event.getChannel().sendMessage(message).queue();
    }

}
