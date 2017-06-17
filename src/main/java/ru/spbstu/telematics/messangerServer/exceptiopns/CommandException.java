package ru.spbstu.telematics.messangerServer.exceptiopns;

/**
 * Created by ihb on 13.06.17.
 */
public class CommandException extends Exception {

    public CommandException(String message) {
        super(message);
    }
}
