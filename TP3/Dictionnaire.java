/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
		But : Classe Dictionnaire permettant de stocker et gerer un dictionnaire de mots, 
		significations et traductions. Utilisation d'un Trie pour un accès rapide aux mots base sur les prefixes.
		Utilisation de structures de donnees telles que Map (carte) pour associer les mots a leurs 
		significations et traductions. Gestion de l'historique des recherches a l'aide d'une liste.
	Travail Pratique 3
	Derniere Mise-a-jour : 12/12/2023
*/

import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

public class Dictionnaire {
    private NoeudTrie racine; // Racine du Trie
    private Map<String, String> carteMots; 
    // Carte pour stocker les mots et leurs significations. La structure Map est ideale ici
    //car elle permet d'associer chaque mot (cle) a sa signification (valeur). 
    //C'est tres rapide pour rechercher, ajouter ou supprimer des mots.
    private Map<String, String> carteTraductions; 
    // Carte pour stocker les mots et leurs traductions. Pareil que pour carteMots, 
    //c'est tres pour des recherches rapides et l'association mot-traduction.
    private List<String> historiqueRecherches; // Liste pour garder l'historique des recherches. 
    //Une liste est parfaite ici pour garder une trace des mots recherches dans l'ordre. 
    // On peut facilement y ajouter des elements a la fin.


    public Dictionnaire() {
        racine = new NoeudTrie();
        carteMots = new HashMap<>();
        carteTraductions = new HashMap<>();
        historiqueRecherches = new ArrayList<>();
    }

    // Compte le nombre de mots similaires base sur un prefixe
    public int getSimilarWordsCount(String mot) {
        String plusLongPrefixe = findLongestPrefix(mot);
        List<String> motsSimilaires = getWordsWithPrefix(plusLongPrefixe);
        return motsSimilaires.size();
    }

    // Ajoute un mot, sa signification et sa traduction dans le dictionnaire
    public void ajouterMot(String mot, String signification, String traduction) {
        mot = mot.toLowerCase();
        NoeudTrie actuel = racine;
        for (char ch : mot.toCharArray()) {
            actuel.addChild(ch); // Use addChild method
            actuel = actuel.enfants.get(ch);
        }
        actuel.estFinDeMot = true;
        carteMots.put(mot, signification);
        carteTraductions.put(mot, traduction);
    }


    // Cherche un mot et renvoie sa signification
    public String chercherMot(String mot) {
        mot = mot.toLowerCase();
        historiqueRecherches.add(mot);
        if (carteMots.containsKey(mot)) {
            return carteMots.get(mot);
        } else {
            String plusLongPrefixe = findLongestPrefix(mot);
            List<String> motsSimilaires = getWordsWithPrefix(plusLongPrefixe);
            int nombreMotsSimilaires = motsSimilaires.size();
            String resultat = "\nMot non trouve. \n" + nombreMotsSimilaires + 
            		" resultats similaires trouves : " + motsSimilaires +"\n";
            return resultat;
        }
    }

    // Trouve le plus long prefixe pour un mot donne
    private String findLongestPrefix(String mot) {
        NoeudTrie actuel = racine;
        StringBuilder constructeurPrefixe = new StringBuilder(); 
        // Utiliser un StringBuilder ici est une bonne option car il permet de construire des chaines 
        //de caracteres de maniere efficace, surtout quand on modifie la chaine souvent dans une boucle.

        for (char ch : mot.toCharArray()) {
            if (actuel.enfants.containsKey(ch)) {
                constructeurPrefixe.append(ch);
                actuel = actuel.enfants.get(ch);
            } else {
                break;
            }
        }
        return constructeurPrefixe.toString();
    }

    // Traduit un mot en français
    public String traduireMot(String mot) {
    	historiqueRecherches.add(mot);
        return carteTraductions.getOrDefault(mot.toLowerCase(), "Traduction non disponible");
    }

    // Renvoie l'historique des recherches
    public List<String> getHistoriqueRecherches() {
        return historiqueRecherches;
    }

    // Obtient les mots commencant par un prefixe donner
    private List<String> getWordsWithPrefix(String prefixe) {
    	List<String> mots = new ArrayList<>(); 
    	// Une ArrayList est utilisee pour stocker les mots trouves. 
    	//Elle est flexible et permet d'ajouter des elements facilement tout en conservant l'ordre.
        NoeudTrie actuel = racine;
        for (char ch : prefixe.toCharArray()) {
            actuel = actuel.enfants.getOrDefault(ch, null);
            if (actuel == null) {
                return mots;
            }
        }
        findAllWordsFromNode(actuel, mots, new StringBuilder(prefixe));
        return mots.stream().filter(mot -> mot.startsWith(prefixe)).collect(Collectors.toList());
    }

    // Trouve tous les mots a partir d'un noeud donne
    private void findAllWordsFromNode(NoeudTrie noeud, List<String> liste, StringBuilder prefixeActuel) {
        if (noeud.estFinDeMot) {
            liste.add(prefixeActuel.toString());
        }
        for (Map.Entry<Character, NoeudTrie> entree : noeud.enfants.entrySet()) {
            prefixeActuel.append(entree.getKey());
            findAllWordsFromNode(entree.getValue(), liste, prefixeActuel);
            prefixeActuel.deleteCharAt(prefixeActuel.length() - 1);
        }
    }
}
