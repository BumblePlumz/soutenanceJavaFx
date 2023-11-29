package fr.cda.immobilier.utils;

import fr.cda.immobilier.utils.tools.LoggerTools;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Classe de configuration des propriétés hibernate/JPA
 */
public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (Exception e) {
            LoggerTools.logFatal("Class Config : le fichier de configuration n'a pas été chargé correctement", e.getCause());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Mise à jour des propriétés de connexion à la base de données
     * @param values
     */
    public static void updateProperty(Map<String, String> values) {
        System.out.println("début update");
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            for (Map.Entry<String, String> entry : values.entrySet()) {
                properties.setProperty(entry.getKey(), entry.getValue());
            }
            try (OutputStream output = new FileOutputStream("src/main/resources/config.properties")){
                properties.store(output, null);
            }
        } catch (IOException e) {
            LoggerTools.logFatal("Class Config : le fichier de configuration n'a pas été sauvegardé correctement", e.getCause());
        }
    }
}
