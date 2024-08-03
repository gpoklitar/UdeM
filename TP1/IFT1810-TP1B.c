/*
TP1-B
Chargé de cours : Marcel Côté (cours théorique du mardi : groupe : A)
Remis par: Gueorgui Poklitar
Matricule: 20251051
Coordonnees: gueorgui.poklitar@umontreal.ca
*/

//Debut standard du programme.
#include<stdio.h>
int main()
{
	/*Ici on declare 3 constante de nature float (reel), 
	puis on definit la ponderation fix pour chaque constante.
	Cette ponderation restera la meme tout au long du programme.*/
	const float pondFinal = 0.35;
	const float pondIntra = 0.25;
    const float pondTP = 0.40;
    
    //Definition des variables qui figurerons dans le programme.
	float final;
	float intra;
	float noteTP;
	float moyPond;
	float globale;
	
	//On demande a l'utilisateur de saisir la note de l'intra.
	//Cette demande est fait a travers la diffusion d'un text par la commande printf().
	printf("Saisissez la note obtenu lors de votre examen intra (note sur 100):\n");
	/*On demande au programme de detecter un reel, 
	entree par l'utilisateur,puis de l'entreposer comme variable "intra".*/
	//Il s'agit de la note intra.
	scanf("%f",&intra);
	
	//On demande a l'utilisateur de saisir la note de l'final.
	/*On demande au programme de detecter un reel, 
	entree par l'utilisateur,puis de l'entreposer comme variable "final".*/
	//Il s'agit de la note final.		
	printf("Saisissez la note obtenu lors de votre examen final (note sur 100):\n");
	scanf("%f",&final);
	
	//On demande a l'utilisateur de saisir la note des TPs.
	/*On demande au programme de detecter un reel, 
	entree par l'utilisateur,puis de l'entreposer comme variable "noteTP".*/
	//Il s'agit de la note final.
	printf("Saisissez la note obtenu lors de vos TPs (note sur 100):\n");
	scanf("%f",&noteTP);
	
	/*On demande de calculer et definir la variable moyPond avec cette formule 
	et les donnees saisis par l'utilisateur.*/
	moyPond=(final*pondFinal + intra*pondIntra)/0.60;
	
    /*Si la moyenne pondere(intra et final ensemble) est plus petite que 40% , et la note du TP est plus grande que 50% ,
    la note des TPs est rebalancer a 50% .*/
	if (moyPond < 40 && noteTP > 50) {
        noteTP = 50;
    }
	
	//Le programme calcule et defini la note globale qui sera utiliser plus tard.
	globale=(final*pondFinal + intra*pondIntra + noteTP*pondTP);
	
	//Les resultats sont diffusees comme reel, en diffusent 2 chiffres apres la decimale.
	printf("\nLes resultats dont vous avez saisi se traduisent de la facon suivante, \nselon les exigences de ponderations: \n%.2f (intra), %.2f (final), %.2f (TPs).\n\nVotre note globale pour ce cours est donc %.2f .\n",intra,final,noteTP,globale);
	//Puis on diffuse la note litteraire.
	printf("Votre note litteraire est donc: ");
	
	//La note litteraire est diffuser selon les perametre de cette section.
	//Un message print, diffuse la note litteraire equivalente.
	if(globale>=90)
        printf("A+");
    else if(globale>=85)
        printf("A");
    else if(globale>=80)
        printf("A-");
	else if(globale>=77)
        printf("B+");
    else if(globale>=73)
        printf("B");
    else if(globale>=70)
        printf("B-");        
	else if(globale>=65)
        printf("C+");
    else if(globale>=60)
        printf("C");
    else if(globale>=57)
        printf("C-");
	else if(globale>=54)
        printf("D+");
    else if(globale>=50)
        printf("D");
    else if(globale>=35)
        printf("E");         
    else
        printf("F");

//Fin standard du programme en C.
	return 0;
}

//Test 1

/*
Saisissez la note obtenu lors de votre examen intra (note sur 100):
80.00
Saisissez la note obtenu lors de votre examen final (note sur 100):
82.60
Saisissez la note obtenu lors de vos TPs (note sur 100):
94.60

Les resultats dont vous avez saisi se traduisent de la facon suivante,
selon les exigences de ponderations:
80.00 (intra), 82.60 (final), 94.60 (TPs).

Votre note globale pour ce cours est donc 86.75 .
Votre note litteraire est donc: A
--------------------------------
Process exited after 31.03 seconds with return value 0
Appuyez sur une touche pour continuer...
*/

//Test 2

/*
Saisissez la note obtenu lors de votre examen intra (note sur 100):
42.20
Saisissez la note obtenu lors de votre examen final (note sur 100):
25.90
Saisissez la note obtenu lors de vos TPs (note sur 100):
99.90

Les resultats dont vous avez saisi se traduisent de la facon suivante,
selon les exigences de ponderations:
42.20 (intra), 25.90 (final), 50.00 (TPs).

Votre note globale pour ce cours est donc 39.61 .
Votre note litteraire est donc: E
--------------------------------
Process exited after 18.4 seconds with return value 0
Appuyez sur une touche pour continuer...
*/

//Test 3

/*
Saisissez la note obtenu lors de votre examen intra (note sur 100):
38.20
Saisissez la note obtenu lors de votre examen final (note sur 100):
36.90
Saisissez la note obtenu lors de vos TPs (note sur 100):
32.60

Les resultats dont vous avez saisi se traduisent de la facon suivante,
selon les exigences de ponderations:
38.20 (intra), 36.90 (final), 32.60 (TPs).

Votre note globale pour ce cours est donc 35.51 .
Votre note litteraire est donc: E
--------------------------------
Process exited after 16.84 seconds with return value 0
Appuyez sur une touche pour continuer...
*/
