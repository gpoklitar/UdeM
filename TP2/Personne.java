/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Creation de la classe personne, pour importer un document de type text, 
	lire et entreposer les valeurs de ce document et manipuler les valeurs du document avec methodes standards du ArrayList.
	TP2 - Numero 1
	Dernière Mise-à-jour : 13/06/2023 
*/

//Classe personne.
public class Personne {
	  private double taille, poids;
	  private char sexe;
	  private String nom, prenom; // Ajout du nom et prenom...

	  // Constructeur avec nom et prenom.
	  // Pour faciliter la lecture du code, le nom de variable a ete changer. Ex : p -> poids.
	  public Personne(String nom, String prenom, char sexe, double taille, double poids) {
	      this.nom = nom;
	      this.prenom = prenom;	      
	      this.sexe = sexe;
	      this.taille = taille;
	      this.poids = poids;
	  }
	  // Constructeur 2 avec conversion des personnes avec grandeur et poids en pieds, pouces et livres.
	  public Personne (String nom, String prenom, char sexe, int nbPieds, int nbPouces, int nbLivres) {
		  this(nom, prenom, sexe, (nbPieds + nbPouces / 12.0) * 0.3048, nbLivres / 2.2);
	  } // Ajustement des facteurs de conversions pour le poids, 1 kg = 2.2 lb.

	  
	  // METHODES.
	  
	  // Methode pour afficher un sommaire des parametres d'une personne precise.
	  public void afficher(String message) {
	      System.out.println("Informations de la personne " + message + ":\n");
	      System.out.println(" - Nom     : " + nom);
	      System.out.println(" - Prenom  : " + prenom);
	      System.out.println(" - Sexe    : " + (sexe == 'F' ? "feminin" : "masculin"));
	      System.out.printf(" - Taille  : %4.2f metre\n", taille);
	      System.out.println(" - Poids   : " + poids + " kgs\n");
	  }
	  
	  // Methodes getters pour nom, prenom, sexe, taille et poids.
	  public String getNom() {
	      return nom;
	  }
	  
	  public String getPrenom() {
	      return prenom;
	  }
	  
	  public String getSexe() { // Affichage modifier pour simplifier la lecture pour l'utilisateur.
		  if(sexe == 'F') {
			  return "feminin";}
		  else{ 
			  return "masculin";}
	  }
	  
	  public double getTaille() {
	      return taille;
	  }
	  
	  public double getPoids() {
	      return poids;
	  }
	  
	  // Methodes setters pour nom, prenom, sexe, taille et poids.
	  public void setNom(String nouvNom) {
	      nom = nouvNom;
	  }

	  public void setPrenom(String nouvPrenom) {
	      prenom = nouvPrenom;
	  }
	  // Change le sexe seulement si utilisateur inscrit 'M' ou 'F' si autres valeur, le changement ne sera pas retenus.
	  public void setSexe(char nouvSexe) { 
	      if (nouvSexe == 'M' || nouvSexe == 'F')
	          sexe = nouvSexe;
	  }
	  
	  public void setTaille(double nouvTaille) {
	      if (nouvTaille > 0)
	          taille = nouvTaille;
	  }
	  
	  public void setPoids(double nouvPoids) {
	      if (nouvPoids > 0)
	          poids = nouvPoids;
	  }
	  
	  // Deux methodes additionnelles, pour modifier directement la grandeur et le poids (grandir et grossir).
	  public void grandir(double quantiteTaille) {
	      taille += quantiteTaille;
	  }
	  
	  public void grossir(double quantitePoids) {
	      poids += quantitePoids;
	  }
	  
	  // Methode toString, ajustement du format de publication purement cosmetique pour faciliter la lecture de l'utilisateur.
	  public String toString() {
	      return String.format(" Nom: %9s", nom) + String.format(" | Prenom: %12s", prenom) + 
	    		 String.format(" | Sexe: %-9s", (sexe == 'F' ? "feminin" : "masculin")) + 
	    		 String.format("| Taille: %6.2f m ", taille) + String.format("| Poids: %6.1f kgs", poids);
	  }

	  // Methode equals avec object.
	  public boolean equals(Object obj) {
	      if (this == obj)
	          return true;
	      if (obj == null || getClass() != obj.getClass())
	          return false;
	      Personne personne = (Personne) obj;
	      return nom.equals(personne.nom) && prenom.equals(personne.prenom);
	  }
	}
