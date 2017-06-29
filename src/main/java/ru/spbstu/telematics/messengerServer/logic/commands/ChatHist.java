package ru.spbstu.telematics.messengerServer.logic.commands;

import ru.spbstu.telematics.messengerServer.data.DataManager;
import ru.spbstu.telematics.messengerServer.data.storage.MessageStore;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.*;
import ru.spbstu.telematics.messengerServer.exceptions.CommandException;
import ru.spbstu.telematics.messengerServer.network.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihb on 14.06.17.
 */
public class ChatHist implements ICommand {

    MessageStore messageStore = DataManager.getInstance().getMessageStore();

    @Override
    public void execute(Session session, Message message) throws CommandException {

        ChatHistMessage chatHistMessage = (ChatHistMessage) message;

        if(messageStore.getChatById(chatHistMessage.getChatId()).getParticipants().contains(chatHistMessage.getSenderId())){
            message = new ChatHistResultMessage(ChatHistResultMessage.PERMISSION_DENIED_ERROR);
        } else {
            List<Long> messagesId = messageStore.getMessagesFromChat(chatHistMessage.getChatId());

            List<TextMessage> textMessages = new ArrayList<>();
            for(Long messageId : messagesId){
                textMessages.add(messageStore.getMessageById(messageId));
            }

            message = new ChatHistResultMessage(textMessages);
        }

        session.send(message);
    }
}
