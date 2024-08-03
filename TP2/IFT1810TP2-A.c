/*
TP2-A
Chargé de cours : Marcel Côté (cours théorique du mardi : groupe : A)
Remis par: Gueorgui Poklitar
Matricule: 20251051
Coordonnees: gueorgui.poklitar@umontreal.ca
*/

#include <stdio.h>
#include <stdlib.h>
#include <time.h>//pour la fonction srand

#define MAX_NOMBRE 500 //definition du nombre maximal

int main() {
    srand(1810); //seed 1810
    char rejouer;
    
    do { //recommence le jeu, boucle do-while
        int nombreMystere = rand() % MAX_NOMBRE + 1; //nombre aleatoire
        int essais = 0; //essais commencent a 0
        int choix;
        
        printf("Le jeu consiste a deviner le nombre choisi par l'ordinateur\n\n");
        do {
            printf("Le joueur doit entrer un nombre entre 1 et 500: ");
            scanf("%d", &choix);
            essais++; //incrementation variable essais
            
            if (choix < 1 || choix > MAX_NOMBRE) { //section pour trier les essais, plus petit ou plus grand, correct ou incorrect
                printf("Le nombre est invalide, recommencer s.v.p.\n");
                essais = 0;
            } 
			else if (choix < nombreMystere) {
                printf("%d est plus petit que le nombre mystere\n", choix);
            } 
			else if (choix > nombreMystere) {
                printf("%d est plus grand que le nombre mystere\n", choix);
            } 
			else {
                printf("\nBravo, le joueur a trouve le nombre mystere en %d coups\n", essais);
            }//fin de la parti
        } 
		while (choix != nombreMystere); //tant que choix n'egale pas nombre mystere aleatoire
        
		printf("Voulez-vous une autre partie (o/n): "); //menu pour rejouer
        scanf(" %c", &rejouer);
    } 
	while (rejouer == 'o' || rejouer == 'O'); 
	
	if(rejouer == 'n' || rejouer == 'N'){
		printf("\n\n\n");//si option N, fermeture du programme
		printf("Fin du programme!\n");
	}
	else { 
		printf("\nErreur, option [ %c ] n'est pas une option possible!\n", rejouer);//si option inconnue
		printf("Fin du programme!\n");
	}
    
    return 0;
}
//Resultat obtenu :
/*
Le jeu consiste a deviner le nombre choisi par l'ordinateur

Le joueur doit entrer un nombre entre 1 et 500: 600
Le nombre est invalide, recommencer s.v.p.
Le joueur doit entrer un nombre entre 1 et 500: 0
Le nombre est invalide, recommencer s.v.p.
Le joueur doit entrer un nombre entre 1 et 500: 400
400 est plus petit que le nombre mystere
Le joueur doit entrer un nombre entre 1 et 500: 450

Bravo, le joueur a trouve le nombre mystere en 2 coups
Voulez-vous une autre partie (o/n): o
Le jeu consiste a deviner le nombre choisi par l'ordinateur

Le joueur doit entrer un nombre entre 1 et 500: 450
450 est plus grand que le nombre mystere
Le joueur doit entrer un nombre entre 1 et 500: 425
425 est plus grand que le nombre mystere
Le joueur doit entrer un nombre entre 1 et 500: 412

Bravo, le joueur a trouve le nombre mystere en 3 coups
Voulez-vous une autre partie (o/n): n

--------------------------------
Process exited after 80.27 seconds with return value 0
Press any key to continue . . .
*/
