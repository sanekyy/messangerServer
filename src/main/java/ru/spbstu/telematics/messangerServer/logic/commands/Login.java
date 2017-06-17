package ru.spbstu.telematics.messangerServer.logic.commands;


import ru.spbstu.telematics.messangerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messangerServer.messages.LoginMessage;
import ru.spbstu.telematics.messangerServer.messages.Message;
import ru.spbstu.telematics.messangerServer.messages.StatusMessage;
import ru.spbstu.telematics.messangerServer.network.Session;
import ru.spbstu.telematics.messangerServer.store.User;
import ru.spbstu.telematics.messangerServer.store.UserStorage;
import ru.spbstu.telematics.messangerServer.store.UserStore;

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
