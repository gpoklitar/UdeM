/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Creation des methodes addCard(r,s), play(s), 
	iterator() et suitIterator(s) dans la class CardHand. 
	Dans une main avec 4 doigts, chaque doigts entrepose 
	des cartes triees par couleurs. Les methodes sont 
	implementees en O(1) autant que possible avec l'aide 
	de l'interface de PositionalList repris du cours.
	Travail Pratique 2
	Dernière Mise-à-jour : 04/11/2023
*/

import java.lang.IllegalStateException;

/**
* Position is an interface for the Position ADT
* 
* Based on Goodrich, Tamassia & Goldwasser
*
* @author      Francois Major
* @version     %I%, %G%
* @since       1.0
*/

public interface Position<E> {
    // return the element stored at this Position
    E getElement() throws IllegalStateException;
    // return the container of this Position
    Object getContainer();
}
