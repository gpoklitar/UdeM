/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Classe Personne, qui est parent de classe Passager et Equipage. 
	Importation des fichier .txt, pour les trier et sortir une liste pour 
	remplir un vol. Suite au triage le programme  renvoi le resultat et 
	retire le groupe selectionnee pour le vol du fichier .txt des passagers.
	Travail : T.P.#3
	Dernière Mise-à-jour : 07/07/2023
*/

//importations utilisee dans notre code...
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
//Debut classe Tp3Test
public class Tp3Test {
	
//Methode statique de lecture du fichier txt pour passagers avec try-catch.
    private static LinkedList<Passager> lirePassagersFichier(String fichier) {
        LinkedList<Passager> passagers = new LinkedList<>();

        try {
            File file = new File(fichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                int numeroReservation = Integer.parseInt(parts[0]);
                String nom = parts[1];
                String prenom = parts[2];
                char sexe = parts[3].charAt(0);
                double taille = Double.parseDouble(parts[4]);
                double poids = Double.parseDouble(parts[5]);

                Passager passager = new Passager(nom, prenom, sexe, 
                taille, poids, numeroReservation);
                
                passagers.add(passager);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return passagers;
    }
    
//Methode statique de lecture du fichier txt pour equipage avec try-catch.
    private static LinkedList<Equipage> lireEquipageFichier(String fichier) {
        LinkedList<Equipage> equipage = new LinkedList<>();

        try {
            File file = new File(fichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                char poste = parts[0].charAt(0);
                String nom = parts[1];
                String prenom = parts[2];
                char sexe = parts[3].charAt(0);
                double taille = Double.parseDouble(parts[4]);
                double poids = Double.parseDouble(parts[5]);

                Equipage membre = new Equipage(nom, prenom, sexe, poste, 
                taille, poids);
                equipage.add(membre);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return equipage;
    }

//Mothode trouver premier pilote + retrait de la liste equipage.
    private static Equipage getFirstPilot(LinkedList<Equipage> equipage) {
        for (Equipage membre : equipage) {
            if (membre.getPoste() == 'P') {
                equipage.remove(membre);
                return membre;
            }
        }
        return null;
    }
//Mothode trouver premier copilote + retrait de la liste equipage.
    private static Equipage getFirstCopilote(LinkedList<Equipage> equipage) {
        for (Equipage membre : equipage) {
            if (membre.getPoste() == 'C') {
                equipage.remove(membre);
                return membre;
            }
        }
        return null;
    }
//Mothode trouver premier agent de bord + retrait de la liste equipage.   
    private static Equipage getFirstAgentBord(LinkedList<Equipage> equipage) {
        for (Equipage membre : equipage) {
            if (membre.getPoste() == 'A') {
                equipage.remove(membre);
                return membre;
            }
        }
        return null;
    }
//Methode pour supprimer le groupe de passagers du vol dans la liste passagers. 
    private static void removePassengersGroupe(LinkedList<Passager> passagers, 
    		LinkedList<Personne> vol) {
        Iterator<Passager> passagerIterator = passagers.iterator();
//Avec iterator.
        while (passagerIterator.hasNext()) {
            Passager passager = passagerIterator.next();
//Contains, pour trouver les passager du groupe.
            if (vol.contains(passager)) {
                passagerIterator.remove();
            }
        }
    }

//Methode pour retirer le contenu du fichier texte, avec try-catch.
    private static void clearFichier(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//Methode copier/ecrire les passagers restant dans ce fichier txt, + try-catch.
    private static void writePassengersFichier(String fileName, 
    		LinkedList<Passager> passagers) {
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            
            for (Passager passager : passagers) {
                fileWriter.write(passager.getNumeroReservation() +" "+
				passager.getNom() +" "+ passager.getPrenom() +" "+
				passager.getSexe() + " " +
				passager.getTaille() + " " + passager.getPoids() +
				System.lineSeparator());
            }
            fileWriter.close(); //Fermeture du writer.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
//                         METHODE MAIN	!
    
    public static void main(String[] args) {
//Lecture de fichier "passagers.txt" et "equipage.txt" et entreposage en LinkedList.
        LinkedList<Passager> passagers = lirePassagersFichier("passagers.txt");
        LinkedList<Equipage> equipage = lireEquipageFichier("equipage.txt");
//Utilisation de Collections.sort...Tri des passagers par le PassagerComparator.
        Collections.sort(passagers, new PassagerComparator());
//Creation de la liste vol.
        LinkedList<Personne> vol = new LinkedList<>();
        
//Variable pour le poids total est mise a 0.        
        double totalPoids = 0;

//Recherche et ajout du premier pilote
        Equipage premierPilote = getFirstPilot(equipage);
        if (premierPilote != null) {
            vol.add(premierPilote);
            totalPoids += premierPilote.getPoids();
            //Avec ajout de poids au compte.
        }
//Recherche et ajout du premier copilote
        Equipage premierCopilote = getFirstCopilote(equipage);
        if (premierCopilote != null) {
            vol.add(premierCopilote);
            totalPoids += premierCopilote.getPoids();
            //Avec ajout de poids au compte.
        }
//Recherche et ajout du premier agent de bord.
        Equipage premierAgent = getFirstAgentBord(equipage);
        if (premierAgent != null) {
            vol.add(premierAgent);
            totalPoids += premierAgent.getPoids();
            //Avec ajout de poids au compte.
        }

        int passengerCount = 0; //Compteur de passagers
//Iterateur pour parcourir les passagers
        ListIterator<Passager> passagerIterator = passagers.listIterator();
//Liste separee pour les passagers
        LinkedList<Passager> passagersToAddBack = new LinkedList<>();

        while(passengerCount<50 && totalPoids<4000 && passagerIterator.hasNext()) {
            Passager passager = passagerIterator.next();
//Ajout d'un agent de bord tous les 15 passagers (sauf le premier groupe)...
//Nous avions deja ajouter un premier agent de bord.
            if (passengerCount % 15 == 0 && passengerCount != 0) {
                Equipage agentDeBord = getFirstAgentBord(equipage);
                if (agentDeBord != null) {
                    double newTotalPoids = totalPoids + agentDeBord.getPoids();
                    //Ajout de poids au compte... pour le reste du vol.
                    
//Au cas ou le dernier agent fait deborder la limite de poids on
//ne l'ajoute pas au vol et on l'insert dans la liste equipage au tout debut.
                    if (newTotalPoids <= 4000) {
                    	totalPoids = newTotalPoids;
                        vol.add(agentDeBord);
                    } else {
                        equipage.addFirst(agentDeBord);
                        break;
                    }
                }
            }
//Au cas ou le dernier passager fait deborder la limite de poids on
//ne l'ajoute pas au vol et on l'insert dans la liste passager au tout debut.
            double newTotalWeight = totalPoids + passager.getPoids();
            if (newTotalWeight <= 4000 && passengerCount < 50) {
            	totalPoids = newTotalWeight;
                vol.add(passager);
                passengerCount++;
            } else {
                passagersToAddBack.addFirst(passager);
                break;
            }
        }

        //Ajout des passagers dans la liste originale.
        for (Passager passagerToAddBack : passagersToAddBack) {
            passagerIterator.add(passagerToAddBack);
        }

        //Recupere la derniere personne dans la liste vol.
        Personne lastPerson = vol.getLast(); 

        if (lastPerson instanceof Equipage) {
            //Si la derniere personne est un membre de l'equipage
            equipage.addFirst((Equipage) lastPerson); 
            //Ajout la dernière personne au debut de la liste equipage
            vol.removeLast(); //Supprime cette personne d'equipage de la liste vol
            
        } else if (lastPerson instanceof Passager) {
            // Si la dernière personne est un passager
            Passager lastPassager = (Passager) lastPerson;
            //Converti la derniere personne en objet type Passager
            
            while (passagerIterator.hasNext()) {
                //(Tant qu'il y a un prochain passager dans la liste passagers)
                Passager nextPassager = passagerIterator.next(); 
                //Prends le prochain passager de la liste
                
                if (lastPassager.getNumeroReservation() == 
                nextPassager.getNumeroReservation()) {
//Si num reserv. du dernier passager est identique au num reserv. du prochain passager
                    passagers.addFirst(lastPassager); 
                    //Ajoute le dernier passager au debut de la liste passagers
                    vol.removeLast();
                    lastPassager = nextPassager; 
                    //Met a jour le dernier passager avec le prochain passager
                } else {
                    break;
                }
            }
        }
//Enhenced for-loop pour toutes les personnes de la liste vol...
//Impression du resultat...
        for (Personne personne : vol) {
            System.out.println(personne);
        }
        System.out.println
("=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
//Touche cosmetique de la liste vol...
        System.out.println("Nombre de passagers: " + passengerCount);
        System.out.println("Poids total: " + totalPoids + " kg");
        
        removePassengersGroupe(passagers, vol); 
        //enlever les passager du vol dans la liste passagers.
        clearFichier("passagers.txt");
        //Enleve toutes les lignes du fichier.
        writePassengersFichier("passagers.txt", passagers);
        //Ajoute les passagers restant au fichier txt.
    }
}


//RESULTAT
/*
                Pilote | ---- |         MARA |        ROY | F | 1.63 m |  54.88 kg
              Copilote | ---- |       CLAUDE |      MARIO | M | 1.57 m |  56.25 kg
        Agente de bord | ---- |      MARTINE |     BRIAND | F | 1.90 m | 100.00 kg
 Numero de reservation | 1000 |       DANIEL |     BERUBE | M | 1.68 m |  65.00 kg
 Numero de reservation | 1000 |       CLAUDE |   MOLAISON | M | 1.57 m |  56.25 kg
 Numero de reservation | 1000 |      CHANTAL |        ROY | F | 1.63 m |  54.88 kg
 Numero de reservation | 1001 |       CLAUDE |    MOIMEME | M | 1.57 m |  56.25 kg
 Numero de reservation | 1001 |      RONALDE |        ROY | F | 1.63 m |  54.88 kg
 Numero de reservation | 1008 |       CLAUDE |    MALAISE | M | 1.57 m |  56.25 kg
 Numero de reservation | 1008 |      CHANTAL |     REGENT | F | 1.63 m |  54.88 kg
 Numero de reservation | 1010 |       DANIEL |      BARON | M | 1.68 m |  65.00 kg
 Numero de reservation | 1080 |       DANIEL |     BANANE | M | 1.68 m |  65.00 kg
 Numero de reservation | 1100 |   MARC-ANDRE |     BEDARD | M | 1.43 m |  80.50 kg
 Numero de reservation | 1100 |     ADELAIDE |       NOEL | F | 1.50 m |  45.00 kg
 Numero de reservation | 1101 |   MARC-ANDRE |  BERRURIER | M | 1.43 m |  80.50 kg
 Numero de reservation | 1101 |     ADELAIDE |    JACQUES | F | 1.50 m |  45.00 kg
 Numero de reservation | 1106 |       AGATHE | LAROCHELLE | M | 1.88 m |  90.50 kg
 Numero de reservation | 1108 |   MARC-ANDRE |     BOYARD | M | 1.43 m |  80.50 kg
         Agent de bord | ---- |         MARC |   GIROUARD | M | 1.88 m |  90.50 kg
 Numero de reservation | 1113 |       AGATHE |   LAPIERRE | M | 1.88 m |  90.50 kg
 Numero de reservation | 1118 |       AGATHE |   GARNOTTE | M | 1.88 m |  90.50 kg
 Numero de reservation | 1180 |     ADELAIDE |    NORBOUR | F | 1.50 m |  45.00 kg
 Numero de reservation | 1200 |  MARIE-ANDRE |     BEDARD | F | 1.43 m |  70.50 kg
 Numero de reservation | 1200 |       CLAUDE |     MOLSON | M | 1.57 m |  86.25 kg
 Numero de reservation | 1200 |        RENEE |        ROY | F | 1.53 m |  50.88 kg
 Numero de reservation | 1234 |        DENIS |  DESMARAIS | M | 1.55 m |  40.50 kg
 Numero de reservation | 1234 |      MARTINE |      HOULE | F | 1.90 m | 100.00 kg
 Numero de reservation | 1236 |      MARTINE |      BAIRD | F | 1.90 m | 100.00 kg
 Numero de reservation | 1236 |       DENISE |  DESMEULES | M | 1.55 m |  40.50 kg
 Numero de reservation | 1238 |       DENISE |      SWAMP | M | 1.55 m |  40.50 kg
 Numero de reservation | 1238 |      MARTINE |      VAGUE | F | 1.90 m | 100.00 kg
 Numero de reservation | 1300 |       DARIUS |     BERUBE | M | 1.68 m |  65.00 kg
 Numero de reservation | 1300 |         JEAN |       NOEL | F | 1.50 m |  45.00 kg
 Numero de reservation | 1413 |       PIERRE |   LAPIERRE | M | 1.80 m |  90.50 kg
         Agent de bord | ---- |         CARL |  DESMARAIS | M | 1.55 m |  40.50 kg
 Numero de reservation | 1434 |       DENISE |  DESMARAIS | M | 1.55 m |  40.50 kg
 Numero de reservation | 1434 |       MARTIN |      HOULE | H | 1.70 m | 100.00 kg
 Numero de reservation | 1500 |        SERGE |     BEDARD | M | 1.43 m |  80.50 kg
 Numero de reservation | 1500 |        DONAT |     BERUBE | M | 1.68 m |  65.00 kg
 Numero de reservation | 1500 |        ADELE |   CHRISTIE | F | 1.50 m |  45.00 kg
 Numero de reservation | 1500 |       CLAUDE |   POLAISON | M | 1.57 m |  56.25 kg
 Numero de reservation | 1500 |      CHANTAL |        ROE | F | 1.63 m |  54.88 kg
 Numero de reservation | 2113 |       AGATHE |      LAPIN | M | 1.88 m |  90.50 kg
 Numero de reservation | 2234 |       DENISE |  DESCHAMPS | M | 1.55 m |  40.50 kg
 Numero de reservation | 2234 |    GABRIELLE |      HOULE | F | 1.90 m | 100.00 kg
 Numero de reservation | 2345 |    FRANCOISE |       DUBE | F | 1.59 m |  50.00 kg
 Numero de reservation | 2345 | MARIE_SOLEIL |       FORD | F | 1.72 m |  62.50 kg
 Numero de reservation | 2346 |    FRANCOISE |   DUBEURRE | F | 1.59 m |  50.00 kg
 Numero de reservation | 2346 | MARIE_SOLEIL |       FORT | F | 1.72 m |  62.50 kg
 Numero de reservation | 2348 |      FRISBEE |       DUBE | F | 1.59 m |  50.00 kg
        Agente de bord | ---- |     GERMAINE |       DUBE | F | 1.59 m |  50.00 kg
 Numero de reservation | 2348 | MARIE_SOLEIL |  FARLOUCHE | F | 1.72 m |  62.50 kg
 Numero de reservation | 2400 |         ERIC |    FILLION | M | 1.67 m |  65.00 kg
 Numero de reservation | 2400 |         MARC |    FILLION | M | 1.85 m | 100.00 kg
 Numero de reservation | 2406 |         ERIC |     FILLON | M | 1.67 m |  65.00 kg
 Numero de reservation | 2406 |         MARC |     FILLON | M | 1.85 m | 100.00 kg
=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
Nombre de passagers: 50
Poids total: 3738.78 kg
 */

//Suite a la premiere compilation, le fichier passager.txt ressemble a cela...
//Le groupe du premier vol n'est plus dans le fichier.
/*
2408 DUFILLION ERIC M 1.67 65.0
2408 DUFILLION MARC M 1.85 100.0
2410 PILON ERIC M 1.67 65.0
2410 PILON MARC M 1.85 100.0
2445 DUBE FRANCINE F 1.49 40.0
2445 FORD MARCEL M 1.72 62.5
2500 MARION ERIC M 1.67 65.0
2500 MARION MARC M 1.85 100.0
2545 DUBE JEAN F 1.59 50.0
2545 FORD EDSEL M 1.72 62.5
 */
//NOTE...
//Le programme peut etre executee a nouveau avec les passagers restant dan le fichier txt.
//RESULTAT 2
/*
                Pilote | ---- |         MARA |        ROY | F | 1.63 m |  54.88 kg
              Copilote | ---- |       CLAUDE |      MARIO | M | 1.57 m |  56.25 kg
        Agente de bord | ---- |      MARTINE |     BRIAND | F | 1.90 m | 100.00 kg
 Numero de reservation | 2408 |         ERIC |  DUFILLION | M | 1.67 m |  65.00 kg
 Numero de reservation | 2408 |         MARC |  DUFILLION | M | 1.85 m | 100.00 kg
 Numero de reservation | 2410 |         ERIC |      PILON | M | 1.67 m |  65.00 kg
 Numero de reservation | 2410 |         MARC |      PILON | M | 1.85 m | 100.00 kg
 Numero de reservation | 2445 |     FRANCINE |       DUBE | F | 1.49 m |  40.00 kg
 Numero de reservation | 2445 |       MARCEL |       FORD | M | 1.72 m |  62.50 kg
 Numero de reservation | 2500 |         ERIC |     MARION | M | 1.67 m |  65.00 kg
 Numero de reservation | 2500 |         MARC |     MARION | M | 1.85 m | 100.00 kg
 Numero de reservation | 2545 |         JEAN |       DUBE | F | 1.59 m |  50.00 kg
 Numero de reservation | 2545 |        EDSEL |       FORD | M | 1.72 m |  62.50 kg
=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
Nombre de passagers: 10
Poids total: 921.13 kg

 */
