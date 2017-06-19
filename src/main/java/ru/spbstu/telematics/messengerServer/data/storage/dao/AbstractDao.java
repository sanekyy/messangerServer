package ru.spbstu.telematics.messengerServer.data.storage.dao;

import ru.spbstu.telematics.messengerServer.data.storage.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ihb on 18.06.17.
 */
public abstract class AbstractDao<E, K> {
    private Connection connection;

    AbstractDao() {
        try {
            connection = ConnectionPool.retrieve();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract List<E> loadAll();
    public abstract E update(E entity);
    public abstract E load(K id);
    public abstract boolean delete(E entity);
    public abstract E insert(E entity);

    // Возвращения экземпляра Connection в пул соединений
    public void returnConnectionInPool() {
        //connectionPool.returnConnection(connection);
    }

    // Получение экземпляра PrepareStatement
    PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    // Закрытие PrepareStatement
    void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
