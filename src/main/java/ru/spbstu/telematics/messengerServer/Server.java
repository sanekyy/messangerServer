package ru.spbstu.telematics.messengerServer;

import ru.spbstu.telematics.messengerServer.data.DataManager;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.exceptions.ProtocolException;
import ru.spbstu.telematics.messengerServer.network.IProtocol;
import ru.spbstu.telematics.messengerServer.network.Session;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static ru.spbstu.telematics.messengerServer.AppConfig.DEBUG;

/**
 * Created by ihb on 23.06.17.
 */
public class Server {

    private IProtocol protocol = DataManager.getInstance().getProtocol();

    private static Map<SelectionKey, Session> sessionMap = new HashMap<>();

    private Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private Queue<ByteBuffer> availableBuffers = new ConcurrentLinkedQueue<>();
    private Queue<ByteBuffer> usedBuffers = new ConcurrentLinkedQueue<>();

    void run() throws IOException, ProtocolException {
        Selector selector = Selector.open();
        ServerSocketChannel socket = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(AppConfig.PORT);

        socket.bind(address);

        socket.configureBlocking(false);

        int ops = socket.validOps();
        SelectionKey selectKey = socket.register(selector, ops, null);

        log("server running");
        while (true) {

            log("\nwaiting message..");
            selector.select();

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();

                if (key.isAcceptable()) {
                    SocketChannel client = socket.accept();

                    client.configureBlocking(false);

                    client.register(selector, SelectionKey.OP_READ);
                    log("Connection Accepted: " + client.getRemoteAddress() + "\n");

                    sessionMap.put(key, new Session(key));
                } else if (key.isReadable()) {
                    ByteBuffer buffer;
                    if (availableBuffers.size() == 0) {
                        buffer = ByteBuffer.allocate(AppConfig.BUFFER_SIZE);
                        usedBuffers.add(buffer);
                    } else {
                        buffer = availableBuffers.peek();
                        usedBuffers.add(buffer);
                    }

                    SocketChannel client = (SocketChannel) key.channel();

                    if (client.read(buffer) == -1) {
                        log("Connection closed " + client.getRemoteAddress());
                        sessionMap.get(key).close();
                        sessionMap.remove(key);
                    } else {
                        executor.execute(() -> handle(key, buffer));
                    }
                } else if (key.isWritable()) {
                    Queue<ByteBuffer> bufferQueue = getSession(key).getBufferToSendQueue();

                    ByteBuffer buffer;

                    while ((buffer = bufferQueue.poll()) != null) {
                        ((SocketChannel) key.channel()).write(buffer);
                    }

                    key.interestOps(SelectionKey.OP_READ);
                }

                it.remove();
            }
        }
    }


    private void handle(SelectionKey key, ByteBuffer buffer) {
        Message message;

        try {
            message = protocol.decode(buffer.array());
        } catch (ProtocolException e) {
            if (DEBUG) {
                System.out.println(e.getMessage());
            }
            return;
        } finally {
            usedBuffers.remove(buffer);
            availableBuffers.add(buffer);
        }

        log("get " + message);

        getSession(key).onMessage(message);
    }

    private void log(String str) {
        System.out.println(str);
    }

    private Session getSession(SelectionKey key) {
        return sessionMap.computeIfAbsent(key, c -> new Session(key));
    }

    public static Map<SelectionKey, Session> getSessions() {
        return sessionMap;
    }
}
