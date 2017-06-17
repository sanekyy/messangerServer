package ru.spbstu.telematics.messangerServer.network;

import ru.spbstu.telematics.messangerServer.exceptiopns.ProtocolException;
import ru.spbstu.telematics.messangerServer.messages.Message;

/**
 *
 */
public interface IProtocol {

    Message decode(byte[] bytes) throws ProtocolException;

    byte[] encode(Message msg) throws ProtocolException;

}
