package ru.spbstu.telematics.messengerServer.data;

import ru.spbstu.telematics.messengerServer.data.storage.MessageStore;
import ru.spbstu.telematics.messengerServer.data.storage.UserStore;
import ru.spbstu.telematics.messengerServer.data.storage.dao.MessageDao;
import ru.spbstu.telematics.messengerServer.data.storage.dao.UserDao;

/**
 * Created by ihb on 18.06.17.
 */
public class DataManager {

    private static DataManager INSTANCE = new DataManager();

    private UserStore userStore = new UserStore();

    private MessageStore messageStore = new MessageStore();

    private DataManager() {

    }

    public static DataManager getInstance() {
        return INSTANCE;
    }

    public UserStore getUserStore(){
        return userStore;
    }

    public MessageStore getMessageStore(){
        return messageStore;
    }
}
