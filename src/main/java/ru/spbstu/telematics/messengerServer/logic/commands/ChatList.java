package ru.spbstu.telematics.messengerServer.logic.commands;

import ru.spbstu.telematics.messengerServer.data.DataManager;
import ru.spbstu.telematics.messengerServer.data.storage.MessageStore;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.ChatListMessage;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.ChatListResultMessage;
import ru.spbstu.telematics.messengerServer.exceptions.CommandException;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.network.Session;

import java.util.List;

/**
 * Created by ihb on 14.06.17.
 */
public class ChatList implements ICommand {

    MessageStore messageStore = DataManager.getInstance().getMessageStore();

    @Override
    public void execute(Session session, Message message) throws CommandException {

        ChatListMessage chatListMessage = (ChatListMessage) message;

        List<Long> chatsId = messageStore.getChatsByUserId(chatListMessage.getSenderId());

        message = new ChatListResultMessage(chatsId);
        session.send(message);
    }
}
