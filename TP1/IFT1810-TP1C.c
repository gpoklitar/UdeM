/*
TP1-C
Chargé de cours : Marcel Côté (cours théorique du mardi : groupe : A)
Remis par: Gueorgui Poklitar
Matricule: 20251051
Coordonnees: gueorgui.poklitar@umontreal.ca
*/

//Debut standard du programme.
#include<stdio.h>
int main()
{
	//Definition des 2 variables.
	char statut;
	int age;
	
	//Demande à l'utilisateur de saisir un statut à travers un message imprimé.
	printf("Saisissez, \n'c' ou 'C' pour celibataire,\n'm' ou 'M' pour marie,\n's' ou 'S' pour separe,\n'v' ou 'V' pour veuf.\nPuis appuyez sur la touche (ENTER):\n");
	//Demande au programme d'entreproser un charachtere comme variable statut.
	scanf(" %c",&statut);
	
	//Demande à l'utilisateur de saisir l'age à travers un message imprimé.
	printf("Saisissez l'age, \npuis appuyez sur la touche (ENTER):\n");
	//Demande au programme d'entreproser un entier comme variable age.
	scanf("%d", &age);
	
	//Etape de validation pour variable statut.
	//Demande au programme de retourner une valeur selon ce que l'utilisateur a selectionner.
	switch(statut)
	{
		//Retourne ce message si utilisateur a selectionner "c" minuscule ou "C" majuscule.
		case 'C':
		case 'c':
			printf("C'est une personne celibataire");
		    break;
		//Retourne ce message si utilisateur a selectionner "m" minuscule ou "M" majuscule.
		case 'M':
		case 'm':
		    printf("C'est une personne mariee");
		    break; 
		//Retourne ce message si utilisateur a selectionner "s" minuscule ou "S" majuscule.    
		case 'S':
		case 's':
		    printf("C'est une personne separee");
		    break; 
		//Retourne ce message si utilisateur a selectionner "v" minuscule ou "V" majuscule.   
		case 'V':
		case 'v':
			printf("C'est une personne veuve");
			break;	
		//Retourne le message suivant si aucune de valeurs plus haut ont été saisis.
		default:
		    printf("C'est une personne dont le statut est indetermine");
			break;	
	}
	// Validation de la variable age.
	//Si l'age est a l'exterieur de 0 et 129, le message suivant est renvoyé.
	if(age < 0 || age > 129)
	{
        printf(" dont l'age est errone. ");
	}
	//autres conditions
	else
	{
		//Si age est moins de 18, renvoi le message pour mineurs.
	    if(age<18)
	    {
	        printf(" et qui est de l'age mineur.");
	    }
	    //Si l'age est plus petit ou egale a 65, renvoi le message pour adulte.
	    else if( age<=65 )
	    {
	       	printf(" et qui est de l'age adulte.");
		}
		//Tout autre age, renvoi le message d'age d'or.
		else
		{
			printf(" et qui est de l'age d'or.");
		}
	}
	return 0;
}

//Test 1 (s 29)

/*
Saisissez,
'c' ou 'C' pour celibataire,
'm' ou 'M' pour marie,
's' ou 'S' pour separe,
'v' ou 'V' pour veuf.
Puis appuyez sur la touche (ENTER):
s
Saisissez l'age,
puis appuyez sur la touche (ENTER):
29
C'est une personne separee et qui est de l'age adulte.
--------------------------------
Process exited after 5.106 seconds with return value 0
Appuyez sur une touche pour continuer...
*/

//Test 2 (V 145)

/*
Saisissez,
'c' ou 'C' pour celibataire,
'm' ou 'M' pour marie,
's' ou 'S' pour separe,
'v' ou 'V' pour veuf.
Puis appuyez sur la touche (ENTER):
V
Saisissez l'age,
puis appuyez sur la touche (ENTER):
145
C'est une personne veuve dont l'age est errone.
--------------------------------
Process exited after 9.097 seconds with return value 0
Appuyez sur une touche pour continuer...
*/

//Test 3 (z 32)

/*
Saisissez,
'c' ou 'C' pour celibataire,
'm' ou 'M' pour marie,
's' ou 'S' pour separe,
'v' ou 'V' pour veuf.
Puis appuyez sur la touche (ENTER):
z
Saisissez l'age,
puis appuyez sur la touche (ENTER):
32
C'est une personne dont le statut est indetermine et qui est de l'age adulte.
--------------------------------
Process exited after 4.791 seconds with return value 0
Appuyez sur une touche pour continuer...
*/

//Test 4 (M 70)

/*
Saisissez,
'c' ou 'C' pour celibataire,
'm' ou 'M' pour marie,
's' ou 'S' pour separe,
'v' ou 'V' pour veuf.
Puis appuyez sur la touche (ENTER):
M
Saisissez l'age,
puis appuyez sur la touche (ENTER):
70
C'est une personne mariee et qui est de l'age d'or.
--------------------------------
Process exited after 11.36 seconds with return value 0
Appuyez sur une touche pour continuer...
*/
