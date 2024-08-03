/*
Nom: Gueorgui Poklitar
TP3-IFT1142
Description: fonctions et interactions.. avec le fichier XML. 
À noter le professeur nous à rendu access au dernieres notes de cours 26h avant la remise finale... 
Plusieurs de mes fonctionnalités du TP2 qui fonctionnait bien avez été adapter au TP3 mais avec l'ajoue et la resolution 
des autres partis ils n'ont pas été ajuster adequatement. Ceux qui n'ont pas été accomplis au TP2, on été reussi au TP3.

Si seulement nous avions plus de temps pour polire le projet, mon projet serait présentable. 
*/

// Fonction pour créer une carte de film dans le style horizontal à partir des données d'un film XML
const creerCardHorizontal = (unFilm) => {
  // Extraire les genres du film et les concaténer en une chaîne séparée par des virgules
  const genres = Array.from(unFilm.getElementsByTagName("genres")).map(genre => genre.textContent).join(", ");
  // Extraire l'URL de l'affiche du film, en utilisant une image par défaut si aucune URL n'est fournie
  const posterSrc = unFilm.getElementsByTagName("posterUrl")[0].textContent || 'client/images/pp1.png';
  // Extraire l'ID du film
  const filmId = unFilm.getElementsByTagName("id")[0].textContent;
  
  // Construire la structure HTML de la carte horizontale
  return `
    <div id="${filmId}" class="card mb-3">
      <div class="row g-0">
        <div class="col-md-4">
          <img src="${posterSrc}" onerror="this.onerror=null; this.src='client/images/pp1.png';" class="img-fluid rounded-start" alt="${unFilm.getElementsByTagName("title")[0].textContent}">
        </div>
        <div class="col-md-8">
          <div class="card-body">
            <h5 class="card-title">${unFilm.getElementsByTagName("title")[0].textContent}</h5>
            <p class="card-text">${unFilm.getElementsByTagName("plot")[0].textContent}</p>
            <p class="card-text"><small class="text-muted">Genres: ${genres}</small></p>
            <p class="card-text"><small class="text-muted">Director: ${unFilm.getElementsByTagName("director")[0].textContent}</small></p>
            <p class="card-text"><small class="text-muted">Actors: ${unFilm.getElementsByTagName("actors")[0].textContent}</small></p>
            <p class="card-text"><small class="text-muted">Year: ${unFilm.getElementsByTagName("year")[0].textContent} - Runtime: ${unFilm.getElementsByTagName("runtime")[0].textContent} mins</small></p>
            <button type="button" class="btn btn-info btn-edit" data-film-id="${filmId}">Edit</button>
            <button type="button" class="btn btn-danger" onclick="deleteMovie('${filmId}');">Delete</button>
          </div>
        </div>
      </div>
    </div>
  `;
};

// Fonction pour créer une carte de film dans le style vertical à partir des données d'un film XML
const creerCardVertical = (unFilm) => {
  // Extraire les genres du film et les concaténer en une chaîne séparée par des virgules
  const genres = Array.from(unFilm.getElementsByTagName("genres")).map(genre => genre.textContent).join(", ");
  // Extraire l'URL de l'affiche du film, en utilisant une image par défaut si aucune URL n'est fournie
  const posterSrc = unFilm.getElementsByTagName("posterUrl")[0].textContent || 'client/images/pp1.png';
  
  // Construire la structure HTML de la carte verticale
  return `
    <div id="${unFilm.getElementsByTagName("id")[0].textContent}" class="card" style="width: 18rem;">
      <img src="${posterSrc}" onerror="this.onerror=null; this.src='client/images/pp1.png';" class="card-img-top" alt="${unFilm.getElementsByTagName("title")[0].textContent}">
      <div class="card-body">
        <h5 class="card-title">${unFilm.getElementsByTagName("title")[0].textContent}</h5>
        <h6 class="card-subtitle mb-2 text-muted">${unFilm.getElementsByTagName("year")[0].textContent}</h6>
        <p class="card-text">${unFilm.getElementsByTagName("plot")[0].textContent}</p>
        <p class="card-text"><small class="text-muted">Genre: ${genres}</small></p>
        <p class="card-text"><small class="text-muted">Director: ${unFilm.getElementsByTagName("director")[0].textContent}</small></p>
        <p class="card-text"><small class="text-muted">Actors: ${unFilm.getElementsByTagName("actors")[0].textContent}</small></p>
        <p class="card-text"><small class="text-muted">Runtime: ${unFilm.getElementsByTagName("runtime")[0].textContent} min</small></p>
      </div>
    </div>
  `;
};

