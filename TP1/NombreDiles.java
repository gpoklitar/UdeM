/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Compter le nombre d'iles dans une grille binaire représentant 
	une carte ou '1' correspond à de la terre et '0' a de l'eau. 
	Une ile est définie comme une zone de terre entouree d'eau, 
	ou la terre peut etre connectee horizontalement ou verticalement. 
	Le code utilise une approche recursive pour explorer la grille et 
	identifier les iles, puis renvoie le nombre total d'iles.
	TP1 - Numero 1
	Dernière Mise-à-jour : 11/10/2023
*/

public class NombreDiles {
    // Methode principale pour compter le nombre d'iles dans la grille
    public int nombreDiles(char[][] grille) {
        // Verifie si la grille est vide ou nulle
        if (grille == null || grille.length == 0) {
            return 0;
        }

        int nombreDiles = 0;
        int lignes = grille.length;
        int colonnes = grille[0].length;

        // Parcours de la grille pour detecter les iles.
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                if (grille[i][j] == '1') {
                    // Nouvelle ile detectee, incremente le compteur
                    nombreDiles++;
                    // Appel a la fonction de recherche en profondeur
                    parcoursProfondeur(grille, i, j);
                }
            }
        }

        return nombreDiles;
    }

    // Fonction recursive pour explorer les terres adjacentes
    private void parcoursProfondeur(char[][] grille, int ligne, int colonne) {
        int lignes = grille.length;
        int colonnes = grille[0].length;

        // Verifie les limites de la grille et si la case est de l'eau.
        if (ligne < 0 || ligne >= lignes || colonne < 0 || 
		colonne >= colonnes || grille[ligne][colonne] == '0') {
            return;
        }

        grille[ligne][colonne] = '0';  // Marque la case comme visitee

        // Appels recursifs pour explorer les terres adjacentes.
        parcoursProfondeur(grille, ligne - 1, colonne); // Vers le haut
        parcoursProfondeur(grille, ligne + 1, colonne); // Vers le bas
        parcoursProfondeur(grille, ligne, colonne - 1); // Vers la gauche
        parcoursProfondeur(grille, ligne, colonne + 1); // Vers la droite
    }

    public static void main(String[] args) {
        NombreDiles solution = new NombreDiles();

        char[][] grille1 = {
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
        };
        System.out.println("Nombres d'iles comptee : " + solution.nombreDiles(grille1));  
        // Resultat = 1

        char[][] grille2 = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
        };
        System.out.println("Nombres d'iles comptee : " + solution.nombreDiles(grille2));  
        // Resultat = 3
    }
}

// RESULTAT
/*
Nombres d'iles comptee : 1
Nombres d'iles comptee : 3
*/
