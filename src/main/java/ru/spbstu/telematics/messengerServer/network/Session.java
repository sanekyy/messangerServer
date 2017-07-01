package ru.spbstu.telematics.messengerServer.network;


import lombok.Getter;
import lombok.Setter;
import ru.spbstu.telematics.messengerServer.AppConfig;
import ru.spbstu.telematics.messengerServer.data.DataManager;
import ru.spbstu.telematics.messengerServer.data.storage.models.User;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.exceptions.ProtocolException;
import ru.spbstu.telematics.messengerServer.logic.CommandHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
@Getter
@Setter
public class Session {

    /**
     * Пользователь сессии, пока не прошел логин, user == null
     * После логина устанавливается реальный пользователь
     */
    private User user;

    // сокет на клиента
    private SelectionKey key;

    private IProtocol protocol = DataManager.getInstance().getProtocol();

    Queue<ByteBuffer> bufferToSendQueue = new ConcurrentLinkedQueue<>();

    public Session(SelectionKey key) {
        this.key = key;
    }

    public boolean isLoggedIn(Message message){
        return user != null && !"".equals(user.getToken()) && user.getToken().equals(message.getToken());
    }

    public void send(Message message){
        if(isLoggedIn(message)){
            message.setSenderId(user.getId());
        }

        try {
            bufferToSendQueue.add(ByteBuffer.wrap(protocol.encode(message)));
        } catch (ProtocolException e) {
            if (AppConfig.DEBUG) {
                e.printStackTrace();
            }
            return;
        }

        key.interestOps(SelectionKey.OP_WRITE);
        key.selector().wakeup();
    }

    public void onMessage(Message message) {
        switch (message.getType()){
            case MSG_REGISTRATION:
                CommandHandler.registration(this, message);
                break;
            case MSG_LOGIN:
                CommandHandler.login(this, message);
                break;
            case MSG_INFO:
                CommandHandler.info(this, message);
                break;
            case MSG_CHAT_LIST:
                CommandHandler.chatList(this, message);
                break;
            case MSG_CHAT_CREATE:
                CommandHandler.chatCreate(this, message);
                break;
            case MSG_CHAT_HIST:
                CommandHandler.chatHist(this, message);
                break;
            case MSG_TEXT:
                CommandHandler.text(this, message);
                break;
        }
    }

    public void close() {
        try {
            key.channel().close();
        } catch (IOException e) {
            if (AppConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }
}