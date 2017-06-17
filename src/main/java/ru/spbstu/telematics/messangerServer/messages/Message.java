package ru.spbstu.telematics.messangerServer.messages;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 */
@Getter
@Setter
public abstract class Message implements Serializable {

    Long id;
    Long senderId;
    Type type;

}
