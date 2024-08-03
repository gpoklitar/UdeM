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


//Utilise aussi la classe List.
import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;
import java.util.Iterator;


    public class SinglyLinkedList<E> implements List<E> {
    	
    protected static class Node<E> {
	// Les attributs
	protected E element;    // r�f�rence � l'�l�ment
	protected Node<E> next; // r�f�rence au noeud suivant dans la liste
	// constructor
	protected Node( E element, Node<E> next ) {
	    this.element = element;
	    this.next = next;
	}
	// getters & setters
	protected E    getElement() { return this.element; }               // accesseur d'�l�ment
	protected void setElement( E element ) { this.element = element; } // accesseur d'�l�ment
	protected Node<E> getNext() { return this.next; }               // accesseur de noeud suivant
	protected void    setNext( Node<E> next ) { this.next = next; } /// accesseur de noeud suivant
    } 
    
    
    
    // Les  attributes
    
    protected Node<E> head = null;  // tete de la liste, ou null si vide
    protected Node<E> tail = null;  // queue de la liste, ou null si vide
    
    protected int size = 0;         // nombre de noeuds dans la liste
    
    
   
    
    
    

    protected class SinglyLinkedListIterator implements Iterator<E> {
	
	private Node<E> current = getHead(); 

	public boolean hasNext() { return current != null; }
	public E next() throws NoSuchElementException {
	    if( current == null ) throw new NoSuchElementException( "No more element" );
	    E element = current.getElement();
	    if( current == tail )
		current = null; 
	    else current = current.getNext();
	    return element;
	}
    } 
    
    

		    
   

   
    protected Node<E> getHead() { return this.head; }
    protected Node<E> getTail() { return this.tail; }
 
	
    // constructor 
    public SinglyLinkedList() { } // make an initially empty list instance
    public Iterator<E> iterator() { // return an iterator over the elements in proper order
	return new SinglyLinkedListIterator();
    }
    // interface List implementation
    public int size() { return this.size; } // return the number of elements in list
   // return true if this list has no element, false otherwise
    public boolean isEmpty() { return this.size == 0; } 
    public E first() { if( this.isEmpty() ) return null; return this.head.getElement(); } // return without removing the first element in the list, null if empty
    public E last() { if( this.isEmpty() ) return null; return this.tail.getElement(); } // return without removing the last element in the list, null if empty
   
    public void addFirst( E element ) { 
	this.head = new Node<>( element, this.head ); // cr�e et lie un nouveau n�ud pour l'�l�ment
	if( this.isEmpty() ) this.tail = this.head;   // si vide, le nouveau n�ud devient t�te et queue
	this.size++;
    }
    
    
    
    public String toString() {
	if( this.isEmpty() ) return "[]";
	String SLL = "[";
	Node<E> current = this.getHead();
	while( current != this.getTail() ) {
	    SLL += current.getElement() + ",";
	    current = current.getNext();
	}
	SLL += current.getElement() + "]";
	return SLL;
    }
    
    
    
    
    
    public void addLast( E element ) { 
	Node<E> newNode = new Node<>( element, null ); // cr�e un nouveau n�ud pour la queue �ventuelle
	if( this.isEmpty() ) this.head = newNode;      // si vide, le nouveau n�ud est la queue et la t�te
	else this.tail.setNext( newNode );             // sinon, ajouter apr�s la queue actuelle
	this.tail = newNode;                           // le nouveau Node devient la queue
	this.size++;
    }
    
    
    public void add( E element ) { this.addLast( element ); } // compatible with Java List
    
    
    protected void checkIndex( int i, int n ) throws IndexOutOfBoundsException {
    	if( i < 0 || i >= n ) throw new IndexOutOfBoundsException( "index " + i + " out of bounds" );
        }
    
    public void add( int index, E element ) throws IndexOutOfBoundsException { 
	checkIndex( index, this.size + 1 );
	if( index == 0 )
		this.addFirst(element); 
	else if( index == this.size ) 
		this.addLast( element ); 
	else {
	    Node<E> current = this.head;
	    Node<E> previous = null;    // besoin du pr�c�dent pour lier le nouveau Node
	    for( int i = 0; i < index; i++ ) {
		previous = current;
		current = current.getNext();
	    }
	    Node<E> newNode = new Node<>( element, current ); // le courant suit le nouveau n�ud
	    previous.setNext(newNode);
	    this.size++;
	}
    }	
    
    
    
    public E removeFirst() { 
	if( this.isEmpty() ) return null;   // si vide, rien � supprimer
	E element = this.head.getElement();
	this.head = this.head.getNext();    // la nouvelle t�te est la suivante de  la t�te, ou null si la liste a un seul n�ud
	this.size--;
	if( this.isEmpty() ) this.tail = null; // si la liste devient vide, la queue devient nulle
	return element;
    }
    
    
    
    	
    public void removeLast() {  
    	// V�rifie si la liste est vide 
        if(head == null) {  
            System.out.println("List is empty");  
            return;  
        }  
        else {  
        	// V�rifie si la liste contient un seul �l�ment  
            if(head != tail ) {  
                Node<E> current = head;  
             // Boucle dans la liste jusqu'� l'avant-dernier �l�ment de sorte que current.next pointe vers la queue  
                while(current.next != tail) {  
                    current = current.next;  
                }  
             // L'avant-dernier �l�ment deviendra la nouvelle queue de la liste
                tail = current;  
                tail.next = null;  
            }  
            //Si la liste ne contient qu'un seul �l�ment
            // Ensuite, il le supprimera et la t�te et la queue pointeront vers null 
            else {  
                head = tail = null;  
            }  
        }  
    }  
  
    
    
    public E get( int index ) throws IndexOutOfBoundsException { // // return element at index in O(n)
	checkIndex( index, this.size );
	Node<E> current = getHead();
	for( int k = 0; k < index; k++ )
	    current = current.getNext();
	return current.getElement();
    }
    public E set( int index, E element ) throws IndexOutOfBoundsException { // replaces element at index i, error if out of bounds
	checkIndex( index, this.size );
	Node<E> current = getHead();
	for( int k = 0; k < index; k++ )
	    current = current.getNext();
	E oldElement = current.getElement();
	current.setElement( element );
	return oldElement;
    }	
    
    public E remove( E element ) { 
	Node<E> current = getHead();
	Node<E> previous = null;
	while( current != null && current.getElement() != element ) {
	    previous = current;
	    current = current.getNext();
	}
	if( current != null ) {
	    if( previous == null ) { 
		this.removeFirst();
		return current.getElement();
	    }
	    previous.setNext( current.getNext() );
	    this.size--;
	    return current.getElement();
	}
	return null;
    }
    
    
    public E remove( int index ) throws IndexOutOfBoundsException { // remove element at index, error if out of bounds
	checkIndex( index, this.size );
	if( index == 0 ) this.removeFirst(); // special case
	Node<E> current = this.getHead();
	Node<E> previous = null; // need the previous to link Nodes around Node at index
	for( int k = 0; k < index; k++ ) {
	    previous = current;
	    current = current.getNext();
	}
	E oldElement = current.getElement();
	previous.setNext( current.getNext() );
	if( current == this.tail ) this.tail = previous;
	this.size--;
	return oldElement;
    }	
    
 // return index of first occurrence of element in O(n), -1 if not present
    
    public int indexOf( E element ) { 
	Node<E> current = this.head;
	int index = 0;
	while( current != null && current.getElement() != element ) {
	    current = current.getNext();
	    index++;
	}
	if( current != null ) return index;
	return -1;
    }
    
    
    public boolean equals( List<E> other ) {
	if( other == null ) return false;
	if( this.getClass() != other.getClass() ) return false;
	SinglyLinkedList that = (SinglyLinkedList) other; // use non-parameterized type
	if( this.size() != that.size() ) return false;
	Node<E> currentThis = this.head;
	Node<E> currentThat = that.head;
	// traverse both lists concurrently
	while( currentThis != null ) {
	    if( !currentThis.getElement().equals( currentThat.getElement() ) ) return false; // mismatch
	    currentThis = currentThis.getNext();
	    currentThat = currentThat.getNext();
	}
	return true; // if we get here, then this and that are equal
    }
    
    public boolean equals( SinglyLinkedList<E> other ) {
    	if( other == null ) return false;
    	if( this.getClass() != other.getClass() ) return false;
    	if( this.size() != other.size() ) return false;
    	Node<E> currentThis = this.head;
    	Node<E> currentThat = other.head;
    	// traverse both lists concurrently
    	while( currentThis != null ) {
    	    if( !currentThis.getElement().equals( currentThat.getElement() ) ) return false; // mismatch
    	    currentThis = currentThis.getNext();
    	    currentThat = currentThat.getNext();
    	}
    	return true; // if we get here, then this and that are equal
        }
    
 // Methode recursive pour inverser la liste
    public void recursiveReverse() {
        if (head != null && head != tail) {
            head = recursiveReverse(head); //appel de recursiveReverse
        }
    }

    // Methode recursive pour inverser la liste 2e partie, 
    //prenant un node courant en entree
    private Node<E> recursiveReverse(Node<E> current) {
        if (current.next == null) {
            tail = head;  // Mets a jour la queue pour qu'elle soit la tete
            return current;
        }

        // Recursion pour inverser le reste de la liste
        Node<E> newHeadNode = recursiveReverse(current.next);

        // Inversion des liens pour le node courant
        current.next.next = current;
        current.next = null;

        return newHeadNode; // Retourne le nouveau node de la tete
    }

    // Méthode iterative pour inverser la liste
    public void iterativereverse() {
        if (head == null || head == tail) {
            // La liste est vide ou contient un seul element, rien a inverser
            return;
        }

        Node<E> previous = head;
        Node<E> current = head.getNext();

        while (current != null) {
            Node<E> next = current.getNext();
            current.setNext(previous);
            previous = current;
            current = next;
        }

        // echange des pointeurs de la tete et de la queue apres l'inversion
        tail = head;
        tail.setNext(null);
        head = previous;
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> myList = new SinglyLinkedList<>();
        myList.addLast(10);
        myList.addLast(20);
        myList.addLast(30);
        myList.addLast(40);

        System.out.println("Liste d'origine : " + myList);
        myList.iterativereverse();
        System.out.println("Liste inversee (iterative) : " + myList);

        myList.recursiveReverse();
        System.out.println("Liste inversee (recursive) : " + myList);
    }

	    
}

//COMMENTAIRE DU RESULTAT
    
//Onobserve que la liste d'origine est effectivement 
//inversee suite a la methode iterative.
//Par la suite, on remarque que la liste 
//est retablis dans son ordre originale suite 
//a une autre inversion, cette fois recursivement.

// RESULTAT 
    
 /*
Liste d'origine : [10,20,30,40]
Liste inversée (itérative) : [40,30,20,10]
Liste inversée (récursive) : [10,20,30,40]
 */