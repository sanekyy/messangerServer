package ru.spbstu.telematics.messengerServer.data.storage.models.messages;

import lombok.Getter;

/**
 * Created by ihb on 18.06.17.
 */

@Getter
public class RegistrationMessage extends Message {

    String login;
    String password;

    public RegistrationMessage(String login, String password) {
        setType(Type.MSG_REGISTRATION);

        this.login = login;
        this.password = password;
    }
}
