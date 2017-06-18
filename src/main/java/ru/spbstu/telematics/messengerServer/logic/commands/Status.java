package ru.spbstu.telematics.messengerServer.logic.commands;

import ru.spbstu.telematics.messengerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messengerServer.messages.Message;
import ru.spbstu.telematics.messengerServer.messages.StatusMessage;
import ru.spbstu.telematics.messengerServer.network.Session;

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
