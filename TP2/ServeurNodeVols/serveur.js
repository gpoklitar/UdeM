const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs').promises;
const path = require('path');
const app = express();
const port = 8383;

app.listen(port, () => {
    console.log(`Serveur démarré sur http://localhost:${port}`);
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const cheminFichier = './vols.json';

app.get('/vols', (req, res) => {
    res.setHeader('Content-Type', 'application/json');
    res.sendFile(path.join(__dirname, cheminFichier));
});

app.post('/sauvegarde', async (req, res) => {
    const jsonData = req.body;
    const jsonString = JSON.stringify(jsonData, null, 2);

    try {
        await fs.writeFile(cheminFichier, jsonString);
        console.log('Données écrites avec succès dans le fichier vols.json');
        res.status(200).send('Données écrites avec succès dans le fichier vols.json');
    } catch (err) {
        console.error('Erreur lors de l\'écriture dans le fichier :', err);
        res.status(500).send('Erreur lors de l\'écriture dans le fichier');
    }
});
