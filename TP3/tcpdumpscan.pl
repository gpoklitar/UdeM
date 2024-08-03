#!/usr/bin/perl

use strict;
use warnings;

# Initialisation des structures d'entreposage.
my %protocoles;
my %destinations;
my %uids_nfs;
my %reponses_arp;
my $temps_debut;
my $temps_fin;

# glob pour trouver le fichier tcpdump.* (peut trouver .txt, .log, etc.)
my @fichiers = glob 'tcpdump.*';
die "Fichier tcpdump n'est pas trouvé" unless @fichiers;

# Lecture du fichier tcpdump...
foreach my $fichier (@fichiers) {
    open(my $fh, '<', $fichier) or die "Impossible d'ouvrir $fichier: $!";

    while (my $ligne = <$fh>) {
        if ($ligne =~ /^\s*\d+\s+(\S+)\s+(\S+)\s+(\S+)\s+(\S+)\s+(\S+)\s+(.*)$/) {
            my $temps = $1;
            my $source = $2;
            my $destination = $3;
            my $protocole = $4;
            my $info = $6;

            # Temps de début et de fin
            $temps_debut = $temps if not defined $temps_debut;
            $temps_fin = $temps;

            # Comptage des protocoles
            $protocoles{$protocole}++;

            # Comptage des destinations
            $destinations{$destination}++;

            # Extraction des UIDs pour le protocole NFS
            if ($protocole eq 'NFS' && $info =~ /uid:(\d+)/) {
                $uids_nfs{$1}++;
            }

            # Capture des réponses ARP
            if ($protocole eq 'ARP' && $ligne =~ /ARP\s+(\d+\.\d+\.\d+\.\d+)\s+is\s+at\s+(\S+)/) {
                $reponses_arp{"$1 -> $2"} = 1;
            }
        }
    }

    close($fh);
}

# Exportation des rapports.
generate_report('destinations.txt', \%destinations);
generate_report('proto.txt', \%protocoles);
generate_report('nfsuid.txt', \%uids_nfs);

# Impression des réponses ARP et la durée de temps.
my $total = $temps_fin - $temps_debut;
print "Réponses ARP:\n";
foreach my $reponse (sort{ lc($a) cmp lc($b) } keys %reponses_arp) {
    print "$reponse\n";
}
print "\nTemps de début et de fin: $temps_debut à $temps_fin / Total de $total secondes\n";

sub generate_report {
    my ($fichier, $donnees_ref) = @_;
    open(my $fh, '>', $fichier) or die "Impossible d'ouvrir $fichier: $!";
    
    foreach my $cle (sort { custom_sort($a, $b) } keys %{$donnees_ref}) {
        print $fh "$cle: $donnees_ref->{$cle}\n";
    }

    close($fh);
}

sub custom_sort {
    my ($a, $b) = @_;

    my %special_cases = (
	'broadcast' => 1,
	'spanning-tree-(for-bridges)_00' => 1,
    );

    my $a_special = exists $special_cases{lc($a)};
    my $b_special = exists $special_cases{lc($b)};

    if ($a_special == $b_special) {
	return lc($a) cmp lc($b);
    }

    return $a_special ? 1 : -1;
}


