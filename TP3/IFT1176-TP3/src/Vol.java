/*
    Auteurs : Gueorgui Poklitar
    Matricule : 20251051
    But : La classe Vol représente les vols. 
    Elle gère les informations telles que l'identifiant du vol, la date, 
    la destination, le numéro de vol, les réservations, ainsi que l'avion associé à ce vol. 
  	Elle fournit des méthodes pour récupérer les informations d'un vol par 
  	son identifiant, obtenir la liste de tous les vols, ajouter un nouveau vol, mettre à 
  	jour les informations d'un vol existant et supprimer un vol de la base de données. 
  	Elle permet de calculer la disponibilité des sièges pour un vol donné.
    Travail Pratique 3
    Dernière Mise-à-jour : 14/07/2024
*/

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Déclaration de la classe Vol
public class Vol {
    private int id; // Identifiant du vol
    private LocalDate date; // Date du vol
    private int numero; // Numéro du vol
    private int reservations; // Nombre de réservations
    private String destination; // Destination du vol
    private int avionId; // Identifiant de l'avion associé
    private int capacity; // Capacité de l'avion
    private String modele; // Modèle de l'avion
    private String type; // Type de l'avion

    // Getters et Setters pour les attributs de la classe Vol
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public int getReservations() { return reservations; }
    public void setReservations(int reservations) { this.reservations = reservations; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public int getAvionId() { return avionId; }
    public void setAvionId(int avionId) { this.avionId = avionId; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    // Méthode pour obtenir la disponibilité des sièges
    public int getDisponibilite() {
        return capacity - reservations;
    }

    // Méthode pour récupérer un vol par son identifiant
    public static Vol getVolById(int id) throws SQLException {
        String query = "SELECT * FROM vols WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id); // Définir l'identifiant du vol à récupérer
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Vol vol = new Vol();
                    vol.setId(resultSet.getInt("id"));
                    vol.setDate(resultSet.getDate("date").toLocalDate());
                    vol.setNumero(resultSet.getInt("numero"));
                    vol.setReservations(resultSet.getInt("reservations"));
                    vol.setDestination(resultSet.getString("destination"));
                    vol.setAvionId(resultSet.getInt("avion_id"));
                    Avion avion = Avion.getAvionById(vol.getAvionId());
                    if (avion != null) {
                        vol.setCapacity(avion.getCapacity());
                        vol.setModele(avion.getModele());
                        vol.setType(avion.getType());
                    }
                    return vol; // Retourner le vol récupéré
                }
            }
        }
        return null; // Retourner null si aucun vol n'est trouvé
    }

    // Méthode pour récupérer tous les vols
    public static List<Vol> getAllVols() throws SQLException {
        String query = "SELECT * FROM vols";
        List<Vol> vols = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Vol vol = new Vol();
                vol.setId(resultSet.getInt("id"));
                vol.setDate(resultSet.getDate("date").toLocalDate());
                vol.setNumero(resultSet.getInt("numero"));
                vol.setReservations(resultSet.getInt("reservations"));
                vol.setDestination(resultSet.getString("destination"));
                vol.setAvionId(resultSet.getInt("avion_id"));
                Avion avion = Avion.getAvionById(vol.getAvionId());
                if (avion != null) {
                    vol.setCapacity(avion.getCapacity());
                    vol.setModele(avion.getModele());
                    vol.setType(avion.getType());
                }
                vols.add(vol); // Ajouter le vol à la liste des vols
            }
        }
        return vols; // Retourner la liste des vols
    }

    // Méthode pour ajouter un nouveau vol
    public static void addVol(Vol vol) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO vols (numero, date, reservations, destination, avion_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, vol.getNumero());
        statement.setDate(2, java.sql.Date.valueOf(vol.getDate()));
        statement.setInt(3, vol.getReservations());
        statement.setString(4, vol.getDestination());
        statement.setInt(5, vol.getAvionId());

        statement.executeUpdate(); // Exécuter la requête d'insertion

        statement.close();
        connection.close();
    }

    // Méthode pour mettre à jour un vol existant
    public static void updateVol(Vol vol) throws SQLException {
        String query = "UPDATE vols SET numero = ?, destination = ?, date = ?, avion_id = ?, reservations = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, vol.getNumero());
            pstmt.setString(2, vol.getDestination());
            pstmt.setDate(3, Date.valueOf(vol.getDate()));
            pstmt.setInt(4, vol.getAvionId());
            pstmt.setInt(5, vol.getReservations());
            pstmt.setInt(6, vol.getId());
            pstmt.executeUpdate(); // Exécuter la requête de mise à jour
        }
    }

    // Méthode pour récupérer les vols par numéro de vol
    public static List<Vol> getVolsByNumero(int numero) throws SQLException {
        List<Vol> vols = new ArrayList<>();
        String query = "SELECT v.id, v.numero, v.destination, v.date, v.avion_id, v.reservations, " +
                       "f.modele, f.type, f.capacity " +
                       "FROM vols v " +
                       "JOIN flotte f ON v.avion_id = f.id " +
                       "WHERE v.numero = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, numero); // Définir le numéro de vol à récupérer
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Vol vol = new Vol();
                    vol.setId(resultSet.getInt("id"));
                    vol.setNumero(resultSet.getInt("numero"));
                    vol.setDestination(resultSet.getString("destination"));
                    vol.setDate(resultSet.getDate("date").toLocalDate());
                    vol.setAvionId(resultSet.getInt("avion_id"));
                    vol.setReservations(resultSet.getInt("reservations"));
                    vol.setModele(resultSet.getString("modele"));
                    vol.setType(resultSet.getString("type"));
                    vol.setCapacity(resultSet.getInt("capacity"));
                    vols.add(vol); // Ajouter le vol à la liste des vols
                }
            }
        }
        return vols; // Retourner la liste des vols trouvés
    }

    // Méthode pour supprimer un vol par son identifiant
    public static void deleteVol(int id) throws SQLException {
        String query = "DELETE FROM vols WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id); // Définir l'identifiant du vol à supprimer
            statement.executeUpdate(); // Exécuter la requête de suppression
        }
    }

    // Méthode pour obtenir le prochain identifiant disponible
    public static int getNextId() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT MAX(id) AS max_id FROM vols";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        int nextId = 1;
        if (resultSet.next()) {
            nextId = resultSet.getInt("max_id") + 1;
        }

        resultSet.close();
        statement.close();
        connection.close();

        return nextId; // Retourner le prochain identifiant disponible
    }
    
    // Méthode pour obtenir le prochain identifiant auto-incrémenté disponible
    public static int getNextAvailableId() throws SQLException {
        String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'vols'";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("AUTO_INCREMENT");
            } else {
                throw new SQLException("Unable to retrieve the next auto-increment value.");
            }
        }
    }
}
