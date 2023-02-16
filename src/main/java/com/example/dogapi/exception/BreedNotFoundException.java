package com.example.dogapi.exception;

public class BreedNotFoundException extends RuntimeException{
    public BreedNotFoundException(String breedName) {
        super("The breed " + " with name/id: '" + breedName + "'" +
                " does not exits in our records");
    }
}
