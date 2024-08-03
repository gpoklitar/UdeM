/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Completer le TO DO dans le code fournis du TP1 num6. 
	Le code prends un nombre decimal et le renvoie en code binaire. 
	Implemente egalement des classe pour les files.
	TP1 - Numero 6
	Dernière Mise-a-jour : 11/10/2023
*/

// MODIFICATION A LA LIGNE 11 pour etendre la capacitée a N = 9
// Donc permet jusqu'a une capacite de 7 avant d'obtenire le message de 'Full queue'.

import java.util.Arrays;
import java.lang.IllegalStateException;

/**
* ArrayQueue est une impl�mentation de la file  utilisant un tableau (circulaire)
* Toutes les op�rations s'ex�cutent en O(1).
**/
public class QueueArray<E> implements Queue<E> {
    // Les attributs
    public static final int N = 9;           // capacit� par d�faut
    protected E[] data;                         // tableau pour stocker les �l�ments
    protected int f = 0;                        // index pour le d�but de la file 
    protected int r = 0;                        // index de l��l�ment suivant celui de l�arri�re
  
    // constructeurs
    public QueueArray() { this( N ); }   // construit la file avec la capacit� par d�faut
    public QueueArray( int N ) {         // construit la file avec une capacit� donn�e
	this.data = (E[]) new Object[N]; 
    }
 
    // renvoie le nombre d'�l�ments dans la file 
    
    public int     size() {
    	return( (N-f+r)%N ); 
    } 
    
    // renvoie vrai si la file est vide, faux sinon
    public boolean isEmpty() { 
    	return( f == r );
    }   
    
    // ins�re l'�l�ment e � la fin de la file
    
    public void    enqueue( E e ) throws IllegalStateException { 
	if( this.size()== N-1 ) throw new IllegalStateException( "Full queue" );
	this.data[r] = e;
	r=(r+1)%N;
    }
    
    
    // renvoie le premier �l�ment de la file d'attente, null si vide
    
    public E       first() { 
	if( this.isEmpty() ) return null;
	return this.data[this.f];
    }
    
    
    // supprime et retourne le premier �l�ment de la file , null si vide
    
    public E       dequeue() { 
	if( isEmpty() ) return null;
	E element = this.data[this.f];
	this.data[this.f] = null; 
	this.f = ( this.f + 1 ) % this.data.length;
	
	return element;
    }
    
    public String toString() { return Arrays.toString( this.data ); }
}
