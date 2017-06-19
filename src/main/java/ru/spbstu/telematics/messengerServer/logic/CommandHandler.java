package ru.spbstu.telematics.messengerServer.logic;

import ru.spbstu.telematics.messengerServer.exceptiopns.CommandException;
import ru.spbstu.telematics.messengerServer.logic.commands.*;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.network.Session;

/**
 * Created by ihb on 13.06.17.
 */
public class CommandHandler {

    private static ICommand registration = new Registration();
    private static ICommand login = new Login();
    private static ICommand sendMessage = new SendMessage();
    private static ICommand info = new Info();
    private static ICommand chatList = new ChatList();
    private static ICommand chatCreate = new ChatCreate();
    private static ICommand chatHist = new ChatHist();

    public static void registration(Session session, Message message){
        try {
            registration.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public static void login(Session session, Message message) {
        try {
            login.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public static void info(Session session, Message message) {
        try {
            info.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}
