package ru.yandex.practicum.filmorate.validator.login;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LoginContainSpacesValidator implements ConstraintValidator<LoginContainSpaces, String> {

    @Override
    public void initialize(LoginContainSpaces constraintAnnotation) {
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        return login != null && !login.contains(" ") && !login.isBlank();
    }
}

