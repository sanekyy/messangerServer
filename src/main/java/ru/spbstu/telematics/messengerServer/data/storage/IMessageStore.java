package ru.spbstu.telematics.messengerServer.data.storage;

import ru.spbstu.telematics.messengerServer.data.storage.models.Chat;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.TextMessage;

import java.util.List;

public interface IMessageStore {
    /**
     * получаем список ид пользователей заданного чата
     */
    List<Long> getChatsByUserId(Long userId);

    /**
     * получить информацию о чате
     */
    Chat getChatById(Long chatId);

    /**
     * получить информацию о чатах
     */
    List<Chat> getChatsById(List<Long> chatsId);

    /**
     * Список сообщений из чата
     */
    List<Long> getMessagesFromChat(Long chatId);

    /**
     * Получить информацию о сообщении
     */
    TextMessage getMessageById(Long messageId);

    /**
     * Добавить сообщение в чат
     */
    TextMessage addMessage(TextMessage message);

    /**
     * Добавить пользователя к чату
     */
    void addUserToChat(Long userId, Long chatId);


    /**
     * Создать чат
     */
    Chat createChat(Chat chat);
}
