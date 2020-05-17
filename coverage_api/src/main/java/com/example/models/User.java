package com.example.models;


import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
/*@Builder(setterPrefix = "with",
        builderMethodName = "build")*/
@Builder(builderMethodName = "build")
@Accessors(chain = true)
public class User {
    @Getter @Setter public int id;
    @Getter @Setter public String username;
    @Getter @Setter public String firstName;
    @Getter @Setter public String lastName;
    @Getter @Setter public String email;
    @Getter @Setter public String password;
    @Getter @Setter public String phone;
    @Getter @Setter public int userStatus;
}
