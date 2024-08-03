/*
Nom: Gueorgui Poklitar
TP3-IFT1142
Description: Implementation du fichier XML avec le reste de mon projet.
*/

let listeFilms;
let genres;

const obtenirListeFilms = () => {
  $.ajax({
    url: "serveur/DonnéesFilms.xml",
    type: "GET",
    dataType: "xml",
  })
  .done((reponseXML) => {
    listeFilms = Array.from(reponseXML.getElementsByTagName("movies"));
    genres = [...new Set(Array.from(reponseXML.getElementsByTagName("genres")).map(genre => genre.textContent))];
    genres.sort();

    afficherGenres(genres);
    afficherListeFilms(listeFilms);
    genererPagination();
  })
  .fail((erreur) => {
    console.error("Error loading XML: ", erreur);
  });
};

$(document).ready(function() {
  obtenirListeFilms();
});