// Fonction pour générer un ID unique pour un nouveau film à ajouter
function generateUniqueId() {
  // Vérifier si des films sont déjà présents dans la liste
  if (!listeFilms || listeFilms.length === 0) {
    console.log("Aucun film disponible, démarrage de l'ID à partir de 1");
    return 1; // Commencer les IDs à partir de 1 s'il n'y a pas de films
  }
  // Extraire les IDs de tous les films et trouver le plus grand
  const ids = listeFilms.map(film => parseInt(film.id));
  const maxId = Math.max(...ids);
  console.log("ID maximal actuel dans la liste:", maxId);
  return maxId + 1; // Retourner un nouvel ID unique
}

// Fonction pour ajouter ou modifier un film
const ajouterModifierFilm = (action, idFilm) => {
    // Afficher le formulaire de modification ou d'ajout de film
    const modalForm = new bootstrap.Modal(document.getElementById("modalFormulaire"), { keyboard: false });
    modalForm.show();

    // Identifier l'action demandée et mettre à jour le titre du formulaire modal
    if (action === "ajouter") {
        document.getElementById("formulaireFilm").reset(); // Réinitialiser le formulaire d'ajout de film
        document.getElementById("modalFormulaireLabel").innerHTML = "Ajouter un film";
    } else if (action === "modifier" && idFilm) {
        document.getElementById("modalFormulaireLabel").innerHTML = "Modifier un film";
        montrerModifierFilm(idFilm); 
    }
};

// Fonction pour afficher les données du film à modifier dans le formulaire modal
const montrerModifierFilm = (idFilm) => {
  console.log("Tentative de modification du film avec l'ID:", idFilm); // Journaliser l'ID recherché
  // Trouver l'index du film à modifier dans la liste des films
  const index = listeFilms.findIndex((unFilm) => parseInt(unFilm.getElementsByTagName("id")[0].textContent.trim()) === parseInt(idFilm));
  if (index !== -1) {
    const film = listeFilms[index];
    console.log("Film trouvé:", film); // Journaliser l'objet du film trouvé
    // Mettre à jour les champs du formulaire modal avec les données du film
    document.getElementById("id").value = film.getElementsByTagName("id")[0].textContent;
    document.getElementById("title").value = film.getElementsByTagName("title")[0].textContent;
    document.getElementById("year").value = film.getElementsByTagName("year")[0].textContent;
    document.getElementById("runtime").value = film.getElementsByTagName("runtime")[0].textContent;
    document.getElementById("director").value = film.getElementsByTagName("director")[0].textContent;
    document.getElementById("actors").value = film.getElementsByTagName("actors")[0].textContent;
    document.getElementById("plot").value = film.getElementsByTagName("plot")[0].textContent;
    document.getElementById("posterUrl").value = film.getElementsByTagName("posterUrl")[0].textContent;
  } else {
    console.error("Film introuvable pour l'ID:", idFilm); // Journaliser une erreur si non trouvé
    alert("Film introuvable!");
  }
};

