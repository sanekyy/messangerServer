package ru.spbstu.telematics.messengerServer.data.storage;

import ru.spbstu.telematics.messengerServer.data.storage.dao.ChatDao;
import ru.spbstu.telematics.messengerServer.data.storage.dao.MessageDao;
import ru.spbstu.telematics.messengerServer.data.storage.models.Chat;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.TextMessage;

import java.util.List;

/**
 * Created by ihb on 18.06.17.
 */
public class MessageStore implements IMessageStore {

    private MessageDao messageDao = new MessageDao();
    private ChatDao chatDao = new ChatDao();


    @Override
    public List<Long> getChatsByUserId(Long userId) {
        return chatDao.loadByUserId(userId);
    }

    @Override
    public Chat getChatById(Long chatId) {
        return chatDao.load(chatId);
    }

    @Override
    public List<Chat> getChatsById(List<Long> chatsId) {
        return chatDao.load(chatsId);
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) {
        return messageDao.loadByChatId(chatId);
    }

    @Override
    public TextMessage getMessageById(Long messageId) {
        return messageDao.load(messageId);
    }


    @Override
    public TextMessage addMessage(TextMessage textMessage) {
        return messageDao.insert(textMessage);
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) {
        chatDao.insertUserInChat(userId, chatId);
    }

    @Override
    public Chat createChat(Chat chat) {
        return chatDao.insert(chat);
    }


}
