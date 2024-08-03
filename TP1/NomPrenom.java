/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Similaire au numero 3, mais avec une autre class additionnelle avec methodes 
	pour inverser l'ordre des prenom et compter les nombre total de noms et prenoms
	TP1 - Numero 4.1
	Dernière Mise-à-jour : 23/05/2023
*/

public class NomPrenom { //Class et declaration des variables de base.
	
    private String nom;
    private String prenom;

	//METHODES NON-STATIQUES DE NomPrenom 
    
    public NomPrenom(String nomPrenomString) { //Constructeurs des variables avec split initial pour cree un tableau pour les noms et pour les prenoms.
        String[] nomPrenom = nomPrenomString.split(", ");
        this.nom = nomPrenom[0];
        this.prenom = nomPrenom[1];
    }

    public int getNomNombre() { //Methode pour recuperer le nombre des noms.
        return nom.split(" |-").length;
    }

    public int getPrenomNombre() { //Methode pour recuperer le nombre des prenoms
        return prenom.split(" |-").length;
    }

    public void printOrdreInverse() {  //Methode pour inverser les prenoms.
        String[] prenoms = prenom.split(" |-");
        String ordreInverse = nom + ",";  // Nouveau string... initalement debute avec le nom et suivit d'une virgule, par default.

        for (int i = prenoms.length - 1; i >= 0; i--) { // Commence de la fin des prenoms.
            ordreInverse += " " + prenoms[i]; //Ajoute le dernier prenom au nouveau string avec un espace en avant, et decremente le placement des prenoms au suivant, jusqu'au dernier prenom.
        }
        
        System.out.println(ordreInverse); // Imprime la totalité des noms et prenoms, selon le nouvelle ordre.
    }
}