// Fonction pour enregistrer les modifications apportées à un film
const modifierFilm = () => {
  const idFilm = document.getElementById("id").value;
  // Trouver l'index du film à modifier dans la liste des films
  const index = listeFilms.findIndex(film => film.getElementsByTagName("id")[0].textContent.trim() === idFilm);
  
  if (index !== -1) {
    let film = listeFilms[index];
    console.log("Avant la mise à jour:", film.outerHTML);  // Journaliser le film XML avant la mise à jour

    // Mettre à jour les données du film avec les valeurs des champs du formulaire
    film.getElementsByTagName("title")[0].textContent = document.getElementById("title").value;
    film.getElementsByTagName("year")[0].textContent = document.getElementById("year").value;
    film.getElementsByTagName("runtime")[0].textContent = document.getElementById("runtime").value;
    film.getElementsByTagName("director")[0].textContent = document.getElementById("director").value;
    film.getElementsByTagName("actors")[0].textContent = document.getElementById("actors").value;
    film.getElementsByTagName("plot")[0].textContent = document.getElementById("plot").value;
    film.getElementsByTagName("posterUrl")[0].textContent = document.getElementById("posterUrl").value;

    console.log("Après la mise à jour:", film.outerHTML);  // Journaliser le film XML après la mise à jour

    afficherListeFilms(listeFilms); // Mettre à jour l'affichage de la liste des films
    $('#modalFormulaire').modal('hide'); // Cacher le formulaire modal
  } else {
    alert('Film introuvable!');
  }
};

// Fonction pour mettre à jour un élément XML avec une nouvelle valeur
function updateXmlElement(film, tag, value) {
  if (film.getElementsByTagName(tag)[0]) {
    film.getElementsByTagName(tag)[0].textContent = value;
  }
}

// Fonction pour mettre à jour les genres d'un film avec une nouvelle liste de genres
function updateGenres(film, genresString) {
  let genresElement = film.getElementsByTagName("genres")[0];
  while (genresElement.firstChild) { // Supprimer les genres existants
    genresElement.removeChild(genresElement.firstChild);
  }
  genresString.split(", ").forEach(genre => {
    let genreElement = film.ownerDocument.createElement("genre");
    genreElement.textContent = genre;
    genresElement.appendChild(genreElement);
  });
}

// Écouter les clics sur la page pour activer les fonctions d'ajout/modification de film
document.addEventListener('click', function(e) {
  if (e.target.classList.contains('btn-edit')) {
    const filmId = e.target.getAttribute('data-film-id');
    ajouterModifierFilm('modifier', filmId);
  }
  if (e.target.classList.contains('btn-delete')) {
    const filmId = e.target.getAttribute('data-film-id');
    deleteMovie(filmId);
  }
});

// Fonction pour afficher la liste des films dans une présentation spécifiée (horizontale ou verticale)
const afficherListeFilms = (films) => {
  // Construire le contenu HTML en fonction du style de présentation choisi
  let content = "";
  let presentationStyle = document.getElementById('selPresentationStyle').value;

  films.forEach(film => {
    content += (presentationStyle === "horizontal") ? creerCardHorizontal(film) : creerCardVertical(film);
  });

  console.log("HTML à définir:", content);  // Journaliser le HTML à définir dans le conteneur de liste
  // Définir le contenu HTML dans le conteneur de liste des films
  document.getElementById('listeFilms').innerHTML = content;
  setupPagination(Math.ceil(films.length / recordsPerPage)); // Mettre en place la pagination
};

// Fonction pour changer le style de présentation des films (horizontal ou vertical)
function changePresentationStyle() {
  const selectedStyle = document.getElementById('selPresentationStyle').value;
  if (selectedStyle === 'horizontal') {
    afficherListeFilmsHorizontal(listeFilms);
  } else if (selectedStyle === 'vertical') {
    afficherListeFilmsVertical(listeFilms);
  }
}

// Fonction pour afficher la liste des films dans le style horizontal
function afficherListeFilmsHorizontal(films) {
  let htmlContent = '';
  films.forEach(film => {
    htmlContent += creerCardHorizontal(film);
  });
  document.getElementById('listeFilms').innerHTML = htmlContent;
}

