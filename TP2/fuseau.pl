#!/usr/bin/perl

# Utiliser les règles strictes et warnings pour facilité la programmation
use strict;
use warnings;

# Terminer le script si le nombre de paramètres n'est pas égal à 2
die "Nombre incorrecte de parametres\n" unless @ARGV == 2;

# Diviser le premier argument en heures et minutes
my ($heure, $minutes) = split /:/, $ARGV[0];
# Assigner le deuxième argument à la variable fuseau
my $fuseau = $ARGV[1];

# Terminer le script si l'heure n'est pas comprise entre 1 et 12
die "heure invalide\n" if $heure < 1 || $heure > 12;
# Terminer le script si les minutes ne sont pas comprises entre 0 et 59
die "minutes invalide\n" if $minutes < 0 || $minutes > 59;

# Définir un hash avec les décalages horaires pour chaque fuseau
my %fuseau_offsets = (
    PST => -3,
    MST => -2,
    CST => -1,
    AST => 1,
    NST => 1.5
);

# Terminer le script si le fuseau horaire fourni n'est pas valide
die "fuseau horaire invalide\n" unless exists $fuseau_offsets{$fuseau};

# Gérer le cas spécial du fuseau horaire NST
if ($fuseau eq 'NST') {
    $minutes += 30; # Ajouter 30 minutes pour NST
    if ($minutes > 59) {
        $minutes -= 60; # Soustraire 60 si les minutes dépassent 59
        $heure = ($heure + 2) % 12; # Ajouter 2 heures en conséquence
    } else {
        $heure = ($heure + 1) % 12; # Sinon, ajouter seulement 1 heure
    }
} else {
    # Ajouter le décalage horaire au nombre d'heures et utiliser le modulo pour rester dans (1,12)
    $heure = ($heure + $fuseau_offsets{$fuseau}) % 12;
}

# Si les heures sont à 0, les ramener à 12
$heure = 12 if $heure == 0; 
# Formater les minutes pour avoir toujours deux chiffres
$minutes = sprintf("%02d", $minutes); 

# Afficher l'heure finale
print "$heure:$minutes\n";

# Sortir du script sans erreur
exit 0; 
