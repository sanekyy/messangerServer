package ru.spbstu.telematics.messengerServer;

import ru.spbstu.telematics.messengerServer.exceptiopns.ProtocolException;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.network.IProtocol;
import ru.spbstu.telematics.messengerServer.network.Session;
import ru.spbstu.telematics.messengerServer.network.StringProtocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * Created by ihb on 17.06.17.
 */
public class Main {

    static IProtocol protocol = new StringProtocol();

    static Map<SocketChannel, Session> sessionMap = new HashMap<>();

    static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws IOException, ProtocolException {

        Selector selector = Selector.open();
        ServerSocketChannel socket = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(AppConfig.HOST, AppConfig.PORT);


        socket.bind(address);

        // TODO: 13.06.17 what is it?
        socket.configureBlocking(false);

        int ops = socket.validOps();
        SelectionKey selectKey = socket.register(selector, ops, null);

        log("server running");
        while (true) {

            log("waiting message..");
            selector.select();

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();

                if (key.isAcceptable()) {
                    SocketChannel client = socket.accept();

                    // TODO: 13.06.17 what is it?
                    client.configureBlocking(false);

                    client.register(selector, SelectionKey.OP_READ);
                    log("Connection Accepted: " + client.getRemoteAddress() + "\n");

                    sessionMap.put(client, new Session(client.socket()));
                } else if (key.isReadable()) {

                    ByteBuffer buffer = ByteBuffer.allocate(AppConfig.BUFFER_SIZE);

                    SocketChannel client = (SocketChannel) key.channel();

                    if (client.read(buffer) == -1) {
                        log("Connection closed " + client.getRemoteAddress());
                        sessionMap.remove(client);
                        client.finishConnect();
                        client.close();
                    } else {
                        executor.execute(() -> handle(client, buffer));
                    }

                }

                it.remove();
            }
        }
    }

    private static void handle(SocketChannel client, ByteBuffer buffer) {
        Message message = null;

        try {
            message = protocol.decode(buffer.array());
        } catch (ProtocolException e) {
            e.printStackTrace();
            return;
        }

        log("get " + message);

        getSession(client).onMessage(message);
    }

    private static void log(String str) {
        System.out.println(str);
    }

    private static Session getSession(SocketChannel socketChannel) {
        return sessionMap.computeIfAbsent(socketChannel, c -> new Session(c.socket()));
    }
}
