/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Classe pour la requete de modification de donnée vers le serveur, 
	pour sauvgrader les données sur le serveur à partir des modifications dans l'interface.
	Donnée par le professeur.
	Travail Pratique 2
	Dernière Mise-à-jour : 23/06/2024
*/

import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class Requetes {
    public void requetePost(JSONObject jsonInput) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String stringToSend = jsonInput.toString();

            // Connection vers le localhost.s
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8383/sauvegarde"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(stringToSend))
                .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
