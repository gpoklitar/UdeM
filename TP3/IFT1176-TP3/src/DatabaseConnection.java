/*
    Auteurs : Gueorgui Poklitar
    Matricule : 20251051
    But : La classe DatabaseConnection gère la connexion à la base de données. 
    Elle fournit des méthodes pour se connecter, déconnecter et obtenir des 
    connexions pour les opérations de base de données.
    Travail Pratique 3
    Dernière Mise-à-jour : 14/07/2024
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Déclaration de la classe DatabaseConnection
public class DatabaseConnection {
    // URL de la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/bdvols";
    // Nom d'utilisateur pour la connexion à la base de données
    private static final String USER = "root";
    // Mot de passe pour la connexion à la base de données
    private static final String PASSWORD = "root";

    // Constructeur privé pour empêcher l'instantiation
    private DatabaseConnection() {
        // Constructeur privé pour empêcher l'instantiation
    }

    // Méthode pour obtenir une connexion à la base de données
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}