/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Definition de classe Vol, constructeurs, getter/setter,
	Definition de 350 reservations max par vol. toString et compareTo.
	Travail Pratique 1
	Dernière Mise-à-jour : 01/06/2024
*/

import java.io.Serializable;

public class Vol implements Comparable<Vol>, Serializable {
    private int numeroVol;
    private String destination;
    private Date dateDepart;
    private int numeroAvion;
    private int totalReservations;
    private final int maxReservations = 350;

    // Constructeur du vol
    public Vol(int numeroVol, String destination, Date dateDepart, int numeroAvion, int totalReservations) {
        this.numeroVol = numeroVol;
        this.destination = destination;
        this.dateDepart = dateDepart;
        this.numeroAvion = numeroAvion;
        this.totalReservations = totalReservations;
    }

    // Les getters
    public int getNumeroVol() {
        return numeroVol;
    }
    public String getDestination() {
        return destination;
    }
    public Date getDateDepart() {
        return dateDepart;
    }
    public int getNumeroAvion() {
        return numeroAvion;
    }
    public int getTotalReservations() {
        return totalReservations;
    }
    public int getMaxReservations() {
        return maxReservations;
    }

    // Les setters
    public void setTotalReservations(int totalReservations) {
        this.totalReservations = totalReservations;
    }
    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    // Methode d'ajout de reservations
    public boolean addReservation(int number) {
        if (this.totalReservations + number <= maxReservations) {
            this.totalReservations += number;
            return true;
        } else {
            return false;
        }
    }

    // Methode de toString()
    @Override
    public String toString() {
        return numeroVol + "\t" + destination + "\t" + dateDepart + "\t" + numeroAvion + "\t" + totalReservations;
    }

    // Methode de triage avec compareTo()
    @Override
    public int compareTo(Vol autre) {
        return Integer.compare(this.numeroVol, autre.numeroVol);
    }
}
