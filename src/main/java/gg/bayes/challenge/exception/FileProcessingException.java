package gg.bayes.challenge.exception;

public class FileProcessingException extends Exception {

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileProcessingException(String message) {
        super(message);
    }
}
