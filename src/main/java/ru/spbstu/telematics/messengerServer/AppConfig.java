package ru.spbstu.telematics.messengerServer;

/**
 * Created by ihb on 13.06.17.
 */
public class AppConfig {

    public static final boolean DEBUG = true;


    static final int PORT = 8080;
    static final String HOST = "localhost";

    static final int BUFFER_SIZE = 1024 * 64;



    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "aQmissyy";

    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/messenger?useSSL=false&serverTimezone=UTC";
}
