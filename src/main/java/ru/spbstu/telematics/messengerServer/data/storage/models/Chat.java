package ru.spbstu.telematics.messengerServer.data.storage.models;


import lombok.Getter;
import lombok.Setter;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.TextMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihb on 20.06.17.
 */

@Getter
@Setter
public class Chat {

    Long id;

    List<Long> messages = new ArrayList<>();

    List<Long> participants = new ArrayList<>();

    Long admin;

    public void addMessage(TextMessage textMessage){
        messages.add(textMessage.getId());
    }

    public void addParticipants(User user){
        participants.add(user.getId());
    }

    public void removeParticipants(User user){
        participants.remove(user.getId());
    }
}
