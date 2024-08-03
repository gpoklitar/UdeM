/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Utilise une pile (Stack) pour verifier si chaque 
	balise d'ouverture a une balise de  fermeture correspondante 
	dans un string en format HTML.
	TP1 - Numero 4
	Dernière Mise-à-jour : 11/10/2023
*/


public interface Stack<E> {
    public int     size();         // renvoie le nombre d'�l�ments dans la pile
    public boolean isEmpty();      // renvoie vrai si la pile est vide, faux sinon
    public void    push( E e );    // ins�re l'�l�ment e en haut de la pile
    public E       top();          // retourne l'�l�ment en haut de la pile, null si vide
    public E       pop();          // supprime et retourne l'�l�ment en haut de la pile, null si vide
}

