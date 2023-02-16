package com.example.dogapi.exception;

public class DogNotFoundException extends RuntimeException {

    public DogNotFoundException(long id) {
        super("The dog " + " with name/id: '" + id + "'" +
                " does not exits in our records");
    }
}
