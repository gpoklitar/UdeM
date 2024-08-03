/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Convertir un nombre d'une base a une autre (limite aux bases 2 et 10) 
	en utilisant une pile. La conversion est realisee en suivant les 
	etapes du processus de division et empilage des restes.Le resultat est 
	affiche en tant que chaine de caracteres.
	TP1 - Numero 3
	Dernière Mise-a-jour : 11/10/2023
*/

import java.util.Scanner;
import java.util.Stack;

public class BaseChanger {
    // Methode pour convertir un nombre d'une base a une autre en utilisant une pile
    public String convert(int n, int base) {
        // Verifie si la base est 2 ou 10
        if (base != 2 && base != 10) {
            return "Conversion de base non supportee. "
            		+ "Veuillez choisir 2 ou 10 comme base.";
        }

        // Verifie si le nombre est negatif
        if (n < 0) {
            return "La conversion de nombres negatifs n'est pas prise en charge.";
        }

        // Cree une pile pour stocker les restes de division
        Stack<Integer> stack = new Stack<>();

        // Divisions successives pour obtenir les restes
        while (n > 0) {
            int reste = n % base;  // Calcul du reste
            stack.push(reste);      // Empile le reste
            n = n / base;          // Mise a jour de n
        }

        // Construit la representation de la conversion a partir des restes empiles
        StringBuilder resultat = new StringBuilder();
        while (!stack.isEmpty()) {
            int chiffre = stack.pop();  
            resultat.append(chiffre);  // Construction du resultat
        }

        return resultat.toString();
    }

    public static void main(String[] args) {
    	System.out.println("Entrez un nombre à convertir et "
    			+ "la base vers laquelle le convertir, séparés par un espace");
    	Scanner scanner = new Scanner(System.in);
    	String[] inputs = scanner.nextLine().split(" ");
    	scanner.close();
    	int n = Integer.parseInt(inputs[0]);
    	int base = Integer.parseInt(inputs[1]);
    	BaseChanger baseChanger = new BaseChanger();
    	System.out.println(baseChanger.convert(n,base));
    	}

}

// RESULTAT
/*
Entrez un nombre à convertir et la base vers laquelle le convertir, séparés par un espace
1073 2
10000110001
*/
