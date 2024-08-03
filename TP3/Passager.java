/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Classe Personne, qui est parent de classe Passager et Equipage. 
	Importation des fichier .txt, pour les trier et sortir une liste pour 
	remplir un vol. Suite au triage le programme  renvoi le resultat et 
	retire le groupe selectionnee pour le vol du fichier .txt des passagers.
	Travail : T.P.#3
	Dernière Mise-à-jour : 07/07/2023
*/

//Passager est une extension de Personne (enfant de Personne).
public class Passager extends Personne {
    private int numeroReservation;

//Constructeur de Passager.
    public Passager(String nom, String prenom, char sexe, 
    double taille, double poids, int numeroReservation) {
//Keyword super, pour liee le constructeur du parent.
        super(nom, prenom, sexe, taille, poids);
        this.numeroReservation = numeroReservation;
    }
    
//Methode get seulement, on ne veut pas pouvoir set un No de reservation.
    public int getNumeroReservation() {
        return numeroReservation;
    }
    
//Un override du toString, pour un affichage personalisee pour les passagers.
    @Override
    public String toString() {
        return " Numero de reservation | " + 
        		String.format("%4s", numeroReservation) + 
        		" | " + super.toString();
    }
}

