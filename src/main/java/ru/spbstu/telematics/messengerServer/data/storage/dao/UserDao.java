package ru.spbstu.telematics.messengerServer.data.storage.dao;

import ru.spbstu.telematics.messengerServer.data.storage.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihb on 18.06.17.
 */
public class UserDao extends AbstractDao<User, Long> {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS\n" +
            "    `user` (\n" +
            "        `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "        `login` VARCHAR(50) NOT NULL UNIQUE,\n" +
            "        `password` VARCHAR(100) NOT NULL,\n" +
            "        `token` VARCHAR(100) NOT NULL,\n" +
            "        PRIMARY KEY(`id`)\n" +
            "    );\n";

    private static final String SELECT_ALL = "SELECT * FROM user";
    private static final String UPDATE_ONE = "UPDATE user SET login = ?, password = ?, token = ? WHERE id = ?";
    private static final String LOAD_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String DELETE_ONE = "DELETE FROM user WHERE id = ?";
    private static final String INSERT_ONE = "INSERT INTO user (login, password, token) VALUES (?, ?, ?)";
    private static final String LOAD_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user WHERE login = ? and password = ?";
    private static final String LOAD_BY_LOGIN = "SELECT * FROM user WHERE login = ?";


    public UserDao(){
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
    public List<User> loadAll() {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = getPrepareStatement(SELECT_ALL);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(parseOne(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        return users;
    }

    @Override
    public User update(User user) {
        PreparedStatement ps = getPrepareStatement(UPDATE_ONE);

        try {
            ps.setString(1, user.getLogin());
            ps.setString(2,user.getPassword());
            ps.setString(3, user.getToken());
            ps.setLong(4, user.getId());
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return user;
    }

    @Override
    public User load(Long id) {

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
    public boolean delete(User user) {

        PreparedStatement ps = getPrepareStatement(DELETE_ONE);

        try {
            ps.setLong(1, user.getId());
            return ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return false;
    }

    @Override
    public User insert(User user) {
        PreparedStatement ps = getPrepareStatement(INSERT_ONE);

        try {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getToken());
            ps.execute();
            return loadByLogin(user.getLogin());
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }
        return null;
    }

    public User loadByLoginAndPassword(String login, String password){

        PreparedStatement ps = getPrepareStatement(LOAD_BY_LOGIN_AND_PASSWORD);

        try {
            ps.setString(1, login);
            ps.setString(2, password);
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

    public User loadByLogin(String login){

        PreparedStatement ps = getPrepareStatement(LOAD_BY_LOGIN);

        try {
            ps.setString(1, login);
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


    private User parseOne(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(1));
        user.setLogin(rs.getString(2));
        user.setPassword(rs.getString(3));
        user.setToken(rs.getString(4));

        return user;
    }













}
