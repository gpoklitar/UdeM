/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Chargement de compagnie et les methodes pour 
	modifier les données de la compagnie importée.
	Definition des methodes pour chargement de fichier .txt ou .obj,
	pour la sauvgarde des fichier, insertion de vol, modification de date,
	retrait de vol, reservation, recherche de vol et lister les vols.
	Travail Pratique 1
	Dernière Mise-à-jour : 01/06/2024
*/

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

public class Compagnie implements Serializable {
    private String nomCompagnie;
    private ArrayList<Vol> listeVols;
    private ArrayList<Avion> flotte;
    private static int nombreVolsActifs;

    // Constructeur avec appel de chargement de fichier.
    public Compagnie(String nomCompagnie) {
        this.nomCompagnie = nomCompagnie;
        this.listeVols = new ArrayList<Vol>();
        this.flotte = new ArrayList<Avion>();
        chargerFichierObjetsOuTexte();
    }

    // Méthode chargement de fichier .obj ou .txt...
    private void chargerFichierObjetsOuTexte() {
        String objectFilePath = "resources\\" + nomCompagnie + ".obj";
        String textFilePath = "resources\\" + nomCompagnie + ".txt";
        //Si .obj exist, lecture en priorité du .obj...
        File objectFile = new File(objectFilePath);
        if (objectFile.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objectFilePath));
                listeVols = (ArrayList<Vol>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else { // Si .obj n'existe pas, charge .txt au lieu pour le chargement.
            chargerFichierTexte(textFilePath);
        }
    }

