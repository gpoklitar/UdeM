/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Definition de classe Vol, constructeurs, getter/setter,
	Definition de 350 reservations max par vol. toString et compareTo et la gestion des réservations.
	Travail Pratique 2
	Dernière Mise-à-jour : 23/06/2024
*/

public class Vol {
    private int numero;
    private String destination;
    private Date date;
    private Avion avion;
    private int reservations;
    private static final int SEAT_CAPACITY = 350;

    //Constructeur de vol.
    public Vol(int numero, String destination, Date date, Avion avion, int reservations) {
        this.numero = numero;
        this.destination = destination;
        this.date = date;
        this.avion = avion;
        this.reservations = reservations < 0 ? 0 : Math.min(reservations, SEAT_CAPACITY);
    }

    
    // Getters
    public int getNumero() {
        return numero;
    }
    public String getDestination() {
        return destination;
    }
    public Date getDate() {
        return date;
    }
    public Avion getAvion() {
        return avion;
    }
    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    
    
    // Setters
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    
    // Gestion des réservations set, add (ajout) et les "get"
    public void setReservations(int reservations) {
        if (reservations < 0 || reservations > SEAT_CAPACITY) {
            throw new IllegalArgumentException("Le nombre de réservations doit être compris entre 0 et " + SEAT_CAPACITY + ".");
        }
        this.reservations = reservations;
    }
    public void addReservations(int additionalReservations) {
        if (reservations + additionalReservations > SEAT_CAPACITY || reservations + additionalReservations < 0) {
            throw new IllegalArgumentException("Le nombre de réservations doit être compris entre 0 et " + SEAT_CAPACITY + ".");
        }
        this.reservations += additionalReservations;
    }
    public int getReservations() {
        return reservations;
    }
    public int getSeatsAvailable() {
        return SEAT_CAPACITY - reservations;
    }    

}
