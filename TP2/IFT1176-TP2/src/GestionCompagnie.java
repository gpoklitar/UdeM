/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Classe avec des methodes de gestion pour les compagnie, interaction avec le fichier .JSON,
	ayant des getters de base, methodes d'ajout et suppression de vols pour les methodes d'interaction avec les fichier .JSON.
	Triage, et methodes de validations pour supporter et facilité le reste des etapes.
	Travail Pratique 2
	Dernière Mise-à-jour : 23/06/2024
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.*;

public class GestionCompagnie {
    private Compagnie compagnie;

    //Constructeur pour methodes de gestion de vols secondaire
    public GestionCompagnie(Compagnie compagnie) {
        this.compagnie = compagnie;
    }



    //Getters
    public Vol getVol(int numero) {
        return compagnie.getVols().get(numero);
    }
    public Compagnie getCompagnie() {
        return compagnie;
    }
    
    
    //Ajouter ou supprimer les vols dans le .json.
    public void ajouterVol(int numero, String destination, Date date, Avion avion, int reservations) {
        Vol vol = new Vol(numero, destination, date, avion, reservations);
        compagnie.ajouterVol(vol);
    }
    public boolean supprimerVol(int numero) {
        Vol vol = compagnie.getVols().get(numero);
        if (vol != null) {
            compagnie.supprimerVol(vol);
            return true;
        }
        return false;
    }

    
    
    //Methode de triage
    public List<Vol> trierVols() {
        List<Vol> sortedVols = new ArrayList<>(compagnie.getVols().values());
        sortedVols.sort(Comparator.comparingInt(Vol::getNumero));
        return sortedVols;
    }


    //Validation, vol specifique existe et si il y a des vols d'enregistrée.
    public boolean volExiste(int numero) {
        return compagnie.getVols().containsKey(numero);
    }
    public boolean isFlightsLoaded() {
        return !compagnie.getVols().isEmpty();
    }
    
    //Accees vers le fichier .JSON
    public String toJSON() {
        JSONObject json = new JSONObject();
        json.put("compagnie", compagnie.getNom());
        JSONArray volsArray = new JSONArray();
        
        for (Vol vol : compagnie.getVols().values()) {
        	
            JSONObject volJson = new JSONObject();
            volJson.put("numero", vol.getNumero());
            volJson.put("destination", vol.getDestination());
            
            volJson.put("date", new JSONObject()
                    .put("jour", vol.getDate().getJour())
                    .put("mois", vol.getDate().getMois())
                    .put("annee", vol.getDate().getAnnee()));
            volJson.put("reservations", vol.getReservations());
            
            volJson.put("avion", new JSONObject()
                    .put("modele", vol.getAvion().getModele())
                    .put("type", vol.getAvion().getType()));
            volsArray.put(volJson);
        }
        json.put("vols", volsArray);
        return json.toString();
    }
    
    
    //Charger et sauvgarder les vols à partir et vers le fichier .JSON.
    public void sauvegarderVolsJSON(String filePath) throws IOException {
        Files.write(Paths.get(filePath), toJSON().getBytes());
    }
    public void chargerVolsJSON(String jsonContent) throws IOException {
        JSONObject json = new JSONObject(jsonContent);
        compagnie = new Compagnie(json.getString("compagnie"));
        
        JSONArray volsArray = json.getJSONArray("vols");
        for (int i = 0; i < volsArray.length(); i++) {
            JSONObject volJson = volsArray.getJSONObject(i);
            int numero = volJson.getInt("numero");
            String destination = volJson.getString("destination");
            JSONObject dateJson = volJson.getJSONObject("date");
            
            Date date = new Date(dateJson.getInt("jour"), dateJson.getInt("mois"), dateJson.getInt("annee"));
            JSONObject avionJson = volJson.getJSONObject("avion");
            
            Avion avion = new Avion(avionJson.getString("modele"), avionJson.getString("type"));
            int reservations = volJson.getInt("reservations");
            ajouterVol(numero, destination, date, avion, reservations);
        }
    }
}
