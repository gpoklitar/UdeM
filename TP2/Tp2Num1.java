/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Creation de la classe Tp2Num1, creation des methodes importer et lire un document de type text, 
	entreposage des valeurs de ce document et manipuler les valeurs du document avec methodes standards du ArrayList.
	TP2 - Numero 1
	Dernière Mise-à-jour : 13/06/2023
*/

// Importation des packages de file, l'exception du file non-trouver, les ArrayList, scanner et iterator.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;

// Classe Tp2Num1.
public class Tp2Num1 {
	
	// Methode pour lire un fichier contenent des personnes.
    public static void lireFichier(ArrayList<Personne> personnes, String nomFichier) {
        try {
            File fichier = new File(nomFichier);
            Scanner scanner = new Scanner(fichier);
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] paraPersonne = ligne.split("[ ']+"); 
                // Entrepose les parametres des personnes et separation avec un espace ou apostrophe (pour grandeur en pieds'pouces).

                if (paraPersonne.length == 5) { // SI on capte une ligne de 5 parametres (donc constructeur 1).
                    char sexe = paraPersonne[2].charAt(0);
                    double taille = Double.parseDouble(paraPersonne[3].replace(',', '.')); // Assurer un standard uniforme des valeurs taille et poids.
                    double poids = Double.parseDouble(paraPersonne[4].replace(',', '.'));
                    String nom = paraPersonne[0].trim();
                    String prenom = paraPersonne[1].replace('_','-').trim(); 
                    // Utilisation de replace pour uniformiser les prenoms, ex : MARIE_SOLEIL -> MARIE-SOLEIL.

                    Personne personne = new Personne(nom, prenom, sexe, taille, poids);
                    personnes.add(personne);
                }
                if (paraPersonne.length == 6) { // SI on capte une ligne de 6 parametres (constructeur 2 avec conversion).
                    char sexe = paraPersonne[2].charAt(0);
                    int nbPieds = Integer.parseInt(paraPersonne[3]);
                    int nbPouces = Integer.parseInt(paraPersonne[4]);
                    int nbLivres = Integer.parseInt(paraPersonne[5]);
                    String nom = paraPersonne[0].trim();
                    String prenom = paraPersonne[1].replace('_','-').trim();

                    Personne personne = new Personne(nom, prenom, sexe, nbPieds, nbPouces, nbLivres);
                    personnes.add(personne);
                }
            }
            scanner.close(); // Fermeture du scanner, pratique standard pour eviter les erreurs.
        } catch (FileNotFoundException e) { // Catch une exception si fichier n'est pas trouvee. Plus facile a trouver ou est l'erreur.
            System.out.println("Le fichier n'a pas été trouvé.");
        }
    }
    
    // Methode pour retrouver une personne dans le arraylist.
    public static int trouverIndexPersonne(ArrayList<Personne> personnes, String nom, String prenom) {
        for (int i = 0; i < personnes.size(); i++) {
            Personne personne = personnes.get(i);
            if (personne.getNom().equals(nom) && personne.getPrenom().equals(prenom)) {
                return i; // On a trouvé la personne, donc on retourne son index
            }
        }
        return -1; // SI la personne n'a pas ete trouvee.
    }
    
    
    // MAIN.
    
    public static void main(String[] args) {
    	
    	// Creation du arraylist, lire le fichier avec la methode lireFichier et remplir le arraylist.
        ArrayList<Personne> personnes = new ArrayList<>();
        lireFichier(personnes, "info.txt");

        // Afficher les informations de la 4eme personne de la liste avec la methode afficher de la classe personne.
        if (personnes.size() >= 4) { // Validation pour avoir suffisament de personnes pour executer l'affichage.
            Personne personne4 = personnes.get(3); // Index 3, donc la 4e personne...
            personne4.afficher("4eme personne");
        } else {
            System.out.println("Il n'y a pas suffisamment de personnes dans la liste.");
        }

        // Retrouver la personne, et SI Denise Desmarais, on change son sexe de masculin a feminin.
        for (Personne personne : personnes) { // Utilisation du "Enhenced for-loop". (un for-loop simplifiee)...
            if (personne.getNom().equals("DESMARAIS") && personne.getPrenom().equals("DENISE")) { 
                personne.setSexe('F');
                break; // Sortie de la boucle.
            }
        }

        // Creation de Francine Allaire et initializer Francine allaire a l'index de Francoise Dube.
        Personne francineAllaire = new Personne("ALLAIRE", "FRANCINE",'F', 1.6, 50);
        int indexFrancineDube = trouverIndexPersonne(personnes, "DUBE", "FRANCOISE");
        if (indexFrancineDube != -1) {
            personnes.set(indexFrancineDube, francineAllaire);
        }

        // Creation de Alex Roy comme personne, ajoute de Alex Roy en premiere position (index 0).
        Personne alexRoy = new Personne("ROY", "ALEX",'M', 2.0, 80);
        personnes.add(0, alexRoy);

        // Appel methode remove pour supprimer Eric Fillion de la liste.
        int indexEricFillion = trouverIndexPersonne(personnes, "FILLION", "ERIC");
        if (indexEricFillion != -1) {
            personnes.remove(indexEricFillion);
        }

        // Afficher le contenu de la liste avec iterator...
        System.out.println("Contenu de la liste :");
        Iterator<Personne> iterator = personnes.iterator();
        while (iterator.hasNext()) {
        	Personne personne = iterator.next();
        	System.out.println(personne.toString());
        }
    }
}
//TEST avec document "info.txt" originale:
//RESULTAT 1a
/*
Informations de la personne 4eme personne:

 - Nom     : NOEL
 - Prenom  : ADELAIDE
 - Sexe    : feminin
 - Taille  : 1,50 metre
 - Poids   : 45.0 kgs

Contenu de la liste :
 Nom:       ROY | Prenom:         ALEX | Sexe: masculin | Taille:   2,00 m | Poids:   80,0 kgs
 Nom:       ROY | Prenom:      CHANTAL | Sexe: feminin  | Taille:   1,63 m | Poids:   54,9 kgs
 Nom:  MOLAISON | Prenom:       CLAUDE | Sexe: masculin | Taille:   1,57 m | Poids:   56,3 kgs
 Nom:    BEDARD | Prenom:   MARC-ANDRE | Sexe: masculin | Taille:   1,43 m | Poids:   80,5 kgs
 Nom:      NOEL | Prenom:     ADELAIDE | Sexe: feminin  | Taille:   1,50 m | Poids:   45,0 kgs
 Nom:    BERUBE | Prenom:       DANIEL | Sexe: masculin | Taille:   1,68 m | Poids:   65,0 kgs
 Nom:     HOULE | Prenom:      MARTINE | Sexe: feminin  | Taille:   1,90 m | Poids:  100,0 kgs
 Nom:  LAPIERRE | Prenom:       AGATHE | Sexe: masculin | Taille:   1,88 m | Poids:   90,5 kgs
 Nom: DESMARAIS | Prenom:       DENISE | Sexe: feminin  | Taille:   1,55 m | Poids:   40,5 kgs
 Nom:   ALLAIRE | Prenom:     FRANCINE | Sexe: feminin  | Taille:   1,60 m | Poids:   50,0 kgs
 Nom:      FORD | Prenom: MARIE-SOLEIL | Sexe: feminin  | Taille:   1,72 m | Poids:   62,5 kgs
 Nom:   FILLION | Prenom:         MARC | Sexe: masculin | Taille:   1,85 m | Poids:  100,0 kgs
*/



