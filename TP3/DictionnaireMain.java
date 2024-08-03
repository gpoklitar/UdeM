/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
		But : Classe DictionnaireMain represente l'interface utilisateur pour interagir avec 
		le dictionnaire.Utilisation d'un objet de la classe Dictionnaire pour gerer le dictionnaire 
		de mots, significations et traductions. Utilisation d'une boucle DO/WHILE pour afficher un 
		menu interactif et gerer les choix de l'utilisateur. Gestion des entrees utilisateur, y 
		compris la recherche de mots, la traduction et l'affichage de l'historique. Utilisation 
		de la classe UtilitairesCSV pour peupler le dictionnaire et generer des resultats de prefixe.
	Travail Pratique 3
	Derniere Mise-a-jour : 12/12/2023
*/

import java.util.InputMismatchException;
import java.util.Scanner;

public class DictionnaireMain {

    public static void main(String[] args) {
        Dictionnaire dictionnaire = new Dictionnaire();
        UtilitairesCSV.peuplerDictionnaireDepuisCSV(dictionnaire, "dictionary.csv");
        genererFichierPrefixeTest(); // Generer testPrefix.txt au debut

        Scanner scanner = new Scanner(System.in);
        int choix;
        boolean quitter = false;

        do {
            afficherMenu();

            try {
                choix = scanner.nextInt();
                scanner.nextLine(); // Consommer le retour a la ligne

                switch (choix) {
                    case 1:
                        System.out.print("Entrez le mot a rechercher : ");
                        String mot = scanner.nextLine();
                        System.out.println(dictionnaire.chercherMot(mot));
                        break;
                    case 3:
                        System.out.print("Entrez le mot a traduire : ");
                        mot = scanner.nextLine();
                        System.out.println(dictionnaire.traduireMot(mot));
                        break;
                    case 2:
                        System.out.println("Historique de recherche : " + 
                    dictionnaire.getHistoriqueRecherches());
                        break;
                    case 4:
                        UtilitairesCSV.genererResultatsPrefixe(dictionnaire, "testPrefix.txt",
                        		"testPrefixResult.txt");
                        System.out.println("\n 'testPrefixResult.txt' genere avec succes.\n ");
                        break;
                    case 5:
                        System.out.println("Sortie...");
                        quitter = true;
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez entrer une option"
                        		+ " de menu valide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entree invalide. Veuillez entrer une option de menu valide.");
                scanner.nextLine(); // Consommer l'entree invalide
                choix = 0; // Definir choix sur une valeur invalide pour eviter une boucle infinie
            }
        } while (!quitter);

        scanner.close();
    }

    // Methode pour generer testPrefix.txt au debut du programme
    private static void genererFichierPrefixeTest() {
        UtilitairesCSV.creerNouveauFichier("testPrefix.txt");
    }

    // Methode pour afficher le menu
    private static void afficherMenu() {
        System.out.println("=========================================================="
        		+ "=====================");
        System.out.println("1. Rechercher un mot");
        System.out.println("2. Afficher l'historique");
        System.out.println("3. Traduire un mot");
        System.out.println("4. Obtenir 'testPrefixResult.txt'");
        System.out.println("5. Quitter");
        System.out.print("Entrez votre choix : ");
    }
}


//RESULTAT : voici un test...
//-----------------------------------------------------------------------------
//Tous les fonctionnalite marchent bien. Ainsi que la selection des prefixes.

//L'historique affiche toutes les recherches et demandes de traductions a la fois.

//Option 4 permet de consulter le resultat du fichier text generer pendent que 
//le programme roule toujours, donc sans avoir besoin de quitter.
//-----------------------------------------------------------------------------



