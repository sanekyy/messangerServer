package ru.spbstu.telematics.messangerServer.store;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */

@Getter
@Setter
public class User {
    private Long id;

    private String login;

    private String password;

    private String token;
}
