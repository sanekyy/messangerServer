package ru.spbstu.telematics.messengerServer;


import ru.spbstu.telematics.messengerServer.exceptions.ProtocolException;

import java.io.IOException;

/**
 * Created by ihb on 17.06.17.
 */
public class Main {
    public static void main(String[] args) throws IOException, ProtocolException {
        new Server().run();
    }
}