/*
Bienvenue !
Veuillez selectionner une option ci-dessous :
===============================================================================
1. Rechercher un mot
2. Afficher l'historique
3. Traduire un mot
4. Obtenir 'testPrefixResult.txt'
5. Quitter
Entrez votre choix : 1
Entrez le mot a rechercher : a
: "The first letter of the English and of many other alphabets. The capital A of the alphabets of Middle and Western Europe
===============================================================================
1. Rechercher un mot
2. Afficher l'historique
3. Traduire un mot
4. Obtenir 'testPrefixResult.txt'
5. Quitter
Entrez votre choix : 1
Entrez le mot a rechercher : e

Mot non trouve. 
165 resultats similaires trouves : [earwigging, earwitness, ease, eased, easeful, easel, easeless, easement, easily, easiness, easing, east, east indian, easter, easterling, easterly, eastern, easternmost, easting, east-insular, eastward, eastwards, easy, easy-chair, easy-going, eat, eatable, eatage, eaten, eater, eath, eating, eau de cologne, eau de vie, eavedrop, eaves, eavesdrop, eavesdropper, eavesdropping, ebb, ebb tide, ebbed, ebbing, ebionite, ebionitism, eblanin, eblis, ebon, ebonies, ebonist, ebonite, ebonize, ebonized, ebonizing, ebony, ebracteate, ebracteolate, ebrauke, ebrieties, ebriety, ebrillade, ebriosity, ebrious, ebulliate, ebullience, ebulliency, ebullient, ebullioscope, ebullition, eburin, eburnation, eburnean, eburnification, eburnine, ecardines, ecarte, ecaudate, ecballium, ecbasis, ecbatic, ecbole, ecbolic, ecboline, eccaleobion, ecce homo, eccentric, eccentrical, eccentrically, eccentricities, eccentricity, ecchymose, ecchymoses, ecchymosis, ecchymotic, eccle, ecclesia, ecclesiae, ecclesial, ecclesiarch, ecclesiast, ecclesiastes, ecclesiastic, ecclesiastical, ecclesiastically, ecclesiasticism, ecclesiasticus, ecclesiological, ecclesiologist, ecclesiology, eccritic, ecderon, ecdyses, ecdysis, ecgonine, echauguette, eche, echelon, echidna, echidnine, echinate, echinated, echini, echinid, echinidan, echinital, echinite, echinococcus, echinoderm, echinodermal, echinodermata, echinodermatous, echinoid, echinoidea, echinozoa, echinulate, echinus, echiuroidea, echo, echoed, echoer, echoes, echoing, echoless, echometer, echometry, echon, echoon, echoscope, eclair, eclaircise, eclaircissement, eclampsia, eclampsy, eclat, eclectic, eclectically, eclecticism, eclegm, eclipse, eclipsed, english word, esopian, esopic, esthesiometer, esthetics]

===============================================================================
1. Rechercher un mot
2. Afficher l'historique
3. Traduire un mot
4. Obtenir 'testPrefixResult.txt'
5. Quitter
Entrez votre choix : 1
Entrez le mot a rechercher : eas

Mot non trouve. 
23 resultats similaires trouves : [ease, eased, easeful, easel, easeless, easement, easily, easiness, easing, east, east indian, easter, easterling, easterly, eastern, easternmost, easting, east-insular, eastward, eastwards, easy, easy-chair, easy-going]

===============================================================================
1. Rechercher un mot
2. Afficher l'historique
3. Traduire un mot
4. Obtenir 'testPrefixResult.txt'
5. Quitter
Entrez votre choix : 1
Entrez le mot a rechercher : easel
n.: "A frame (commonly) of wood serving to hold a canvas upright
===============================================================================
1. Rechercher un mot
2. Afficher l'historique
3. Traduire un mot
4. Obtenir 'testPrefixResult.txt'
5. Quitter
Entrez votre choix : 3
Entrez le mot a traduire : easel
Chevalet
===============================================================================
1. Rechercher un mot
2. Afficher l'historique
3. Traduire un mot
4. Obtenir 'testPrefixResult.txt'
5. Quitter
Entrez votre choix : 2
Historique de recherche : [a, e, eas, easel, easel]
===============================================================================
1. Rechercher un mot
2. Afficher l'historique
3. Traduire un mot
4. Obtenir 'testPrefixResult.txt'
5. Quitter
Entrez votre choix : 4

 'testPrefixResult.txt' genere avec succes.
 
===============================================================================
1. Rechercher un mot
2. Afficher l'historique
3. Traduire un mot
4. Obtenir 'testPrefixResult.txt'
5. Quitter
Entrez votre choix : 5
Sortie...

*/
