package ru.spbstu.telematics.messengerServer.logic.commands;

import ru.spbstu.telematics.messengerServer.data.DataManager;
import ru.spbstu.telematics.messengerServer.data.storage.UserStore;
import ru.spbstu.telematics.messengerServer.data.storage.models.User;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.RegistrationMessage;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.StatusMessage;
import ru.spbstu.telematics.messengerServer.exceptions.CommandException;
import ru.spbstu.telematics.messengerServer.network.Session;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by ihb on 19.06.17.
 */

public class Registration implements ICommand {

    Random rand = new Random(System.currentTimeMillis());

    private UserStore userStore = DataManager.getInstance().getUserStore();

    @Override
    public void execute(Session session, Message message) throws CommandException {

        RegistrationMessage registrationMessage = (RegistrationMessage) message;

        UserStore userStore = DataManager.getInstance().getUserStore();

        if(userStore.getUserByLogin(registrationMessage.getLogin()) != null){
            message = new StatusMessage(StatusMessage.REGISTRATION_ERROR);
            session.send(message);
        } else { // create new User
            User user = new User(
                    registrationMessage.getLogin(),
                    registrationMessage.getPassword()
            );

            user.setToken(new BigInteger(130, rand).toString(32));

            user = userStore.addUser(user);

            user.setPassword("");

            session.setUser(user);

            message = new StatusMessage(StatusMessage.REGISTRATION_SUCCESS, user);
            session.send(message);
        }
    }
}