// Fonction pour afficher la liste des films dans le style vertical
function afficherListeFilmsVertical(films) {
  let htmlContent = '';
  films.forEach(film => {
    htmlContent += creerCardVertical(film);
  });
  document.getElementById('listeFilms').innerHTML = htmlContent;
}

// Fonction pour ajouter un nouveau film
const ajouterFilm = () => {
  if (!listeFilms || listeFilms.length === 0) {
    console.error("Aucune liste de films disponible ou elle est vide.");
    return;
  }
  
  const xmlDoc = listeFilms[0].ownerDocument;

  // Créer un nouvel élément de film avec un ID unique
  let newFilm = xmlDoc.createElement("film");
  newFilm.setAttribute("id", generateUniqueId().toString()); // Convertir l'ID en chaîne si nécessaire
  
  // Ajouter des éléments enfants pour les données du film
  newFilm.appendChild(createXmlElement(xmlDoc, "title", document.getElementById("newTitle").value));
  newFilm.appendChild(createXmlElement(xmlDoc, "year", document.getElementById("newYear").value));
  newFilm.appendChild(createXmlElement(xmlDoc, "runtime", document.getElementById("newRuntime").value));
  newFilm.appendChild(createXmlElement(xmlDoc, "director", document.getElementById("newDirector").value));
  newFilm.appendChild(createXmlElement(xmlDoc, "actors", document.getElementById("newActors").value));
  newFilm.appendChild(createXmlElement(xmlDoc, "plot", document.getElementById("newPlot").value));
  newFilm.appendChild(createXmlElement(xmlDoc, "posterUrl", document.getElementById("newPosterUrl").value));

  // Gérer les genres séparément pour ajouter chaque genre à l'élément 'genres'
  let genresElement = xmlDoc.createElement("genres");
  const genresInput = document.getElementById("newGenres");
  if (genresInput && genresInput.value) {
    genresInput.value.split(", ").forEach(genre => {
      let genreElement = xmlDoc.createElement("genre");
      genreElement.textContent = genre;
      genresElement.appendChild(genreElement);
    });
  }
  newFilm.appendChild(genresElement);

  listeFilms.push(newFilm); // Ajouter le nouveau film à la liste
  afficherListeFilms(listeFilms); // Actualiser l'affichage de la liste
  $('#addMovieModal').modal('hide'); // Fermer le modal d'ajout
  document.getElementById("addMovieForm").reset(); // Réinitialiser le formulaire après l'ajout
};

// Fonction utilitaire pour créer un nouvel élément XML avec un nom de balise et une valeur donnés
function createXmlElement(doc, tagName, text) {
  let element = doc.createElement(tagName);
  element.textContent = text;
  return element;
}

// Fonction pour générer un ID unique pour un nouveau film
function generateUniqueId() {
  if (!listeFilms || listeFilms.length === 0) {
    return 1; // Commencer par l'ID 1 si aucun film n'est présent
  }
  return Math.max(...listeFilms.map(film => parseInt(film.getAttribute("id")))) + 1;
}

// Écouter l'événement 'DOMContentLoaded' pour ajouter un gestionnaire de clic sur le bouton d'enregistrement
document.addEventListener('DOMContentLoaded', function() {
  const saveButton = document.getElementById('enregistrerBtn');
  if (saveButton) {
      saveButton.addEventListener('click', ajouterFilm);
  } else {
      console.error('Le bouton d\'enregistrement n\'a pas été trouvé sur la page.');
  }
});

// Fonction pour supprimer un film de la liste
const deleteMovie = (filmId) => {
  if (confirm("Êtes-vous sûr de vouloir supprimer ce film ?")) {
      // Convertir l'ID du film en chaîne et supprimer les espaces blancs pour assurer une correspondance précise.
      const targetId = filmId.trim();
      // Trouver l'index du film à supprimer
      const index = listeFilms.findIndex(film => film.getElementsByTagName("id")[0].textContent.trim() === targetId);

      if (index === -1) {
        throw new Error(`Aucun film avec l'ID ${filmId} trouvé.`);
      }

      // Cette ligne supprime le film à l'index spécifié de la liste des films
      listeFilms.splice(index, 1);

      // Actualiser l'affichage de la liste et la pagination car la liste a changé
      afficherListeFilms(listeFilms); 
      genererPagination(listeFilms); 

      alert('Film supprimé avec succès !');
  }
};

