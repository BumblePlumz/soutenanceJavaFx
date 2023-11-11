package fr.cda.scraping.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerTools {
    private static final Logger errorLogger = LogManager.getLogger(LoggerTools.class);
    private static final Logger infosLogger = LogManager.getLogger(LoggerTools.class);
    private static final Logger fatalLogger = LogManager.getLogger(LoggerTools.class);

    public static void logError(String message, Throwable throwable) {
        errorLogger.error(message, throwable);
    }
    public static void logInfo(String message, Throwable throwable){
        infosLogger.error(message, throwable);
    }
    public static void logFatal(String message, Throwable throwable) {
        fatalLogger.fatal(message, throwable);
    }
}
