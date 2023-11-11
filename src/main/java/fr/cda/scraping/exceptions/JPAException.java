package fr.cda.scraping.exceptions;

public class JPAException extends RuntimeException {
    public JPAException(String message, Throwable cause) {
        super(message, cause);
    }
}
