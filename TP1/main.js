/*
Nom: Gueorgui Poklitar
TP1-IFT1142
Description: Utilisation d'un fichier cocktails.js non-modifiée, transfer en JSON, 
lecture du JSON et application sur le programme main de l'interface avec options de gestion.
*/


//Lecture du fichier cocktails.js
const fs = require('fs').promises;
const readline = require('readline');
const path = './cocktails.js';

//Crée une interface de ligne de commande pour lire l'entree utilisateur et ecrire la sortie
const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

//lecture
fs.readFile(path, 'utf8', (err, data) => {
  if (err) {
    console.error('Error reading the file', err);
    return;
  }

  // Extraction des données, suppression de la declaration de la variable initial dans cocktails.js
  const jsonData = data.replace(/^.*?=/, '');

  //Ecrire les données, dans un nouveau fichier JSON
  fs.writeFile('./cocktails.json', jsonData, 'utf8', (err) => {
    //si erreur...
    if (err) {
      console.error('Error writing the file', err);
      return;
    }
    console.clear();
    mainMenu(); //appel du menu principale.
  });
});
//Charger les données des cocktails à partir du fichier JSON spécifié
const cocktailsFilePath = './cocktails.json';
const cocktails = loadCocktailsData(cocktailsFilePath);


//Fonctions de support, formattage, presentation, temps d'attentes, sauvgardes, impression des tableaux des cocktails, chargement des données...
function wrapText(text, maxLength) {
    if (!text) return [''];
    
    const lines = [];
    let currentLine = '';
    
    text.split(' ').forEach(word => {
      if ((currentLine + word).length > maxLength) {
        lines.push(currentLine.trim());
        currentLine = word;
      } else {
        currentLine += ' ' + word;
      }
    });
    
    if (currentLine) {
      lines.push(currentLine.trim());
    }
    
    return lines;
}

function promptUser(message) {
  return new Promise((resolve, reject) => {
    rl.question(message, (answer) => {
      resolve(answer);
    });
  });
}

async function initializeCocktailsData() {
    try {
      const data = await fs.readFile(path, 'utf8');
      const jsonData = data.replace(/^.*?=/, ''); 
      await fs.writeFile('./cocktails.json', jsonData, 'utf8');
      console.clear();
      console.log('The file was successfully saved as cocktails.json');
    } catch (err) {
      console.error('Error processing the file', err);
    }
}

