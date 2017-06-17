package ru.spbstu.telematics.messangerServer.logic.commands;

import ru.spbstu.telematics.messangerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messangerServer.messages.Message;
import ru.spbstu.telematics.messangerServer.messages.StatusMessage;
import ru.spbstu.telematics.messangerServer.network.Session;

/**
 * Created by ihb on 17.06.17.
 */
public class Status implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

        StatusMessage statusMessage = (StatusMessage) message;


        switch (statusMessage.getStatusCode()) {
            case StatusMessage.LOGIN_ERROR:
                System.out.println("Login error");
                break;
            case StatusMessage.STATUS_OK:
                System.out.print("Login success");
                break;
            default:
                throw new CommandException("Status Code unknown");
        }
    }
}
