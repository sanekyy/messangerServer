package ru.spbstu.telematics.messengerServer.logic.commands;

import ru.spbstu.telematics.messengerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.network.Session;

/**
 * Created by ihb on 14.06.17.
 */
public class ChatHist implements ICommand {
    @Override
    public void execute(Session session, Message message) throws CommandException {

    }
}
