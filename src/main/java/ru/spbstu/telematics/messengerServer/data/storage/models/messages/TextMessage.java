package ru.spbstu.telematics.messengerServer.data.storage.models.messages;

import lombok.Getter;
import lombok.Setter;

/**
 * Простое текстовое сообщение
 */

@Setter
@Getter
public class TextMessage extends Message {


    Long chatId;

    String text;

    Long timestamp = System.currentTimeMillis();


    public TextMessage() {
        setType(Type.MSG_TEXT);
    }
}