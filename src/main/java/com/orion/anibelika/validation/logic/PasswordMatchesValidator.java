package com.orion.anibelika.validation.logic;

import com.orion.anibelika.dto.RegisterUserDTO;
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
        RegisterUserDTO user = (RegisterUserDTO) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
