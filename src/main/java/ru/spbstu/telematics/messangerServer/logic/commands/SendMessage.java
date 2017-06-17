package ru.spbstu.telematics.messangerServer.logic.commands;

import ru.spbstu.telematics.messangerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messangerServer.messages.Message;
import ru.spbstu.telematics.messangerServer.network.Session;

/**
 * Created by ihb on 14.06.17.
 */
public class SendMessage implements ICommand {

    @Override
    public void execute(Session ISession, Message message) throws CommandException {

    }
}
