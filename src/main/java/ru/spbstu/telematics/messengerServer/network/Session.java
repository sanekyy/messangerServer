package ru.spbstu.telematics.messengerServer.network;


import lombok.Getter;
import lombok.Setter;
import ru.spbstu.telematics.messengerServer.exceptions.ProtocolException;
import ru.spbstu.telematics.messengerServer.logic.CommandHandler;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.data.storage.models.User;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

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
    private Socket socket;

    public Session(Socket socket) {
        this.socket = socket;
    }

    public boolean isLoggedIn(Message message){
        return user != null && !"".equals(user.getToken()) && user.getToken().equals(message.getToken());
    }

    public void send(Message message){
        if(isLoggedIn(message)){
            message.setSenderId(user.getId());
        }

        // TODO: 17.06.17 fix me
        ByteBuffer buffer = null;
        try {
            // TODO: 20.06.17 fix new StringProtocol..
            buffer = ByteBuffer.wrap(new StringProtocol().encode(message));
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        try {
            socket.getChannel().write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        // TODO: закрыть in/out каналы и сокет. Освободить другие ресурсы, если необходимо
    }
}