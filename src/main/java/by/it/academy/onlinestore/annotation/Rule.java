package by.it.academy.onlinestore.annotation;

import java.lang.reflect.Field;

public interface Rule {
    void validate(Field field, Object object);
}
