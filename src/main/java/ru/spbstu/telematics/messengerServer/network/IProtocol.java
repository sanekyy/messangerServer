package ru.spbstu.telematics.messengerServer.network;

import ru.spbstu.telematics.messengerServer.exceptions.ProtocolException;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;

/**
 *
 */
public interface IProtocol {

    Message decode(byte[] bytes) throws ProtocolException;

    byte[] encode(Message msg) throws ProtocolException;

}
