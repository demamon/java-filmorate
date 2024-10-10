package ru.yandex.practicum.filmorate.validator.login;
import java.lang.annotation.*;

import jakarta.validation.Constraint;

@Constraint(validatedBy = LoginContainSpacesValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginContainSpaces {
    String message() default "Логин не должен содержать пробелы";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
