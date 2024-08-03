/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Classe contenant le main().
	Cosmetique de l'interface, bouttons de l'application, connexion/interaction avec les fichier .json et serveur.
	Experience utilisateur. Utilise et relie tous les autres classes.
	Travail Pratique 2
	Dernière Mise-à-jour : 23/06/2024
*/



//Les importation de l'appli
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class MainApp {
    private GestionCompagnie gestionCompagnie;
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable flightTable;

 // Constructeur principal de l'application
    public MainApp() {
        gestionCompagnie = new GestionCompagnie(new Compagnie("")); // Nom de la compagnie sera défini plus tard
        frame = new JFrame("Système de Gestion des Vols"); // Création de la fenêtre principale
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermeture de l'application à la fermeture de la fenêtre
        frame.setSize(800, 600); // Taille de la fenêtre

        // Listener pour sauvegarder les vols lors de la fermeture de la fenêtre
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (gestionCompagnie.isFlightsLoaded()) { // Si les vols sont chargés
                        gestionCompagnie.sauvegarderVolsJSON("vols.json"); // Sauvegarde des vols en JSON
                    }
                } catch (IOException ex) {
                    ex.printStackTrace(); // Affichage de l'erreur si la sauvegarde échoue
                }
            }
        });

        createAndShowMainMenu(); // Affichage du menu principal
    }


    
    
 // Création et affichage du menu principal, cosmétique
    private void createAndShowMainMenu() {
        // On enlève tout le contenu de la fenêtre
        frame.getContentPane().removeAll();
        // On passe en mode GridBag pour la mise en page
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Espacement entre les composants

        // Titre avec le nom de la compagnie
        JLabel titleLabel = new JLabel("Aéroport de Montréal (YUL)");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0; // Colonne 0
        c.gridy = 0; // Ligne 0
        c.gridwidth = 2; // Prend deux colonnes
        frame.add(titleLabel, c); // On ajoute le titre

        // Bouton pour charger les vols
        JButton loadButton = new JButton("Charger les vols");
        loadButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFlightsFromURL(); // Charge les vols depuis une URL
            }
        });
        c.gridy = 1; // Ligne suivante
        c.gridwidth = 1; // Prend une colonne
        frame.add(loadButton, c); // Ajout du bouton

        // Bouton pour ajouter un vol
        JButton addButton = new JButton("Ajouter un vol");
        addButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowAddFlightMenu(); // Affiche le menu pour ajouter un vol
            }
        });
        c.gridx = 1; // Colonne suivante
        frame.add(addButton, c); // Ajout du bouton

        // Bouton pour modifier un vol
        JButton modifyButton = new JButton("Modifier un vol");
        modifyButton.setFont(new Font("Arial", Font.PLAIN, 16));
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowModifyFlightMenu(); // Affiche le menu pour modifier un vol
            }
        });
        c.gridy = 2; // Ligne suivante
        c.gridx = 0; // Retour à la première colonne
        frame.add(modifyButton, c); // Ajout du bouton

        // Bouton pour supprimer un vol
        JButton deleteButton = new JButton("Supprimer un vol");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 16));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowDeleteFlightMenu(); // Affiche le menu pour supprimer un vol
            }
        });
        c.gridx = 1; // Colonne suivante
        frame.add(deleteButton, c); // Ajout du bouton

        // Bouton pour rechercher un vol
        JButton searchButton = new JButton("Rechercher un vol");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowSearchFlightMenu(); // Affiche le menu pour rechercher un vol
            }
        });
        c.gridy = 3; // Ligne suivante
        c.gridx = 0; // Retour à la première colonne
        frame.add(searchButton, c); // Ajout du bouton

        // Bouton pour sauvegarder les vols
        JButton saveButton = new JButton("Sauvegarder");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 16));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFlights(); // Sauvegarde les vols
            }
        });
        c.gridx = 1; // Colonne suivante
        frame.add(saveButton, c); // Ajout du bouton

        // Crée la table des vols
        createFlightTable();

        frame.revalidate(); // On valide les changements
        frame.repaint(); // On redessine la fenêtre
        frame.setVisible(true); // Rendre la fenêtre visible
    }

    // Crée la table visuelle des vols, cosmétique
    private void createFlightTable() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Numéro");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Date");
        tableModel.addColumn("Modèle");
        tableModel.addColumn("Type");
        tableModel.addColumn("Places disponibles");

        flightTable = new JTable(tableModel); // Création de la table

        JTableHeader header = flightTable.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(label.getFont().deriveFont(Font.BOLD)); // Police en gras pour l'en-tête
                label.setHorizontalAlignment(JLabel.CENTER); // Centrer le texte
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK)); // Bordure noire
                return label;
            }
        });

        // Affichage des places disponibles en rouge si complet
        flightTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if ("COMPLET".equals(value)) {
                    cellComponent.setForeground(Color.RED); // Texte en rouge si complet
                    cellComponent.setFont(cellComponent.getFont().deriveFont(Font.BOLD)); // Police en gras
                } else {
                    cellComponent.setForeground(Color.BLACK); // Texte en noir sinon
                    cellComponent.setFont(cellComponent.getFont().deriveFont(Font.PLAIN)); // Police normale
                }
                return cellComponent;
            }
        });

        JScrollPane scrollPane = new JScrollPane(flightTable); // Ajoute la table dans un panneau déroulant
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; // Colonne 0
        c.gridy = 4; // Ligne 4
        c.gridwidth = 2; // Prend deux colonnes
        c.weightx = 1.0; // Prend toute la largeur
        c.weighty = 1.0; // Prend toute la hauteur
        c.fill = GridBagConstraints.BOTH; // Remplissage complet
        frame.add(scrollPane, c); // Ajout du panneau déroulant à la fenêtre
    }

    // Met à jour la table visuelle des vols, cosmétique
    private void updateFlightsTable() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                List<Vol> vols = gestionCompagnie.trierVols(); // Trie les vols
                tableModel.setRowCount(0); // Vide la table
                for (Vol vol : vols) {
                    // Ajoute chaque vol à la table
                    tableModel.addRow(new Object[]{
                            vol.getNumero(),
                            vol.getDestination(),
                            vol.getDate().toString(),
                            vol.getAvion().getModele(),
                            vol.getAvion().getType(),
                            vol.getSeatsAvailable() == 0 ? "COMPLET" : vol.getSeatsAvailable()
                    });
                }
                return null;
            }
        };
        worker.execute(); // Exécute le travailleur en arrière-plan
    }

    
    

 // Option ajouter un vol, cosmétique
    private void createAndShowAddFlightMenu() {
        // On vire tout ce qu'il y a dans la fenêtre
        frame.getContentPane().removeAll();
        // On passe en mode GridBag pour la mise en page
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Espacement entre les composants

        // Titre "Ajouter un vol" en gros et gras
        JLabel titleLabel = new JLabel("Ajouter un vol");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0; // Colonne 0
        c.gridy = 0; // Ligne 0
        c.gridwidth = 2; // Prend deux colonnes
        frame.add(titleLabel, c); // On l'ajoute à la fenêtre

        // Label pour le numéro de vol
        JLabel numLabel = new JLabel("Numéro de vol:");
        c.gridy = 1; // Ligne suivante
        c.gridwidth = 1; // Prend une colonne
        frame.add(numLabel, c); // Ajout du label

        // Champ de texte pour entrer le numéro de vol
        JTextField numField = new JTextField(10);
        c.gridx = 1; // Colonne suivante
        frame.add(numField, c); // Ajout du champ de texte

        // Label pour la destination
        JLabel destLabel = new JLabel("Destination:");
        c.gridx = 0; // Retour à la colonne 0
        c.gridy = 2; // Nouvelle ligne
        frame.add(destLabel, c); // Ajout du label

        // Champ de texte pour entrer la destination
        JTextField destField = new JTextField(10);
        c.gridx = 1; // Colonne suivante
        frame.add(destField, c); // Ajout du champ de texte

        // Label pour la date
        JLabel dateLabel = new JLabel("Date (JJ-MM-AAAA):");
        c.gridx = 0; // Colonne 0
        c.gridy = 3; // Nouvelle ligne
        frame.add(dateLabel, c); // Ajout du label

        // Champ de texte pour entrer la date
        JTextField dateField = new JTextField(10);
        c.gridx = 1; // Colonne suivante
        frame.add(dateField, c); // Ajout du champ de texte

        // Label pour le modèle de l'avion
        JLabel modelLabel = new JLabel("Modèle:");
        c.gridx = 0; // Colonne 0
        c.gridy = 4; // Nouvelle ligne
        frame.add(modelLabel, c); // Ajout du label

        // Champ de texte pour entrer le modèle de l'avion
        JTextField modelField = new JTextField(10);
        c.gridx = 1; // Colonne suivante
        frame.add(modelField, c); // Ajout du champ de texte

        // Label pour le type de l'avion
        JLabel typeLabel = new JLabel("Type:");
        c.gridx = 0; // Colonne 0
        c.gridy = 5; // Nouvelle ligne
        frame.add(typeLabel, c); // Ajout du label

        // Champ de texte pour entrer le type de l'avion
        JTextField typeField = new JTextField(10);
        c.gridx = 1; // Colonne suivante
        frame.add(typeField, c); // Ajout du champ de texte

        // Bouton pour enregistrer le vol
        JButton saveButton = new JButton("Enregistrer");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Police du bouton
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // On récupère et vérifie le numéro de vol
                    int numero = Integer.parseInt(numField.getText());
                    if (gestionCompagnie.volExiste(numero)) {
                        throw new IllegalArgumentException("Le numéro de vol existe déjà.");
                    }
                    // On récupère les autres infos du vol
                    String destination = destField.getText();
                    String[] dateParts = dateField.getText().split("-");
                    if (dateParts.length != 3) {
                        throw new IllegalArgumentException("La date doit être au format JJ-MM-AAAA");
                    }
                    int jour = Integer.parseInt(dateParts[0]);
                    int mois = Integer.parseInt(dateParts[1]);
                    int annee = Integer.parseInt(dateParts[2]);
                    Date date = new Date(jour, mois, annee);
                    String modele = modelField.getText();
                    String type = typeField.getText();
                    Avion avion = new Avion(modele, type);

                    // On ajoute le vol avec les infos récupérées
                    gestionCompagnie.ajouterVol(numero, destination, date, avion, 0); // Places disponibles par défaut

                    // Confirmation de l'ajout du vol
                    JOptionPane.showMessageDialog(frame, "Vol ajouté avec succès !");
                    createAndShowMainMenu(); // Retour au menu principal
                    updateFlightsTable(); // Mise à jour de la table des vols
                } catch (NumberFormatException ex) {
                    // Message d'erreur pour le format du numéro
                    JOptionPane.showMessageDialog(frame, "Format de numéro invalide : " + ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    // Autres erreurs de validation
                    JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage());
                } catch (Exception ex) {
                    // Erreur inattendue
                    JOptionPane.showMessageDialog(frame, "Erreur inattendue : " + ex.getMessage());
                }
            }
        });
        c.gridx = 0; // Colonne 0
        c.gridy = 6; // Nouvelle ligne
        frame.add(saveButton, c); // Ajout du bouton enregistrer

        // Bouton pour annuler et revenir au menu principal
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Police du bouton
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowMainMenu(); // Retour au menu principal
                updateFlightsTable(); // Mise à jour de la table des vols
            }
        });
        c.gridx = 1; // Colonne suivante
        frame.add(cancelButton, c); // Ajout du bouton annuler

        frame.revalidate(); // On valide les changements
        frame.repaint(); // On redessine la fenêtre
    }


    
    
 // Option modification des vols 1 (trouver le vol à modifier), +cosmeique
    private void createAndShowModifyFlightMenu() {
        // On enlève tout le contenu de la fenêtre
        frame.getContentPane().removeAll();
        // On définit un nouveau layout en GridBag
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Espacement des éléments

        // Titre "Modifier un vol" avec une police en gras et de taille 20
        JLabel titleLabel = new JLabel("Modifier un vol");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0; // Positionnement en x
        c.gridy = 0; // Positionnement en y
        c.gridwidth = 2; // Prend deux colonnes
        frame.add(titleLabel, c); // On ajoute le titre à la fenêtre

        // Label pour le numéro de vol
        JLabel numLabel = new JLabel("Numéro de vol:");
        c.gridy = 1; // Changement de ligne
        c.gridwidth = 1; // Une seule colonne
        frame.add(numLabel, c); // Ajout du label

        // Champ de texte pour entrer le numéro de vol
        JTextField numField = new JTextField(10);
        c.gridx = 1; // Positionnement à côté du label
        frame.add(numField, c); // Ajout du champ de texte

        // Bouton pour rechercher le vol
        JButton searchButton = new JButton("Rechercher");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Police du bouton
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numero = Integer.parseInt(numField.getText()); // Récupère et convertit le numéro de vol
                    Vol vol = gestionCompagnie.getVol(numero); // Cherche le vol correspondant
                    if (vol != null) {
                        createAndShowEditFlightMenu(vol); // Si vol trouvé, afficher le menu d'édition
                    } else {
                        JOptionPane.showMessageDialog(frame, "Vol non trouvé."); // Message si vol non trouvé
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Format de numéro invalide : " + ex.getMessage()); // Message d'erreur format numéro
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage()); // Autre erreur
                }
            }
        });
        c.gridx = 0; // Retour à la première colonne
        c.gridy = 2; // Nouvelle ligne
        frame.add(searchButton, c); // Ajout du bouton de recherche

        // Bouton pour retourner au menu principal
        JButton backButton = new JButton("Retour");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Police du bouton
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowMainMenu(); // Afficher le menu principal
                updateFlightsTable(); // Mettre à jour la table des vols
            }
        });
        c.gridx = 1; // Positionnement à côté du bouton de recherche
        frame.add(backButton, c); // Ajout du bouton retour

        frame.revalidate(); // Valider les changements
        frame.repaint(); // Repeindre la fenêtre
    }


 // Option modification des vols 2 (formulaire de modification), cosmétique
    private void createAndShowEditFlightMenu(Vol vol) {
        // On enlève tout ce qu'il y a dans la fenêtre
        frame.getContentPane().removeAll();
        // On passe en mode GridBag pour la mise en page
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Espacement entre les composants

        // Titre avec le numéro de vol
        JLabel titleLabel = new JLabel("Modification du vol " + vol.getNumero());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0; // Colonne 0
        c.gridy = 0; // Ligne 0
        c.gridwidth = 2; // Prend deux colonnes
        frame.add(titleLabel, c); // On ajoute le titre

        // Label pour le numéro de vol
        JLabel numLabel = new JLabel("Numéro de vol:");
        c.gridy = 1; // Ligne suivante
        c.gridwidth = 1; // Prend une colonne
        frame.add(numLabel, c); // Ajout du label

        // Champ de texte pour afficher le numéro de vol (non éditable)
        JTextField numField = new JTextField(10);
        numField.setText(String.valueOf(vol.getNumero()));
        numField.setEditable(false); // Non éditable
        c.gridx = 1; // Colonne suivante
        frame.add(numField, c); // Ajout du champ de texte

        // Label pour la destination
        JLabel destLabel = new JLabel("Destination:");
        c.gridx = 0; // Retour à la colonne 0
        c.gridy = 2; // Nouvelle ligne
        frame.add(destLabel, c); // Ajout du label

        // Champ de texte pour afficher et éditer la destination
        JTextField destField = new JTextField(10);
        destField.setText(vol.getDestination());
        c.gridx = 1; // Colonne suivante
        frame.add(destField, c); // Ajout du champ de texte

        // Label pour la date
        JLabel dateLabel = new JLabel("Date (JJ-MM-AAAA):");
        c.gridx = 0; // Colonne 0
        c.gridy = 3; // Nouvelle ligne
        frame.add(dateLabel, c); // Ajout du label

        // Champ de texte pour afficher et éditer la date
        JTextField dateField = new JTextField(10);
        dateField.setText(vol.getDate().toString());
        c.gridx = 1; // Colonne suivante
        frame.add(dateField, c); // Ajout du champ de texte

        // Label pour le modèle de l'avion
        JLabel modelLabel = new JLabel("Modèle:");
        c.gridx = 0; // Colonne 0
        c.gridy = 4; // Nouvelle ligne
        frame.add(modelLabel, c); // Ajout du label

        // Champ de texte pour afficher et éditer le modèle de l'avion
        JTextField modelField = new JTextField(10);
        modelField.setText(vol.getAvion().getModele());
        c.gridx = 1; // Colonne suivante
        frame.add(modelField, c); // Ajout du champ de texte

        // Label pour le type de l'avion
        JLabel typeLabel = new JLabel("Type:");
        c.gridx = 0; // Colonne 0
        c.gridy = 5; // Nouvelle ligne
        frame.add(typeLabel, c); // Ajout du label

        // Champ de texte pour afficher et éditer le type de l'avion
        JTextField typeField = new JTextField(10);
        typeField.setText(vol.getAvion().getType());
        c.gridx = 1; // Colonne suivante
        frame.add(typeField, c); // Ajout du champ de texte

        // Label pour le nombre de places disponibles (non éditable)
        JLabel seatsLabel = new JLabel("Places disponibles:");
        c.gridx = 0; // Colonne 0
        c.gridy = 6; // Nouvelle ligne
        frame.add(seatsLabel, c); // Ajout du label

        JTextField seatsField = new JTextField(10);
        seatsField.setText(String.valueOf(vol.getSeatsAvailable()));
        seatsField.setEditable(false); // Non éditable
        c.gridx = 1; // Colonne suivante
        frame.add(seatsField, c); // Ajout du champ de texte

        // Label pour le nombre de réservations à modifier
        JLabel reserveLabel = new JLabel("(+/-) Nombre de réservations:");
        c.gridx = 0; // Colonne 0
        c.gridy = 7; // Nouvelle ligne
        frame.add(reserveLabel, c); // Ajout du label

        JTextField reserveField = new JTextField(10);
        c.gridx = 1; // Colonne suivante
        frame.add(reserveField, c); // Ajout du champ de texte

        // Bouton pour enregistrer les modifications
        JButton saveButton = new JButton("Enregistrer");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Police du bouton
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Récupère et valide les nouvelles données
                    String destination = destField.getText();
                    String[] dateParts = dateField.getText().split("-");
                    if (dateParts.length != 3) {
                        throw new IllegalArgumentException("La date doit être au format JJ-MM-AAAA");
                    }
                    int jour = Integer.parseInt(dateParts[0]);
                    int mois = Integer.parseInt(dateParts[1]);
                    int annee = Integer.parseInt(dateParts[2]);
                    Date date = new Date(jour, mois, annee);
                    String modele = modelField.getText();
                    String type = typeField.getText();
                    Avion avion = new Avion(modele, type);

                    int additionalReservations = Integer.parseInt(reserveField.getText());
                    if (additionalReservations > vol.getSeatsAvailable()) {
                        throw new IllegalArgumentException("Le nombre de réservations doit être valide et ne pas dépasser les places disponibles");
                    }

                    // Met à jour les informations du vol
                    vol.setDestination(destination);
                    vol.setDate(date);
                    vol.setAvion(avion);
                    vol.setReservations(vol.getReservations() + additionalReservations);

                    JOptionPane.showMessageDialog(frame, "Vol modifié avec succès !");
                    createAndShowMainMenu(); // Retour au menu principal
                    updateFlightsTable(); // Mise à jour de la table des vols
                } catch (NumberFormatException ex) {
                    // Message d'erreur pour le format du numéro
                    JOptionPane.showMessageDialog(frame, "Format de nombre invalide : " + ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    // Autres erreurs de validation
                    JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage());
                } catch (Exception ex) {
                    // Erreur inattendue
                    JOptionPane.showMessageDialog(frame, "Erreur inattendue : " + ex.getMessage());
                }
            }
        });
        c.gridx = 0; // Colonne 0
        c.gridy = 8; // Nouvelle ligne
        frame.add(saveButton, c); // Ajout du bouton enregistrer

        // Bouton pour annuler et revenir au menu principal
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Police du bouton
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowMainMenu(); // Retour au menu principal
                updateFlightsTable(); // Mise à jour de la table des vols
            }
        });
        c.gridx = 1; // Colonne suivante
        frame.add(cancelButton, c); // Ajout du bouton annuler

        frame.revalidate(); // On valide les changements
        frame.repaint(); // On redessine la fenêtre
    }

    // Option de suppression des vols dans l'appli, cosmétique
    private void createAndShowDeleteFlightMenu() {
        // On enlève tout ce qu'il y a dans la fenêtre
        frame.getContentPane().removeAll();
        // On passe en mode GridBag pour la mise en page
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Espacement entre les composants

        // Titre pour supprimer un vol
        JLabel titleLabel = new JLabel("Supprimer un vol");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0; // Colonne 0
        c.gridy = 0; // Ligne 0
        c.gridwidth = 2; // Prend deux colonnes
        frame.add(titleLabel, c); // Ajout du titre

        // Label pour le numéro de vol
        JLabel numLabel = new JLabel("Numéro de vol:");
        c.gridy = 1; // Ligne suivante
        c.gridwidth = 1; // Prend une colonne
        frame.add(numLabel, c); // Ajout du label

        // Champ de texte pour entrer le numéro de vol
        JTextField numField = new JTextField(10);
        c.gridx = 1; // Colonne suivante
        frame.add(numField, c); // Ajout du champ de texte

        // Bouton pour supprimer le vol
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Police du bouton
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numero = Integer.parseInt(numField.getText());
                    boolean deleted = gestionCompagnie.supprimerVol(numero);
                    if (deleted) {
                        JOptionPane.showMessageDialog(frame, "Vol supprimé avec succès !");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Vol non trouvé.");
                    }
                    createAndShowMainMenu(); // Retour au menu principal
                    updateFlightsTable(); // Mise à jour de la table des vols
                } catch (NumberFormatException ex) {
                    // Message d'erreur pour le format du numéro
                    JOptionPane.showMessageDialog(frame, "Format de numéro invalide : " + ex.getMessage());
                } catch (Exception ex) {
                    // Erreur inattendue
                    JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage());
                }
            }
        });
        c.gridx = 0; // Colonne 0
        c.gridy = 2; // Nouvelle ligne
        frame.add(deleteButton, c); // Ajout du bouton supprimer

        // Bouton pour annuler et revenir au menu principal
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Police du bouton
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowMainMenu(); // Retour au menu principal
                updateFlightsTable(); // Mise à jour de la table des vols
            }
        });
        c.gridx = 1; // Colonne suivante
        frame.add(cancelButton, c); // Ajout du bouton annuler

        frame.revalidate(); // On valide les changements
        frame.repaint(); // On redessine la fenêtre
    }

    // Option de recherche pour faciliter la gestion de l'utilisateur, cosmétique
    private void createAndShowSearchFlightMenu() {
        // On enlève tout ce qu'il y a dans la fenêtre
        frame.getContentPane().removeAll();
        // On passe en mode GridBag pour la mise en page
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); // Espacement entre les composants

        // Titre pour rechercher un vol
        JLabel titleLabel = new JLabel("Rechercher un vol");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0; // Colonne 0
        c.gridy = 0; // Ligne 0
        c.gridwidth = 2; // Prend deux colonnes
        frame.add(titleLabel, c); // Ajout du titre

        // Label pour le numéro de vol
        JLabel numLabel = new JLabel("Numéro de vol:");
        c.gridy = 1; // Ligne suivante
        c.gridwidth = 1; // Prend une colonne
        frame.add(numLabel, c); // Ajout du label

        // Champ de texte pour entrer le numéro de vol
        JTextField numField = new JTextField(10);
        c.gridx = 1; // Colonne suivante
        frame.add(numField, c); // Ajout du champ de texte

        // Bouton pour rechercher le vol
        JButton searchButton = new JButton("Rechercher");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Police du bouton
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numero = Integer.parseInt(numField.getText());
                    Vol vol = gestionCompagnie.getVol(numero);
                    if (vol != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Numéro de vol: ").append(vol.getNumero()).append("\n");
                        sb.append("Destination: ").append(vol.getDestination()).append("\n");
                        sb.append("Date: ").append(vol.getDate().toString()).append("\n");
                        sb.append("Modèle d'avion: ").append(vol.getAvion().getModele()).append("\n");
                        sb.append("Type d'avion: ").append(vol.getAvion().getType()).append("\n");
                        sb.append("Places disponibles: ").append(vol.getSeatsAvailable()).append("\n");
                        JTextArea textArea = new JTextArea(sb.toString());
                        textArea.setEditable(false);
                        JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Détails du vol", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Vol non trouvé.");
                    }
                } catch (NumberFormatException ex) {
                    // Message d'erreur pour le format du numéro
                    JOptionPane.showMessageDialog(frame, "Format de numéro invalide : " + ex.getMessage());
                } catch (Exception ex) {
                    // Erreur inattendue
                    JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage());
                }
            }
        });
        c.gridx = 0; // Colonne 0
        c.gridy = 2; // Nouvelle ligne
        frame.add(searchButton, c); // Ajout du bouton rechercher

        // Bouton pour annuler et revenir au menu principal
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Police du bouton
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowMainMenu(); // Retour au menu principal
                updateFlightsTable(); // Mise à jour de la table des vols
            }
        });
        c.gridx = 1; // Colonne suivante
        frame.add(cancelButton, c); // Ajout du bouton annuler

        frame.revalidate(); // On valide les changements
        frame.repaint(); // On redessine la fenêtre
    }

    
    
 // Charge les vols à partir d'un URL de serveur (localhost)
    private void loadFlightsFromURL() {
        try {
            String jsonResponse = fetchFlightsFromServer("http://localhost:8383/vols");
            loadFlightsFromJSON(jsonResponse); // Charge les vols depuis le JSON
            updateFlightsTable(); // Met à jour la table des vols
            JOptionPane.showMessageDialog(frame, "Vols chargés avec succès !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des vols : " + ex.getMessage());
        }
    }

    // Récupère les vols depuis le serveur
    private String fetchFlightsFromServer(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity); // Convertit la réponse en chaîne
    }

    // Charge les vols depuis un JSON et affiche le nom de la compagnie
    private void loadFlightsFromJSON(String jsonResponse) {
        try {
            gestionCompagnie.chargerVolsJSON(jsonResponse); // Charge les vols
            frame.setTitle("Système de Gestion des Vols : " + gestionCompagnie.getCompagnie().getNom());
            createAndShowMainMenu(); // Affiche le menu principal
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sauvegarde les vols en JSON
    private void saveFlights() {
        try {
            JSONObject jsonCompagnie = new JSONObject();
            JSONArray jsonVols = new JSONArray();

            for (Vol vol : gestionCompagnie.getCompagnie().getVols().values()) {
                JSONObject jsonVol = new JSONObject();
                jsonVol.put("numero", vol.getNumero());
                jsonVol.put("destination", vol.getDestination());

                JSONObject jsonDate = new JSONObject();
                jsonDate.put("jour", vol.getDate().getJour());
                jsonDate.put("mois", vol.getDate().getMois());
                jsonDate.put("annee", vol.getDate().getAnnee());
                jsonVol.put("date", jsonDate);

                JSONObject jsonAvion = new JSONObject();
                jsonAvion.put("modele", vol.getAvion().getModele());
                jsonAvion.put("type", vol.getAvion().getType());
                jsonVol.put("avion", jsonAvion);

                jsonVol.put("reservations", vol.getReservations());
                jsonVols.put(jsonVol);
            }

            jsonCompagnie.put("compagnie", gestionCompagnie.getCompagnie().getNom());
            jsonCompagnie.put("vols", jsonVols);

            sendFlightsToServer(jsonCompagnie.toString()); // Envoie les données au serveur
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Envoie les données des vols au serveur
    private void sendFlightsToServer(String jsonString) throws IOException {
        String url = "http://localhost:8383/sauvegarde";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonString);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        HttpResponse response = httpClient.execute(request);
        HttpEntity responseEntity = response.getEntity();
        String responseString = EntityUtils.toString(responseEntity); // Affiche la réponse
        System.out.println(responseString);
    }

    // Point d'entrée principal (main)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp());
    }

}
