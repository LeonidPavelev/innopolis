package ru.innopolis.entity;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UsersEntity {

    private Long userId;

    private String firstName;

    private String lastName;

    private Timestamp createdAt;
}