//TEST ADDITIONEL AU BESOIN: Ajout de la personne Gueorgui Poklitar dans la liste pour 
//tester le constructeur des personnes avec taille en pieds et pouces et poids en livres.

//exemple : POKLITAR GUEORGUI M 5'7 166
//resultat :  Nom:  POKLITAR | Prenom:     GUEORGUI | Sexe: masculin | Taille:   1.70 m | Poids:   75.5 kgs

//note : On observe que la conversion a ete faite correctement et est accepter 
//comme personne dans notre liste (Voir: resultat 1b, derniere ligne).

//RESULTAT 1b
/*
Informations de la personne 4eme personne:

 - Nom     : NOEL
 - Prenom  : ADELAIDE
 - Sexe    : feminin
 - Taille  : 1.50 metre
 - Poids   : 45.0 kgs

Contenu de la liste :
 Nom:       ROY | Prenom:         ALEX | Sexe: masculin | Taille:   2.00 m | Poids:   80.0 kgs
 Nom:       ROY | Prenom:      CHANTAL | Sexe: feminin  | Taille:   1.63 m | Poids:   54.9 kgs
 Nom:  MOLAISON | Prenom:       CLAUDE | Sexe: masculin | Taille:   1.57 m | Poids:   56.3 kgs
 Nom:    BEDARD | Prenom:   MARC-ANDRE | Sexe: masculin | Taille:   1.43 m | Poids:   80.5 kgs
 Nom:      NOEL | Prenom:     ADELAIDE | Sexe: feminin  | Taille:   1.50 m | Poids:   45.0 kgs
 Nom:    BERUBE | Prenom:       DANIEL | Sexe: masculin | Taille:   1.68 m | Poids:   65.0 kgs
 Nom:     HOULE | Prenom:      MARTINE | Sexe: feminin  | Taille:   1.90 m | Poids:  100.0 kgs
 Nom:  LAPIERRE | Prenom:       AGATHE | Sexe: masculin | Taille:   1.88 m | Poids:   90.5 kgs
 Nom: DESMARAIS | Prenom:       DENISE | Sexe: feminin  | Taille:   1.55 m | Poids:   40.5 kgs
 Nom:   ALLAIRE | Prenom:     FRANCINE | Sexe: feminin  | Taille:   1.60 m | Poids:   50.0 kgs
 Nom:      FORD | Prenom: MARIE-SOLEIL | Sexe: feminin  | Taille:   1.72 m | Poids:   62.5 kgs
 Nom:   FILLION | Prenom:         MARC | Sexe: masculin | Taille:   1.85 m | Poids:  100.0 kgs
 Nom:  POKLITAR | Prenom:     GUEORGUI | Sexe: masculin | Taille:   1.70 m | Poids:   75.5 kgs
*/
