/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Definition de la classe Date en POO, avec régles
	d'erreurs/validité de dates et année bissextile, pour 
	une gestion des vols realiste avec vrai dates qui existent.
	Travail Pratique 2
	Dernière Mise-à-jour : 23/06/2024
*/

import java.io.Serializable;
import java.text.DecimalFormat;

public class Date implements Serializable {
    private int jour;
    private int mois;
    private int annee;

    //Constructeur de date
    public Date(int jour, int mois, int annee) {
        if (!isValidDate(jour, mois, annee)) {
            throw new IllegalArgumentException("Invalid date: " + jour + "/" + mois + "/" + annee);
        }
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
    }

    
    //Les getters jour, mois, année.
    public int getJour() {
        return jour;
    }
    public int getMois() {
        return mois;
    }
    public int getAnnee() {
        return annee;
    }

    
    //Les setters jour, année, mois.
    public void setJour(int jour) {
        if (!isValidDate(jour, this.mois, this.annee)) {
            throw new IllegalArgumentException("Invalid day: " + jour);
        }
        this.jour = jour;
    }
    public void setMois(int mois) {
        if (!isValidDate(this.jour, mois, this.annee)) {
            throw new IllegalArgumentException("Invalid month: " + mois);
        }
        this.mois = mois;
    }
    public void setAnnee(int annee) {
        if (!isValidDate(this.jour, this.mois, annee)) {
            throw new IllegalArgumentException("Invalid year: " + annee);
        }
        this.annee = annee;
    }

    
    // La date en question, existe-t'elle ou non?
    //Difference du nombre de jours selon le mois.
    private boolean isValidDate(int jour, int mois, int annee) {
        if (mois < 1 || mois > 12) {
            return false;
        }
        if (jour < 1 || jour > 31) {
            return false;
        }
        if (mois == 2) {
            if (isLeapYear(annee)) {
                return jour <= 29;
            } else {
                return jour <= 28;
            }
        }
        if (mois == 4 || mois == 6 || mois == 9 || mois == 11) {
            return jour <= 30;
        }
        return true;
    }
    //Régle de l'année bissextile.
    private boolean isLeapYear(int annee) {
        if (annee % 4 == 0) {
            if (annee % 100 == 0) {
                return annee % 400 == 0;
            }
            return true;
        }
        return false;
    }

    
    
    //Methode de to string.
    @Override
    public String toString() {
    	//Formatage, pour guarantire une uniformité des entrés de dates.
        DecimalFormat df = new DecimalFormat("00");
        DecimalFormat dfy = new DecimalFormat("0000");
        return df.format(jour) + "-" + df.format(mois) + "-" + dfy.format(annee);
    }
}
