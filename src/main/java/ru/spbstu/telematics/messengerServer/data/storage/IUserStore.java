package ru.spbstu.telematics.messengerServer.data.storage;


import ru.spbstu.telematics.messengerServer.data.storage.models.User;

import java.util.List;

public interface IUserStore {
    /**
     * Добавить пользователя в хранилище
     * Вернуть его же
     */
    User addUser(User user);

    /**
     * Обновить информацию о пользователе
     */
    User updateUser(User user);

    User getUserByLoginAndPassword(String login, String password);

    User getUserByLogin(String login);

    /**
     * Получить пользователя по id, например запрос информации/профиля
     * return null if user not found
     */
    User getUserById(Long id);

    /**
     * Получить пользователей по id, например запрос информации/профиля
     * return null if user not found
     */
    List<User> getUsersById(List<Long> participants);
}
