package com.example.dogapi.exception;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String nameOrId) {
        super("The image " + " with name/id: '" + nameOrId + "'" +
                " does not exits in our records");
    }
}
