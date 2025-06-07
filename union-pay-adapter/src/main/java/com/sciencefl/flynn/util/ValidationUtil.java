package com.sciencefl.flynn.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Collection;
import java.util.Set;

public class ValidationUtil {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    // 校验单个对象
    public static <T> void validate(T obj) {
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getPropertyPath()).append(": ")
                        .append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }

    // 校验对象集合
    public static <T> void validate(Collection<T> collection) {
        int index = 0;
        for (T obj : collection) {
            Set<ConstraintViolation<T>> violations = validator.validate(obj);
            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("第").append(index).append("个对象: ");
                for (ConstraintViolation<T> violation : violations) {
                    sb.append(violation.getPropertyPath()).append(": ")
                            .append(violation.getMessage()).append("; ");
                }
                throw new IllegalArgumentException(sb.toString());
            }
            index++;
        }
    }
}
