package ru.spbstu.telematics.messengerServer.data.storage.models.messages;

import lombok.Getter;
import ru.spbstu.telematics.messengerServer.data.storage.models.User;

/**
 * Created by ihb on 19.06.17.
 */

@Getter
public class InfoResultMessage extends Message {

    public static final int STATUS_OK = -1;

    public static final int USER_NOT_FOUND = 1;

    public static final int PERMISSION_DENIED = 2;

    final int statusCode;

    User user;

    public InfoResultMessage(int statusCode){
        // TODO: 19.06.17 move Type to static
        setType(Type.MSG_INFO_RESULT);

        this.statusCode = statusCode;
    }

    public InfoResultMessage(int statusCode, User user) {
        setType(Type.MSG_INFO_RESULT);

        this.statusCode = statusCode;
        this.user = user;
    }
}
