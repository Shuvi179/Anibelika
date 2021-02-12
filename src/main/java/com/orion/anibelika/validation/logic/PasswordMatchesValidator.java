package com.orion.anibelika.validation.logic;

import com.orion.anibelika.dto.PasswordResetDTO;
import com.orion.anibelika.dto.RegisterUserDTO;
import com.orion.anibelika.dto.UpdatePasswordDTO;
import com.orion.anibelika.validation.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        // No need to do something.
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof RegisterUserDTO) {
            RegisterUserDTO user = (RegisterUserDTO) obj;
            return user.getPassword().equals(user.getMatchingPassword());
        } else if (obj instanceof UpdatePasswordDTO) {
            UpdatePasswordDTO dto = (UpdatePasswordDTO) obj;
            return dto.getNewPassword().equals(dto.getMatchingPassword());
        } else {
            PasswordResetDTO dto = (PasswordResetDTO) obj;
            return dto.getPassword().equals(dto.getMatchingPassword());
        }
    }
}
