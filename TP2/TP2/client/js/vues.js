/*
Nom: Gueorgui Poklitar
TP2-IFT1142
Description: Interactions visuelles de l'utilisateur avec la page web. affichage des cartes de filmes etc...
*/


const creerCard = (unFilm) => {
    // Détermine la source de l'affiche du film, utilise une image par défaut si l'URL n'est pas définie
    const posterSrc = unFilm.posterUrl || 'client/images/pp1.png';
    return `
        <div class="card">
            <img src="${posterSrc}" onerror="this.onerror=null; this.src='client/images/pp1.png';" class="card-img-top" alt="Affiche du film ${unFilm.title}">
            <div class="card-body">
                <h5 class="card-title">${unFilm.title}</h5>
                <p class="card-text">Année: ${unFilm.year}</p>
                <p class="card-text">Durée: ${unFilm.runtime} minutes</p>
                <p class="card-text">Genres: ${unFilm.genres.join(', ')}</p>
                <p class="card-text">Réalisateur: ${unFilm.director}</p>
                <p class="card-text">Acteurs: ${unFilm.actors}</p>
                <p class="card-text">Synopsis: ${unFilm.plot}</p>
            </div>
            <div class="card-actions">
                <button class="btn btn-info btn-edit" data-bs-toggle="modal" data-bs-target="#editMovieModal" data-id="${unFilm.id}">Modifier</button>
                <button class="btn btn-danger" onclick="confirmDeleteMovie(${unFilm.id});">Supprimer</button>
            </div>
            <div class="collapse collapse-content" id="plot${unFilm.id}">
                <div class="card card-body">
                    ${unFilm.plot}
                </div>
            </div>
        </div>
    `;
};

// Fonction pour confirmer la suppression d'un film
function confirmDeleteMovie(movieId) {
    // Affiche la modal de suppression
    $('#deleteMovieModal').modal('show');

    // Lorsque le bouton 'Supprimer' dans la modal est cliqué
    $('#confirmDelete').off('click').on('click', function() {
        // Trouve l'index du film dans la liste
        const movieIndex = listeFilms.findIndex(film => film.id === movieId);
        if (movieIndex > -1) {
            // Supprime le film de la liste
            listeFilms.splice(movieIndex, 1);

            // Met à jour l'affichage
            afficherListeFilms(listeFilms);

            // Cache la modal
            $('#deleteMovieModal').modal('hide');
        }
    });
}


// Fonction pour afficher la liste des films
const afficherListeFilms = (filmsToDisplay) => {
    let rep = "";
    if (filmsToDisplay.length === 0) {
        rep = "<p>Aucun film ne correspond à votre recherche.</p>";
    } else {
        // Crée une carte pour chaque film à afficher
        filmsToDisplay.forEach(unFilm => {
            rep += creerCard(unFilm);
        });
    }
    // Affiche les cartes dans la div 'listeFilms'
    document.getElementById('listeFilms').innerHTML = rep;
};

// Initialisation du document
$(document).ready(function() {
    // Autre code d'initialisation
    
    // Lie l'événement de clic aux boutons de modification
    $(document).on('click', '.btn-edit', function() {
        const movieId = $(this).data('id');
        editMovie(movieId);
    });
});


// Assure que currentPage est une variable globale accessible dans cette portée
let currentPage = 1; // Cette valeur doit être définie comme la page de départ par défaut
let pageSize = 5; // Vous pouvez ajuster ceci pour afficher un nombre différent de films par page

// ...

