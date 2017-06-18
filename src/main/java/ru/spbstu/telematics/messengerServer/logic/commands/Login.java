package ru.spbstu.telematics.messengerServer.logic.commands;


import ru.spbstu.telematics.messengerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messengerServer.messages.LoginMessage;
import ru.spbstu.telematics.messengerServer.messages.Message;
import ru.spbstu.telematics.messengerServer.messages.StatusMessage;
import ru.spbstu.telematics.messengerServer.network.Session;
import ru.spbstu.telematics.messengerServer.store.User;
import ru.spbstu.telematics.messengerServer.store.UserStorage;
import ru.spbstu.telematics.messengerServer.store.UserStore;

/**
 * Created by ihb on 13.06.17.
 */
public class Login implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        if (session.isLoggedIn()) {
            throw new CommandException("Already logged in");
        }

        LoginMessage loginMessage = (LoginMessage) message;

        UserStore userStore = new UserStorage();

        User user = userStore.getUser(
                loginMessage.getLogin(),
                loginMessage.getPassword()
        );

        session.send(new StatusMessage(
                user == null ? StatusMessage.LOGIN_ERROR : StatusMessage.STATUS_OK,
                user
        ));
    }
}
