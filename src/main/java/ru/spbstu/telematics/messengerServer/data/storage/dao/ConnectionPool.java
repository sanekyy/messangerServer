package ru.spbstu.telematics.messengerServer.data.storage.dao;

import ru.spbstu.telematics.messengerServer.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by ihb on 18.06.17.
 */
class ConnectionPool {

    private static Vector<Connection> availableConnections = new Vector<>();
    private static Vector<Connection> usedConnections = new Vector<>();


    public static synchronized Connection retrieve() throws SQLException {
        Connection newConn = null;
        if (availableConnections.size() == 0) {
            try {
                newConn = DriverManager.getConnection(AppConfig.DB_URL, AppConfig.DB_USERNAME, AppConfig.DB_PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            newConn = availableConnections.lastElement();
            availableConnections.removeElement(newConn);
        }
        usedConnections.addElement(newConn);
        return newConn;
    }

    public static synchronized void putback(Connection c) throws NullPointerException {
        if (c != null) {
            if (usedConnections.removeElement(c)) {
                availableConnections.addElement(c);
            } else {
                throw new NullPointerException("Connection not in the usedConnections array");
            }
        }
    }
}