CREATE DATABASE IF NOT EXISTS bdvols
DEFAULT CHARACTER SET utf8
COLLATE utf8_unicode_ci;
USE bdvols;

CREATE TABLE IF NOT EXISTS flotte (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modele VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    type VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    capacity INT DEFAULT 350
) DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS vols (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    numero INT,
    reservations INT,
    destination VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
    avion_id INT,
    FOREIGN KEY (avion_id) REFERENCES flotte(id)
) DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

INSERT INTO flotte (modele, type, capacity) VALUES
('Boeing', '787', 350),
('Airbus', 'A380', 380),
('Boeing', '747', 400),
('Airbus', 'A320', 180),
('Boeing', '737', 200),
('Airbus', 'A350', 300),
('Bell', '525', 18),
('Lockheed Martin', '382J', 4),
('Airbus', 'A220-100', 135);

INSERT INTO vols (date, numero, reservations, destination, avion_id) VALUES
('2024-07-14', 23457, 350, 'London', 1),
('2024-07-14', 78912, 320, 'Hong Kong', 2),
('2024-07-14', 17890, 340, 'Guadeloupe', 3),
('2024-07-14', 23458, 202, 'Madrid', 5),
('2024-07-14', 19012, 250, 'Nouvelle-Orleans', 6),
('2024-07-15', 28901, 350, 'Las Vegas', 2),
('2024-07-15', 14567, 167, 'Toronto', 4),
('2024-07-15', 34567, 350, 'Dubai', 1),
('2024-07-15', 34568, 87, 'Los Angeles', 5),
('2024-07-15', 22345, 337, 'Martinique', 3),
('2024-07-16', 90124, 350, 'Bangkok', 2),
('2024-07-16', 45678, 152, 'Les Iles Baleares', 4),
('2024-07-16', 45679, 350, 'Rome', 3),
('2024-07-16', 53456, 327, 'San Francisco', 1),
('2024-07-16', 45680, 300, 'Lisbon', 6),
('2024-07-17', 67891, 60, 'Amsterdam', 5),
('2024-07-17', 67890, 289, 'Sydney', 6),
('2024-07-17', 89013, 220, 'Singapore', 3),
('2024-07-17', 36789, 67, 'New-York', 1),
('2024-07-17', 56789, 135, 'Berlin', 9),
('2024-07-18', 67892, 275, 'Mexico City', 3),
('2024-07-18', 89014, 132, 'Buenos Aires', 1),
('2024-07-18', 12345, 145, 'Tokyo', 5),
('2024-07-18', 67834, 244, 'Paris', 2);
