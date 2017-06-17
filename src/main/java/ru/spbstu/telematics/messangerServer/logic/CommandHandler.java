package ru.spbstu.telematics.messangerServer.logic;

import ru.spbstu.telematics.messangerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messangerServer.logic.commands.*;
import ru.spbstu.telematics.messangerServer.messages.Message;
import ru.spbstu.telematics.messangerServer.network.Session;

/**
 * Created by ihb on 13.06.17.
 */
public class CommandHandler {

    private static ICommand login = new Login();
    private static ICommand sendMessage = new SendMessage();
    private static ICommand info = new Info();
    private static ICommand chatList = new ChatList();
    private static ICommand chatCreate = new ChatCreate();
    private static ICommand chatHist = new ChatHist();

    public static void login(Session session, Message message) {
        try {
            login.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}
