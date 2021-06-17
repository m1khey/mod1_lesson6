package org.example.app.exception;

public class UploadDownloadFileException extends Exception {

    private final String message;

    public UploadDownloadFileException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
