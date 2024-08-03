/*
TP1-A
Chargé de cours : Marcel Côté (cours théorique du mardi : groupe : A)
Remis par: Gueorgui Poklitar
Matricule: 20251051
Coordonnees: gueorgui.poklitar@umontreal.ca
*/

/*Le programmeur inclut les fichiers qui seront utiliser dépendamment des fonctions que l'on veut 
inclure dans notre code stdio.h est le fichier des fonctions standard alors que math.h est le 
fichier des fonctions de nature mathématique. Dans notre cas, nous utilisons math.h pour inclure
 la fonction pow() qui comptabilise la puissance exponentielle de notre formule de triangle.
*/
#include<math.h>
#include<stdio.h>

//int main() designe le debut du program, la fonction de base du language C. Suivis de { pour y inclure les specification du programme.
int main()
{
	//Au debut du programme on definit les diverse variables qui existent dans notre programme ainsi que leur nature (int, float, char...).
	//Les premieres 4 variables concernent les parametres du rectangle.(longeur,largeur,surface et perimetre), ils sont tous des entiers.
	int longRect;
	int largRect;
	int surfaceRect;
	int perimetre;
	//Les 4 autres variables concernent les parametres du triangle.(base,hauteur, surface et perimetre), ils sont tous des float.
	//Le perimetre n'est pas demandée dans les specification, mais pour maintenir un format similair au rectangle nous inclurons les deux.
	float baseTri;
	float hautTri; 
	float surfaceTri;
	float perimetreTri;
	
	//une balise/titre est ajouté, une addition purement cosmetique.
	printf("------------Rectangle-----------\n");
	
	//On demande a l'utilisateur de saisire la longeur du rectangle.            
	printf("\nSaisissez la longeur du rectangle, puis appuyez sur la touche (ENTER):\n");
	//Le programme recherche un entier, saisis par l'utilisateur. Cette entier correspondera a la variable de la longeur.
	scanf("%d",&longRect);
	
	//On demande a l'utilisateur de saisire la largeur du rectangle. 
	printf("Saisissez la largeur du rectangle, puis appuyez sur la touche (ENTER):\n");
	//Le programme recherche un autre entier, saisis par l'utilisateur. Cette entier correspondera a la variable de la largeur.
	scanf("%d",&largRect);
	
	//Une fois les variables du rectengles saisis, on demande au programme de calculer 
	//les variables de la surface et du perimetre du rectengle selon la formule suivante.
	surfaceRect = longRect * largRect;
	perimetre = (longRect + largRect)*2 ;
	
	//On demande au programme de montrer les resultats obtenus suite aux calculations, surface et perimetre du rectengle.
	printf("\nSurface = %d\nPerimetre = %d\n\n\n",surfaceRect,perimetre);
	
	//une balise/titre est ajouté, une addition purement cosmetique.
	printf("-------Triangle rectangle-------\n");
	
	//On demande a l'utilisateur de saisire la base du triangle rectangle.             
	printf("\nSaisissez la base du triangle rectangulaire, puis appuyez sur la touche (ENTER): \n");
	//Le programme recherche un reel, saisis par l'utilisateur. Ce reel correspondera a la variable de la base du triangle.            
	scanf("%f",&baseTri);
	//On demande a l'utilisateur de saisire la hauteur du triangle rectangle.            
	printf("Saisissez la hauteur du triangle rectangulaire, puis appuyez sur la touche (ENTER):\n");
	//Le programme recherche un reel, saisis par l'utilisateur. Ce reel correspondera a la variable de la hauteur du triangle.
	scanf("%f",&hautTri);
	
	//Une fois les variables du triangle rectangle sont saisis, on demande au programme de calculer 
	//les variables de la surface et du perimetre du rectengle selon la formule suivante avec les variables saisis par l'utilisateur.
	//Le perimetre est une addition du programmeur, plutot cosmetique et optionelle. Une formule simplifié du theoreme de pythagore, a^2 + b^2 = c^2.  
	surfaceTri = (baseTri*hautTri)/2 ;
	perimetreTri= pow(pow(baseTri,2.0) + pow(hautTri,2.0),0.5) + baseTri+hautTri;
	
	//On demande au programme de montrer les resultats obtenus suite aux calculations, surface et perimetre du triangle rectangle.
	//A noter que %.2f signifie qu'on veut le resultat du reel obtenus lors du calcule, de montrer des resultats a 2 chiffres apres la décimale. 
	printf("\nSurface = %.2f\nPerimetre = %.2f\n",surfaceTri,perimetreTri);
	
	//On fini avec return 0, la fin standard des programmes de la langue C.
	return 0;
	//Puis on ferme notre }, pour signifier la fin de l'inclusion du programme.
}

/* 
------------Rectangle-----------

Saisissez la longeur du rectangle, puis appuyez sur la touche (ENTER):
12
Saisissez la largeur du rectangle, puis appuyez sur la touche (ENTER):
8

Surface = 96
Perimetre = 40


-------Triangle rectangle-------

Saisissez la base du triangle rectangulaire, puis appuyez sur la touche (ENTER):
10.0
Saisissez la hauteur du triangle rectangulaire, puis appuyez sur la touche (ENTER):
8.2

Surface = 41.00
Perimetre = 31.13

--------------------------------
Process exited after 96.47 seconds with return value 0
Appuyez sur une touche pour continuer... 
*/

