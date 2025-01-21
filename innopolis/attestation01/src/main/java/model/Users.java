package model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;
@Value
@Builder
@Jacksonized
public class Users {

    int userId;

    String firstName;

    String lastName;

    Timestamp createdAt;
}
