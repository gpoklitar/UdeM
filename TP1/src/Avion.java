/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Definition de la classe Avion en POO. 
	Constructeur, methodes get() et toString().
	Travail Pratique 1
	Dernière Mise-à-jour : 01/06/2024
*/

import java.io.Serializable;

public class Avion implements Serializable {
    private int numeroAvion;
    private String typeAvion;
    private int nombrePlaces;

    // Constructeur numero, type et places.
    public Avion(int numeroAvion, String typeAvion, int nombrePlaces) {
        this.numeroAvion = numeroAvion;
        this.typeAvion = typeAvion;
        this.nombrePlaces = nombrePlaces;
    }

    // Getters.
    public int getNumeroAvion() {
        return numeroAvion;
    }
    public String getTypeAvion() {
        return typeAvion;
    }
    public int getNombrePlaces() {
        return nombrePlaces;
    }

    // toString méthode.
    @Override
    public String toString() {
        return numeroAvion + "\t" + typeAvion + "\t" + nombrePlaces;
    }
}
