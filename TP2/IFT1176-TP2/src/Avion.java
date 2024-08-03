/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Definition de la classe Avion en POO. 
	Constructeur, methodes get() et toString(). Similaire au TP1.
	Travail Pratique 2
	Dernière Mise-à-jour : 23/06/2024
*/

import java.io.Serializable;

public class Avion implements Serializable {
    private static final long serialVersionUID = 1L;
    private String modele;
    private String type;

    //Constructeur
    public Avion(String modele, String type) {
        this.modele = modele;
        this.type = type;
    }

    //Getter
    public String getModele() {
        return modele;
    }
    public String getType() {
        return type;
    }

    //La methode to string .
    @Override
    public String toString() {
        return "Avion{modele='" + modele + "', type='" + type + "'}";
    }
}
