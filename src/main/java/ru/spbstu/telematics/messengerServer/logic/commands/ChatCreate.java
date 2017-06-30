package ru.spbstu.telematics.messengerServer.logic.commands;


import ru.spbstu.telematics.messengerServer.data.DataManager;
import ru.spbstu.telematics.messengerServer.data.storage.MessageStore;
import ru.spbstu.telematics.messengerServer.data.storage.UserStore;
import ru.spbstu.telematics.messengerServer.data.storage.models.Chat;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.ChatCreateMessage;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.StatusMessage;
import ru.spbstu.telematics.messengerServer.exceptions.CommandException;
import ru.spbstu.telematics.messengerServer.network.Session;

import java.util.Optional;

/**
 * Created by ihb on 14.06.17.
 */
public class ChatCreate implements ICommand {

    private UserStore userStore = DataManager.getInstance().getUserStore();
    private MessageStore messageStore = DataManager.getInstance().getMessageStore();

    @Override
    public void execute(Session session, Message message) throws CommandException {


        ChatCreateMessage chatCreateMessage = (ChatCreateMessage) message;

        if(userStore.getUsersById(chatCreateMessage.getParticipants()).contains(null)){
            message = new StatusMessage(StatusMessage.USER_NOT_EXIST_ERROR);
        } else if(chatCreateMessage.getParticipants().size() == 2) {

            Optional<Chat> chatOptional = messageStore.getChatsById(messageStore.getChatsByUserId(chatCreateMessage.getParticipants().get(0)))
                    .stream()
                    .filter(chat -> chat.getParticipants().size() == 2)
                    .filter(chat -> chat.getParticipants().contains(chatCreateMessage.getParticipants().get(1)))
                    .findFirst();

            if(chatOptional.isPresent()) {
                message = new StatusMessage(StatusMessage.CHAT_CREATE_ERROR, chatOptional.get().getId());
            } else {
                Chat chat = new Chat();

                chat.setAdmin(chatCreateMessage.getSenderId());
                chat.setParticipants(chatCreateMessage.getParticipants());


                chat = messageStore.createChat(chat);

                if (chat.getId() != -1L) {
                    message = new StatusMessage(StatusMessage.CHAT_CREATE_SUCCESS);
                } else {
                    message = new StatusMessage(StatusMessage.CHAT_CREATE_ERROR);
                }
            }
        } else {
            Chat chat = new Chat();

            chat.setAdmin(chatCreateMessage.getSenderId());
            chat.setParticipants(chatCreateMessage.getParticipants());


            chat = messageStore.createChat(chat);

            if (chat.getId() != -1L) {
                message = new StatusMessage(StatusMessage.CHAT_CREATE_SUCCESS);
            } else {
                message = new StatusMessage(StatusMessage.CHAT_CREATE_ERROR);
            }
        }

        session.send(message);
    }





}
