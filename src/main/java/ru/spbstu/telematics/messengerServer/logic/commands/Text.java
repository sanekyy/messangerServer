package ru.spbstu.telematics.messengerServer.logic.commands;

import ru.spbstu.telematics.messengerServer.Server;
import ru.spbstu.telematics.messengerServer.data.DataManager;
import ru.spbstu.telematics.messengerServer.data.storage.MessageStore;
import ru.spbstu.telematics.messengerServer.data.storage.models.Chat;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.StatusMessage;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.TextMessage;
import ru.spbstu.telematics.messengerServer.exceptions.CommandException;
import ru.spbstu.telematics.messengerServer.network.Session;

import java.util.List;

/**
 * Created by ihb on 21.06.17.
 */
public class Text implements ICommand {

    MessageStore messageStore = DataManager.getInstance().getMessageStore();

    @Override
    public void execute(Session session, Message message) throws CommandException {

        final TextMessage textMessage = (TextMessage) message;

        Chat chat = messageStore.getChatById(textMessage.getChatId());
        if(chat == null || !chat.getParticipants().contains(message.getSenderId())){
            message = new StatusMessage(StatusMessage.TEXT_MESSAGE_ERROR);
        } else {

            messageStore.addMessage(textMessage);

            if (textMessage.getId() != -1L) {
                message = new StatusMessage(StatusMessage.TEXT_MESSAGE_SUCCESS);
            } else {
                message = new StatusMessage(StatusMessage.TEXT_MESSAGE_ERROR);
            }

            List<Long> participants = messageStore.getChatById(textMessage.getChatId()).getParticipants();

            Server.getSessions().forEach((socket, session1) -> {
                if (participants.contains(session1.getUser().getId()) && session1 != session)
                    session1.send(textMessage);
            });
        }

        session.send(message);
    }
}
