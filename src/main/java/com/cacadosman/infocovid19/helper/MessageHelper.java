package com.cacadosman.infocovid19.helper;

import java.util.Arrays;

public class MessageHelper {

    public static boolean isCommand(String message) {
        String[] messages = message.split(" ");
        if (messages.length > 1) {
            if(messages[0].equals("/covid")) {
                return true;
            }
        }
        return false;
    }

    public static int getParamCount(String message) {
        return message.split(" ").length;
    }

    public static String getParam(String message, int index) {
        return message.split(" ")[index].toLowerCase();
    }

    public static String sliceParamUntilEnd(String message, int index) {
        String[] oldMessages = message.split(" ");
        String[] newMessages = Arrays.copyOfRange(oldMessages, index, oldMessages.length);
        String newMessage = String.join(" ", newMessages).toLowerCase();
        return newMessage;
    }
}
