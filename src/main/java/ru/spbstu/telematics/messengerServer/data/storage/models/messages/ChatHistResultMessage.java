package ru.spbstu.telematics.messengerServer.data.storage.models.messages;

import lombok.Getter;

import java.util.List;

/**
 * Created by ihb on 20.06.17.
 */
@Getter
public class ChatHistResultMessage extends Message {

    public static final int STATUS_SUCCESS = -1;

    public static final int PERMISSION_DENIED_ERROR = 1;
    public static final int CHAT_NOT_EXIST = 2;


    private final int statusCode;


    List<TextMessage> messages;

    public ChatHistResultMessage(List<TextMessage> messages){
        setType(Type.MSG_CHAT_HIST_RESULT);

        statusCode = STATUS_SUCCESS;

        this.messages = messages;
    }

    public ChatHistResultMessage(int statusCode){
        setType(Type.MSG_CHAT_HIST_RESULT);

        this.statusCode = statusCode;
    }
}
