package ru.spbstu.telematics.messengerServer.logic.commands;

import ru.spbstu.telematics.messengerServer.data.DataManager;
import ru.spbstu.telematics.messengerServer.data.storage.MessageStore;
import ru.spbstu.telematics.messengerServer.data.storage.models.Chat;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.ChatHistMessage;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.ChatHistResultMessage;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.TextMessage;
import ru.spbstu.telematics.messengerServer.exceptions.CommandException;
import ru.spbstu.telematics.messengerServer.network.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihb on 14.06.17.
 */
public class ChatHist implements ICommand {

    private MessageStore messageStore = DataManager.getInstance().getMessageStore();

    @Override
    public void execute(Session session, Message message) throws CommandException {

        ChatHistMessage chatHistMessage = (ChatHistMessage) message;

        Chat chat = messageStore.getChatById(chatHistMessage.getChatId());

        if (chat == null) {
            message = new ChatHistResultMessage(ChatHistResultMessage.CHAT_NOT_EXIST);
        } else if (!chat.getParticipants().contains(chatHistMessage.getSenderId())) {
            message = new ChatHistResultMessage(ChatHistResultMessage.PERMISSION_DENIED_ERROR);
        } else {
            List<TextMessage> textMessages = new ArrayList<>();
            for (Long messageId : chat.getMessages()) {
                textMessages.add(messageStore.getMessageById(messageId));
            }

            message = new ChatHistResultMessage(textMessages);
        }

        session.send(message);
    }
}
