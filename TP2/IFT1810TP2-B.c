/*
TP2-B
Chargé de cours : Marcel Côté (cours théorique du mardi : groupe : A)
Remis par: Gueorgui Poklitar
Matricule: 20251051
Coordonnees: gueorgui.poklitar@umontreal.ca
*/

#include <stdio.h>
//prototypage
int menuA();
int menuB(int nbRetr);
int menuC();
int menuPrincipale();
void menuIntro();
//fonction principale (int main)
int main()
{
	menuIntro(); //appel de la section intro du menu
	menuPrincipale();//appel de la fonction menuPrincipale
	return 0;
}
//option menu A
int menuA()
{
	int n1, n2, n3;
	
	printf ("Veuillez entrer 3 nombres : ");
	scanf(" %d %d %d",&n1, &n2, &n3);//saisi 3 nombres a ordonner 
	//logique de triage
	if(n1 < n2 && n2 < n3){
		printf("Voici les trois nombres en ordre croissant %d %d %d.\n\n", n1, n2, n3);//imprime ordre selon la logique
	}
	else if (n2 < n1 && n1 < n3){
	    printf("Voici les trois nombres en ordre croissant %d %d %d.\n\n", n2, n1, n3);
	}
	else if(n3 < n2 && n2 < n1){
	    printf("Voici les trois nombres en ordre croissant %d %d %d.\n\n", n3, n2, n1);
	}
	else if(n2 < n3 && n3 < n1){
	    printf("Voici les trois nombres en ordre croissant %d %d %d.\n\n", n2, n3, n1);
	}
	else if (n3 < n1 && n1 < n2){
	    printf("Voici les trois nombres en ordre croissant %d %d %d.\n\n", n3, n1, n2);
	}
	else{
	    printf("Voici les trois nombres en ordre croissant %d %d %d.\n\n", n1, n3, n2);
	}
	return 0;
}
//option menu B
int menuB(int nbRetr)
{
	int *valRetr = &nbRetr; // pointer pour distinguer valeur retrouver et nombre retrouver.
	int valRech; //valeur rechercher
	int nbSuite; //nombre de suite
	int nb; //nombres
	int i=1; //lignes a imprimer

	printf("Entrez la valeur recherchee : ");
	scanf("%d",&valRech);
	printf("Entrez le nombre d'entiers dans la suite : ");
	scanf("%d",&nbSuite);
	
	do {
      	printf("Entrez le nombre %d : ",i); //enumeration de chaque ligne 
 		scanf(" %d",&nb); //enter le nombre
 		i++; //increment de i pour la prochaine ligne
		 	if(nb == valRech) //si le nombre est egale a la valeur rechercher,
			 {// fait une incrementation +1 a nbRetr
		 		nbRetr++;
			 }
   		}
   	while( i<=nbSuite );//tant que nombre de lignes imprimé est plus petit/egale a nbSuite defini plus haut
	printf("L'entier se retrouve %d fois.\n\n",*valRetr); //print la valeur des nombres retrouvé selon l'incrementation a l'aide d'un pointer renvoye de la variable nbRetr	
}
//option menu C
int menuC()
{
	int i, j; // dimention i et j
	int nbLongeur;
	
	    printf("Entrez la longueur de la ligne : ");
	    scanf(" %d", &nbLongeur); //entrer nombre pour longeur de la ligne
	
			for (i = 0; i < 1; i++){ // commence a l'espace 0, i plus petit que 1, pour faire une ligne mince de 1 charactere
			     for (j = i; j < nbLongeur; j++) { //commence a 0, longeur de la ligne imprime jusqu'a variable defini, plus petit que nbLongeur
			     printf("*"); //imprime charactere *
			}
		printf("\n\n");
	    }
	return 0;
}
//menu principale de base
int menuPrincipale()
{
	
	char option;
	
		do	{
		printf("\n\nEntrez votre choix : ");
		scanf(" %c",&option); //saisi de l'option
				
		switch(option){
			case 'A':
			case 'a':
				menuA();//appel menu A
				break;
			case 'B':
			case 'b':
				menuB(0);//appel menu B avec valeur du parametre initial de 0
				break;
			case 'C':
			case 'c':
				menuC();//appel menu C
				break;
			case 'X':
			case 'x':
				return 0; // si x, fin du programme
			default:
				printf("\nChoix incorrect!\n\n");//si option inconnue
		}
	}									
	while(option != 'x'||'X');// tant que option choisi n'est pas x, refait la boucle.
}
//option menu intro (dans le menu principale)
void menuIntro()
{ //lignes de codes simplifier sous une fonction void, sans parametres et retours
	printf("Menu des taches\nA) Ordonner trois nombres en ordre croissant\nB) Rechercher un nombre dans une suite de nombres\nC) Tracer une ligne \n\nX) Sortir du programme");	
}

/*RESULTAT:

Menu des taches
A) Ordonner trois nombres en ordre croissant
B) Rechercher un nombre dans une suite de nombres
C) Tracer une ligne

X) Sortir du programme

Entrez votre choix : A
Veuillez entrer 3 nombres : 5 7 10
Voici les trois nombres en ordre croissant 5 7 10.



Entrez votre choix : A
Veuillez entrer 3 nombres : 7 5 10
Voici les trois nombres en ordre croissant 5 7 10.



Entrez votre choix : A
Veuillez entrer 3 nombres : 10 7 5
Voici les trois nombres en ordre croissant 5 7 10.



Entrez votre choix : B
Entrez la valeur recherchee : 3
Entrez le nombre d'entiers dans la suite : 5
Entrez le nombre 1 : 3
Entrez le nombre 2 : 7
Entrez le nombre 3 : 3
Entrez le nombre 4 : 5
Entrez le nombre 5 : 3
L'entier se retrouve 3 fois.



Entrez votre choix : C
Entrez la longueur de la ligne : 5
*****



Entrez votre choix : X

--------------------------------
Process exited after 116.8 seconds with return value 0
Appuyez sur une touche pour continuer...

*/
