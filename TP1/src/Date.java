/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Definition de la classe Date en POO, avec régles
	d'erreurs/validité de dates et année bissextile, pour 
	une gestion des vols realiste avec vrai dates qui existent.
	Travail Pratique 1
	Dernière Mise-à-jour : 01/06/2024
*/

import java.io.Serializable;

public class Date implements Serializable {
    private int jour;
    private int mois;
    private int an;

    public Date() {
        // Constructeur par défaut
    }

    //Constructeur régles défini et selon les validations.
    public Date(int jour, int mois, int an) {
        if (an < 1999 || an > 2099) { //messages d'erreur pour usagé, selon le type...
            throw new IllegalArgumentException("L'année séléctionné est invalide.");
        }
        if (mois < 1 || mois > 12) {
            throw new IllegalArgumentException("Le mois séléctionné est invalide.");
        }
        if (jour < 1 || jour > joursDansMois(mois, an)) {
            if (mois == 2 && jour == 29 && !estAnBissextile(an)) {
                throw new IllegalArgumentException("Année non-bissextile.");
            }
            throw new IllegalArgumentException("Le jour séléctionné est invalide.");
        }
        
        this.jour = jour;
        this.mois = mois;
        this.an = an;
    }

    // Getters
    public int getJour() {
        return jour;
    }
    public int getMois() {
        return mois;
    }
    public int getAn() {
        return an;
    }

    //Methode toString()...
    @Override
    public String toString() { // JJ/MM/AAAA
        return String.format("%02d/%02d/%04d", jour, mois, an);
    }

    //Validation des vrais jours pour chaque mois.
    private int joursDansMois(int mois, int an) {
        switch (mois) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return (estAnBissextile(an)) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Mois invalide.");
        }
    }

    //Consideration année bissextile pour fevrier. 
    private boolean estAnBissextile(int an) {
        if (an % 4 == 0) {
            if (an % 100 == 0) {
                return an % 400 == 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