// Fonction pour générer la pagination pour une liste donnée de films
const genererPagination = (updatedList) => {
  let recordsPerPage = 10;
  let totalPages = Math.ceil(updatedList.length / recordsPerPage);

  // Détruire la pagination existante si elle existe déjà
  if ($pagination.data('twbs-pagination')) {
    $pagination.twbsPagination('destroy');
  }

  // Configurer le plugin de pagination avec le nombre total de pages et la fonction onPageClick
  $pagination.twbsPagination({
    totalPages: totalPages,
    visiblePages: 7,
    onPageClick: function (_, page) {
      let startIndex = (page - 1) * recordsPerPage;
      let endIndex = Math.min(startIndex + recordsPerPage, updatedList.length);
      afficherListeFilms(updatedList.slice(startIndex, endIndex));
    }
  });
};

// Fonction pour afficher les genres dans la liste déroulante des catégories
const afficherGenres = () => {
  let selCategs = document.getElementById("selCategs");
  selCategs.options.length = 0;
  for (let genre of genres) {
    let option = new Option(genre, genre);
    selCategs.options.add(option);
  }
};

// Fonction de comparaison pour trier les films en fonction du critère de tri et de l'ordre choisi
const comparateur = (unFilm, unAutreFilm) => {
  if (trierPar === "id" || trierPar === "year" || trierPar === "runtime") {
    if (ordreTri === "cro") {
      return parseInt(unFilm.getElementsByTagName(trierPar)[0].textContent) - parseInt(unAutreFilm.getElementsByTagName(trierPar)[0].textContent);
    } else {
      return parseInt(unAutreFilm.getElementsByTagName(trierPar)[0].textContent) - parseInt(unFilm.getElementsByTagName(trierPar)[0].textContent);
    }
  } else { // Pour 'title'
    if (ordreTri === "cro") {
      return unFilm.getElementsByTagName(trierPar)[0].textContent.localeCompare(unAutreFilm.getElementsByTagName(trierPar)[0].textContent);
    } else {
      return unAutreFilm.getElementsByTagName(trierPar)[0].textContent.localeCompare(unFilm.getElementsByTagName(trierPar)[0].textContent);
    }
  }
};

// Fonction pour générer la pagination
const setupPagination = (films) => {
  let totalPages = Math.ceil(films.length / recordsPerPage);
  let $paginationContainer = $('#pagination-container');

  // Détruire la pagination existante si elle existe déjà
  if ($paginationContainer.data('twbs-pagination')) {
    $paginationContainer.twbsPagination('destroy');
  }

  // Configurer la pagination uniquement s'il y a des pages à paginer
  if (totalPages > 1) {
    $paginationContainer.twbsPagination({
      totalPages: totalPages,
      visiblePages: 5,
      onPageClick: function (event, page) {
        let startIndex = (page - 1) * recordsPerPage;
        let endIndex = startIndex + recordsPerPage;
        displayFilms(films.slice(startIndex, endIndex)); // Fonction pour afficher les films pour une page spécifique
      }
    });
  }
};

// Fonction pour afficher les films dans une présentation spécifique (horizontale ou verticale)
const displayFilms = (films) => {
  // Construire le HTML pour les films à afficher
  let html = films.map(film => creerCardHorizontal(film)).join('');
  // Définir le HTML généré dans le conteneur de liste des films
  document.getElementById('listeFilms').innerHTML = html;
};

document.addEventListener('DOMContentLoaded', function() {
  afficherListeFilms(listeFilms); 
});


