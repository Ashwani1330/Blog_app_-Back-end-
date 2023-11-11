package com.bcknd.blogapp.users;

import com.bcknd.blogapp.common.dtos.ErrorsResponse;
import com.bcknd.blogapp.users.dtos.CreateUserRequest;
import com.bcknd.blogapp.users.dtos.LoginUserRequest;
import com.bcknd.blogapp.users.dtos.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final ModelMapper modelMapper;

    public UsersController(UsersService usersService, ModelMapper modelMapper) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("")
    ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest request) {
        UserEntity savedUser = usersService.createUser(request);
        URI savedUserUri = URI.create("/users/" + savedUser.getId());
        return ResponseEntity.created(savedUserUri)
                .body(modelMapper.map(savedUser, UserResponse.class));
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request) {
        UserEntity savedUser = usersService.loginUser(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(modelMapper.map(savedUser, UserResponse.class));
    }

    @ExceptionHandler({
            UsersService.UserNotFoundException.class
    })
    ResponseEntity<ErrorsResponse> handleUserNotFoundException(Exception ex) {
        String message;
        HttpStatus status;

        if (ex instanceof UsersService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else {

            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorsResponse response = ErrorsResponse.builder()
                .message(message)
                .build();



        return ResponseEntity.status(status).body(response);
    }

}
