/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Similaire au numero 3, mais avec une autre class additionnelle avec methodes 
	pour inverser l'ordre des prenom et compter les nombre total de noms et prenoms
	TP1 - Numero 4.2
	Dernière Mise-à-jour : 23/05/2023
*/

public class TestNomPrenom {
	
	//METHODES STATIQUES DE TestNomPrenom
	
    private static NomPrenom[] nomsPrenoms = new NomPrenom[5]; //Creations du tableau avec grandeur identifiée
    
    
    private static void initializeNomsPrenoms() { //Methode static pour initializer le tableau individus
        String[] individus = { //Liste d'individus
                "Massie Chene, Ademar",
                "Lacolle-Lepage, Pierre-Paul Andre",
                "Desgagne-Lacourse, Fabien",
                "Aucours, Yvon",
                "Lacido, Remi Fassol"
        };

        for (int i = 0; i < individus.length; i++) { //Entreposage des individus
            nomsPrenoms[i] = new NomPrenom(individus[i]);
        }
    }

    private static void afficherNomsPrenoms() { //Methode static pour imprimer les individus avec un appel de methode pour inverser l'ordre des prenoms.
        for (NomPrenom np : nomsPrenoms) {
            np.printOrdreInverse();
        }
    }

    private static int compterPrenoms() { //Methode static qui appel le compte de nombres de prenoms et les additionnent ensemble sous un total.
        int totalPrenoms = 0;
        for (NomPrenom np : nomsPrenoms) {
            totalPrenoms += np.getPrenomNombre();
        }
        return totalPrenoms; //Avec un retour int.
    }

    private static int compterNoms() {//Methode static qui appel le compte de nombres de noms et les additionnent ensemble sous un total.
        int totalNoms = 0;
        for (NomPrenom np : nomsPrenoms) {
            totalNoms += np.getNomNombre();
        }
        return totalNoms; //Avec un retour int.
    }
	
    
	// METHODE MAIN

    public static void main(String[] args) { //On appel nos methodes statiques dans notre main + affichage.
    	
        initializeNomsPrenoms();

        System.out.println("Voici les noms et prenoms saisis, avec un ordre inverse des prenoms:\n"); //Cosmetique pour presentation...
        afficherNomsPrenoms();

        int totalPrenoms = compterPrenoms(); //Resultat des mthodes appelées est entreposé
        int totalNoms = compterNoms();

        System.out.println("\nNombre total de prenoms pour tout le tableau: " + totalPrenoms); //presentation des resultats
        System.out.println("Nombre total de noms pour tout le tableau: " + totalNoms);
    }
}

//RESULTAT
/*
Voici les noms et prenoms saisis, avec un ordre inverse des prenoms:

Massie Chene, Ademar
Lacolle-Lepage, Andre Paul Pierre
Desgagne-Lacourse, Fabien
Aucours, Yvon
Lacido, Fassol Remi

Nombre total de prenoms pour tout le tableau: 8
Nombre total de noms pour tout le tableau: 8
 */

