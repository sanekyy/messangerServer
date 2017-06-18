package ru.spbstu.telematics.messengerServer.store;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 */

@Getter
@Setter
public class User implements Serializable {

    private Long id;

    private String login;

    private String password;

    private String token;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
