package ru.spbstu.telematics.messangerServer.logic.commands;

import ru.spbstu.telematics.messangerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messangerServer.messages.Message;
import ru.spbstu.telematics.messangerServer.network.Session;

/**
 * Created by ihb on 14.06.17.
 */
public class ChatList implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

    }
}
