/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
		But : Classe UtilitairesCSV contenant des methodes pour peupler un dictionnaire a 
		partir d'un fichier CSV et generer des resultats de prefixe. La methode 
		peuplerDictionnaireDepuisCSV lis un fichier CSV et ajoute les mots au 
		dictionnaire. genererResultatsPrefixe compte le nombre de mots similaires 
		bases sur un prefixe et les ecrit dans un fichier de sortie. creerNouveauFichier cree 
		un nouveau fichier si necessaire.ecrireResultatsDansFichier ecrit les resultats dans
		un fichier. Cette classe est utilisee pour peupler un dictionnaire et generer des 
		resultats de prefixe en utilisant des structures de donnees appropriees.
	Travail Pratique 3
	Derniere Mise-a-jour : 12/12/2023
*/


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UtilitairesCSV {

    // Remplit le dictionnaire a partir du fichier CSV
    public static void peuplerDictionnaireDepuisCSV(Dictionnaire dictionnaire, String nomFichier) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                // Divise la ligne en valeurs en utilisant une virgule comme delimiteur
                String[] valeurs = ligne.split(",");
                if (valeurs.length >= 4) {
                    // Recupere les valeurs du CSV
                    String motAnglais = valeurs[0].trim();
                    String motFrancais = valeurs[1].trim();
                    String type = valeurs[2].trim();
                    String signification = valeurs[3].trim();
                    String significationComplete = type + ": " + signification;

                    // Ajoute le mot au dictionnaire en utilisant une structure de donnees appropriee
                    // Utilisation de HashMap pour une recherche rapide par mot en anglais
                    // Utilisation de ArrayList pour stocker les significations multiples d'un mot
                    dictionnaire.ajouterMot(motAnglais, significationComplete, motFrancais);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Genere des resultats de prefixe
    public static void genererResultatsPrefixe(Dictionnaire dictionnaire, String fichierEntree, 
    		String fichierSortie) {
        List<String> lignes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fichierEntree))) {
            String mot;
            while ((mot = br.readLine()) != null) {
            // Compte le nombre de mots similaires dans le dictionnaire en 
            	//utilisant une structure de donnees appropriee
                // Utilisation de prefixe d'arbre (Trie) pour une recherche de prefixe efficace
                int compteMotsSimilaires = dictionnaire.getSimilarWordsCount(mot.trim());
                lignes.add(compteMotsSimilaires + "");
            }

            // Ecrit les resultats dans un fichier de sortie
            ecrireResultatsDansFichier(lignes, fichierSortie);
        } catch (FileNotFoundException e) {
            // Cree un nouveau fichier d'entree s'il n'existe pas
            creerNouveauFichier(fichierEntree);
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors du traitement du fichier.");
        }
    }

    // Cree un nouveau fichier si necessaire
    public static void creerNouveauFichier(String nomFichier) {
        try {
            File fichier = new File(nomFichier);
            if (!fichier.exists() && fichier.createNewFile()) {
                System.out.println("Salut !");
                System.out.println("Le document texte '" + nomFichier + "' a ete ajoute a votre "
                		+ "dossier !");
                System.out.println("=============================================================="
                		+ "=================");
                System.out.println("Vous pouvez maintenant utiliser '" + nomFichier + "' "
                		+ "pour tester le nombre de correspondances que vos prefixes selectionnes ont.");
                System.out.println("Ouvrez '" + nomFichier + "' et inserez les prefixes que vous "
                		+ "souhaitez tester.");
                System.out.println("Ensuite, choisissez l'option '4' ci-dessous pour obtenir les "
                		+ "resultats du test.");
            } else {
                System.out.println("Bienvenue !");
                System.out.println("Veuillez selectionner une option ci-dessous :");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ecrit les resultats dans un fichier
    private static void ecrireResultatsDansFichier(List<String> lignes, String nomFichierSortie) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomFichierSortie))) {
            for (String ligne : lignes) {
                bw.write(ligne);
                bw.newLine();
            }
        }
    }
}

