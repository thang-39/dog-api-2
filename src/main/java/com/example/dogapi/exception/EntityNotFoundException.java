package com.example.dogapi.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(long id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() +
                " with id '" + id + "'" +
                " does not exits in our records");
    }
}
