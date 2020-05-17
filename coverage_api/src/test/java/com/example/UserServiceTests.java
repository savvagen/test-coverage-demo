package com.example;

import com.example.models.ApiResponse;
import com.example.models.User;
import com.example.services.UserService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


public class UserServiceTests extends TestBase{

    @Test
    public void shouldCreateUser(){
        User user = User.build().id(8)
                .username("new.user")
                .firstName("test")
                .lastName("test")
                .email("new.test@gmail.com")
                .password("secret")
                .userStatus(8).build();
        ApiResponse response = new UserService().createUser(user)
                .then().assertThat().statusCode(200)
                .and().extract().body().as(ApiResponse.class);
        assertThat(response.code).isEqualTo(200);
        assertThat(response.type).isEqualTo("unknown");
        assertThat(response.message).isEqualTo("8");

    }

    @Test
    public void shouldGetUser(){
        User user = new UserService().getUser("new.user")
                .then().assertThat().statusCode(200)
                .extract().body().as(User.class);
        assertThat(user.username).isEqualTo("new.user");
    }

    @Test
    public void shouldGetInvalidUser(){
        ApiResponse response = new UserService().getUser("none")
                .then().assertThat().statusCode(404)
                .and().extract().body().as(ApiResponse.class);
        assertThat(response.code).isEqualTo(1);
        assertThat(response.type).isEqualTo("error");
        assertThat(response.message).isEqualTo("User not found");

    }

}
