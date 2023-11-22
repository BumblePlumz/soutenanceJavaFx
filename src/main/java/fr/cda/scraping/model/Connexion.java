package fr.cda.scraping.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    // Informations de connexion à la base de données
    private static final String URL = "jdbc:mysql://home.bumblefamily.fr:3306/soutenance";
    private static final String UTILISATEUR = "bumbleplumz";
    private static final String MOT_DE_PASSE = "ticolaze56";

    public static Connection getConnexion() {
        Connection connexion = null;

        try {
            // Charger le pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("trying to connect...");
            // Établir la connexion
            connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);

            if (connexion != null) {
                System.out.println("Connexion réussie !");
            } else {
                System.out.println("Échec de la connexion.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connexion;
    }

    public static void closeConnexion(Connection connexion) {
        try {
            if (connexion != null && !connexion.isClosed()) {
                connexion.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
