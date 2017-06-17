package ru.spbstu.telematics.messangerServer.network;


import lombok.Setter;
import ru.spbstu.telematics.messangerServer.exceptiopns.ProtocolException;
import ru.spbstu.telematics.messangerServer.logic.CommandHandler;
import ru.spbstu.telematics.messangerServer.messages.Message;
import ru.spbstu.telematics.messangerServer.store.User;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
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

    public boolean isLoggedIn(){
        return user != null;
    }

    public void send(Message message){
        // TODO: 17.06.17 fix me
        ByteBuffer buffer = null;
        try {
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
            case MSG_LOGIN:
                CommandHandler.login(this, message);
        }
    }

    public void close() {
        // TODO: закрыть in/out каналы и сокет. Освободить другие ресурсы, если необходимо
    }
}