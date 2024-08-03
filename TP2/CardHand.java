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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class CardHand {

    private LinkedPositionalList<Card> hand; 
    //Liste positionnelle liee pour stocker les cartes
    // Carte pour suivre la derniere position de chaque couleurs.
    private Map<Suit, Position<Card>> lastPositionOfSuit;

    public CardHand() {
        hand = new LinkedPositionalList<>(); 
        // Initialisation de la main avec liste positionnelle liee.
        lastPositionOfSuit = new HashMap<>(); 
        // Initialisation de la carte pour les positions des couleurs.
        for (Suit s : Suit.values()) {
            lastPositionOfSuit.put(s, null); 
            // On commence avec aucune carte pour chaque couleur
        }
    }

    public void addCard(Rank r, Suit s) {
        Card newCard = new Card(r, s); // Creation d'une nouvelle carte
        Position<Card> lastPos = lastPositionOfSuit.get(s); 
        // On cherche la derniere position pour cette couleur

        if (lastPos == null) {
            // S'il ny a pas de carte precedente pour cette couleur, on ajoute a la fin.
            Position<Card> newPos = hand.addLast(newCard);
            lastPositionOfSuit.put(s, newPos); 
            // Mise a jour de la position de la derniere carte pour cette couleur
        } else {
            // S'il y a une carte precedente, on ajoute apres cette carte
            Position<Card> newPos = hand.addAfter(lastPos, newCard);
            lastPositionOfSuit.put(s, newPos); 
            // Mise a jour de la position de la derniere carte pour cette couleur.
        }
    }

    public Card play(Suit s) {
        Position<Card> cardPos = lastPositionOfSuit.get(s); 
        // On recupere la derniere carte de la couleur demandee.
        if (cardPos == null) {
        // S'il n'y a pas de carte de cette couleur et que la main est vide, on lance une exception
            if (hand.isEmpty()) {
                throw new IllegalStateException("Aucune carte a jouer");
            }
            cardPos = hand.last(); // Sinon, on prend la derniere carte de la main.
        }

        // On recupere la position avant celle qu'on va enlever, si elle existe.
        Position<Card> newPos = hand.before(cardPos);

        // On enleve la carte de la main
        Card cardToPlay = hand.remove(cardPos);

        // On verifie si on doit mettre a jour la carte des dernieres positions
        if (newPos == null || newPos.getElement().getSuit() != s) {
            // Si la nouvelle position est nulle ou n'est pas de la meme couleur, on efface.
            lastPositionOfSuit.put(s, null);
        } else {
            // Sinon, on met a jour la position
            lastPositionOfSuit.put(s, newPos);
        }

        return cardToPlay; // On retourne la carte jouee
    }

    public Iterator<String> iterator() {
        // Creation dun iterateur qui va permettre de parcourir les cartes de la main.
        return new Iterator<String>() {
            private Iterator<Card> cardIterator = hand.iterator(); 
            //Iterateur pour la liste des cartes
            private Suit currentSuit = null; 
            //Pour suivre la couleur actuel pendant literation
            private int finger = 0; 
            //Pour suivre le nombre de doigts ou groupes de cartes par couleur
            private boolean isNewSuit = true; 

            @Override
            public boolean hasNext() {
                return cardIterator.hasNext(); // Verifie sil y a une prochaine carte
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException(); 
                    // Si pas de prochaine carte, on lance une exception
                }
                
                StringBuilder outputBuilder = new StringBuilder(); 
                // StringBuilder pour plus d'efficacite
                Card card = cardIterator.next(); // On recupere la prochaine carte

                // On verifie si on a une nouvelle couleur
                if (currentSuit != card.getSuit()) {
                    currentSuit = card.getSuit(); // Mise a jour de la couleur actuel
                    finger++; // Increment du nombre de doigts
                    isNewSuit = true; // Marquage qu'on a un nouvelle couleur.
                }

                //Si cest une nouvelle couleur, on ajoute le numero du doigt en prefixe.
                if (isNewSuit) {
                    outputBuilder.append("Doigt ").append(finger).append(":\n  ");
                    isNewSuit = false;
                } else {
                    outputBuilder.append("  "); // Sinon, on ajoute seulement deux espaces
                }

                outputBuilder.append(card.toString()); 
                //Ajout de la representation textuelle de la carte

                return outputBuilder.toString(); // Retour de la chaine construite
            }
        };
    }

    // Creation dun iterateur specifique pour la couleur donnee
    public Iterator<Card> suitIterator(final Suit s) {
        return new Iterator<Card>() {
            // On trouve la premiere position de la carte du de la couleur "s"
            private Position<Card> current = findFirstPositionOfSuit(s);

            // Methode privee pour trouver la premiere position d'une carte de la couleur specifique
            private Position<Card> findFirstPositionOfSuit(Suit s) {
                Position<Card> pos = hand.first();
                while (pos != null && pos.getElement().getSuit() != s) {
                    pos = hand.after(pos); // On avance jusqu'a trouver la premiere carte de la couleur
                }
                return pos; // Retour de la premiere position trouvee
            }

            @Override
            public boolean hasNext() {
                // Verification de la presence dune prochaine carte de la meme couleur
                return current != null && current.getElement().getSuit() == s;
            }

            @Override
            public Card next() {
            // Si la position actuelle est nulle ou nest pas de la couleur attendu, on lance une exception
                if (current == null || current.getElement().getSuit() != s) {
                    throw new NoSuchElementException("Pas de prochaine carte de cette couleur.");
                }
                Card card = current.getElement(); // On recupere la carte actuelle.
                // On avance jusqu'a la prochaine carte de la meme couleur
                do {
                    current = hand.after(current);
                } while (current != null && current.getElement().getSuit() != s);

                return card; //On retourne la carte actuelle
            }
        };
    }

    // Classe interne pour representer une carte
    public static class Card {
        private final Rank rank; // Rang de la carte
        private final Suit suit; // couleur de la carte

        public Card(Rank rank, Suit suit) {
            this.rank = rank; //Initialisation du rang de la carte
            this.suit = suit; //Initialisation du couleur de la carte.
        }

        public Rank getRank() {
            return rank; // Getter pour le rang
        }

        public Suit getSuit() {
            return suit; // Getter pour le couleur
        }

        @Override
        public String toString() {
            return rank + " de " + suit; // Representation textuelle de la carte
        }
    }

    // Enumeration pour les couleur des cartes
    public enum Suit {
        COEUR, TREFLE, PIQUE, CARREAU
    }

    // Enumeration pour les rangs des cartes
    public enum Rank {
        AS, DEUX, TROIS, QUATRE, CINQ, SIX, SEPT, HUIT, NEUF, DIX, VALET, DAME, ROI
    }
}
