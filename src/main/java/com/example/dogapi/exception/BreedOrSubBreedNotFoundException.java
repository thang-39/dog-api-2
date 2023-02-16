package com.example.dogapi.exception;

public class BreedOrSubBreedNotFoundException extends RuntimeException{
    public BreedOrSubBreedNotFoundException(String breed, String subBreed) {
        super("The breed: '" + breed + "' OR sub-breed: '" + subBreed +
                        "' does not exits in our records");
    }
}
