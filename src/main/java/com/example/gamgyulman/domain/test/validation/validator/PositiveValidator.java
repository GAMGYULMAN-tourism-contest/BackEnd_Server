package com.example.gamgyulman.domain.test.validation.validator;

import com.example.gamgyulman.domain.test.validation.annotation.PositiveNum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PositiveValidator implements ConstraintValidator<PositiveNum, Integer> {

    @Override
    public void initialize(PositiveNum constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = integer > 0;

        if (!valid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("값이 양수가 아닙니다.").addConstraintViolation();
        }
        return valid;
    }
}