function wait(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function saveCocktailsData(cocktails) {
    try {
        await fs.writeFile('./cocktails.json', JSON.stringify(cocktails, null, 2), 'utf8');
        console.log('Cocktails data updated successfully.');
    } catch (error) {
        console.error('Failed to update cocktails data:', error);
    }
}

async function getCocktailsData() {
    const cocktailsFilePath = './cocktails.json';
    return await loadCocktailsData(cocktailsFilePath);
}

function printCocktailsTable(cocktails) {
    console.log("Nom                 | Verre           | Ingrédients                                  | Préparation");
    console.log("---------------------------------------------------------------------------------------------------------------------------------------");
    cocktails.sort((a, b) => a.name.localeCompare(b.name)).forEach(cocktail => {
        const ingredients = cocktail.ingredients.map(ingredientItem => {
            return ingredientItem.ingredient || ingredientItem.special; 
        }).join(', ');

        const preparationLines = wrapText(cocktail.preparation, 50);
        const ingredientLines = wrapText(ingredients, 45); 
        const maxLines = Math.max(preparationLines.length, ingredientLines.length);

        for (let i = 0; i < maxLines; i++) {
            const name = i === 0 ? cocktail.name.padEnd(20, ' ') : ''.padEnd(20, ' ');
            const glass = i === 0 ? cocktail.glass.padEnd(16, ' ') : ''.padEnd(16, ' ');
            const ingredient = ingredientLines[i] || '';
            const preparation = preparationLines[i] || '';

            console.log(`${name}| ${glass}| ${ingredient.padEnd(45, ' ')}| ${preparation}`);
        }
        console.log("-".repeat(135));
    });
    console.log(`Nombre de cocktails : ${cocktails.length}`);
}

async function loadCocktailsData(filePath) {
    try {
      const cocktailsData = await fs.readFile(filePath, 'utf8');
      return JSON.parse(cocktailsData);
    } catch (error) {
      console.error('Error loading cocktails data:', error);
      return [];
    }
}

//Functions du menu principale, ajouter, modifier et supprimer une entré dans le fichier JSON.
// Fonction pour ajouter un nouveau cocktail
async function addCocktail() {
    // Demande et vérifie le nom du cocktail
    const name = await promptUser('Entrez le nom du cocktail : ');
    if (!name) {
        console.log('Aucune entrée n\'a été ajoutée car le nom du cocktail est obligatoire.');
        await wait(2500); // Attend 2,5 secondes avant de revenir au menu
        await mainMenu();
        return;
    }

    // Collecte des informations supplémentaires sur le cocktail
    const colorsInput = await promptUser('Entrez la ou les couleurs du cocktail (séparées par une virgule si plusieurs) : ');
    const colors = colorsInput.split(',').map(color => color.trim()); // Traite l'entrée des couleurs
    const glass = await promptUser('Entrez le type de verre : ');
    const category = await promptUser('Entrez la catégorie du cocktail : ');
    const ingredientsInput = await promptUser('Entrez les ingrédients (ingrédient:quantité, séparés par des virgules) : ');
    
    // Traite l'entrée des ingrédients
    const ingredients = ingredientsInput ? ingredientsInput.split(',').map(ingredient => {
        const [ingredientName, amount] = ingredient.split(':');
        return { ingredient: ingredientName.trim(), amount: amount.trim() };
    }) : undefined;
    
    const garnish = await promptUser('Entrez la garniture (si applicable) : ');
    const preparation = await promptUser('Entrez les étapes de préparation : ');

    // Crée et sauvegarde le nouveau cocktail
    try {
        const newCocktail = { name, colors, glass, category, ingredients, garnish, preparation };
        const cocktails = await getCocktailsData();
        cocktails.push(newCocktail);
        await saveCocktailsData(cocktails);
        console.log('Cocktail ajouté avec succès.');
    } catch (error) {
        console.error('Échec de l\'ajout d\'un nouveau cocktail :', error);
    } finally {
        await wait(2500); // Attend avant de revenir au menu principal
        await mainMenu();
    }
}

// Fonction pour modifier un cocktail existant
async function modifyCocktail() {
    let cocktails = await getCocktailsData(); // Charge la liste des cocktails
    cocktails = cocktails.sort((a, b) => a.name.localeCompare(b.name)); // Trie les cocktails par nom

    // Affiche les cocktails et demande à l'utilisateur d'en sélectionner un à modifier
    cocktails.forEach((cocktail, index) => console.log(`${index + 1}. ${cocktail.name}`));
    const cocktailIndex = parseInt(await promptUser('Entrez le numéro du cocktail à modifier : '), 10) - 1;
    if (cocktailIndex >= 0 && cocktailIndex < cocktails.length) {
        const cocktail = cocktails[cocktailIndex]; // Sélectionne le cocktail à modifier

        // Permet à l'utilisateur de mettre à jour les informations du cocktail
        // Si aucune entrée n'est fournie pour un champ, la valeur actuelle est conservée
        // Sauvegarde les modifications dans le fichier JSON
        await fs.writeFile('./cocktails.json', JSON.stringify(cocktails, null, 2), 'utf8');
        console.log('Cocktail modifié avec succès.');
    } else {
        console.log('Sélection de cocktail invalide.');
    }
    await mainMenu(); // Retour au menu principal
}

// Fonction pour supprimer un cocktail
async function deleteCocktail() {
    let cocktails = await getCocktailsData(); // Charge la liste des cocktails
    cocktails.sort((a, b) => a.name.localeCompare(b.name)); // Trie les cocktails par nom

    // Demande à l'utilisateur de choisir un cocktail à supprimer
    // Si le numéro est valide, supprime le cocktail de la liste et met à jour le fichier JSON
    // Confirme la suppression ou informe de l'invalidité de la sélection
    await mainMenu(); // Retour au menu principal
}




//Fonctions du sous-menu, list complete des cocktails, list selon le verre, liste par ingredient dont types d'alcool et list des cocktails avec cubes de glaces.
async function listAllCocktails() {
    console.clear(); //Efface la console pour une meilleure lisibilité
    console.log("Liste de tous les cocktails avec détails:");
  
    //Chargement et attente des données des cocktails depuis le fichier JSON
    const cocktailsFilePath = './cocktails.json';
    const cocktails = await loadCocktailsData(cocktailsFilePath);
  
    //Tri des cocktails par nom pour une présentation ordonnée
    const sortedCocktails = cocktails.sort((a, b) => a.name.localeCompare(b.name));
  
    printCocktailsTable(sortedCocktails); // Affichage des cocktails dans un tableau formaté
  
    //instruction pour retourner au menu précédent
    console.log('Press any key to return to the previous menu...');
    await promptUser(''); // Attente de la réponse de l'utilisateur
    await listCocktailsSubMenu(); //Retour au sous-menu
}

async function listCocktailsByGlass() {
    const cocktails = await getCocktailsData(); //Chargement des données des cocktails
    const glasses = {}; //initialisation du dictionnaire pour les types de verres
    
    // regroupement des cocktails par type de verre
    cocktails.forEach(cocktail => {
      if (!glasses[cocktail.glass]) {
        glasses[cocktail.glass] = [];
      }
      glasses[cocktail.glass].push(cocktail);
    });
  
    console.clear(); // Nettoyage de la console
    console.log('Liste de cocktails par type de verre:');
  
    const glassTypes = Object.keys(glasses).sort(); // Triage des types de verres
    
    //Affichage des types de verres disponibles
    glassTypes.forEach((glass, index) => {
      console.log(`${index + 1}. ${glass}`);
    });
  
    //selection du type de verre par le user
    const chosenGlassIndex = await promptUser('Entrez le numéro du type de verre pour lister les cocktails: ');
    const chosenGlass = glassTypes[parseInt(chosenGlassIndex, 10) - 1];
    
    if (!chosenGlass || !glasses[chosenGlass]) {
      console.log('Verre non trouvé, veuillez réessayer.');
      await listCocktailsByGlass(); //En cas d'erreur
      return;
    }
  
    console.log(`\nCocktails servis dans un ${chosenGlass}:`);
    printCocktailsTable(glasses[chosenGlass]); //Affichage des cocktails pour le verre sélectionné
  
    await promptUser('Appuyez sur n’importe quelle touche pour continuer...');
    await listCocktailsSubMenu(); //Retour au sous-menu
}

async function listCocktailsByIngredient() {
    const cocktails = await getCocktailsData(); //Chargement des donnees
    let ingredients = {}; // Préparation list pour les ingredients
    
    //Organisation des cocktails par ingrédient
    cocktails.forEach(cocktail => {
        cocktail.ingredients.forEach(ingredientItem => {
            const ingredientName = ingredientItem.ingredient || ingredientItem.special;//Inclusion des ingredient speciaux!
            if (!ingredients[ingredientName]) {
                ingredients[ingredientName] = [];
            }
            ingredients[ingredientName].push(cocktail);
        });
    });
  
    console.clear(); //Nettoyage de la console
    console.log('Liste de cocktails par ingrédient:');
  
    // Triage des ingredients pour affichage
    let ingredientList = Object.keys(ingredients).sort((a, b) => a.toLowerCase().localeCompare(b.toLowerCase()));
  
    // Affichage des ingrédient disponibles
    ingredientList.forEach((ingredient, index) => {
        console.log(`${index + 1}. ${ingredient}`);
    });
  
    // Sélection de l'ingrédient par l'utilisateur
    const chosenIngredientIndex = await promptUser('Entrez le numéro de l’ingrédient pour lister les cocktails: ');
    const chosenIngredient = ingredientList[parseInt(chosenIngredientIndex, 10) - 1];
    
    if (!chosenIngredient || !ingredients[chosenIngredient]) {
        console.log('Ingrédient non trouvé, veuillez réessayer.');
        await listCocktailsByIngredient(); //cas d'erreur
        return;
    }
  
    console.log(`\nCocktails contenant ${chosenIngredient}:`);
    printCocktailsTable(ingredients[chosenIngredient]); //affichage des cocktails contenant l'ingrédient sélectionné
  
    await promptUser('Appuyez sur n’importe quelle touche pour continuer...');
    await listCocktailsSubMenu(); //Retour au sous-menu
}

async function listCocktailsWithIceCubes() {
    const cocktails= await getCocktailsData(); // Chargement des données des cocktails
    const withIceCubes = cocktails.filter(cocktail => cocktail.preparation && cocktail.preparation.toLowerCase().includes('ice cubes')); // Filtrage des cocktails avec des cubes de glace

    console.clear(); // Nettoyage de la console
    console.log('Liste de cocktails avec des cubes de glace:');
    if (withIceCubes.length > 0) {
        printCocktailsTable(withIceCubes); // Affichage des cocktails sélectionnés
    } else {
        console.log("Aucun cocktail avec des cubes de glace trouvé."); // Message si aucun cocktail n'est trouvé
    }

    await promptUser('Press any key to continue...'); //Attente d'une action de l'utilisateur pour continuer
    await listCocktailsSubMenu(); //Retour au sous-menu
}




//Fonctions du menu et du sous-menu
async function mainMenu() {
    console.clear();
    const cocktailsFilePath = './cocktails.json';
    const cocktails = await loadCocktailsData(cocktailsFilePath);
  
    console.log(`MENU GESTION DES COCKTAILS`);
    console.log(`1- Lister les cocktails`);
    console.log(`2- Ajouter un nouveau cocktail`);
    console.log(`3- Modifier un cocktail`);
    console.log(`4- Supprimer un cocktail`);
    console.log(`5- Quitter`);
    const choice = await promptUser('Entrez votre choix : ');
  //switch-case pour menu
    switch (choice) {
      case '1':
        await listCocktailsSubMenu(cocktails);
        break;
      case '2':
        console.log('Ajouter un nouveau cocktail');
        await addCocktail();
        break;
      case '3':
        console.log('Modifier un cocktail');
        await modifyCocktail();
        break;
      case '4':
        console.log('Supprimer un cocktail');
        await deleteCocktail();
        break;
      case '5':
        console.log('Quitter');
        rl.close();
        break;
      default:
        console.log('Choix invalide. Veuillez réessayer.');
        await mainMenu();
    }
}

async function listCocktailsSubMenu() {
  console.clear();
  console.log(`LISTER DES COCKTAILS`);
  console.log(`1- Lister tous les cocktails`);
  console.log(`2- Lister les cocktails selon le type de "glass"`);
  console.log(`3- Lister tous les cocktails contenant un type d’alcool donné`);
  console.log(`4- Lister tous les cocktails ayant dans leur préparation des cubes de glace.`);
  console.log(`5- Quitter`);
  const choice = await promptUser('Entrez votre choix : ');
 //switch-case pour sous-menu
  switch (choice) {
    case '1':
      await listAllCocktails();
      break;
    case '2':
      await listCocktailsByGlass();
      break;
    case '3':
      await listCocktailsByIngredient();
      break;
    case '4':
      await listCocktailsWithIceCubes();
      break;
    case '5':
      await mainMenu();
      break;
    default:
      console.log('Cette fonctionnalité est en cours de développement.');
      await listCocktailsSubMenu();
  }
}


//Fonction de démarrage du programme
async function main() {
    await initializeCocktailsData();
    await mainMenu();
}

//Appel du main();
main();
  