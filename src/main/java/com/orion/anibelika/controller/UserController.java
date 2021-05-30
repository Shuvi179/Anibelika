package com.orion.anibelika.controller;

import com.orion.anibelika.dto.PasswordResetDTO;
import com.orion.anibelika.dto.RegisterUserDTO;
import com.orion.anibelika.dto.UpdatePasswordDTO;
import com.orion.anibelika.dto.UserDTO;
import com.orion.anibelika.service.RegistrationService;
import com.orion.anibelika.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;

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
    @Operation(summary = "Add new user")
    public ResponseEntity<UserDTO> addNewUser(@RequestBody @Valid RegisterUserDTO registerUserDTO) {
        return new ResponseEntity<>(registrationService.registerUser(registerUserDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserDataById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/confirm")
    @Operation(summary = "Confirm user account")
    public void confirmUser(@RequestParam String uuid) {
        userService.confirmUser(uuid);
    }

    @GetMapping(value = "/current")
    @Operation(summary = "Get current user")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "Get image for user by id")
    public byte[] getUserImage(@PathVariable @Min(1) Long id) {
        return userService.getUserImage(id);
    }

    @GetMapping(value = "/{id}/image/small", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "Get small image for user by id")
    public byte[] getSmallUserImage(@PathVariable @Min(1) Long id) {
        return userService.getSmallUserImage(id);
    }

    @PostMapping(value = "/{id}/image")
    @Operation(summary = "Update user image by id")
    public void addUserImage(@PathVariable @Min(1) Long id, @RequestParam("file") MultipartFile file) throws IOException {
        userService.saveUserImage(id, file.getBytes());
    }

    @PostMapping(value = "/reset")
    @Operation(summary = "Start password reset process")
    public void startResetProcess(@RequestParam String email) {
        userService.startResetPasswordProcess(email);
    }

    @PutMapping(value = "/reset")
    @Operation(summary = "Reset user password")
    public void resetUserPassword(@RequestParam String uuid, @RequestBody @Valid PasswordResetDTO dto) {
        userService.resetUserPassword(uuid, dto);
    }

    @PutMapping(value = "/{userId}/email/{email}")
    @Operation(summary = "update Email (Only for development)")
    public void updateEmail(@PathVariable Long userId, @PathVariable String email) {
        userService.updateEmail(userId, email);
    }

    @PostMapping(value = "/resend/{email}")
    @Operation(summary = "Resend email message")
    public void resetUserPassword(@PathVariable String email) {
        registrationService.resendEmailMessage(email);
    }

    @PutMapping(value = "/password")
    @Operation(summary = "Update user password")
    public void updateUserPassword(@RequestBody UpdatePasswordDTO dto) {
        userService.updateUserPassword(dto);
    }

    @PutMapping(value = "/nickname/{nickName}")
    @Operation(summary = "Update user nickName")
    public void updateUserNickName(@PathVariable String nickName) {
        userService.updateUserNickName(nickName);
    }
}
