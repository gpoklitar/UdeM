/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
		But : Classe NoeudTrie representant les noeuds d'un Trie utilise 
		pour stocker des mots. Utilisation d'une structure de donnees 
		Map<Character, NoeudTrie> pour representer les enfants de chaque noeud.
		L'utilisation de cette structure permet une construction efficace du 
		trie en liant chaque lettre au noeud suivant. La variable estFinDeMot 
		indique si le noeud represente la fin d'un mot, ce qui est utile pour 
		trouver des mots complets dans le Trie. La methode addChild simplifie 
		l'ajout d'enfants a un noeud.La methode deleteChild, bien qu'elle ne 
		soit pas utilisee dans ce travail, est presente pour une utilisation 
	future eventuelle.
	Travail Pratique 3
	Derniere Mise-a-jour : 12/12/2023

*/


import java.util.HashMap;
import java.util.Map;

class NoeudTrie {
	
	private static final int INITIAL_CAPACITY = 26;
	//Limite du hashmap pour preserver la memoire.
	private static final float LOAD_FACTOR = 0.75f;
	//Load-facteur par default

	Map<Character, NoeudTrie> enfants; 
	// Chaque noeud contient une map d'enfants. C'est un choix genial 
	//pour construire un trie car il permet de lier chaque lettre au 
	//noeud suivant. Ca rend le parcours des mots tres efficace
	boolean estFinDeMot; // Indique si le noeud represente la fin d'un mot. 
	//Utile pour savoir quand on a trouve un mot complet dans le trie.


    NoeudTrie() {
    	enfants = new HashMap<>(INITIAL_CAPACITY, LOAD_FACTOR);
        estFinDeMot = false;
    }
    
    // Methode addChild pour faciliter et simplifier l'utilisation. 
    public void addChild(char ch) {
        if (enfants == null) {
            enfants = new HashMap<>();
        }
        enfants.putIfAbsent(ch, new NoeudTrie());
    }
    
    // Le deleteChild n'est pas requis pour ce travail, 
    //question d'implementation je le garde dans la class, pour utilitee future.
    public boolean deleteChild(char ch) {
        if (enfants != null && enfants.remove(ch) != null) {
            if (enfants.isEmpty()) {
                enfants = null; 
            }
            return true;
        }
        return false;
    }

}

