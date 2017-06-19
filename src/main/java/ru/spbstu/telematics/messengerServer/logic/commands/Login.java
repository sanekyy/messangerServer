package ru.spbstu.telematics.messengerServer.logic.commands;


import ru.spbstu.telematics.messengerServer.data.DataManager;
import ru.spbstu.telematics.messengerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.LoginMessage;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.StatusMessage;
import ru.spbstu.telematics.messengerServer.network.Session;
import ru.spbstu.telematics.messengerServer.data.storage.models.User;
import ru.spbstu.telematics.messengerServer.data.storage.UserStore;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by ihb on 13.06.17.
 */
public class Login implements ICommand {

    private Random rand = new Random(System.currentTimeMillis());

    private UserStore userStore = DataManager.getInstance().getUserStore();

    @Override
    public void execute(Session session, Message message) throws CommandException {
        if (session.isLoggedIn(message)) {
            throw new CommandException("Already logged in");
        }

        LoginMessage loginMessage = (LoginMessage) message;

        User user = userStore.getUserByLoginAndPassword(
                loginMessage.getLogin(),
                loginMessage.getPassword()
        );

        if(user != null){ // generate token
            user.setToken(new BigInteger(130, rand).toString(32));
            userStore.updateUser(user);
            user.setPassword("");
        }

        session.setUser(user);

        session.send(new StatusMessage(
                user == null ? StatusMessage.LOGIN_ERROR : StatusMessage.LOGIN_SUCCESS,
                user
        ));
    }
}
