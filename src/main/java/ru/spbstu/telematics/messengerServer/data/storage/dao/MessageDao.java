package ru.spbstu.telematics.messengerServer.data.storage.dao;

import ru.spbstu.telematics.messengerServer.data.storage.models.User;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.Message;
import ru.spbstu.telematics.messengerServer.data.storage.models.messages.TextMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihb on 18.06.17.
 */
public class MessageDao extends AbstractDao<TextMessage, Long> {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS\n" +
            "    `message` (\n" +
            "        `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "        `sender_id` INT NOT NULL,\n" +
            "        `chat_id` INT NOT NULL,\n" +
            "        `text` VARCHAR(300) NOT NULL,\n" +
            "        `timestamp` BIGINT NOT NULL,\n" +
            "        PRIMARY KEY(`id`)\n" +
            "    )\n";

    private static final String SELECT_ALL = "SELECT * FROM message";
    private static final String UPDATE_ONE = "UPDATE message SET sender_id = ?, chat_id = ?, text = ?, timestamp = ? WHERE id = ?";
    private static final String LOAD_BY_ID = "SELECT * FROM message WHERE id = ?";
    private static final String LOAD_BY_CHAT_ID = "SELECT * FROM message WHERE chat_id = ?";
    private static final String DELETE_ONE = "DELETE FROM message WHERE id = ?";
    private static final String INSERT_ONE = "INSERT INTO message (sender_id, chat_id, text, timestamp) VALUES (?, ?, ?, ?)";


    public MessageDao(){
        createTableIfNotExist();
    }

    private void createTableIfNotExist(){
        PreparedStatement ps = getPrepareStatement(CREATE_TABLE);
        try {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<TextMessage> loadAll() {
        List<TextMessage> messages = new ArrayList<>();
        PreparedStatement ps = getPrepareStatement(SELECT_ALL);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(parseOne(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        return messages;
    }

    @Override
    public TextMessage update(TextMessage textMessage) {
        PreparedStatement ps = getPrepareStatement(UPDATE_ONE);

        try {
            ps.setLong(1, textMessage.getSenderId());
            ps.setLong(2,textMessage.getChatId());
            ps.setString(3, textMessage.getText());
            ps.setLong(4, textMessage.getTimestamp());
            ps.setLong(5, textMessage.getId());
            return parseOne(ps.executeQuery());
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return textMessage;
    }

    @Override
    public TextMessage load(Long id) {
        PreparedStatement ps = getPrepareStatement(LOAD_BY_ID);

        try {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return parseOne(rs);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return null;
    }

    @Override
    public boolean delete(TextMessage textMessage) {
        PreparedStatement ps = getPrepareStatement(DELETE_ONE);

        try {
            ps.setLong(1, textMessage.getId());
            return ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return false;
    }

    @Override
    public TextMessage insert(TextMessage textMessage) {
        PreparedStatement ps = getPrepareStatement(INSERT_ONE);

        try {
            ps.setLong(1, textMessage.getSenderId());
            ps.setLong(2, textMessage.getChatId());
            ps.setString(3, textMessage.getText());
            ps.setLong(4, textMessage.getTimestamp());
            ps.execute();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    textMessage.setId(generatedKeys.getLong(1));
                }
                else {
                    textMessage.setId(-1L);
                    throw new SQLException("Creating chat failed, no ID obtained.");
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        return textMessage;
    }

    public List<Long> loadByChatId(Long chatId) {
        List<Long> messages = new ArrayList<>();

        PreparedStatement ps = getPrepareStatement(LOAD_BY_CHAT_ID);
        try {
            ps.setLong(1, chatId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                messages.add(rs.getLong(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        return messages;
    }

    private TextMessage parseOne(ResultSet rs) throws SQLException {
        TextMessage textMessage = new TextMessage();
        textMessage.setId(rs.getLong(1));
        textMessage.setSenderId(rs.getLong(2));
        textMessage.setChatId(rs.getLong(3));
        textMessage.setText(rs.getString(4));
        textMessage.setTimestamp(rs.getLong(5));

        return textMessage;
    }
}
