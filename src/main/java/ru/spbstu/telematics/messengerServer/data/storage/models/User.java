package ru.spbstu.telematics.messengerServer.data.storage.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    private Long id;

    private String login;

    private String password;

    private String token = "";


    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

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
