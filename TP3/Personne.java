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

//Classe Personne, est abstraite (aucune personne nest cree, 
//mais sont tous des personnes à la base.)
//avec une implementation de Comparable sur la list personne.
public abstract class Personne implements Comparable<Personne> {
    private String nom, prenom;
    private double taille, poids;
    private char sexe;
    
//Constructeur 1
    public Personne(String nom,String prenom,char sexe,double taille,
    double poids) {
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.taille = taille;
        this.poids = poids;
    }   
//Constructeur 2 avec conversion des personnes avec pieds, pouces et livres.
	public Personne(String nom,String prenom,char sexe,
	int nbPieds,int nbPouces,int nbLivres) {
		this(nom, prenom, sexe, 
		(nbPieds + nbPouces / 12.0) * 0.3048, nbLivres / 2.2);
	} // Ajustement des facteurs de conversions pour le poids, 1 kg = 2.2 lb.
	
//Les getters.
    public double getTaille() {
        return taille;
    }

    public double getPoids() {
        return poids;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public char getSexe() {
        return sexe;
    }
    
//Les setters.
    public void setTaille(double nouvTaille) {
    	if (nouvTaille > 0) {
    		taille = nouvTaille; 
    	}
    }

    public void setPoids(double nouvPoids) {
    	if (nouvPoids > 0) {
    		taille = nouvPoids;
    	}
    }

    public void setNom(String nouvNom) {
    	nom = nouvNom;
    }

    public void setPrenom(String nouvPrenom) {
    	prenom = nouvPrenom;
    }

    public void setSexe(char nouvSexe) {
        sexe = nouvSexe;
    }

//Methode toString. 
    @Override
    public String toString() {
        return String.format("%12s", prenom) + 
        		String.format(" | %10s", nom) + 
        		String.format(" | %-2s", sexe) + 
        		String.format("| %4.2f m ", taille) + 
        		String.format("| %6.2f kg", poids);
    }
    
//Methode equals.
    @Override
    public boolean equals(Object o) {
        if (o instanceof Personne) {
            Personne p = (Personne) o;
            return nom.equals(p.nom) && prenom.equals(p.prenom);
        } else {
            return false;
        }
    }
    
//Methode compareTo.
    @Override
    public int compareTo(Personne autre) {
        int res = nom.compareTo(autre.nom);
        if (res == 0)
            res = prenom.compareTo(autre.prenom);
        return res;
    }
}

