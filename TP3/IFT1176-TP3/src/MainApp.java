/*
    Auteurs : Gueorgui Poklitar
    Matricule : 20251051
    But : La classe MainApp gère l'interface principale de l'application. 
  	Elle permet de naviguer entre les différentes fonctionnalités de 
  	l'application telles que la gestion des avions et des vols. 
  	Elle initialise et configure les composants graphiques nécessaires 
  	à l'interaction de l'utilisateur avec l'application. Cette classe sert 
  	de point d'entrée principal pour l'exécution du programme et la 
  	gestion des événements utilisateurs.
    Travail Pratique 3
    Dernière Mise-à-jour : 14/07/2024
*/


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

// Déclaration de la classe MainApp
public class MainApp {
    private JFrame frame; // Fenêtre principale de l'application
    private JTable tableVols; // Tableau pour afficher les vols
    private DefaultTableModel modelVols; // Modèle de données pour le tableau des vols
    private JPanel panelButtons; // Panel pour les boutons
    private List<Vol> vols; // Liste des vols
    private List<Avion> avions; // Liste des avions

    // Méthode principale pour démarrer l'application
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainApp window = new MainApp(); // Créer une instance de MainApp
                window.frame.setVisible(true); // Rendre la fenêtre visible
            } catch (Exception e) {
                e.printStackTrace(); // Afficher les erreurs
            }
        });
    }

    // Constructeur de MainApp
    public MainApp() {
        initialize(); // Initialiser l'interface utilisateur
    }

    // Méthode pour initialiser les composants de l'interface utilisateur
    private void initialize() {
        frame = new JFrame("Système de Gestion des Vols"); // Créer la fenêtre principale
        frame.setBounds(100, 100, 800, 600); // Définir les dimensions de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Définir l'action de fermeture
        frame.getContentPane().setLayout(new BorderLayout()); // Définir le layout de la fenêtre

        panelButtons = new JPanel(); // Créer un panel pour les boutons
        panelButtons.setLayout(new GridBagLayout()); // Définir le layout du panel
        frame.getContentPane().add(panelButtons, BorderLayout.NORTH); // Ajouter le panel à la fenêtre

        // Panel pour la gestion des vols
        JPanel panelVols = new JPanel();
        panelVols.setLayout(new GridLayout(4, 1, 10, 10)); // Définir le layout du panel
        panelVols.setBorder(BorderFactory.createTitledBorder("Gestion des Vols")); // Définir le titre du panel

        JButton btnAjouter = new JButton("Ajouter un vol"); // Bouton pour ajouter un vol
        btnAjouter.addActionListener(e -> showAddVolForm()); // Définir l'action du bouton
        panelVols.add(btnAjouter); // Ajouter le bouton au panel

        JButton btnModifier = new JButton("Modifier un vol"); // Bouton pour modifier un vol
        btnModifier.addActionListener(e -> showEditVolForm()); // Définir l'action du bouton
        panelVols.add(btnModifier); // Ajouter le bouton au panel

        JButton btnSupprimer = new JButton("Supprimer un vol"); // Bouton pour supprimer un vol
        btnSupprimer.addActionListener(e -> showDeleteVolForm()); // Définir l'action du bouton
        panelVols.add(btnSupprimer); // Ajouter le bouton au panel

        JButton btnRecherche = new JButton("Rechercher un vol"); // Bouton pour rechercher un vol
        btnRecherche.addActionListener(e -> showSearchVolForm()); // Définir l'action du bouton
        panelVols.add(btnRecherche); // Ajouter le bouton au panel

        // Panel pour la gestion de la flotte
        JPanel panelFlotte = new JPanel();
        panelFlotte.setLayout(new GridLayout(1, 1, 10, 10)); // Définir le layout du panel
        panelFlotte.setBorder(BorderFactory.createTitledBorder("Gestion de Flotte")); // Définir le titre du panel

        JButton btnGestionFlotte = new JButton("Gestion de flotte"); // Bouton pour la gestion de la flotte
        btnGestionFlotte.addActionListener(e -> showFleetManagement()); // Définir l'action du bouton
        panelFlotte.add(btnGestionFlotte); // Ajouter le bouton au panel

        // Ajouter les panels au panel principal avec GridBagLayout
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        panelButtons.add(panelVols, c);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHEAST;
        panelButtons.add(panelFlotte, c);

        JScrollPane scrollPane = new JScrollPane(); // Créer un panneau de défilement
        scrollPane.setPreferredSize(new Dimension(800, 400)); // Définir la hauteur fixe pour la liste
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER); // Ajouter le panneau de défilement à la fenêtre

        tableVols = new JTable(); // Créer un tableau pour afficher les vols
        modelVols = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Numéro", "Destination", "Date", "Avion", "Type", "Disponibilité"}
        ) {
            Class[] columnTypes = new Class[]{
                Integer.class, Integer.class, String.class, String.class, String.class, String.class, String.class
            };

            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex]; // Retourner le type de la colonne
            }

            public boolean isCellEditable(int row, int column) {
                return false; // Rendre les cellules non éditables
            }
        };
        tableVols.setModel(modelVols); // Définir le modèle de données pour le tableau
        tableVols.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Définir le mode de sélection du tableau
        scrollPane.setViewportView(tableVols); // Ajouter le tableau au panneau de défilement

        // Rendu personnalisé des cellules pour afficher "COMPLET" en rouge
        tableVols.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if ("COMPLET".equals(value)) {
                    c.setForeground(Color.RED); // Définir la couleur du texte en rouge
                    c.setFont(c.getFont().deriveFont(Font.BOLD)); // Définir la police en gras
                } else {
                    c.setForeground(Color.BLACK); // Définir la couleur du texte en noir
                    c.setFont(c.getFont().deriveFont(Font.PLAIN)); // Définir la police normale
                }
                return c;
            }
        });

        loadVols(); // Charger les vols
    }

    // Méthode pour charger les vols
    private void loadVols() {
        try {
            vols = Vol.getAllVols(); // Récupérer tous les vols
            updateVolsTable(); // Mettre à jour le tableau des vols
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des vols."); // Afficher un message d'erreur
        }
    }

    // Méthode pour mettre à jour le tableau des vols
    private void updateVolsTable() {
        modelVols.setRowCount(0); // Réinitialiser le nombre de lignes du tableau
        modelVols.setColumnIdentifiers(new String[]{"ID", "Numéro", "Destination", "Date", "Avion", "Disponibilité"}); // Supprimer la colonne "Type"
        for (Vol vol : vols) {
            String disponibilite;
            if (vol.getDisponibilite() == 0) {
                disponibilite = "COMPLET";
            } else if (vol.getDisponibilite() > 0) {
                disponibilite = String.valueOf(vol.getDisponibilite());
            } else {
                disponibilite = "SURCAPACITÉ";
            }
            modelVols.addRow(new Object[]{
                vol.getId(), vol.getNumero(), vol.getDestination(),
                vol.getDate().toString(), vol.getModele() + " - " + vol.getType(), disponibilite
            });
        }

        // Appliquer le rendu personnalisé des cellules à la colonne "Disponibilité"
        tableVols.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if ("COMPLET".equals(value)) {
                    c.setForeground(Color.RED);
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else if("SURCAPACITÉ".equals(value)){ 
                    c.setForeground(Color.BLUE);
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setForeground(Color.BLACK);
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        });
    }

    // Méthode pour afficher le formulaire d'ajout de vol
    private void showAddVolForm() {
        clearPanel(panelButtons); // Effacer le panel des boutons
        panelButtons.setLayout(new BorderLayout()); // Définir le layout du panel des boutons

        JPanel formPanel = new JPanel(); // Créer un panel pour le formulaire
        formPanel.setLayout(new GridBagLayout()); // Définir le layout du formulaire
        panelButtons.add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au panel des boutons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Colonne 1
        JPanel column1 = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        c1.insets = new Insets(5, 5, 5, 5);
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.gridx = 0;

        JLabel lblId = new JLabel("ID de vol:"); // Label pour l'ID de vol
        c1.gridy = 0;
        column1.add(lblId, c1);

        JTextField txtId = new JTextField(); // Champ de texte pour l'ID de vol
        txtId.setEditable(false);
        try {
            // Récupérer le prochain ID disponible et l'afficher dans le formulaire
            int nextAvailableId = Vol.getNextAvailableId();
            txtId.setText(String.valueOf(nextAvailableId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        c1.gridy = 1;
        column1.add(txtId, c1);

        JLabel lblNumero = new JLabel("Numéro de vol:"); // Label pour le numéro de vol
        c1.gridy = 2;
        column1.add(lblNumero, c1);

        JTextField txtNumero = new JTextField(); // Champ de texte pour le numéro de vol
        c1.gridy = 3;
        column1.add(txtNumero, c1);

        JLabel lblDestination = new JLabel("Destination:"); // Label pour la destination
        c1.gridy = 4;
        column1.add(lblDestination, c1);

        JTextField txtDestination = new JTextField(); // Champ de texte pour la destination
        c1.gridy = 5;
        column1.add(txtDestination, c1);

        JLabel lblDate = new JLabel("Date (AAAA-MM-JJ):"); // Label pour la date
        c1.gridy = 6;
        column1.add(lblDate, c1);

        JTextField txtDate = new JTextField(); // Champ de texte pour la date
        c1.gridy = 7;
        column1.add(txtDate, c1);

        // Colonne 2
        JPanel column2 = new JPanel(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(5, 5, 5, 5);
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.gridx = 0;

        JLabel lblAvion = new JLabel("Avion:"); // Label pour l'avion
        c2.gridy = 0;
        column2.add(lblAvion, c2);

        JComboBox<String> comboAvion = new JComboBox<>(); // ComboBox pour les avions
        c2.gridy = 1;
        column2.add(comboAvion, c2);

        JLabel lblPlaces = new JLabel("Places disponibles:"); // Label pour les places disponibles
        c2.gridy = 2;
        column2.add(lblPlaces, c2);

        JTextField txtPlaces = new JTextField(); // Champ de texte pour les places disponibles
        txtPlaces.setEditable(false);
        c2.gridy = 3;
        column2.add(txtPlaces, c2);

        JLabel lblReservations = new JLabel("Modifier les réservations:"); // Label pour les réservations
        c2.gridy = 4;
        column2.add(lblReservations, c2);

        JTextField txtReservations = new JTextField(); // Champ de texte pour les réservations
        c2.gridy = 5;
        column2.add(txtReservations, c2);

        comboAvion.addActionListener(e -> {
            try {
                String selectedAvion = (String) comboAvion.getSelectedItem();
                if (selectedAvion != null && selectedAvion.contains(" - ")) {
                    String[] parts = selectedAvion.split(" - ");
                    int places = Avion.getPlacesForModele(parts[0], parts[1]);
                    int reservations = txtReservations.getText().isEmpty() ? 0 : Integer.parseInt(txtReservations.getText());
                    txtPlaces.setText(String.valueOf(places - reservations));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Colonne 3
        JPanel column3 = new JPanel(new GridBagLayout());
        GridBagConstraints c3 = new GridBagConstraints();
        c3.insets = new Insets(5, 5, 5, 5);
        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.gridx = 0;

        JButton btnSave = new JButton("Enregistrer"); // Bouton pour enregistrer
        btnSave.addActionListener(e -> {
            try {
                Vol newVol = new Vol();
                newVol.setId(Integer.parseInt(txtId.getText())); // Définir l'ID à partir du champ non modifiable
                newVol.setNumero(Integer.parseInt(txtNumero.getText()));
                newVol.setDestination(txtDestination.getText());
                newVol.setDate(LocalDate.parse(txtDate.getText()));
                String[] avionDetails = comboAvion.getSelectedItem().toString().split(" - ");
                newVol.setModele(avionDetails[0]);
                newVol.setType(avionDetails[1]);
                int avionId = Avion.getAvionIdByModeleAndType(avionDetails[0], avionDetails[1]);
                newVol.setAvionId(avionId);
                newVol.setCapacity(Integer.parseInt(txtPlaces.getText()) + Integer.parseInt(txtReservations.getText()));
                newVol.setReservations(Integer.parseInt(txtReservations.getText()));
                Vol.addVol(newVol);
                loadVols(); // Recharger les vols
                resetPanel(); // Réinitialiser le panel
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout du vol."); // Afficher un message d'erreur
            }
        });
        c3.gridy = 0;
        column3.add(btnSave, c3);

        JButton btnCancel = new JButton("Annuler"); // Bouton pour annuler
        btnCancel.addActionListener(e -> resetPanel());
        c3.gridy = 1;
        column3.add(btnCancel, c3);

        // Ajouter les colonnes au formPanel
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(column1, c);

        c.gridx = 1;
        formPanel.add(column2, c);

        c.gridx = 2;
        formPanel.add(column3, c);

        txtDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                try {
                    List<String> avions = Avion.getAvailableModelesByDate(LocalDate.parse(txtDate.getText()));
                    comboAvion.removeAllItems();
                    for (String avion : avions) {
                        comboAvion.addItem(avion);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(frame, "Date invalide. Veuillez entrer une date valide au format AAAA-MM-JJ.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    txtDate.requestFocus();
                }
            }
        });

        panelButtons.revalidate();
        panelButtons.repaint();
    }

 // Méthode pour afficher le formulaire de recherche de vol
    private void showSearchVolForm() {
        clearPanel(panelButtons); // Effacer le contenu actuel du panel des boutons
        panelButtons.setLayout(new BorderLayout()); // Définir la disposition du panel

        JPanel formPanel = new JPanel(); // Créer un nouveau panel pour le formulaire
        formPanel.setLayout(new GridBagLayout()); // Utiliser un layout GridBag pour le formulaire
        panelButtons.add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au panel des boutons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Ajouter des marges
        c.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement

        JLabel lblNumero = new JLabel("Numéro de vol:"); // Label pour le numéro de vol
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(lblNumero, c);

        JTextField txtNumero = new JTextField(10); // Champ de texte pour le numéro de vol
        c.gridx = 1;
        formPanel.add(txtNumero, c);

        JButton btnSearch = new JButton("Rechercher"); // Bouton pour rechercher un vol
        btnSearch.addActionListener(e -> {
            try {
                int volNumero = Integer.parseInt(txtNumero.getText());
                List<Vol> volsByNumero = Vol.getVolsByNumero(volNumero); // Rechercher les vols par numéro
                if (!volsByNumero.isEmpty()) {
                    showVolSelectionForm(volsByNumero); // Afficher le formulaire de sélection de vol
                } else {
                    JOptionPane.showMessageDialog(frame, "Vol non trouvé."); // Afficher un message si le vol n'est pas trouvé
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // Gérer les exceptions SQL
            }
        });
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        formPanel.add(btnSearch, c);

        JButton btnCancel = new JButton("Retour"); // Bouton pour annuler et revenir en arrière
        btnCancel.addActionListener(e -> resetPanel());
        c.gridy = 2;
        formPanel.add(btnCancel, c);

        panelButtons.revalidate(); // Revalider le panel des boutons
        panelButtons.repaint(); // Repeindre le panel des boutons
    }

    // Méthode pour afficher le formulaire de sélection de vol
    private void showVolSelectionForm(List<Vol> volsByNumero) {
        clearPanel(panelButtons); // Effacer le contenu actuel du panel des boutons
        panelButtons.setLayout(new BorderLayout()); // Définir la disposition du panel

        JPanel formPanel = new JPanel(); // Créer un nouveau panel pour le formulaire
        formPanel.setLayout(new GridBagLayout()); // Utiliser un layout GridBag pour le formulaire
        panelButtons.add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au panel des boutons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Ajouter des marges
        c.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement

        JLabel lblSelectDate = new JLabel("Sélectionner le vol par date:"); // Label pour la sélection de la date
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(lblSelectDate, c);

        JComboBox<String> comboDates = new JComboBox<>(); // ComboBox pour les dates des vols
        for (Vol vol : volsByNumero) {
            comboDates.addItem(vol.getDate().toString()); // Ajouter les dates des vols au ComboBox
        }
        c.gridx = 1;
        formPanel.add(comboDates, c);

        JButton btnSelect = new JButton("Sélectionner"); // Bouton pour sélectionner un vol
        btnSelect.addActionListener(e -> {
            String selectedDate = (String) comboDates.getSelectedItem();
            for (Vol vol : volsByNumero) {
                if (vol.getDate().toString().equals(selectedDate)) {
                    showEditVolForm(vol); // Afficher le formulaire de modification du vol
                    return;
                }
            }
        });
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        formPanel.add(btnSelect, c);

        JButton btnCancel = new JButton("Retour"); // Bouton pour annuler et revenir en arrière
        btnCancel.addActionListener(e -> resetPanel());
        c.gridy = 2;
        formPanel.add(btnCancel, c);

        panelButtons.revalidate(); // Revalider le panel des boutons
        panelButtons.repaint(); // Repeindre le panel des boutons
    }

    // Méthode pour afficher le formulaire de modification de vol
    private void showEditVolForm() {
        clearPanel(panelButtons); // Effacer le contenu actuel du panel des boutons
        panelButtons.setLayout(new BorderLayout()); // Définir la disposition du panel

        JPanel formPanel = new JPanel(); // Créer un nouveau panel pour le formulaire
        formPanel.setLayout(new GridBagLayout()); // Utiliser un layout GridBag pour le formulaire
        panelButtons.add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au panel des boutons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Ajouter des marges
        c.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement

        JLabel lblId = new JLabel("ID de vol:"); // Label pour l'ID de vol
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(lblId, c);

        JTextField txtId = new JTextField(); // Champ de texte pour l'ID de vol
        c.gridx = 1;
        formPanel.add(txtId, c);
        
        JButton btnSearch = new JButton("Rechercher"); // Bouton pour rechercher un vol par ID
        btnSearch.addActionListener(e -> {
            try {
                int volId = Integer.parseInt(txtId.getText());
                Vol vol = Vol.getVolById(volId); // Rechercher le vol par ID
                if (vol != null) {
                    showEditVolForm(vol); // Afficher le formulaire de modification de vol avec le vol trouvé
                } else {
                    JOptionPane.showMessageDialog(frame, "Vol non trouvé."); // Afficher un message si le vol n'est pas trouvé
                }
            } catch (NumberFormatException | SQLException ex) {
                ex.printStackTrace(); // Gérer les exceptions
                JOptionPane.showMessageDialog(frame, "Erreur lors de la recherche du vol.");
            }
        });
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        formPanel.add(btnSearch, c);

        JButton btnCancel = new JButton("Retour"); // Bouton pour annuler et revenir en arrière
        btnCancel.addActionListener(e -> resetPanel());
        c.gridy = 2;
        formPanel.add(btnCancel, c);

        panelButtons.revalidate(); // Revalider le panel des boutons
        panelButtons.repaint(); // Repeindre le panel des boutons
    }

    // Méthode pour afficher le formulaire de modification de vol avec un vol spécifique
    private void showEditVolForm(Vol vol) {
        clearPanel(panelButtons); // Effacer le contenu actuel du panel des boutons
        panelButtons.setLayout(new BorderLayout()); // Définir la disposition du panel

        JPanel formPanel = new JPanel(); // Créer un nouveau panel pour le formulaire
        formPanel.setLayout(new GridBagLayout()); // Utiliser un layout GridBag pour le formulaire
        panelButtons.add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au panel des boutons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Ajouter des marges
        c.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement

        // Colonne 1
        JPanel column1 = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        c1.insets = new Insets(5, 5, 5, 5);
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.gridx = 0;

        JLabel lblId = new JLabel("ID de vol:"); // Label pour l'ID de vol
        c1.gridy = 0;
        column1.add(lblId, c1);

        JTextField txtId = new JTextField(String.valueOf(vol.getId())); // Champ de texte pour l'ID de vol
        txtId.setEditable(false);
        c1.gridy = 1;
        column1.add(txtId, c1);

        JLabel lblNumero = new JLabel("Numéro de vol:"); // Label pour le numéro de vol
        c1.gridy = 2;
        column1.add(lblNumero, c1);

        JTextField txtNumero = new JTextField(String.valueOf(vol.getNumero())); // Champ de texte pour le numéro de vol
        c1.gridy = 3;
        column1.add(txtNumero, c1);

        JLabel lblDestination = new JLabel("Destination:"); // Label pour la destination
        c1.gridy = 4;
        column1.add(lblDestination, c1);

        JTextField txtDestination = new JTextField(vol.getDestination()); // Champ de texte pour la destination
        c1.gridy = 5;
        column1.add(txtDestination, c1);

        JLabel lblDate = new JLabel("Date (AAAA-MM-JJ):"); // Label pour la date
        c1.gridy = 6;
        column1.add(lblDate, c1);

        JTextField txtDate = new JTextField(vol.getDate().toString()); // Champ de texte pour la date
        c1.gridy = 7;
        column1.add(txtDate, c1);

        // Colonne 2
        JPanel column2 = new JPanel(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(5, 5, 5, 5);
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.gridx = 0;

        JLabel lblAvion = new JLabel("Avion:"); // Label pour l'avion
        c2.gridy = 0;
        column2.add(lblAvion, c2);

        JComboBox<String> comboAvion = new JComboBox<>(); // ComboBox pour les avions
        c2.gridy = 1;
        column2.add(comboAvion, c2);

        JLabel lblPlaces = new JLabel("Places disponibles:"); // Label pour les places disponibles
        c2.gridy = 2;
        column2.add(lblPlaces, c2);

        JTextField txtPlaces = new JTextField(String.valueOf(vol.getCapacity() - vol.getReservations())); // Champ de texte pour les places disponibles
        txtPlaces.setEditable(false);
        c2.gridy = 3;
        column2.add(txtPlaces, c2);

        JLabel lblReservations = new JLabel("Modifier les réservations:"); // Label pour les réservations
        c2.gridy = 4;
        column2.add(lblReservations, c2);

        JTextField txtReservations = new JTextField(String.valueOf(vol.getReservations())); // Champ de texte pour les réservations
        c2.gridy = 5;
        column2.add(txtReservations, c2);

        // Enregistrer la date et l'avion originaux
        String originalDate = vol.getDate().toString();
        String originalAvion = vol.getModele() + " - " + vol.getType();

        // Fonction pour charger les avions
        final class AvionLoader {
            void loadAvions(String date) {
                try {
                    comboAvion.removeAllItems();
                    List<String> availableAvions = Avion.getAvailableModelesByDate(LocalDate.parse(date));
                    for (String avion : availableAvions) {
                        comboAvion.addItem(avion);
                    }
                    // Ajouter l'avion original en haut de la liste s'il n'est pas déjà présent
                    if (date.equals(originalDate) && !availableAvions.contains(originalAvion)) {
                        comboAvion.insertItemAt(originalAvion, 0);
                    }
                    comboAvion.setSelectedItem(originalAvion);
                } catch (SQLException | DateTimeParseException ex) {
                    ex.printStackTrace();
                }
            }
        }

        // Charger initialement les avions
        AvionLoader loader = new AvionLoader();
        loader.loadAvions(originalDate);

        // Action listener pour la sélection d'un avion
        comboAvion.addActionListener(e -> {
            try {
                String selectedAvion = (String) comboAvion.getSelectedItem();
                if (selectedAvion != null && !selectedAvion.isEmpty()) {
                    String[] parts = selectedAvion.split(" - ");
                    int places = Avion.getPlacesForModeleAndType(parts[0], parts[1]);
                    txtPlaces.setText(String.valueOf(places - Integer.parseInt(txtReservations.getText())));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Action listener pour les réservations
        txtReservations.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    int reservations = Integer.parseInt(txtReservations.getText());
                    String selectedAvion = (String) comboAvion.getSelectedItem();
                    if (selectedAvion != null && !selectedAvion.isEmpty()) {
                        String[] parts = selectedAvion.split(" - ");
                        int places = Avion.getPlacesForModeleAndType(parts[0], parts[1]);
                        int availablePlaces = places - reservations;
                        if (availablePlaces < 0) {
                            JOptionPane.showMessageDialog(frame, "La capacité de l'avion est insuffisante pour le nombre de réservations. Veuillez choisir un avion plus grand.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            comboAvion.setSelectedItem(null);
                            txtPlaces.setText("");
                        } else {
                            txtPlaces.setText(String.valueOf(availablePlaces));
                        }
                    }
                } catch (NumberFormatException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Focus listener pour le changement de date
        txtDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    String newDate = txtDate.getText();
                    comboAvion.removeAllItems(); // Effacer la sélection pour forcer l'utilisateur à sélectionner un nouvel avion
                    loader.loadAvions(newDate); // Charger les avions pour la nouvelle date
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Date invalide. Veuillez entrer une date valide au format AAAA-MM-JJ.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    txtDate.requestFocus();
                }
            }
        });

        // Colonne 3
        JPanel column3 = new JPanel(new GridBagLayout());
        GridBagConstraints c3 = new GridBagConstraints();
        c3.insets = new Insets(5, 5, 5, 5);
        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.gridx = 0;

        JButton btnSave = new JButton("Enregistrer"); // Bouton pour enregistrer
        btnSave.addActionListener(e -> {
            try {
                vol.setNumero(Integer.parseInt(txtNumero.getText()));
                vol.setDestination(txtDestination.getText());
                LocalDate date = LocalDate.parse(txtDate.getText());
                vol.setDate(date);
                String selectedAvion = (String) comboAvion.getSelectedItem();
                if (selectedAvion != null && !selectedAvion.isEmpty()) {
                    String[] avionDetails = selectedAvion.split(" - ");
                    vol.setModele(avionDetails[0]);
                    vol.setType(avionDetails[1]);
                    int avionId = Avion.getAvionIdByModeleAndType(avionDetails[0], avionDetails[1]);
                    vol.setAvionId(avionId);
                    int places = Avion.getPlacesForModeleAndType(avionDetails[0], avionDetails[1]);
                    vol.setCapacity(places);
                }
                int reservations = Integer.parseInt(txtReservations.getText());
                if (vol.getCapacity() - reservations < 0) {
                    JOptionPane.showMessageDialog(frame, "Les places disponibles ne peuvent pas être négatives. Veuillez ajuster les réservations ou choisir un avion plus grand.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                vol.setReservations(reservations);
                Vol.updateVol(vol);
                loadVols(); // Recharger les vols
                resetPanel(); // Réinitialiser le panel
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Entrée invalide. Veuillez entrer des valeurs numériques valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame, "Date invalide. Veuillez entrer une date valide au format AAAA-MM-JJ.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la modification du vol.");
            }
        });
        c3.gridy = 0;
        column3.add(btnSave, c3);

        JButton btnCancel = new JButton("Annuler"); // Bouton pour annuler
        btnCancel.addActionListener(e -> resetPanel());
        c3.gridy = 1;
        column3.add(btnCancel, c3);

        // Ajouter les colonnes au formPanel
        c.gridx = 0;
        formPanel.add(column1, c);

        c.gridx = 1;
        formPanel.add(column2, c);

        c.gridx = 2;
        formPanel.add(column3, c);

        panelButtons.revalidate(); // Revalider le panel des boutons
        panelButtons.repaint(); // Repeindre le panel des boutons
    }

    // Méthode pour afficher le formulaire de suppression de vol
    private void showDeleteVolForm() {
        clearPanel(panelButtons); // Effacer le contenu actuel du panel des boutons
        panelButtons.setLayout(new BorderLayout()); // Définir la disposition du panel

        JPanel formPanel = new JPanel(); // Créer un nouveau panel pour le formulaire
        formPanel.setLayout(new GridBagLayout()); // Utiliser un layout GridBag pour le formulaire
        panelButtons.add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au panel des boutons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Ajouter des marges
        c.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement

        JLabel lblId = new JLabel("ID de vol:"); // Label pour l'ID de vol
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(lblId, c);

        JTextField txtId = new JTextField(); // Champ de texte pour l'ID de vol
        c.gridx = 1;
        formPanel.add(txtId, c);

        JButton btnDelete = new JButton("Supprimer"); // Bouton pour supprimer un vol
        btnDelete.addActionListener(e -> {
            try {
                int volId = Integer.parseInt(txtId.getText());
                Vol.deleteVol(volId); // Supprimer le vol par ID
                loadVols(); // Recharger les vols
                resetPanel(); // Réinitialiser le panel
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression du vol.");
            }
        });
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        formPanel.add(btnDelete, c);

        JButton btnCancel = new JButton("Annuler"); // Bouton pour annuler et revenir en arrière
        btnCancel.addActionListener(e -> resetPanel());
        c.gridy = 2;
        formPanel.add(btnCancel, c);

        panelButtons.revalidate(); // Revalider le panel des boutons
        panelButtons.repaint(); // Repeindre le panel des boutons
    }

    // Méthode pour afficher le formulaire de gestion de la flotte
    private void showFleetManagement() {
        clearPanel(panelButtons); // Effacer le contenu actuel du panel des boutons
        panelButtons.setLayout(new BorderLayout()); // Définir la disposition du panel

        JPanel formPanel = new JPanel(); // Créer un nouveau panel pour le formulaire
        formPanel.setLayout(new GridBagLayout()); // Utiliser un layout GridBag pour le formulaire
        panelButtons.add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au panel des boutons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Ajouter des marges
        c.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement

        JButton btnAddAvion = new JButton("Ajouter un avion"); // Bouton pour ajouter un avion
        btnAddAvion.addActionListener(e -> showAddAvionForm());
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(btnAddAvion, c);

        JButton btnEditAvion = new JButton("Modifier un avion"); // Bouton pour modifier un avion
        btnEditAvion.addActionListener(e -> showSearchAvionForm());
        c.gridy = 1;
        formPanel.add(btnEditAvion, c);

        JButton btnDeleteAvion = new JButton("Supprimer un avion"); // Bouton pour supprimer un avion
        btnDeleteAvion.addActionListener(e -> showDeleteAvionForm());
        c.gridy = 2;
        formPanel.add(btnDeleteAvion, c);

        JButton btnBack = new JButton("Retour"); // Bouton pour revenir en arrière
        btnBack.addActionListener(e -> resetPanel());
        c.gridy = 3;
        formPanel.add(btnBack, c);

        loadAvions(); // Charger les avions
        updateAvionsTable(); // Mettre à jour le tableau des avions

        panelButtons.revalidate(); // Revalider le panel des boutons
        panelButtons.repaint(); // Repeindre le panel des boutons
    }

    // Méthode pour afficher le formulaire d'ajout d'un avion
    private void showAddAvionForm() {
        clearPanel(panelButtons); // Effacer le contenu actuel du panel des boutons
        panelButtons.setLayout(new BorderLayout()); // Définir la disposition du panel

        JPanel formPanel = new JPanel(); // Créer un nouveau panel pour le formulaire
        formPanel.setLayout(new GridBagLayout()); // Utiliser un layout GridBag pour le formulaire
        panelButtons.add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au panel des boutons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Ajouter des marges
        c.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement

        JLabel lblId = new JLabel("ID:"); // Label pour l'ID de l'avion
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(lblId, c);

        JTextField txtId = new JTextField(); // Champ de texte pour l'ID de l'avion
        txtId.setEditable(false);
        try {
            txtId.setText(String.valueOf(Avion.getNextId())); // Récupérer le prochain ID disponible
        } catch (SQLException e) {
            e.printStackTrace();
        }
        c.gridx = 1;
        formPanel.add(txtId, c);

        JLabel lblModele = new JLabel("Modèle:"); // Label pour le modèle de l'avion
        c.gridx = 0;
        c.gridy = 1;
        formPanel.add(lblModele, c);

        JTextField txtModele = new JTextField(); // Champ de texte pour le modèle de l'avion
        c.gridx = 1;
        formPanel.add(txtModele, c);

        JLabel lblType = new JLabel("Type:"); // Label pour le type de l'avion
        c.gridx = 0;
        c.gridy = 2;
        formPanel.add(lblType, c);

        JTextField txtType = new JTextField(); // Champ de texte pour le type de l'avion
        c.gridx = 1;
        formPanel.add(txtType, c);

        JLabel lblCapacity = new JLabel("Capacity:"); // Label pour la capacité de l'avion
        c.gridx = 0;
        c.gridy = 3;
        formPanel.add(lblCapacity, c);

        JTextField txtCapacity = new JTextField(); // Champ de texte pour la capacité de l'avion
        c.gridx = 1;
        formPanel.add(txtCapacity, c);

        JButton btnSave = new JButton("Enregistrer"); // Bouton pour enregistrer l'avion
        btnSave.setPreferredSize(new Dimension(100, 25)); // Définir la taille préférée
        btnSave.addActionListener(e -> {
            try {
                Avion newAvion = new Avion();
                newAvion.setId(Integer.parseInt(txtId.getText()));
                newAvion.setModele(txtModele.getText());
                newAvion.setType(txtType.getText());
                newAvion.setCapacity(Integer.parseInt(txtCapacity.getText()));
                Avion.addAvion(newAvion); // Ajouter le nouvel avion
                loadAvions(); // Recharger les avions
                showFleetManagement(); // Afficher la gestion de la flotte
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout de l'avion.");
            }
        });
        c.gridx = 0;
        c.gridy = 4;
        formPanel.add(btnSave, c);

        JButton btnCancel = new JButton("Annuler"); // Bouton pour annuler et revenir en arrière
        btnCancel.setPreferredSize(new Dimension(100, 25)); // Définir la taille préférée
        btnCancel.addActionListener(e -> showFleetManagement());
        c.gridx = 1;
        c.gridy = 4;
        formPanel.add(btnCancel, c);

        panelButtons.revalidate(); // Revalider le panel des boutons
        panelButtons.repaint(); // Repeindre le panel des boutons
    }

    // Méthode pour afficher le formulaire de modification d'un avion
    private void showEditAvionForm(Avion avion) {
        clearPanel(panelButtons); // Effacer le contenu actuel du panel des boutons
        panelButtons.setLayout(new BorderLayout()); // Définir la disposition du panel

        JPanel formPanel = new JPanel(); // Créer un nouveau panel pour le formulaire
        formPanel.setLayout(new GridBagLayout()); // Utiliser un layout GridBag pour le formulaire
        panelButtons.add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au panel des boutons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Ajouter des marges
        c.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement

        JLabel lblId = new JLabel("ID:"); // Label pour l'ID de l'avion
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(lblId, c);

        JTextField txtId = new JTextField(String.valueOf(avion.getId())); // Champ de texte pour l'ID de l'avion
        txtId.setEditable(false);
        c.gridx = 1;
        formPanel.add(txtId, c);

        JLabel lblModele = new JLabel("Modèle:"); // Label pour le modèle de l'avion
        c.gridx = 0;
        c.gridy = 1;
        formPanel.add(lblModele, c);

        JTextField txtModele = new JTextField(avion.getModele()); // Champ de texte pour le modèle de l'avion
        c.gridx = 1;
        formPanel.add(txtModele, c);

        JLabel lblType = new JLabel("Type:"); // Label pour le type de l'avion
        c.gridx = 0;
        c.gridy = 2;
        formPanel.add(lblType, c);

        JTextField txtType = new JTextField(avion.getType()); // Champ de texte pour le type de l'avion
        c.gridx = 1;
        formPanel.add(txtType, c);

        JLabel lblCapacity = new JLabel("Capacity:"); // Label pour la capacité de l'avion
        c.gridx = 0;
        c.gridy = 3;
        formPanel.add(lblCapacity, c);

        JTextField txtCapacity = new JTextField(String.valueOf(avion.getCapacity())); // Champ de texte pour la capacité de l'avion
        c.gridx = 1;
        formPanel.add(txtCapacity, c);

        JButton btnSave = new JButton("Enregistrer"); // Bouton pour enregistrer les modifications
        btnSave.addActionListener(e -> {
            try {
                avion.setModele(txtModele.getText());
                avion.setType(txtType.getText());
                avion.setCapacity(Integer.parseInt(txtCapacity.getText()));
                Avion.updateAvion(avion); // Mettre à jour l'avion
                loadAvions(); // Recharger les avions
                showFleetManagement(); // Afficher la gestion de la flotte
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la modification de l'avion.");
            }
        });
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        formPanel.add(btnSave, c);

        JButton btnCancel = new JButton("Annuler"); // Bouton pour annuler et revenir en arrière
        btnCancel.addActionListener(e -> showFleetManagement());
        c.gridy = 5;
        formPanel.add(btnCancel, c);

        panelButtons.revalidate(); // Revalider le panel des boutons
        panelButtons.repaint(); // Repeindre le panel des boutons
    }

    // Méthode pour afficher le formulaire de suppression d'un avion
    private void showDeleteAvionForm() {
        clearPanel(panelButtons); // Effacer le contenu actuel du panel des boutons
        panelButtons.setLayout(new BorderLayout()); // Définir la disposition du panel

        JPanel formPanel = new JPanel(); // Créer un nouveau panel pour le formulaire
        formPanel.setLayout(new GridBagLayout()); // Utiliser un layout GridBag pour le formulaire
        panelButtons.add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au panel des boutons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Ajouter des marges
        c.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement

        JLabel lblId = new JLabel("ID de l'avion:"); // Label pour l'ID de l'avion
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(lblId, c);

        JTextField txtId = new JTextField(); // Champ de texte pour l'ID de l'avion
        c.gridx = 1;
        formPanel.add(txtId, c);

        JButton btnDelete = new JButton("Supprimer"); // Bouton pour supprimer l'avion
        btnDelete.setPreferredSize(new Dimension(100, 25)); // Définir la taille préférée
        btnDelete.addActionListener(e -> {
            try {
                int avionId = Integer.parseInt(txtId.getText());
                if (Avion.isAvionUsed(avionId)) {
                    JOptionPane.showMessageDialog(frame, "Vous ne pouvez pas supprimer cet avion car il est actuellement utilisé par un ou plusieurs vols.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    Avion.deleteAvion(avionId); // Supprimer l'avion par ID
                    loadAvions(); // Recharger les avions
                    showFleetManagement(); // Afficher la gestion de la flotte
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression de l'avion.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        formPanel.add(btnDelete, c);

        JButton btnCancel = new JButton("Annuler"); // Bouton pour annuler et revenir en arrière
        btnCancel.setPreferredSize(new Dimension(100, 25)); // Définir la taille préférée
        btnCancel.addActionListener(e -> showFleetManagement());
        c.gridy = 2;
        formPanel.add(btnCancel, c);

        panelButtons.revalidate(); // Revalider le panel des boutons
        panelButtons.repaint(); // Repeindre le panel des boutons
    }

    // Méthode pour afficher le formulaire de recherche d'un avion
    private void showSearchAvionForm() {
        clearPanel(panelButtons); // Effacer le contenu actuel du panel des boutons
        panelButtons.setLayout(new BorderLayout()); // Définir la disposition du panel

        JPanel formPanel = new JPanel(); // Créer un nouveau panel pour le formulaire
        formPanel.setLayout(new GridBagLayout()); // Utiliser un layout GridBag pour le formulaire
        panelButtons.add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au panel des boutons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Ajouter des marges
        c.fill = GridBagConstraints.HORIZONTAL; // Remplir horizontalement

        JLabel lblId = new JLabel("ID de l'avion:"); // Label pour l'ID de l'avion
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(lblId, c);

        JTextField txtId = new JTextField(); // Champ de texte pour l'ID de l'avion
        c.gridx = 1;
        formPanel.add(txtId, c);

        JButton btnSearch = new JButton("Rechercher"); // Bouton pour rechercher l'avion
        btnSearch.addActionListener(e -> {
            try {
                int avionId = Integer.parseInt(txtId.getText());
                Avion avion = Avion.getAvionById(avionId); // Rechercher l'avion par ID
                if (avion != null) {
                    showEditAvionForm(avion); // Afficher le formulaire de modification de l'avion avec l'avion trouvé
                } else {
                    JOptionPane.showMessageDialog(frame, "Avion non trouvé."); // Afficher un message si l'avion n'est pas trouvé
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        formPanel.add(btnSearch, c);

        JButton btnCancel = new JButton("Retour"); // Bouton pour annuler et revenir en arrière
        btnCancel.addActionListener(e -> resetPanel());
        c.gridy = 2;
        formPanel.add(btnCancel, c);

        panelButtons.revalidate(); // Revalider le panel des boutons
        panelButtons.repaint(); // Repeindre le panel des boutons
    }

    // Méthode pour charger les avions depuis la base de données
    private void loadAvions() {
        try {
            avions = Avion.getAllAvions(); // Récupérer tous les avions
            updateAvionsTable(); // Mettre à jour le tableau des avions
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des avions.");
        }
    }

    // Méthode pour mettre à jour le tableau des avions
    private void updateAvionsTable() {
        modelVols.setRowCount(0); // Réinitialiser le modèle du tableau
        modelVols.setColumnIdentifiers(new String[]{"ID", "Modèle", "Type", "Capacity"}); // Définir les en-têtes des colonnes
        for (Avion avion : avions) {
            modelVols.addRow(new Object[]{
                avion.getId(), avion.getModele(), avion.getType(), avion.getCapacity()
            }); // Ajouter les avions au tableau
        }
    }

    // Méthode pour réinitialiser le panel
    private void resetPanel() {
        panelButtons.removeAll(); // Effacer tout le contenu du panel des boutons
        panelButtons.setLayout(new GridBagLayout()); // Définir la disposition du panel des boutons

        // Gestion des Vols panel
        JPanel panelVols = new JPanel(); // Créer un panel pour la gestion des vols
        panelVols.setLayout(new GridLayout(4, 1, 10, 10)); // Utiliser un layout GridLayout
        panelVols.setBorder(BorderFactory.createTitledBorder("Gestion des Vols")); // Définir le titre du panel

        JButton btnAjouter = new JButton("Ajouter un vol"); // Bouton pour ajouter un vol
        btnAjouter.addActionListener(e -> showAddVolForm());
        panelVols.add(btnAjouter);

        JButton btnModifier = new JButton("Modifier un vol"); // Bouton pour modifier un vol
        btnModifier.addActionListener(e -> showEditVolForm());
        panelVols.add(btnModifier);

        JButton btnSupprimer = new JButton("Supprimer un vol"); // Bouton pour supprimer un vol
        btnSupprimer.addActionListener(e -> showDeleteVolForm());
        panelVols.add(btnSupprimer);

        JButton btnRecherche = new JButton("Rechercher un vol"); // Bouton pour rechercher un vol
        btnRecherche.addActionListener(e -> showSearchVolForm());
        panelVols.add(btnRecherche);

        // Gestion de Flotte panel
        JPanel panelFlotte = new JPanel(); // Créer un panel pour la gestion de la flotte
        panelFlotte.setLayout(new GridLayout(1, 1, 10, 10)); // Utiliser un layout GridLayout
        panelFlotte.setBorder(BorderFactory.createTitledBorder("Gestion de Flotte")); // Définir le titre du panel

        JButton btnGestionFlotte = new JButton("Gestion de flotte"); // Bouton pour la gestion de la flotte
        btnGestionFlotte.addActionListener(e -> showFleetManagement());
        panelFlotte.add(btnGestionFlotte);

        // Ajouter les panels au panel principal avec GridBagLayout
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5); // Ajouter des marges
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        panelButtons.add(panelVols, c);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHEAST;
        panelButtons.add(panelFlotte, c);

        panelButtons.revalidate(); // Revalider le panel des boutons
        panelButtons.repaint(); // Repeindre le panel des boutons

        loadVols(); // Charger les vols
    }

    // Méthode pour effacer le contenu d'un panel
    private void clearPanel(JPanel panel) {
        panel.removeAll(); // Effacer tout le contenu du panel
        panel.revalidate(); // Revalider le panel
        panel.repaint(); // Repeindre le panel
    }
}