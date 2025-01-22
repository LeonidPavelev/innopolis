package entity;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;
@Value
@Builder
@Jacksonized
public class UsersEntity {

    int userId;

    String firstName;

    String lastName;

    Timestamp createdAt;
}
