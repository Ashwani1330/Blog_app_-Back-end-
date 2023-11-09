package com.bcknd.blogapp.users;

import com.bcknd.blogapp.users.dtos.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UsersServiceTests {

    @Autowired UsersService usersService;

    @Test
    void can_create_users() {

        var user = usersService.createUser(new CreateUserRequest(
                "john",
                "password",
                "john132@blog.com"
        ));

        Assertions.assertNotNull(user);
        Assertions.assertEquals("john", user.getUsername());

    }
}
