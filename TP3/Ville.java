/**
TP3-A
Chargé de cours : Marcel Côté (cours théorique du mardi : groupe : A et B)
Remis par: Gueorgui Poklitar et Ion Hincu
Matricule: 20251051 et 0999079
Coordonnees: gueorgui.poklitar@umontreal.ca
*/

class Ville { //debut de la classe
    String nom; //Declaration initial des variables.
    int population;
    
    Ville(String nom, int population) { //Constructeur 1.
        this.nom = nom;
        this.population = population;
    }
    
    Ville() { //Constructeur 2. Valeurs par default.
        this("Anonyme", 0);
    }  
    
    public String getNom() { //Methode 1. Getter pour le nom.
        return nom;
    }
   
    public int getPopulation() { //Methode 2. Getter pour la population.
        return population;
    }
    
    public void setNom(String nom) { //Methode 3. Setter pour le nom.
        this.nom = nom;
    }
    
    public void setPopulation(int population) { //Methode 4. Setter pour la population.
        this.population = population;
    }
    
    public String toString() { //Methode 5. Le string to string, retour de la chain de charactere avec compte et habitants.
        return nom + " compte " + population + " habitants";
    }
    //Methode 6 (bonus). Bloc d'impression pour evite d'imprimer 2 fois a chaque fois.
    //Methode statique, peut donc etre appeler sans dependre de la classe Ville.
    public static void printBloc(Ville ville1, Ville ville2) {
        System.out.println(ville1);
        System.out.println(ville2);
    }
    //Debut de la methode main du programme. 
    public static void main(String[] args) {
        //Creation des objects ville1 et ville2, fesent partie de la class Ville.
        //Donc regles de la classe Ville s'appliquent a ville1 et ville2.
        Ville ville1 = new Ville(); //Valeur par default.
        Ville ville2 = new Ville("Mascouche", 41000); //Valeur de Mascouche est defini et existante.
        
        printBloc(ville1, ville2); //On fait appel a notre racourcie d'impression. 2 lignes en 1 appel.
        ville1.setNom("Montréal"); //Changement au niveau des objects ville1 et ville 2 a l'aide de (get et set).
        ville1.setPopulation(1700000);//getNom n'a pas ete utiliser dans ce cas.
        ville2.setPopulation(ville2.getPopulation() + 5000); //getPopulation de la ville2 = 41000 et on additionne 5000.
        printBloc(ville1, ville2); //On fait appel a notre racourcie d'impression. 2 lignes en 1 appel.
    }
}
//Resultat
/**
Anonyme compte 0 habitants
Mascouche compte 41000 habitants
Montréal compte 1700000 habitants
Mascouche compte 46000 habitants
*/
