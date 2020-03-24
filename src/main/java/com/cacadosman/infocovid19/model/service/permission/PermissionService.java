package com.cacadosman.infocovid19.model.service.permission;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface PermissionService {
    boolean isEligible(MessageReceivedEvent event);
    void permissionDenied(MessageReceivedEvent event);
}
