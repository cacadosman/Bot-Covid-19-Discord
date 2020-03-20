package com.cacadosman.infocovid19.helper;

public class MessageHelper {

    public static boolean isCommand(String message) {
        String[] messages = message.split(" ");
        if (messages.length > 2) {
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
}
