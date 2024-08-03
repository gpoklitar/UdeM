/*
Nom: Gueorgui Poklitar
TP2-IFT1142
Description: Utilisation des requetes modal pour chercher divers 
donnees ou modifications de la base de donnees des filmes.
*/


// Variables globales pour conserver les données des films et les genres
let listeFilms = [];
let genres = [];
let currentDisplayMovies = [];

// Fonction pour récupérer les données des films
const obtenirListeFilms = () => {
    $.ajax({
        url: "serveur/DonnéesFilms.json",
        type: "GET",
        dataType: "json",
    })
    .done((reponse) => {
        listeFilms = reponse.movies;
        genres = reponse.genres;
        genres.sort();
        afficherListeFilms(listeFilms);
        afficherGenres();
        currentDisplayMovies = listeFilms;
        setupPagination(currentDisplayMovies.length);
        changePage(1);
        populateNewMovieId(); // Remplit l'ID du nouveau film après récupération des données
    })
    .fail((erreur) => {
        console.error("Erreur lors de la récupération des données : ", erreur);
        alert("Une erreur s'est produite lors du chargement des données des films.");
    });
};

// Gestionnaire d'événement pour le clic sur le bouton d'édition en utilisant la délégation d'événement
$(document).ready(function() {
  $(document).on('click', '.btn-edit', function() {
      const movieId = $(this).data('id');
      editMovie(movieId);
  });
});

// Fonction pour gérer l'édition d'un film
const editMovie = (movieId) => {
  const movie = listeFilms.find(film => film.id === movieId);
  if (movie) {
      populateEditMovieForm(movie); // Assure que cette fonction remplit correctement le formulaire d'édition
      $('#editMovieModal').modal('show');
  } else {
      console.error('Film non trouvé avec l\'ID :', movieId);
  }
};

// Gestionnaire d'événement pour la soumission du formulaire d'édition de film
$('#editMovieForm').on('submit', function(e) {
  e.preventDefault();
  const movieData = {
      id: $('#editMovieId').val(),
      title: $('#editMovieTitle').val(),
      year: $('#editMovieYear').val(),
      runtime: $('#editMovieRuntime').val(),
      genres: $('#editMovieGenres').val().split(',').map(genre => genre.trim()),
      director: $('#editMovieDirector').val(),
      actors: $('#editMovieActors').val().split(',').map(actor => actor.trim()),
      plot: $('#editMoviePlot').val(),
      posterUrl: $('#editMoviePosterUrl').val(),
  };
  fetch('/update-movie', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify(movieData),
  })
  .then(response => response.text())
  .then(data => {
      console.log(data);
      $('#editMovieModal').modal('hide');
      updateMovieList(); // Rafraîchit la liste des films après l'édition
  })
  .catch((error) => {
      console.error('Erreur :', error);
  });
});

// Fonction pour remplir le formulaire d'édition de film avec les données
const populateEditMovieForm = (movie) => {
  // Assure que chaque champ du formulaire est rempli avec les données correspondantes du film
  $('#editMovieId').val(movie.id);
  $('#editMovieTitle').val(movie.title);
  $('#editMovieYear').val(movie.year);
  $('#editMovieRuntime').val(movie.runtime);
  $('#editMovieGenres').val(movie.genres.join(', ')); // Convertit le tableau des genres en chaîne de caractères
  $('#editMovieDirector').val(movie.director);
  $('#editMovieActors').val(movie.actors.split(',').map(actor => actor.trim()).join(', '));
  $('#editMoviePlot').val(movie.plot);
  $('#editMoviePosterUrl').val(movie.posterUrl);
};

// Fonction pour mettre à jour la liste des films après l'édition
const updateMovieList = () => {
  obtenirListeFilms(); // Rafraîchit la liste des films après l'édition
};

// Fonction pour générer un ID pour un nouveau film
const generateNewMovieId = () => {
    const lastMovie = listeFilms.reduce((prev, current) => (prev.id > current.id) ? prev : current, { id: 0 });
    return lastMovie.id + 1;
};

// Fonction pour remplir le champ ID dans le formulaire d'ajout de film
const populateNewMovieId = () => {
  const newMovieId = generateNewMovieId();
  $('#addMovieId').val(newMovieId);
};

// Dans requetes.js
function addMovie(movieData) {
$.ajax({
    url: '/add-movie',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(movieData),
})
.done((response) => {
    console.log(response.message); // Journalise ou alerte le message de succès
    obtenirListeFilms(); // Rafraîchit la liste des films pour inclure le nouveau film
})
.fail((jqXHR, textStatus) => {
    alert("Échec de l'ajout du film : " + textStatus); // Informe l'utilisateur de l'échec
});
}

// Exemple d'utilisation, en supposant que vous capturez les données du formulaire dans l'objet movieData
$("#addMovieForm").submit(function(e) {
e.preventDefault(); // Empêche la soumission par défaut du formulaire
const movieData = {
  title: $('#addMovieTitle').val(),
  year: parseInt($('#addMovieYear').val(), 10),
  runtime: parseInt($('#addMovieRuntime').val(), 10),
  genres: $('#addMovieGenres').val().split(',').map(genre => genre.trim()), // Sépare par une virgule et supprime les espaces
  director: $('#addMovieDirector').val(),
  actors: $('#addMovieActors').val().split(',').map(actor => actor.trim()), // Sépare par une virgule et supprime les espaces
  plot: $('#addMoviePlot').val(),
  posterUrl: $('#addMoviePosterUrl').val()
}; // Suppression du }; supplémentaire ici
addMovie(movieData);
});

const rechercheFilms = () => {
  const searchText = $('#searchInput').val().toLowerCase();
  const selectedGenre = $('#selGenres').val();
  const sortCriteria = $('#selTri').val();
  const sortOrder = $('#selOrdre').val();

  let filteredFilms = listeFilms.filter(film => {
      const correspondAuTexteDeRecherche = !searchText ||
                              film.title.toLowerCase().includes(searchText) ||
                              film.director.toLowerCase().includes(searchText) ||
                              film.plot.toLowerCase().includes(searchText) ||
                              film.actors.toLowerCase().includes(searchText);
      const correspondAuGenre = !selectedGenre || film.genres.includes(selectedGenre);
      return correspondAuTexteDeRecherche && correspondAuGenre;
  });

  if (filteredFilms.length === 0) {
      $('#listeFilms').html("<p>Aucun film ne correspond à votre recherche.</p>");
      setupPagination(0);
      return;
  }

  filteredFilms.sort((a, b) => {
      let comparaison = 0;
      if(sortCriteria === 'year' || sortCriteria === 'runtime') {
          comparaison = parseInt(a[sortCriteria], 10) - parseInt(b[sortCriteria], 10);
      } else if(sortCriteria === 'title') {
          comparaison = a[sortCriteria].localeCompare(b[sortCriteria]);
      }
      return sortOrder === 'asc' ? comparaison : -comparaison;
  });

  currentDisplayMovies = filteredFilms;
  setupPagination(currentDisplayMovies.length);
  changePage(1);
};

// Gestionnaires d'événement pour les options de tri
$('#selTri, #selOrdre').change(rechercheFilms);

// Appel de la fonction pour remplir le champ ID dans le formulaire d'ajout de film
$(document).ready(function() {
    obtenirListeFilms(); // Récupère d'abord les données des films
    // Autres gestionnaires d'événement...
});
