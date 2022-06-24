package by.it.academy.onlinestore.annotation.impl;

import by.it.academy.onlinestore.annotation.Rule;
import by.it.academy.onlinestore.annotation.exception.ValidationException;

import java.lang.reflect.Field;
import java.util.Objects;

public class NotNullFieldRule implements Rule {
    @Override
    public void validate(Field field, Object object) {
        try {
            String fieldValue = (String) field.get(object);
            if (Objects.isNull(fieldValue) || fieldValue.isEmpty()) {
                throw new NullPointerException("Can't be null: " + field.getName());
            }
        } catch (IllegalAccessException e) {
            throw new ValidationException(e);
        }
    }
}
