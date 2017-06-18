package ru.spbstu.telematics.messengerServer.messages;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Простое текстовое сообщение
 */

@Setter
@Getter
public class TextMessage extends Message {

    private String text;


    public TextMessage() {
        setType(Type.MSG_TEXT);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        TextMessage message = (TextMessage) other;
        return Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}