// Fonction pour configurer la pagination
const setupPagination = (totalMovies) => {
    const pageCount = Math.ceil(totalMovies / pageSize);
    let visiblePages = 3; // Nombre de boutons de page à afficher
    let paginationHtml = '<nav aria-label="Navigation de page"><ul class="pagination justify-content-center">';
  
    // Bouton Précédent
    paginationHtml += `<li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
        <a class="page-link" href="#" aria-label="Précédent" data-page="${currentPage - 1}">&laquo;</a>
    </li>`;
  
    // Numéros de page
    let startPage = Math.max(currentPage - Math.floor(visiblePages / 2), 1);
    let endPage = Math.min(startPage + visiblePages - 1, pageCount);
  
    if (startPage > 1) {
      paginationHtml += `<li class="page-item"><a class="page-link" href="#" data-page="1">1</a></li>`;
      if (startPage > 2) {
        paginationHtml += '<li class="page-item disabled"><span class="page-link">...</span></li>';
      }
    }
  
    // Boucle pour les numéros de page
    for (let i = startPage; i <= endPage; i++) {
      paginationHtml += `<li class="page-item ${currentPage === i ? 'active' : ''}">
          <a class="page-link" href="#" data-page="${i}">${i}</a>
      </li>`;
    }
  
    if (endPage < pageCount) {
      if (endPage < pageCount - 1) {
        paginationHtml += '<li class="page-item disabled"><span class="page-link">...</span></li>';
      }
      paginationHtml += `<li class="page-item"><a class="page-link" href="#" data-page="${pageCount}">${pageCount}</a></li>`;
    }
  
    // Bouton Suivant
    paginationHtml += `<li class="page-item ${currentPage === pageCount ? 'disabled' : ''}">
        <a class="page-link" href="#" aria-label="Suivant" data-page="${currentPage + 1}">&raquo;</a>
    </li>`;
  
    paginationHtml += '</ul></nav>';
    document.getElementById('pagination').innerHTML = paginationHtml;
};

// À l'intérieur de vues.js

// Fonction pour changer de page
const changePage = (newPage) => {
    if (newPage >= 1 && newPage <= Math.ceil(currentDisplayMovies.length / pageSize)) {
      currentPage = newPage;
      const startIndex = (currentPage - 1) * pageSize;
      const selectedMovies = currentDisplayMovies.slice(startIndex, startIndex + pageSize);
      afficherListeFilms(selectedMovies);
      setupPagination(currentDisplayMovies.length);
    }
};

// Événement de clic sur les liens de page
$(document).on('click', '.page-link', function(e) {
    e.preventDefault();
    const page = parseInt($(this).data('page'), 10);
    if (!isNaN(page)) {
      changePage(page);
    }
});

// Ajouter cela à l'intérieur de $(document).ready(function() { ... }) ou à la fin de votre fichier vues.js
$(function() {
    $('#searchInput').on('keyup', function() {
        var searchQuery = $(this).val().toLowerCase();
        filterMoviesByTitle(searchQuery);
    });
});

// Fonction pour filtrer les films par titre
function filterMoviesByTitle(query) {
    // Vous voudrez peut-être affiner ceci pour être plus efficace ou gérer des cas particuliers
    let filteredMovies = listeFilms.filter(film => film.title.toLowerCase().includes(query));
    afficherListeFilms('', filteredMovies); // Passez la liste filtrée à afficher
}


// Fonction pour afficher les genres
const afficherGenres = () => {
    let selGenres = document.getElementById('selGenres');
    selGenres.options.length = 0; // Effacer d'abord les options existantes

    // Ajouter une option pour "Toutes les catégories"
    selGenres.options[selGenres.options.length] = new Option("Toutes les catégories", "");

    // Remplir le reste des genres comme précédemment
    genres.forEach(genre => {
        selGenres.options[selGenres.options.length] = new Option(genre, genre);
    });
    document.getElementById('selGenres').addEventListener('change', rechercheFilms);
};

// Fonction pour trier les films
const listerTri = () => {
    let selGenres = document.getElementById("selGenres");
    let genre = selGenres.options[selGenres.selectedIndex].text;

    let selTri = document.getElementById('selTri');
    let trierPar = selTri.value; // Le critère de tri sélectionné

    let selOrdre = document.getElementById('selOrdre');
    let ordreTri = selOrdre.value; // "asc" ou "desc"

    listeFilms.sort((a, b) => {
        if (trierPar === "year" || trierPar === "runtime") {
            return ordreTri === "asc" ? a[trierPar] - b[trierPar] : b[trierPar] - a[trierPar];
        } else { // Tri par titre ou toute autre propriété de chaîne
            return ordreTri === "asc" ? a[trierPar].localeCompare(b[trierPar]) : b[trierPar].localeCompare(a[trierPar]);
        }
    });

    afficherListeFilms(genre);
};
