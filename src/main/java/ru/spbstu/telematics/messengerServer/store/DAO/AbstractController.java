package ru.spbstu.telematics.messengerServer.store.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ihb on 18.06.17.
 */
public abstract class AbstractController<E, K> {
    private Connection connection;
    //private ConnectionPool connectionPool;

    public AbstractController() {
        //connectionPool = ConnectionPool.getConnectionPool();
        //connection = connectionPool.getConnection();
    }

    public abstract List<E> getAll();
    public abstract E update(E entity);
    public abstract E getEntityById(K id);
    public abstract boolean delete(K id);
    public abstract boolean create(E entity);

    // Возвращения экземпляра Connection в пул соединений
    public void returnConnectionInPool() {
        //connectionPool.returnConnection(connection);
    }

    // Получение экземпляра PrepareStatement
    public PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    // Закрытие PrepareStatement
    public void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