    // Méthode de lecture du fichier chargée.
    private void chargerFichierTexte(String filePath) {
        System.out.println("Chargement du fichier à partir de : " + filePath); //Debug pour valider charge.
        File file = new File(filePath);
        System.out.println("Fichier existe: " + file.exists()); //Debug pour valider charge...
        try { //Lecture et insertion des champs avec split sur charact. ";"...
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parties = ligne.split(";");
                int numeroVol = Integer.parseInt(parties[0]);
                String destination = parties[1];
                int jour = Integer.parseInt(parties[2]);
                int mois = Integer.parseInt(parties[3]);
                int an = Integer.parseInt(parties[4]);
                int totalReservations = Integer.parseInt(parties[5]);
                Date dateDepart = new Date(jour, mois, an);
                Vol vol = new Vol(numeroVol, destination, dateDepart, 0, totalReservations);
                listeVols.add(vol);
                nombreVolsActifs++;
            }
            br.close(); //Fermeture du lecteur (bonne pratique).
        } catch (FileNotFoundException e) { // Pour debug.
            System.err.println("Le fichier " + filePath + 
            		" n'a pas été trouvé. Assurez-vous qu'il est dans le bon répertoire.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Methode de saufgarde vers fichier .obj
    public void sauvegarderFichierObjets() {
        String filePath = "resources\\" + nomCompagnie + ".obj"; //Assure la bonne extension du fichier.
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(listeVols); //Écriture de listeVols dans fichier .obj
            oos.close();//Fermeture d'écriture (bonne pratique).
        } catch (IOException e) { //Pour debug.
            e.printStackTrace();
        }
    }

    // Methode d'insertion de nouveau vol!
    public void insererVol() {
        try {// numero du nouveau vol?
            String inputNumeroVol = JOptionPane.showInputDialog("Entrez le numéro du nouveau vol:");
            if (inputNumeroVol == null) {
                return; //Usagé cancel l'operation, retour au menu.
            }
            if (inputNumeroVol.trim().isEmpty()) { //message erreur pour validation.
                JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer un numéro de vol valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int numeroVol = Integer.parseInt(inputNumeroVol.trim());
            if (rechercherVol(numeroVol) >= 0) { //message erreur pour validation.
                JOptionPane.showMessageDialog(null, "Le numéro de vol existe déjà!");
                return;
            }
            // destination du vol?
            String destination = JOptionPane.showInputDialog("Entrez la destination:");
            if (destination == null) {
                return; //Usagé cancel l'operation, retour au menu.
            }
            if (destination.trim().isEmpty()) {//message erreur pour validation.
                JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer une destination valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Date du vol?
            Date dateDepart = null;
            boolean dateValide = false;
            while (!dateValide) {
                String dateStr = JOptionPane.showInputDialog("Entrez la date de départ (JJ/MM/AAAA):");
                if (dateStr == null) {
                    return; //Usagé cancel l'operation, retour au menu.
                }
                if (dateStr.trim().isEmpty()) {//message erreur pour validation.
                    JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer une date valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                String[] dateParts = dateStr.split("/");
                if (dateParts.length != 3) {//message erreur pour validation.
                    JOptionPane.showMessageDialog(null, "Format de date incorrect. Utilisez JJ/MM/AAAA.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                //try-catch pour soummetre la raison de l'erreur pour la date.
                try {
                    int jour = Integer.parseInt(dateParts[0].trim());
                    int mois = Integer.parseInt(dateParts[1].trim());
                    int an = Integer.parseInt(dateParts[2].trim());

                    dateDepart = new Date(jour, mois, an);
                    dateValide = true;
                } catch (IllegalArgumentException e) { //type d'erreur de date.
                    String messageErreur = e.getMessage();
                    if (messageErreur.equals("Jour invalide.")) {
                        JOptionPane.showMessageDialog(null, "Jour invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } else if (messageErreur.equals("Mois invalide.")) {
                        JOptionPane.showMessageDialog(null, "Mois invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } else if (messageErreur.equals("Année invalide.")) {
                        JOptionPane.showMessageDialog(null, "Année invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } else if (messageErreur.equals("Date invalide, année bissextile.")) {
                        JOptionPane.showMessageDialog(null, "Date invalide, année bissextile.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Date invalide : " + messageErreur, "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            // Numero de l'avion? (pas besoin, sauf pour options dans le futur)...
            String inputNumeroAvion = JOptionPane.showInputDialog("Entrez le numéro de l'avion:");
            if (inputNumeroAvion == null) {
                return; //Usagé cancel l'operation, retour au menu.
            }
            if (inputNumeroAvion.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer un numéro de l'avion valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int numeroAvion = Integer.parseInt(inputNumeroAvion.trim());

            Vol vol = new Vol(numeroVol, destination, dateDepart, numeroAvion, 0);
            listeVols.add(vol);
            Collections.sort(listeVols);
            nombreVolsActifs++;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer des valeurs valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Methode recherche de vol selon son numero
    private int rechercherVol(int numeroVol) {
        int index = Collections.binarySearch(listeVols, new Vol(numeroVol, "", null, 0, 0));
        //Utilisation de Collections.binarySearch()...
        return index;
    }


    // Methode suppression de vol.
    public void retirerVol() {
        try {
            String inputNumeroVol = JOptionPane.showInputDialog("Entrez le numéro du vol à retirer:");
            if (inputNumeroVol == null) {
                return; //Usagé cancel l'operation, retour au menu.
            }
            if (inputNumeroVol.trim().isEmpty()) {//validation et erreur pour usagé.
                JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer un numéro de vol valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int numeroVol = Integer.parseInt(inputNumeroVol.trim());
            int index = rechercherVol(numeroVol);
            if (index < 0) { //validation et erreur pour usagé.
                JOptionPane.showMessageDialog(null, "Le numéro de vol n'existe pas!");
                return;
            }
            Vol vol = listeVols.get(index);
            int confirmation = JOptionPane.showConfirmDialog(null, "Désirez-vous vraiment retirer ce vol (O/N) ?");
            if (confirmation == JOptionPane.YES_OPTION) { //Validation de suppression de vol oui-non.
                listeVols.remove(index);
                nombreVolsActifs--;
            }
        } catch (NumberFormatException e) {//validation et erreur pour usagé.
            JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer un numéro de vol valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Methode de modification de la date de depart du vol
    public void modifierDate() {
        while (true) {
            try {//saisire numero du vol...
                String numeroVolSaisie = JOptionPane.showInputDialog(null, "Entrez le numéro du vol à modifier :", "MODIFICATION DE LA DATE DE DÉPART", JOptionPane.QUESTION_MESSAGE);
                if (numeroVolSaisie == null) {
                    return; // retour au menu.
                }
                if (numeroVolSaisie.trim().isEmpty()) { //validation de saisie.
                    JOptionPane.showMessageDialog(null, "Aucun numéro de vol n'a été entré.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                int numeroVol = Integer.parseInt(numeroVolSaisie.trim());
                int index = rechercherVol(numeroVol);

                if (index < 0) { //erreur numero n'existe pas.
                    JOptionPane.showMessageDialog(null, "Le numéro de vol n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Vol vol = listeVols.get(index);
                // Saisir Date.
                Date nouvelleDate = null;
                boolean dateValide = false;
                while (!dateValide) {
                    String nouvelleDateSaisie = JOptionPane.showInputDialog(null, "Entrez la nouvelle date de départ (JJ/MM/AAAA) :", "MODIFICATION DE LA DATE DE DÉPART", JOptionPane.QUESTION_MESSAGE);
                    if (nouvelleDateSaisie == null) {
                        return; //Retour au menu
                    }
                    if (nouvelleDateSaisie.trim().isEmpty()) {//validation message d'erreur.
                        JOptionPane.showMessageDialog(null, "Aucune date n'a été entrée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    String[] partiesDate = nouvelleDateSaisie.split("/");
                    if (partiesDate.length != 3) { //validation message d'erreur.
                        JOptionPane.showMessageDialog(null, "Format de date incorrect. Utilisez JJ/MM/AAAA.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    try { //validation de date similair à celle de creation de vol.
                        int jour = Integer.parseInt(partiesDate[0].trim());
                        int mois = Integer.parseInt(partiesDate[1].trim());
                        int an = Integer.parseInt(partiesDate[2].trim());

                        nouvelleDate = new Date(jour, mois, an);
                        dateValide = true;
                    } catch (IllegalArgumentException e) {
                        String messageErreur = e.getMessage();
                        if (messageErreur.equals("Jour invalide.")) { // Type d'erreur de date.
                            JOptionPane.showMessageDialog(null, "Jour invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        } else if (messageErreur.equals("Mois invalide.")) {
                            JOptionPane.showMessageDialog(null, "Mois invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        } else if (messageErreur.equals("Année invalide.")) {
                            JOptionPane.showMessageDialog(null, "Année invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        } else if (messageErreur.equals("Date invalide, année bissextile.")) {
                            JOptionPane.showMessageDialog(null, "Date invalide, année bissextile.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Date invalide : " + messageErreur, "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

                vol.setDateDepart(nouvelleDate); // Validation de modification
                JOptionPane.showMessageDialog(null, "Date de départ modifiée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                break; // Sortie de la boucle.

            } catch (NumberFormatException e) { //si erreur, message usager. Echec de modification.
                JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer un numéro de vol et une date valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Methode de reservation de vol.
    public void reserverVol() {
        try {
            String inputNumeroVol = JOptionPane.showInputDialog("Entrez le numéro du vol que vous désirez réserver:");
            if (inputNumeroVol == null) {
                return; //Retour au menu.
            }
            if (inputNumeroVol.trim().isEmpty()) { //Si numero n'est pas valide ( limite de 350 places de reservation par vol).
                JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer un numéro de vol valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int numeroVol = Integer.parseInt(inputNumeroVol.trim());
            int index = rechercherVol(numeroVol);
            if (index < 0) { //Vol n'existe pas, message usagé.
                JOptionPane.showMessageDialog(null, "Le numéro de vol n'existe pas!");
                return;
            }
            Vol vol = listeVols.get(index); //Nombre de place à reserver? max 350.
            String inputNombrePlaces = JOptionPane.showInputDialog("Entrez le nombre de places à réserver:");
            if (inputNombrePlaces == null) {
                return; //Retour au menu.
            }
            if (inputNombrePlaces.trim().isEmpty()) { // erreur pour usagé.
                JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer un nombre de places valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int nombrePlaces = Integer.parseInt(inputNombrePlaces.trim());
            if (vol.addReservation(nombrePlaces)) { // succes
                JOptionPane.showMessageDialog(null, "Réservation réussie!");
            } else {// depasse le maximum du compte 350.
                JOptionPane.showMessageDialog(null, "Nombre de réservations excède la limite!"); 
            }
        } catch (NumberFormatException e) { //Entré invalide.
            JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer des valeurs valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Methode pour lister tous les vols.
    public void listerVols() { //Formatage de la liste.
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-25s %-15s %-15s\n", "Numéro", "Destination", "Date départ", "Réservations"));
        for (Vol vol : listeVols) {
            sb.append(String.format("%-10d %-25s %-15s %-15d\n",
                    vol.getNumeroVol(),
                    vol.getDestination(),
                    vol.getDateDepart(),
                    vol.getTotalReservations()));
        }

        JTextArea textArea = new JTextArea(sb.toString()); // cosmetique.
        textArea.setFont(new Font("courier new", Font.PLAIN, 12)); 
        //Courier n'existe pas sur mon ordinateur, courier new est la version la plus proche en monospace.
        //avec un font size de 12 en Plain.
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea); //dimension de la fenetre defini.
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 150));
        //Montrer le nom de la compagnie sans underscore et en capital sur la barre de la fenetre.
        JOptionPane.showMessageDialog(null, scrollPane, nomCompagnie.toUpperCase().replace("_", " "), JOptionPane.INFORMATION_MESSAGE);
    }
}
