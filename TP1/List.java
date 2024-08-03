/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Inverser une liste avec recursion et inversion 
	de liste avec iteration voir plus bas repris du 
	document SinglyLinkedList offert dans le cours. 
	Avec test dans le main au bas de la page.
	TP1 - Numero 2
	Dernière Mise-a-jour : 11/10/2023
*/

import java.lang.Iterable;
import java.util.Iterator;
import java.lang.IndexOutOfBoundsException;


public interface List<E> extends Iterable<E> {
    public int         size();              // renvoie le nombre d'�l�ments dans la liste
    public boolean     isEmpty();           // retourne vrai si cette liste n'a pas d'�l�ment, faux sinon
    public E           first();             // retourne sans supprimer le premier �l�ment de la liste, null si vide
    public E           last();              // retourne sans supprimer le dernier �l�ment de la liste, erreur si hors limites
    public void        add( int i, E e )
	throws IndexOutOfBoundsException;       // ajoute un �l�ment � l'index i
    public void        addFirst( E e );     // ajoute un �l�ment au d�but de la liste
    public void        addLast( E e );      // ajoute un �l�ment � la fin de la liste
    public void        add( E e );          // ajoute un �l�ment � la fin de la liste
    public E           removeFirst();       // supprime et renvoie le premier �l�ment de la liste
    public E           get( int i )
	throws IndexOutOfBoundsException;       // renvoie l'�l�ment � l'indice i, erreur si hors limites
    public E           set( int i, E e )
	throws IndexOutOfBoundsException;       // remplace l'�l�ment � l'index i, erreur si hors limite
    public E           remove( E e );       // supprime et renvoie l'�l�ment, nul s'il n'est pas trouv�
    public E           remove( int i )
	throws IndexOutOfBoundsException;       // supprime l'�l�ment � l'index i, erreur si hors limites
    public int         indexOf( E e );      // renvoie l'index de l'�l�ment, -1 s'il n'est pas trouv�
    public boolean     equals( List<E> o ); // renvoie true si autre liste est �gale � cette liste
    public Iterator<E> iterator();          // renvoie un it�rateur sur les �l�ments dans le bon ordre
}
