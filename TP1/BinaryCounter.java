/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Completer le TO DO dans le code fournis du TP1 num6. 
	Le code prends un nombre decimal et le renvoie en code binaire. 
	Implemente egalement des classe pour les files.
	TP1 - Numero 6
	Dernière Mise-a-jour : 11/10/2023
*/

//Utilise la classe QueueArray et Queue basee sur ArrayQueue2 offert dans le cours.
import java.util.Scanner;

public class BinaryCounter {
    private Queue<String> q; // File pour stocker les valeurs binaires

    public void countTo(int n) {
        q = new QueueArray<String>(); // Initialisation de la file
        q.enqueue("1"); // Ajout de la première valeur binaire (1)
        for (int i = 0; i < n; i++) {
            String front = q.dequeue(); // Retire le premier element de la file
            System.out.println(front); // Affiche la valeur binaire retirees
            q.enqueue(front + "0"); //TO DO :Ajoute la version actuelle avec '0' a la fin
            q.enqueue(front + "1"); //TO DO :Ajoute la version actuelle avec '1' a la fin
        }
    }

    public static void main(String[] args) {
        System.out.println("Count in binary to what decimal value?");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // Lire la valeur decimale entree par l'utilisateur
        scanner.close();
        BinaryCounter binaryCounter = new BinaryCounter();
        binaryCounter.countTo(n); // Compte en binaire jusqua la valeur donnee
    }
}


