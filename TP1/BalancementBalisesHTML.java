/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Utilise une pile (Stack) pour verifier si chaque 
	balise d'ouverture a une balise de  fermeture correspondante 
	dans un string en format HTML.
	TP1 - Numero 4
	Dernière Mise-à-jour : 11/10/2023
*/

//Implemente la classe Stack et ArrayStack  pour ce code.

public class BalancementBalisesHTML {
    public static boolean isHTMLBalanced(String html) {
        Stack<String> stack = new ArrayStack<>();

        for (int index = 0; index < html.length(); index++) {
            char currentChar = html.charAt(index);
            if (currentChar == '<') {
                // Recherche de l'indice de la balise de fermeture correspondante '>'.
                int endIndex = html.indexOf('>', index);
                if (endIndex == -1) {
                    return false; // Aucune balise de fermeture correspondante trouvee
                }
                // Extrait la balise complete de l'indice courant a endIndex inclus.
                String tag = html.substring(index, endIndex + 1);
                if (!tag.startsWith("</")) {
                    // Il s'agit d'une balise d'ouverture, donc on la pousse sur la pile
                    stack.push(tag);
                } else {
                    // Il s'agit d'une balise de fermeture, alors on verifie si elle 
                	// correspond a la derniere balise d'ouverture
                    if (stack.isEmpty()) {
                        return false; // Aucune balise d'ouverture correspondante
                    }
                    String openingTag = stack.pop();
                    if (!tag.substring(2).equals(openingTag.substring(1))) {
                        return false; // Balises non correspondantes
                    }
                }
                index = endIndex; // On deplace l'indice  actuel a la fin de la balise
            }
        }

        return stack.isEmpty(); // Si la pile est vide a la fin, 
        //alors les balises sont equilibrees.
    }

    public static void main(String[] args) {
        String html = "<body> <center> <h1> The Little Boat </h1> "
        		+ "</center> <p>The storm tossed the little boat like "
        		+ "a cheap sneaker in an old washing machine. "
        		+ "The three drunken fishermen were used to such treatment, "
        		+ "of course, but not the tree salesman, who even as a "
        		+ "stowaway now felt that he had overpaid for the voyage. "
        		+ "</p> <ol> <li> Will the salesman die? "
        		+ "</li> <li> What color is the boat? "
        		+ "</li> <li> And what about Naomi? </li> </ol> </body>";

        boolean balanced = isHTMLBalanced(html);
        System.out.println("HTML est equilibree? : " + balanced);
    }
}

// RESULTAT
/*
HTML est equilibree? : true
*/
