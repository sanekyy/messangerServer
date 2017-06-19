package ru.spbstu.telematics.messengerServer.logic.commands;

import ru.spbstu.telematics.messengerServer.data.DataManager;
import ru.spbstu.telematics.messengerServer.data.storage.UserStore;
import ru.spbstu.telematics.messengerServer.data.storage.models.User;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.InfoMessage;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.InfoResultMessage;
import ru.spbstu.telematics.messengerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.network.Session;

/**
 * Created by ihb on 14.06.17.
 */
public class Info implements ICommand {

    UserStore userStore = DataManager.getInstance().getUserStore();

    @Override
    public void execute(Session session, Message message) throws CommandException {

        if(!session.isLoggedIn(message)){
            session.send(new InfoResultMessage(InfoResultMessage.PERMISSION_DENIED));
            return;
        }

        InfoMessage infoMessage = (InfoMessage) message;

        User user = userStore.getUserById(infoMessage.getRequiredId());

        if(user == null){
            session.send(new InfoResultMessage(InfoResultMessage.USER_NOT_FOUND));
        } else {
            session.send(new InfoResultMessage(InfoResultMessage.STATUS_OK, user));
        }
    }
}
