package ru.spbstu.telematics.messengerServer.data.storage.dao;

import ru.spbstu.telematics.messengerServer.data.storage.models.Chat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ihb on 20.06.17.
 */
public class ChatDao extends AbstractDao<Chat, Long> {

    private static final String CREATE_CHAT_TABLE = "CREATE TABLE IF NOT EXISTS\n" +
            "    `chat` (\n" +
            "        `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "        `admin_id` INT,\n" +
            "        PRIMARY KEY(`id`)\n" +
            "    );\n";

    private static final String CREATE_USER_IN_CHAT_TABLE = "CREATE TABLE IF NOT EXISTS\n" +
            "    `user_in_chat` (\n" +
            "        `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "        `user_id` INT NOT NULL,\n" +
            "        `chat_id` INT NOT NULL,\n" +
            "        PRIMARY KEY(`id`)\n" +
            "    );\n";

    private static final String CREATE_MESSAGE_TABLE = "CREATE TABLE IF NOT EXISTS\n" +
            "    `message` (\n" +
            "        `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "        `sender_id` INT NOT NULL,\n" +
            "        `chat_id` INT NOT NULL,\n" +
            "        `text` VARCHAR(300) NOT NULL UNIQUE,\n" +
            "        `timestamp` BIGINT NOT NULL,\n" +
            "        PRIMARY KEY(`id`)\n" +
            "    );\n";


    private static final String SELECT_ALL_CHAT = "SELECT * FROM chat";
    private static final String SELECT_MESSAGE_BY_CHAT_ID = "SELECT * FROM message WHERE chat_id = ?";
    private static final String SELECT_USER_BY_CHAT_ID = "SELECT * FROM user_in_chat WHERE chat_id = ?";
    private static final String SELECT_CHAT_BY_USER_ID = "SELECT * FROM user_in_chat WHERE user_id = ?";


    private static final String UPDATE_CHAT_ONE = "UPDATE chat SET admin = ? WHERE id = ?";
    private static final String LOAD_CHAT_BY_ID = "SELECT * FROM chat WHERE id = ?";
    private static final String DELETE_CHAT_ONE = "DELETE FROM chat WHERE id = ?";
    private static final String INSERT_CHAT_ONE = "INSERT INTO chat (admin_id) VALUES (?)";
    private static final String INSERT_USER_IN_CHAT_ONE = "INSERT INTO user_in_chat (user_id, chat_id) VALUES (?, ?)";

    public ChatDao(){
        createTablesIfNotExist();
    }

    private void createTablesIfNotExist(){
        PreparedStatement ps = getPrepareStatement(CREATE_CHAT_TABLE);
        try {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ps = getPrepareStatement(CREATE_MESSAGE_TABLE);
        try {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ps = getPrepareStatement(CREATE_USER_IN_CHAT_TABLE);
        try {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Chat> loadAll() {
        List<Chat> chats = new ArrayList<>();
        PreparedStatement ps = getPrepareStatement(SELECT_ALL_CHAT);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chats.add(parseChat(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        for(Chat chat : chats){
            List<Long> messages = new ArrayList<>();
            List<Long> participants = new ArrayList<>();


            // messages
            ps = getPrepareStatement(SELECT_MESSAGE_BY_CHAT_ID);
            try {
                ps.setLong(1, chat.getId());

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    messages.add(rs.getLong(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closePrepareStatement(ps);
            }

            // participants
            ps = getPrepareStatement(SELECT_USER_BY_CHAT_ID);
            try {
                ps.setLong(1, chat.getId());

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    participants.add(rs.getLong(2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closePrepareStatement(ps);
            }


            chat.setMessages(messages);
            chat.setParticipants(participants);
        }

        return chats;
    }

    @Override
    public Chat update(Chat chat) {
        PreparedStatement ps = getPrepareStatement(UPDATE_CHAT_ONE);
        try {
            ps.setLong(1, chat.getAdmin());
            ps.setLong(2, chat.getId());
            ps.execute();

            // TODO: 23.06.17 chat update change id
            return chat;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        return null;
    }

    @Override
    public Chat load(Long id) {

        Chat chat = new Chat();

        PreparedStatement ps = getPrepareStatement(LOAD_CHAT_BY_ID);
        try {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chat = parseChat(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closePrepareStatement(ps);
        }

        List<Long> messages = new ArrayList<>();
        List<Long> participants = new ArrayList<>();


        // messages
        ps = getPrepareStatement(SELECT_MESSAGE_BY_CHAT_ID);
        try {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        // participants
        ps = getPrepareStatement(SELECT_USER_BY_CHAT_ID);
        try {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                participants.add(rs.getLong(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        if(chat != null) {
            chat.setMessages(messages);
            chat.setParticipants(participants);
        }


        return chat;
    }


    public List<Chat> load(List<Long> chatsId) {
        return chatsId.stream().map(this::load).collect(Collectors.toList());
    }




    @Override
    public boolean delete(Chat chat) {

        PreparedStatement ps = getPrepareStatement(DELETE_CHAT_ONE);

        try {
            ps.setLong(1, chat.getId());
            return ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return false;
    }

    @Override
    public Chat insert(Chat chat) {
        PreparedStatement ps = getPrepareStatement(INSERT_CHAT_ONE);

        try {
            ps.setLong(1, chat.getAdmin());
            ps.execute();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    chat.setId(generatedKeys.getLong(1));
                }
                else {
                    chat.setId(-1L);
                    throw new SQLException("Creating chat failed, no ID obtained.");
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }


        for(Long participant : chat.getParticipants()) {
            insertUserInChat(participant, chat.getId());
        }

        return chat;
    }

    public List<Long> loadByUserId(Long userId) {
        List<Long> chats = new ArrayList<>();
        PreparedStatement ps = getPrepareStatement(SELECT_CHAT_BY_USER_ID);
        try {
            ps.setLong(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chats.add(rs.getLong(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        return chats;
    }

    public void insertUserInChat(Long userId, Long chatId) {
        PreparedStatement ps = getPrepareStatement(INSERT_USER_IN_CHAT_ONE);
        try {
            ps.setLong(1, userId);
            ps.setLong(2, chatId);
            ps.execute();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("Creating user in chat failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
    }

    private Chat parseChat(ResultSet rs) throws SQLException {
        Chat chat = new Chat();
        chat.setId(rs.getLong(1));
        chat.setAdmin(rs.getLong(2));

        return chat;
    }


}
