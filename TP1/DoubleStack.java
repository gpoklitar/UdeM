/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Implementation de deux piles en utilisant une seule liste, 
	avec des operations de pile qui s'executent en en temps constant, 
	peu importe la taille des piles.
	TP1 - Numero 5
	Derniere Mise-a-jour : 11/10/2023
*/

import java.util.ArrayList;

public class DoubleStack {
    private ArrayList<Integer> list;
    private int top1; // Pointeur pour la pile 1
    private int top2; // Pointeur pour la pile 2
    private int capacity; // Capacite totale de la liste

    public DoubleStack(int capacity) {
        this.capacity = capacity;
        list = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            list.add(0); // Ajouter des elements de valeur par defaut (0)
        }
        top1 = -1;
        top2 = capacity;
    }

    public void pushToStack1(int item) {
        if (top1 < top2 - 1) {
            top1++;
            list.set(top1, item); 
            // Ajoute l'element 'item' a la pile 1 en avancant le pointeur 'top1'
        } else {
            System.out.println("Pile 1 pleine, impossible d'ajouter " + item); 
            // Affiche un message si la pile 1 est pleine et ne peut pas ajouter 'item'
        }
    }

    public void pushToStack2(int item) {
        if (top1 < top2 - 1) {
            top2--;
            list.set(top2, item); 
            //Ajoute l'element 'item' a la pile 2 en reculant le pointeur 'top2'
        } else {
            System.out.println("Pile 2 pleine, impossible d'ajouter " + item); 
            // Affiche un message si la pile 2 est pleine et ne peut pas ajouter 'item'
        }

    }

    public int popFromStack1() {
        if (top1 >= 0) {
            int item = list.get(top1); // Recupere l'element en haut de la pile 1
            top1--; // Decremente le pointeur de la pile 1 pour 
            //indiquer que l'element a ete retire
            return item;
        } else {
            System.out.println("Pile 1 vide"); //Affiche un message si la pile 1 
            //est vide et ne peut pas depiler
            return -1; // Valeur par defaut pour indiquer une pile vide
        }
    }

    public int popFromStack2() {
        if (top2 < capacity) {
            int item = list.get(top2); // Recupere l'element en haut de la pile 2
            top2++; // Incremente le pointeur de la pile 2 pour 
            //indiquer que l'element a ete retire
            return item;
        } else {
            System.out.println("Pile 2 vide"); // Affiche un message si la pile 2 
            //est vide et ne peut pas depiler
            return -1; // Valeur par defaut pour indiquer une pile vide
        }
    }


    public static void main(String[] args) {
        DoubleStack doubleStack = new DoubleStack(10);

        doubleStack.pushToStack1(1);
        doubleStack.pushToStack1(2);
        doubleStack.pushToStack1(3);
        doubleStack.pushToStack2(10);
        doubleStack.pushToStack2(20);
        doubleStack.pushToStack2(30);

        System.out.println("Pile 1 - elements retirees : " 
        + doubleStack.popFromStack1()+ " " 
        + doubleStack.popFromStack1()+ " " 
        + doubleStack.popFromStack1());
        
        System.out.println("Pile 2 - elements retirees : " 
        + doubleStack.popFromStack2()+ " " 
        + doubleStack.popFromStack2()+ " " 
        + doubleStack.popFromStack2());
    }
}

// RESULTAT
/*
Pile 1 - elements retirees : 3 2 1
Pile 2 - elements retirees : 30 20 10
*/
