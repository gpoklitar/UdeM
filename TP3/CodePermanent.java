/**
TP3-B
Chargé de cours : Marcel Côté (cours théorique du mardi : groupe : A et B)
Remis par: Gueorgui Poklitar et Ion Hincu
Matricule: 20251051 et 0999079
Coordonnees: gueorgui.poklitar@umontreal.ca
*/

import java.util.Scanner; //Pour pouvroir saisir des valeurs.

public class CodePermanent { //Creation de la classe et definition des variables.
    String nom;
    String prenom;
    int jourNaissance;
    int moisNaissance;
    int anneeNaissance;
    boolean estHomme;
    int nbDoublons;
    
    public CodePermanent(String nom,String prenom,int jourNaissance,int moisNaissance,int anneeNaissance,boolean estHomme,int nbDoublons) {//Constructeur.
        this.nom = nom;
        this.prenom = prenom ;
        this.jourNaissance = jourNaissance;
        this.moisNaissance = moisNaissance;
        this.anneeNaissance = anneeNaissance;
        this.estHomme = estHomme;
        this.nbDoublons = nbDoublons;
    }
    
    public String genererCodePermanent() { //methode pour generer/construire le code permanent.
        String CodePermanent = "";//Commence avec rien.
        //Section nom.
        if (nom.length() >= 3) { //Si la longeur plus ou egale a 3...
            CodePermanent += nom.substring(0,3).toUpperCase(); //Decoupe le string a partir de 0 a 3.(Pour inclure 3 premieres lettres).
        } else {
            CodePermanent += nom.toUpperCase(); // Imprime en majuscule.
            for(int i = 0 ; i < 3 - nom.length(); i++) { //Pour rajouter un X tant que la longeure est moins de 3 lettres.
                CodePermanent += "X";
            }
        }
        //Section prenom.
        CodePermanent += prenom.substring(0,1).toUpperCase(); //Imprime la lettre de 0 a 1 du string prenom en majuscule.
        //Section jourNaissance.
        if (jourNaissance < 10) {
            CodePermanent += "0"; //Pour rajouter un 0 si moins que 2 chiffres.
        }
        CodePermanent += jourNaissance;
        //Section moisNaissance et sexe.
        if (estHomme && moisNaissance < 10) {
            CodePermanent += "0";//Pour rajouter un 0 si moins que 2 chiffres.
            CodePermanent += moisNaissance;
        } else if(estHomme && moisNaissance >= 10){
            CodePermanent += moisNaissance;
        } else if (!estHomme){//Si estHomme est faux (donc sexe feminin), ajoute 50 au mois de naissance.
            CodePermanent += moisNaissance + 50; // Sex feminin auront toujours 2 chiffres.
        }
        //Section anneeNaissance.
        String anneeNaissanceStr = Integer.toString(anneeNaissance);
        String anneeCpStr = anneeNaissanceStr.substring(2,4); 
        CodePermanent += anneeCpStr;
        //Section doublons.
        CodePermanent += String.format("%02d", nbDoublons);
        
        return CodePermanent;
    }
    //Section compte des modification et options de modifications.
    public void modifierInfo() {
        Scanner sc = new Scanner(System.in);
        String choix = "";
        int modifications = 0;
        do {
            System.out.print("Votre choix : ");
            choix = sc.nextLine();
            switch (choix) {
                case "n":
                    System.out.print("Saisir le nom : ");
                    this.nom = sc.nextLine();
                    System.out.println(this.toString());
                    break;
                case "p":
                    System.out.print("Saisir le prenom : ");
                    this.prenom = sc.nextLine();
                    System.out.println(this.toString());
                    break;
                case "j":
                    System.out.print("Saisir le jour de naissance : ");
                    this.jourNaissance = sc.nextInt();
                    sc.nextLine();
                    System.out.println(this.toString());
                    break;
                case "m":
                    System.out.print("Saisir le mois de naissance : ");
                    this.moisNaissance = sc.nextInt();
                    sc.nextLine();
                    System.out.println(this.toString());
                    break;
                case "a":
                    System.out.print("Saisir l'annee de naissance : ");
                    this.anneeNaissance = sc.nextInt();
                    sc.nextLine();
                    System.out.println(this.toString());
                    break;
                case "s":
                    System.out.print("Est-il un homme (O/N) : ");
                    String sexeStr = sc.nextLine();
                    if (sexeStr.equalsIgnoreCase("O")) {
                        this.estHomme = true;
                    } else {
                        this.estHomme = false;
                    }
                    System.out.println(this.toString());
                    break;
                case "d":
                    System.out.print("Saisir la position dans les doubles : ");
                    this.nbDoublons = sc.nextInt();
                    sc.nextLine();
                    System.out.println(this.toString());
                    break;
                case "q":
                    System.out.println("Vous avez effectue " + modifications + " modifications.");
                    break;
                default:
                    System.out.println("Choix invalide.");
                    break;
            }
            modifications++;
        } while (!choix.equalsIgnoreCase("q"));
    }
    // Section string to string afficher code permanent.
    public String toString() {
        return "Voici le code permanent " + this.genererCodePermanent();
    }
    //Section main, saisie initiale des donnees et impression du menu de modification une fois.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Saisir le nom : ");
        String nom = sc.nextLine();
        System.out.print("Saisir le prenom : ");
        String prenom = sc.nextLine();
        System.out.print("Saisir le jour de naissance : ");
        int jourNaissance = sc.nextInt();
        sc.nextLine();
        System.out.print("Saisir le mois de naissance : ");
        int moisNaissance = sc.nextInt();
        sc.nextLine();
        System.out.print("Saisir l'annee de naissance : ");
        int anneeNaissance = sc.nextInt();
        sc.nextLine();
        System.out.print("Est-il un homme (O/N) : ");
        String sexeStr = sc.nextLine();
        boolean estHomme = sexeStr.equalsIgnoreCase("O");
        System.out.print("Saisir la position dans les doubles : ");
        int nbDoublons = sc.nextInt();
        sc.nextLine();
        
        CodePermanent codePermanent = new CodePermanent(nom,prenom,jourNaissance,moisNaissance,anneeNaissance,estHomme,nbDoublons);
        System.out.println(codePermanent.toString());
        
        System.out.println("Menu de modification");
        System.out.println("n) pour le nom");
        System.out.println("p) pour le prenom");
        System.out.println("j) pour le jour de naissance");
        System.out.println("m) pour le mois de naissance");
        System.out.println("a) pour l’annee de naissance");
        System.out.println("s) pour le sexe");
        System.out.println("d) pour la position des doublons");
        System.out.println("q) pour quitter");
        
        codePermanent.modifierInfo(); //Appel la section modification de menu tant qu'il n'y a pas de sortie de programme (option q).
    }
}
//Resultat
/**
Saisir le nom : Cote
Saisir le prenom : Marcel
Saisir le jour de naissance : 25
Saisir le mois de naissance : 3
Saisir l'annee de naissance : 1998
Est-il un homme (O/N) : O
Saisir la position dans les doubles : 3
Voici le code permanent COTM25039803
Menu de modification
n) pour le nom
p) pour le prenom
j) pour le jour de naissance
m) pour le mois de naissance
a) pour l’annee de naissance
s) pour le sexe
d) pour la position des doublons
q) pour quitter
Votre choix : n
Saisir le nom : Le
Voici le code permanent LEXM25039803
Votre choix : s
Est-il un homme (O/N) : N
Voici le code permanent LEXM25539803
Votre choix : m
Saisir le mois de naissance : 11
Voici le code permanent LEXM25619803
Votre choix : q
Vous avez effectue 3 modifications.
 */