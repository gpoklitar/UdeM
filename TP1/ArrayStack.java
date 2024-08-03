/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Utilise une pile (Stack) pour verifier si chaque 
	balise d'ouverture a une balise de  fermeture correspondante 
	dans un string en format HTML.
	TP1 - Numero 4
	Dernière Mise-à-jour : 11/10/2023
*/

import java.lang.IllegalStateException;



public class ArrayStack<E> implements Stack<E> {
    public static final int CAPACITY = 1000;    // capacit� par d�faut
    private E[] data;                           // tableau pour stocker les �l�ments
    private int t = -1;                         // index du haut de la pile
    public ArrayStack() { this( CAPACITY ); }   // construit la pile avec la capacit� par d�faut
	public ArrayStack( int capacity ) {         // construit une pile avec une capacit� donn�e
	this.data = (E[]) new Object[capacity];     
    }
    
 // renvoie le nombre d'�l�ments dans la pile
    public int     size() { return( this.t + 1 ); }   
    
    // renvoie vrai si la pile est vide, faux sinon
    public boolean isEmpty() { return( this.t == -1 ); }     
    
 // ins�re l'�l�ment e en haut de la pile
    public void    push( E e ) throws IllegalStateException { 
	if( this.size() == this.data.length ) throw new IllegalStateException( "Full stack" );
	this.data[++this.t] = e;
    }
    
 // retourne l'�l�ment en haut de la pile, null si vide
    public E       top() { 
	if( this.isEmpty() ) return null;
	return this.data[this.t];
    }
    
 // supprime et retourne l'�l�ment en haut de la pile, null si vide
    public E       pop() { 
	if( isEmpty() ) return null;
	E element = this.data[this.t];
	this.data[this.t] = null;    
	this.t--;
	return element;
    }
}
