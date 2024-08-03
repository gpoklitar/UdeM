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

//Equipage est une extension de Personne (enfant de Personne).
public class Equipage extends Personne {
    private char poste;

//Contructeur d'equipage.
    public Equipage(String nom, String prenom, 
    char sexe, char poste, double taille, double poids) {
        super(nom, prenom, sexe, taille, poids);
        this.poste = poste;
    }

//Methode getter pour ce nouveau champ privee.
    public char getPoste() {
        return poste;
    }

//Methode pour obtenire le nom complete du poste, selon le code de charactere.
    public String getPosteDescription() {
        switch (poste) {
            case 'P':
                return "Pilote";
            case 'C':
                return "Copilote";
            case 'A':
//Ajustement de la description selon le sexe.
                return (getSexe() == 'F') ? "Agente de bord" : "Agent de bord";
            default:
                return "";
        }
    }

//Un override du toString du parent, pour une verson d'affichage pour l'equipage.
    @Override
    public String toString() {
        return String.format(" %21s", getPosteDescription()) + 
        		" | ---- | "+ 
        		super.toString();
    }
}

