package ru.spbstu.telematics.messengerServer.data.storage.models.messages;

import lombok.Getter;

import java.util.List;

/**
 * Created by ihb on 20.06.17.
 */
@Getter
public class ChatCreateMessage extends Message {

    List<Long> participants;
}
