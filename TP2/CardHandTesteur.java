import java.util.Iterator;

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

// Il s'agit d'une classe avec main pour tester la classe CardHand et ses methodes.
// Voir le resultats plus bas pour voir que les methodes fonctionnent comme il faut.

public class CardHandTesteur {

    public static void main(String[] args) {
    	CardHand main = new CardHand();

        // Test de l'ajout de cartes
        main.addCard(CardHand.Rank.AS, CardHand.Suit.PIQUE);
        main.addCard(CardHand.Rank.ROI, CardHand.Suit.COEUR);
        main.addCard(CardHand.Rank.DAME, CardHand.Suit.PIQUE);
        main.addCard(CardHand.Rank.DIX, CardHand.Suit.CARREAU);
        main.addCard(CardHand.Rank.DEUX, CardHand.Suit.PIQUE);
        main.addCard(CardHand.Rank.TROIS, CardHand.Suit.TREFLE);
        main.addCard(CardHand.Rank.QUATRE, CardHand.Suit.TREFLE);
        main.addCard(CardHand.Rank.DIX, CardHand.Suit.PIQUE);

        // Affichage de toutes les cartes apres l'ajout
        System.out.println("Toutes les cartes en main :");
        Iterator<String> it = main.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // Test de jouer une carte
        CardHand.Card carteJouee1 = main.play(CardHand.Suit.COEUR); 
        System.out.println("\nCarte jouee : " + carteJouee1);
        CardHand.Card carteJouee2 = main.play(CardHand.Suit.COEUR);
        System.out.println("\nCarte jouee : " + carteJouee2);

        // Affichage de toutes les cartes apres en avoir joue quelques-unes
        System.out.println("\nToutes les cartes en main apres avoir joue :");
        it = main.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // Test de l'iterateur de couleur pour CARREAU
        System.out.println("\nIteration sur les cartes CARREAU :");
        Iterator<CardHand.Card> iterateurCarreau = main.suitIterator(CardHand.Suit.CARREAU);
        while (iterateurCarreau.hasNext()) {
        	CardHand.Card carte = iterateurCarreau.next();
            System.out.println(carte);
        }
    }
}

//RESULTAT
/*
Toutes les cartes en main :
Doigt 1:
  AS de PIQUE
  DAME de PIQUE
  DEUX de PIQUE
  DIX de PIQUE
Doigt 2:
  ROI de COEUR
Doigt 3:
  DIX de CARREAU
Doigt 4:
  TROIS de TREFLE
  QUATRE de TREFLE

Carte jouee : ROI de COEUR

Carte jouee : QUATRE de TREFLE

Toutes les cartes en main apres avoir joue :
Doigt 1:
  AS de PIQUE
  DAME de PIQUE
  DEUX de PIQUE
  DIX de PIQUE
Doigt 2:
  DIX de CARREAU
Doigt 3:
  TROIS de TREFLE

Iteration sur les cartes CARREAU :
DIX de CARREAU
*/
