package ru.spbstu.telematics.messengerServer.store.DAO;

import ru.spbstu.telematics.messengerServer.store.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ihb on 18.06.17.
 */
public class UserController extends AbstractController<User, Integer> {
    public static final String SELECT_ALL_USERS = "SELECT * FROM SHEMA.USER";

    @Override
    public List<User> getAll() {
        List<User> lst = new LinkedList<>();
        PreparedStatement ps = getPrepareStatement(SELECT_ALL_USERS);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setToken(rs.getString(4));
                lst.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(ps);
        }

        return lst;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public User getEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(User entity) {
        return false;
    }
}
