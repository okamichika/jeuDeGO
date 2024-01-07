package fr.iut.jeudego.exception;

public class InvalidCoordException extends Exception {
    public InvalidCoordException() {
        super("The coordonate are incorrect.");
    }
}
