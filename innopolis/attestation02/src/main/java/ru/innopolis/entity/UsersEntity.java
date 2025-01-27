package ru.innopolis.entity;

import lombok.*;

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
