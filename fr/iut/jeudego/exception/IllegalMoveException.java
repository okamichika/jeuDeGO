package fr.iut.jeudego.exception;

public class IllegalMoveException extends Exception {
    public IllegalMoveException() {
        super("This is an illegal move.");
    }
}
