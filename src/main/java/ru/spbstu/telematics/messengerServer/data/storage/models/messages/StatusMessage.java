package ru.spbstu.telematics.messengerServer.data.storage.models.messages;

import lombok.Getter;
import ru.spbstu.telematics.messengerServer.data.storage.models.User;

/**
 * Created by ihb on 17.06.17.
 */

@Getter
public class StatusMessage extends Message {

    public static final int LOGIN_SUCCESS = -1;
    public static final int REGISTRATION_SUCCESS = -2;
    public static final int CHAT_CREATE_SUCCESS = -3;
    public static final int TEXT_MESSAGE_SUCCESS = -4;

    public static final int LOGIN_ERROR = 1;
    public static final int REGISTRATION_ERROR = 2;
    public static final int CHAT_CREATE_ERROR = 3;
    public static final int USER_NOT_EXIST_ERROR = 4;
    public static final int SERVER_ERROR = 5;
    public static final int MESSAGE_TOO_LONG = 6;
    public static final int CHAT_NOT_EXIST = 7;
    public static final int PERMISSION_DENIED = 8;

    private final int statusCode;

    User user;

    Long chatId;

    public StatusMessage(int statusCode) {
        setType(Type.MSG_STATUS);

        this.statusCode = statusCode;
    }


    public StatusMessage(int statusCode, User user) {
        setType(Type.MSG_STATUS);

        this.statusCode = statusCode;
        this.user = user;
    }

    public StatusMessage(int statusCode, Long chatId) {
        setType(Type.MSG_STATUS);

        this.statusCode = statusCode;
        this.chatId = chatId;
    }
}
