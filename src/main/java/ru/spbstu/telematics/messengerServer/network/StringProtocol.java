package ru.spbstu.telematics.messengerServer.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.*;
import ru.spbstu.telematics.messengerServer.exceptions.ProtocolException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Простейший протокол передачи данных
 */
public class StringProtocol implements IProtocol {

    private static final String DELIMITER = ";";

    @Override
    public Message decode(byte[] bytes) throws ProtocolException {

        String rawData = new String(bytes).trim();
        System.out.println("decoded: " + rawData);

        Pattern groupIdPattern = Pattern.compile(DELIMITER);
        Matcher matcher = groupIdPattern.matcher(rawData);

        if(!matcher.find()){
            throw new ProtocolException("Delimiter doesn't found");
        }

        int startPos = matcher.start();

        Message.Type type = Message.Type.valueOf(rawData.substring(0,startPos));
        rawData = rawData.substring(startPos+1);

        switch (type) {
            case MSG_REGISTRATION:
                return new Gson().fromJson(rawData, new TypeToken<RegistrationMessage>(){}.getType());
            case MSG_LOGIN:
                return new Gson().fromJson(rawData, new TypeToken<LoginMessage>() {}.getType());
            case MSG_TEXT:
                return new Gson().fromJson(rawData, new TypeToken<TextMessage>(){}.getType());
            case MSG_INFO:
                return new Gson().fromJson(rawData, new TypeToken<InfoMessage>(){}.getType());
            case MSG_CHAT_LIST:
                return new Gson().fromJson(rawData, new TypeToken<ChatListMessage>(){}.getType());
            case MSG_CHAT_CREATE:
                return new Gson().fromJson(rawData, new TypeToken<ChatCreateMessage>(){}.getType());
            case MSG_CHAT_HIST:
                return new Gson().fromJson(rawData, new TypeToken<ChatHistMessage>(){}.getType());
            default:
                throw new ProtocolException("Invalid type: " + type);
        }
    }

    @Override
    public byte[] encode(Message message) throws ProtocolException {
        StringBuilder builder = new StringBuilder();
        Message.Type type = message.getType();
        builder.append(type).append(DELIMITER);
        switch (type) {
            case MSG_TEXT:
            case MSG_LOGIN:
            case MSG_STATUS:
            case MSG_INFO_RESULT:
            case MSG_CHAT_LIST_RESULT:
            case MSG_CHAT_HIST_RESULT:
                builder.append(new Gson().toJson(message));
                break;
            default:
                throw new ProtocolException("Invalid type: " + type);


        }
        System.out.println("encoded: " + builder);
        return builder.toString().getBytes();
    }

    private Long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            // who care
        }
        return null;
    }
}