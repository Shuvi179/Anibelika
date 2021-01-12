package com.orion.anibelika.controller;

import com.orion.anibelika.dto.RegisterUserDTO;
import com.orion.anibelika.dto.UserDataDTO;
import com.orion.anibelika.service.RegistrationService;
import com.orion.anibelika.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    private final RegistrationService registrationService;
    private final UserService userService;

    public UserController(RegistrationService registrationService, UserService userService) {
        this.registrationService = registrationService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDataDTO> addNewUser(@RequestBody @Valid RegisterUserDTO registerUserDTO) {
        return new ResponseEntity<>(registrationService.registerUser(registerUserDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDataDTO> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserDataById(id), HttpStatus.OK);
    }

    @PutMapping
    public void updateUser(@RequestBody @Valid UserDataDTO userDataDTO) {
        userService.updateUser(userDataDTO);
    }

    @GetMapping(value = "/confirm")
    public void confirmUser(@RequestParam String uuid) {
        userService.confirmUser(uuid);
    }

}
