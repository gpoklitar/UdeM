/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : La classe Avion représente les avions de la flotte.
	Elle gère les infos comme l'id, le modèle, le type et la capacité.
	Elle permet aussi de récupérer, ajouter, mettre à jour et supprimer des avions dans la base de données.
	Travail Pratique 3
	Dernière Mise-à-jour : 14/07/2024
*/

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Déclaration de la classe Avion
public class Avion {
    private int id; // Identifiant de l'avion
    private String modele; // Modèle de l'avion
    private String type; // Type de l'avion
    private int capacity; // Capacité de l'avion

    // Getters et Setters pour les attributs de la classe Avion
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    
    // Méthode pour récupérer un avion par son identifiant
    public static Avion getAvionById(int id) throws SQLException {
        String query = "SELECT * FROM flotte WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id); // Définir l'identifiant de l'avion à récupérer
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Avion avion = new Avion();
                    avion.setId(resultSet.getInt("id"));
                    avion.setModele(resultSet.getString("modele"));
                    avion.setType(resultSet.getString("type"));
                    avion.setCapacity(resultSet.getInt("capacity"));
                    return avion; // Retourner l'avion récupéré
                }
            }
        }
        return null; // Retourner null si aucun avion n'est trouvé
    }

    // Méthode pour récupérer tous les avions
    public static List<Avion> getAllAvions() throws SQLException {
        String query = "SELECT * FROM flotte";
        List<Avion> avions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Avion avion = new Avion();
                avion.setId(resultSet.getInt("id"));
                avion.setModele(resultSet.getString("modele"));
                avion.setType(resultSet.getString("type"));
                avion.setCapacity(resultSet.getInt("capacity"));
                avions.add(avion); // Ajouter l'avion à la liste des avions
            }
        }
        return avions; // Retourner la liste des avions
    }

    // Méthode pour récupérer tous les modèles d'avions distincts
    public static List<String> getAllModeles() throws SQLException {
        String query = "SELECT DISTINCT modele FROM flotte";
        List<String> modeles = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                modeles.add(resultSet.getString("modele")); // Ajouter le modèle à la liste des modèles
            }
        }
        return modeles; // Retourner la liste des modèles
    }

    // Méthode pour obtenir le nombre de places pour un modèle et un type d'avion
    public static int getPlacesForModele(String modele, String type) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT capacity FROM flotte WHERE modele = ? AND type = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, modele);
        statement.setString(2, type);
        ResultSet resultSet = statement.executeQuery();
        int places = 0;
        if (resultSet.next()) {
            places = resultSet.getInt("capacity"); // Récupérer la capacité de l'avion
        }

        resultSet.close();
        statement.close();
        connection.close();

        return places; // Retourner le nombre de places
    }

    // Méthode pour obtenir le nombre de places pour un modèle et un type d'avion
    public static int getPlacesForModeleAndType(String modele, String type) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT capacity FROM flotte WHERE modele = ? AND type = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, modele);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("capacity"); // Récupérer la capacité de l'avion
            } else {
                throw new SQLException("Avion not found");
            }
        } finally {
            conn.close();
        }
    }

    // Méthode pour obtenir le type d'un avion pour un modèle donné
    public static String getTypeForModele(String modele) throws SQLException {
        String query = "SELECT type FROM flotte WHERE modele = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, modele);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("type"); // Retourner le type de l'avion
                }
            }
        }
        return null; // Retourner null si aucun type n'est trouvé
    }

    // Méthode pour obtenir les modèles d'avions disponibles à une date donnée
    public static List<String> getAvailableModelesByDate(LocalDate date) throws SQLException {
        List<String> modeles = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT modele, type FROM flotte WHERE id NOT IN (SELECT avion_id FROM vols WHERE date = ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDate(1, java.sql.Date.valueOf(date));
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String modele = resultSet.getString("modele");
            String type = resultSet.getString("type");
            modeles.add(modele + " - " + type); // Ajouter le modèle et le type à la liste des modèles disponibles
        }

        resultSet.close();
        statement.close();
        connection.close();

        return modeles; // Retourner la liste des modèles disponibles
    }

    // Méthode pour obtenir l'identifiant d'un avion par son modèle et son type
    public static int getAvionIdByModeleAndType(String modele, String type) throws SQLException {
        String query = "SELECT id FROM flotte WHERE modele = ? AND type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, modele);
            pstmt.setString(2, type);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // Retourner l'identifiant de l'avion
            }
        }
        return -1; // Retourner -1 si aucun avion n'est trouvé
    }

    // Méthode pour obtenir les modèles d'avions disponibles à une date donnée, incluant le vol actuel
    public static List<String> getAvailableModelesByDateIncludingCurrent(LocalDate date, int currentVolId) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT f.modele, f.type FROM flotte f LEFT JOIN vols v ON f.id = v.avion_id AND v.date = ? WHERE v.id IS NULL OR v.id = ?");
        stmt.setDate(1, java.sql.Date.valueOf(date));
        stmt.setInt(2, currentVolId);
        ResultSet rs = stmt.executeQuery();
        List<String> availableModeles = new ArrayList<>();
        while (rs.next()) {
            availableModeles.add(rs.getString("modele") + " - " + rs.getString("type")); // Ajouter le modèle et le type à la liste des modèles disponibles
        }
        return availableModeles; // Retourner la liste des modèles disponibles
    }

    // Méthode pour obtenir le prochain identifiant disponible
    public static int getNextId() throws SQLException {
        String query = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'bdvols' AND TABLE_NAME = 'flotte'";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("AUTO_INCREMENT"); // Retourner le prochain identifiant auto-incrémenté
            }
        }
        return 0;
    }

    // Méthode pour ajouter un nouvel avion
    public static void addAvion(Avion avion) throws SQLException {
        String query = "INSERT INTO flotte (modele, type, capacity) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, avion.getModele());
            statement.setString(2, avion.getType());
            statement.setInt(3, avion.getCapacity());
            statement.executeUpdate(); // Exécuter la requête d'insertion
        }
    }

    // Méthode pour mettre à jour un avion existant
    public static void updateAvion(Avion avion) throws SQLException {
        String query = "UPDATE flotte SET modele = ?, type = ?, capacity = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, avion.getModele());
            statement.setString(2, avion.getType());
            statement.setInt(3, avion.getCapacity());
            statement.setInt(4, avion.getId());
            statement.executeUpdate(); // Exécuter la requête de mise à jour
        }
    }

    // Méthode pour vérifier si un avion est utilisé dans un vol
    public static boolean isAvionUsed(int avionId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT COUNT(*) FROM vols WHERE avion_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, avionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Retourner true si l'avion est utilisé
            }
        }
        return false; // Retourner false si l'avion n'est pas utilisé
    }

    // Méthode pour supprimer un avion
    public static void deleteAvion(int id) throws SQLException {
        if (isAvionUsed(id)) {
            throw new SQLException("Cet avion est actuellement utilisé par un ou plusieurs vols.");
        }

        Connection connection = DatabaseConnection.getConnection();
        String query = "DELETE FROM flotte WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate(); // Exécuter la requête de suppression
        }
    }
}

