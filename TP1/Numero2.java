/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Entrer et sorti du programme à l'aide de navigation JOptionPane, 
	montre en binaire un entier et sa longeur binaire, comme le numero 1.
	TP1 - Numero 2
	Dernière Mise-à-jour : 23/05/2023
*/

import javax.swing.JOptionPane; // Importation du JOptionPane.

public class Numero2{ //Creation class.
	
	public static void main(String[]args) { //Main.
		int reponse; //Réponse pour continuer le programme. 0 = "yes", 1 = "no", 2 = "cancel"
		JOptionPane.showMessageDialog(null, "Bienvenue.\nVoici le numero 2 du TP1.");
		
		
		do { // Loop Do-While, (Faire tant que reponse = yes).
			int valeur = Integer.parseInt(JOptionPane.showInputDialog("Saisissez une valeur entière : "));
			
			String binaire = Integer.toBinaryString(valeur); //Methode pour convertire le Integer en binaire sous forme de String.
			int compteBinaire = binaire.length(); //Methode pour compter la longeur d'un string qui sera entreprosé sous forme de int.
			JOptionPane.showMessageDialog(null, "Valeur saisie : " + valeur + "\n" + "En binaire : " + binaire  + "\n" + "Nombre de caracteres : " + compteBinaire);
			
			
			do {//Option revenir au dernier resultat generer. Si l'usager choisi cancel.
				reponse = JOptionPane.showConfirmDialog(null, "Desirez-vous recommencer avec un autre entier?"); //Confirmation yes, no, cancel.
				if(reponse == 2) {
					JOptionPane.showMessageDialog(null, "Valeur saisie : " + valeur + "\n" + "En binaire : " + binaire  + "\n" + "Nombre de caracteres : " + compteBinaire);
				}
			} while(reponse == 2);
			
			
		} while(reponse == 0);// Fin du loop Do-While. Tant que reponse est oui, si non fin du programme.
		JOptionPane.showMessageDialog(null, "\nFin du programme..." + "\nAu revoir!");
	}
}

