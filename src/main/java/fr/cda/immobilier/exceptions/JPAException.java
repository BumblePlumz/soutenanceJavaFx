package fr.cda.immobilier.exceptions;

public class JPAException extends RuntimeException {
    public JPAException(String message, Throwable cause) {
        super(message, cause);
    }
}
