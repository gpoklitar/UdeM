/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Methodes pour modifier les données de vols de la compagnie importée.
	Definition des methodes pour chargement des données en HashMap pour les vols.
	Ajouter, supprimer, get vols et nom de compagnie.
	Travail Pratique 2
	Dernière Mise-à-jour : 01/23/2024
*/

import java.util.HashMap;
import java.util.Map;

public class Compagnie {
    private String nom;
    private Map<Integer, Vol> vols;

    
    
    //Constructeur
    public Compagnie(String nom) {
        this.nom = nom;
        this.vols = new HashMap<>();
    }

    
    
    //Getter... Get vols et get no
    public Map<Integer, Vol> getVols() {
        return vols;
    }
	public String getNom() {
		return nom;
	}
	
	
	// Gestion primaire des vols ajouter, supprimer pour le HashMap.
	public void ajouterVol(Vol vol) {
        vols.put(vol.getNumero(), vol);
    }
    public void supprimerVol(Vol vol) {
        vols.remove(vol.getNumero());
    }
}
