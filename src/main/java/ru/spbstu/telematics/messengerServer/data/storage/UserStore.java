package ru.spbstu.telematics.messengerServer.data.storage;

import ru.spbstu.telematics.messengerServer.data.storage.dao.UserDao;
import ru.spbstu.telematics.messengerServer.data.storage.models.User;

/**
 * Created by ihb on 14.06.17.
 */
public class UserStore implements IUserStore {

    private UserDao userDao = new UserDao();


    @Override
    public User addUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public User updateUser(User user) {
        return userDao.update(user);
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        return userDao.loadByLoginAndPassword(login, password);
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.loadByLogin(login);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.load(id);
    }
}
