package fr.iut.jeudego.exception;

public class InvalidSizeException extends Exception {
    public InvalidSizeException() {
        super("The size is incorrect, it should be between 2 and 19.");
    }
}