package ru.spbstu.telematics.messengerServer.data.storage.models.messages;

import lombok.Getter;

import java.util.List;

/**
 * Created by ihb on 20.06.17.
 */
@Getter
public class ChatListResultMessage extends Message {

    List<Long> chats;

    public ChatListResultMessage(List<Long> chats){
        setType(Type.MSG_CHAT_LIST_RESULT);

        this.chats = chats;
    }
}
