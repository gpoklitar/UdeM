/*
Nom: Gueorgui Poklitar
TP2-IFT1142
Description: Cote serveur pour pouvoir refleter et sauvgarder les modifications des requetes.
Ecritutre et lecture du JSON.
*/


const express = require('express');
const cors = require('cors');
const fs = require('fs').promises; // Utiliser fs promises pour le mode asynchrone/attendre
const path = require('path');

const app = express();
const PORT = 3000;
const filePath = path.join(__dirname, 'DonneesFilms.json');

app.use(cors());
app.use(express.json());
app.use(express.static('public'));

// Fonction pour lire et parser le fichier JSON
async function readDbFile() {
    const data = await fs.readFile(filePath, 'utf8');
    return JSON.parse(data);
}

// Fonction pour écrire dans le fichier JSON
async function writeDbFile(data) {
    await fs.writeFile(filePath, JSON.stringify(data, null, 2), 'utf8');
}

// Ajouter un film
app.post('/add-movie', async (req, res) => {
    try {
        const newMovie = req.body;
        const db = await readDbFile();
        
        newMovie.id = db.movies.length > 0 ? Math.max(...db.movies.map(movie => movie.id)) + 1 : 1;
        db.movies.push(newMovie);

        await writeDbFile(db);
        res.status(200).json({ message: 'Film ajoute avec succes', movie: newMovie });
    } catch (error) {
        console.error(error); // Journaliser l'erreur pour la visibilité cote serveur
        res.status(500).json({ message: 'Erreur interne du serveur' }); // Envoyer un message d'erreur générique au client
    }
});

// Mettre à jour un film
app.post('/update-movie', async (req, res) => {
    try {
        const updatedMovie = req.body;
        const db = await readDbFile();

        const movieIndex = db.movies.findIndex(movie => movie.id === updatedMovie.id);
        if (movieIndex === -1) return res.status(404).send('Film non trouve');

        db.movies[movieIndex] = updatedMovie;
        await writeDbFile(db);

        res.status(200).send({message: 'Film mis a jour avec succes', movie: updatedMovie});
    } catch (err) {
        console.error('Erreur lors de la gestion de la requete :', err);
        res.status(500).send('Erreur du serveur');
    }
});

// Supprimer un film
app.post('/delete-movie', async (req, res) => {
    try {
        const { id } = req.body;
        const db = await readDbFile();

        const movieIndex = db.movies.findIndex(movie => movie.id === id);
        if (movieIndex === -1) return res.status(404).send('Film non trouve');

        db.movies.splice(movieIndex, 1);
        await writeDbFile(db);

        res.status(200).send('Film supprime avec succes');
    } catch (err) {
        console.error('Erreur lors de la gestion de la requete :', err);
        res.status(500).send('Erreur du serveur');
    }
});

app.listen(PORT, () => {
    console.log(`Serveur en cours d'execution sur http://localhost:${PORT}`);
});
