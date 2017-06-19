package ru.spbstu.telematics.messengerServer.data.storage;

import ru.spbstu.telematics.messengerServer.data.DataManager;
import ru.spbstu.telematics.messengerServer.data.storage.dao.MessageDao;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.TextMessage;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by ihb on 18.06.17.
 */
public class MessageStore implements IMessageStore {

    private MessageDao messageDao = new MessageDao();


    @Override
    public List<Long> getChatsByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) {
        return null;
    }

    @Override
    public TextMessage getMessageById(Long messageId) {
        return messageDao.load(messageId);
    }


    @Override
    public void addMessage(TextMessage textMessage) {
        messageDao.insert(textMessage);
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) {

    }
}
