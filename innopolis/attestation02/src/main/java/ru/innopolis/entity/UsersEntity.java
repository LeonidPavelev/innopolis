package ru.innopolis.entity;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {

    private Long userId;

    private String firstName;

    private String lastName;
}
