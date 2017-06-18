package ru.spbstu.telematics.messengerServer.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.spbstu.telematics.messengerServer.exceptiopns.ProtocolException;
import ru.spbstu.telematics.messengerServer.messages.LoginMessage;
import ru.spbstu.telematics.messengerServer.messages.Message;
import ru.spbstu.telematics.messengerServer.messages.TextMessage;
import ru.spbstu.telematics.messengerServer.messages.Type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Простейший протокол передачи данных
 */
public class StringProtocol implements IProtocol {

    public static final String DELIMITER = ";";

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

        Type type = Type.valueOf(rawData.substring(0,startPos));
        rawData = rawData.substring(startPos+1);

        switch (type) {
            case MSG_TEXT:
                return new Gson().fromJson(rawData, new TypeToken<TextMessage>(){}.getType());
            case MSG_LOGIN:
                    return new Gson().fromJson(rawData, new TypeToken<LoginMessage>() {}.getType());
            case MSG_INFO:
            default:
                throw new ProtocolException("Invalid type: " + type);
        }
    }

    @Override
    public byte[] encode(Message message) throws ProtocolException {
        StringBuilder builder = new StringBuilder();
        Type type = message.getType();
        builder.append(type).append(DELIMITER);
        switch (type) {
            case MSG_TEXT:
                builder.append(new Gson().toJson(message));
                break;
            case MSG_LOGIN:
                builder.append(new Gson().toJson(message));
                break;
            case MSG_STATUS:
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