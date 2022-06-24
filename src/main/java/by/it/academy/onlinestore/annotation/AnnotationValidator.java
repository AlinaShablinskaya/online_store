package by.it.academy.onlinestore.annotation;

import by.it.academy.onlinestore.annotation.exception.ValidationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class AnnotationValidator {
    private final Map<Class<? extends Annotation>, Rule> validationFunctions;
    private final Set<Class<? extends Annotation>> supportedFieldAnnotations;

    public AnnotationValidator(Map<Class<? extends Annotation>, Rule> validationFunctions) {
        this.validationFunctions = validationFunctions;
        supportedFieldAnnotations = this.validationFunctions.keySet();
    }

    public void validate(Object object) {

        if (Objects.isNull(object)) {
            throw new ValidationException("The object is null");
        }

        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(ValidBean.class)) {
            throw new ValidationException("The class "
                    + clazz.getSimpleName()
                    + " is not annotated");
        }

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
             supportedFieldAnnotations.stream()
                    .filter(field::isAnnotationPresent)
                    .map(validationFunctions::get)
                    .forEach(rule -> rule.validate(field, object));
        }
    }
}
