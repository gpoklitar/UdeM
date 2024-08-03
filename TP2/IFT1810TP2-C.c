/*
TP2-C
Chargé de cours : Marcel Côté (cours théorique du mardi : groupe : A)
Remis par: Gueorgui Poklitar
Matricule: 20251051
Coordonnees: gueorgui.poklitar@umontreal.ca
*/

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define NUM_LANCERS 100 // nombre de lancers de des

int main() {
    int des[NUM_LANCERS][2];
    int freq[11] = {0}; // tableau frequance ,pour compter le nombre de fois que chaque valeur apparaît
    int doubles = 0;
    int somme = 0, min = 12, max = 2; // sommes des dés possiles, de 2 a 12
    int i, j;

    srand(1020); // initialise le générateur de nombres aleatoires avec le seed 1020

    // lancer les dés et compter les doubles 
    for (i = 0; i < NUM_LANCERS; i++) {
        des[i][0] = rand() % 6 + 1;
        des[i][1] = rand() % 6 + 1;
        
        if (des[i][0] == des[i][1]) {
            doubles++;
        }
    }

    // afficher les lancers de des
    printf("\nLes lancers de des des sont:\n");
    for (i = 0; i < NUM_LANCERS; i++) {
        printf("(%d, %d) ", des[i][0], des[i][1]);
        
        if ((i + 1) % 10 == 0) {
            printf("\n");
        }
    }
    printf("\n");

    // calculer et afficher les statistique
    for (i = 0; i < NUM_LANCERS; i++) {
        int sum = des[i][0] + des[i][1];
        somme += sum;
        
        if (sum < min) {
            min = sum;
        }
        if (sum > max) {
            max = sum;
        }
    }
    printf("Il y a eu %d doubles,\n", doubles);
    printf("la moyenne des des est %.1f,\n", (float)somme / (NUM_LANCERS));
    printf("la plus petite et la plus grande valeur des des sont %d et %d.\n", min, max);

    // compter le nombre de fois que chaque valeur apparait et afficher l'histogramme
    for (i = 0; i < NUM_LANCERS; i++) {
        freq[des[i][0] + des[i][1] - 2]++;
    }
    printf("La repartition est:\n\n");
    
    for (i = 2; i <= 12; i++) {
        printf("%2d) ", i);
        for (j = 0; j < freq[i - 2]; j++) {
            printf("*");
        }
        printf(" (%d)\n", freq[i - 2]);
    }
    
    return 0;
}
//resultat :
/*
Les lancers de des des sont:
(4, 3) (4, 6) (4, 4) (4, 4) (5, 2) (2, 5) (5, 4) (2, 6) (3, 4) (5, 4)
(4, 1) (6, 2) (2, 4) (3, 1) (6, 2) (2, 6) (3, 4) (3, 2) (2, 3) (3, 3)
(4, 3) (6, 4) (1, 2) (5, 1) (3, 6) (6, 5) (6, 3) (4, 6) (4, 4) (2, 3)
(1, 5) (1, 4) (6, 3) (3, 2) (5, 6) (3, 3) (4, 4) (2, 3) (5, 5) (4, 1)
(1, 4) (5, 5) (6, 3) (4, 2) (5, 1) (5, 1) (5, 5) (2, 2) (4, 3) (5, 3)
(3, 1) (3, 6) (2, 1) (3, 6) (5, 4) (2, 5) (3, 6) (6, 2) (6, 5) (6, 4)
(4, 1) (5, 4) (4, 5) (3, 3) (3, 4) (4, 3) (2, 2) (4, 5) (1, 4) (4, 6)
(5, 2) (2, 1) (2, 1) (1, 2) (4, 6) (5, 2) (2, 6) (2, 5) (4, 4) (4, 1)
(4, 2) (3, 5) (4, 6) (1, 2) (4, 3) (6, 2) (2, 6) (2, 3) (6, 2) (5, 4)
(4, 3) (3, 6) (1, 6) (5, 6) (4, 5) (4, 3) (2, 4) (5, 3) (1, 4) (1, 6)

Il y a eu 13 doubles,
la moyenne des des est 7.2,
la plus petite et la plus grande valeur des des sont 3 et 11.
La repartition est:

 2)  (0)
 3) ****** (6)
 4) **** (4)
 5) ************** (14)
 6) *********** (11)
 7) ****************** (18)
 8) ***************** (17)
 9) **************** (16)
10) ********** (10)
11) **** (4)
12)  (0)

--------------------------------
Process exited after 0.09888 seconds with return value 0
Press any key to continue . . .

*/
