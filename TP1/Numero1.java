/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Entrer et sorti du programme à l'aide de scanner, montre en binaire un entier et sa longeur binaire.
	TP1 - Numero 1
	Dernière Mise-à-jour : 23/05/2023
*/

import java.util.Scanner; // Importation du scanner.

public class Numero1{ //Creation class.
	
	public static void main(String[]args) { //Main.
		Scanner scanner = new Scanner(System.in); //Definition du scanner.
		String reponse; //Réponse pour continuer le programme.
		
		System.out.println("Bienvenue.\nVoici le numero 1 du TP1."); // Message de bienvenue.
		
		
		do { // Loop Do-While, (Faire tant que reponse == oui).
			System.out.print("\nValeur saisie : ");
			int valeur = scanner.nextInt(); //Scanner retiens le prochain entier seulement. 
											//buffer : 137\n (entier 137 suivit de la touche enter)...
			scanner.nextLine(); //Consome le restant du buffer "\n" à la suite de notre entier
								//pour éviter la lecture de celui-ci lors du prochain appel au scanner.

			String binaire = Integer.toBinaryString(valeur); //Methode pour convertire le Integer en binaire sous forme de String.
			System.out.println("En binaire : " + binaire);
			int compteBinaire = binaire.length(); //Methode pour compter la longeur d'un string qui sera entreprosé sous forme de int.
			System.out.println("Nombre de caracteres : " + compteBinaire);
			
			System.out.print("\nDesirez-vous recommencer avec un autre entier? (Oui / Non) : ");
			reponse = scanner.nextLine();//Demande à l'usagé s'il desire continuer.
			
			
		} while(reponse.equalsIgnoreCase("oui"));// Fin du loop Do-While, (Faire tant que reponse == oui).
		scanner.close();
		System.out.println("\nFin du programme..."); //Message de fin du programme.
		System.out.println("Au revoir!");
	}
}

//RESULTAT
/* Bienvenue.
Voici le numero 1 du TP1.

Valeur saisie : 137
En binaire : 10001001
Nombre de caracteres : 8

Desirez-vous recommencer avec un autre entier? (Oui / Non) : oui

Valeur saisie : 39
En binaire : 100111
Nombre de caracteres : 6

Desirez-vous recommencer avec un autre entier? (Oui / Non) : non

Fin du programme...
Au revoir! */
