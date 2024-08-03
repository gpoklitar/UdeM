/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Completer le TO DO dans le code fournis du TP1 num6. 
	Le code prends un nombre decimal et le renvoie en code binaire. 
	Implemente egalement des classe pour les files.
	TP1 - Numero 6
	Dernière Mise-a-jour : 11/10/2023
*/


public interface Queue<E> {
	public void    enqueue( E e ); // ins�re l'�l�ment e � la fin de la file
	public E       dequeue();      // supprime et retourne le premier �l�ment de la file d'attente, null si vide
	public E       first();        // renvoie le premier �l�ment de la file d'attente, null si vide
    public int     size();         // renvoie le nombre d'�l�ments dans la file d'attente
    public boolean isEmpty();      // renvoie vrai si la file est vide, faux sinon
}


