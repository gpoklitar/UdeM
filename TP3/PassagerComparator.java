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

//Importation de l'interface Comparator.
import java.util.Comparator;

public class PassagerComparator implements Comparator<Passager> {
//Methode compare avec override de la methode dans l'interface...
    @Override
    public int compare(Passager p1, Passager p2) {
    	// Comparaison des nums de reservation des passagers
        int reservationComparison = Integer.compare(p1.getNumeroReservation(), 
        p2.getNumeroReservation());
        
        if (reservationComparison != 0) {
//Si num de reserv. sont differents, retourne le resultat de la comparaison.
            return reservationComparison;
        } else {
//Si num de reserv sont identique compare les noms des passagers..
            int nomComparison = p1.getNom().compareTo(p2.getNom());
            
            if (nomComparison != 0) {
//Si les noms sont differents retourne le resultat de la comparaison.
                return nomComparison;
            } else {
// Si les noms sont egaux compare les prenoms des passagers
                return p1.getPrenom().compareTo(p2.getPrenom());
            }
        }
    }
}

