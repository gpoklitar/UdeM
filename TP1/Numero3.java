/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Creation de class avec methodes split, et retour du nombre noms et prenoms, avec des cas singulier & pluriel.
	TP1 - Numero 3
	Dernière Mise-à-jour : 23/05/2023
*/

public class Numero3 {
	
    public static void main(String[] args) {
    	
        String[] individus = { //Individus inscrits
            "Massie Chene, Ademar",
            "Lacolle-Lepage, Pierre-Paul Andre",
            "Desgagne-Lacourse, Fabien",
            "Aucours, Yvon" };

        for (String nomPrenom : individus) { //Pour chaque chaine string individus...
            String[] nomPartie = nomPrenom.split(", "); //Entrepose les chaines de nomPrenom à partir de la chaine individus

            String noms = nomPartie[0]; //Entreposage de la partie nom
            String prenoms = nomPartie[1]; //Entreposage de la partie prenom

            int nomsCompte = noms.split(" |-").length; //Variable de compte pour les noms
            int prenomsCompte = prenoms.split(" |-").length; //Variable de compte pour les prenoms
            
            
            //If statement pour les 4 scenarios possibles pour imprimer la bonne phrase(pluriel vs singulier)
            if (nomsCompte == 1 && prenomsCompte == 1) { //SI le compte de nom ET prenom est exactement 1 (pour les deux)
	            System.out.println("Pour la chaine " +'"'+ nomPrenom +'"'+" : ");
	            System.out.println("Il y a " + nomsCompte +" nom et "+ prenomsCompte + " prenom.\n"); //Nom singulier et prenom singulier
	        
            } else if (nomsCompte == 1) { //SI seulement le nom est exactement 1
	            System.out.println("Pour la chaine " +'"'+ nomPrenom +'"'+" : ");
	            System.out.println("Il y a " + nomsCompte +" nom et "+ prenomsCompte + " prenoms.\n"); //nom singulier et prenom pluriel
            	
            } else if (prenomsCompte == 1) { //SI seulement le prenom est exactement 1
	            System.out.println("Pour la chaine " +'"'+ nomPrenom +'"'+" : ");
	            System.out.println("Il y a " + nomsCompte +" noms et "+ prenomsCompte + " prenom.\n"); //nom pluriel et prenom singulier
            
            } else { //Tout le reste (donc aucun nom ou prenom pluriel)...
	            System.out.println("Pour la chaine " +'"'+ nomPrenom +'"'+" : ");
	            System.out.println("Il y a " + nomsCompte +" noms et "+ prenomsCompte + " prenoms.\n"); //nom pluriel et prenom pluriel
            }
        }
    }
}
//Resultat
/*
Pour la chaine "Massie Chene, Ademar" : 
Il y a 2 noms et 1 prenom.

Pour la chaine "Lacolle-Lepage, Pierre-Paul Andre" : 
Il y a 2 noms et 3 prenoms.

Pour la chaine "Desgagne-Lacourse, Fabien" : 
Il y a 2 noms et 1 prenom.

Pour la chaine "Aucours, Yvon" : 
Il y a 1 nom et 1 prenom.
*/
