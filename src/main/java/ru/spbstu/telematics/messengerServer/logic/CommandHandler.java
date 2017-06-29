package ru.spbstu.telematics.messengerServer.logic;

import ru.spbstu.telematics.messengerServer.exceptions.CommandException;
import ru.spbstu.telematics.messengerServer.logic.commands.*;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.network.Session;

/**
 * Created by ihb on 13.06.17.
 */
public class CommandHandler {

    private static ICommand registration = new Registration();
    private static ICommand login = new Login();
    private static ICommand text = new Text();
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

    public static void chatList(Session session, Message message) {
        try {
            chatList.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public static void chatCreate(Session session, Message message) {
        try {
            chatCreate.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public static void chatHist(Session session, Message message) {
        try {
            chatHist.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public static void text(Session session, Message message) {
        try {
            text.execute(session, message);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}
