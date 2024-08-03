/*	
	Auteurs : Gueorgui Poklitar
	Matricule : 20251051
	But : Menu navigateur du logiciel de gestion de vols.
	Appel des méthodes et cosmetique du menu avec swing et awt. 
	Classe contenant le main().
	Travail Pratique 1
	Dernière Mise-à-jour : 01/06/2024
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GestionCompagnie {
    public static void main(String[] args) {
        //Demande le nom du fichier de la compagnie...
        String nomCompagnie = JOptionPane.showInputDialog("Entrez le nom de la compagnie (par exemple, Cie_Air_Relax):");

        if (nomCompagnie == null || nomCompagnie.trim().isEmpty()) {
            System.exit(0);
        }

        //Cosmetique pour affichage du nom de compagnie.
        String formattedNomCompagnie = nomCompagnie.toUpperCase().replace("_", " ");

        //Creation de compagnie pour lecture de fichier.
        Compagnie compagnie = new Compagnie(nomCompagnie);

        while (true) {
            //Panel pour le dialogue.
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JLabel titleLabel = new JLabel("GESTION DES VOLS", JLabel.CENTER);
            titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16));
            panel.add(titleLabel);
            panel.add(Box.createVerticalStrut(10)); //Espace cosmetique entre titre et menu
            JTextArea menuArea = new JTextArea("1. Liste des vols\n2. Ajout d'un vol\n3. Retrait d'un vol\n4. Modification de la date de départ\n5. Réservation d'un vol\n0. Terminer");
            menuArea.setEditable(false);
            menuArea.setOpaque(false);
            menuArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            panel.add(menuArea);
            panel.add(Box.createVerticalStrut(10)); //Espace cosmetique entre menu et label de choix.
            JLabel promptLabel = new JLabel("Faites votre choix:");
            promptLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            panel.add(promptLabel);
            JTextField choixField = new JTextField();
            panel.add(choixField);

            //Le pannel des boutons.
            JPanel buttonPanel = new JPanel();
            JButton okButton = new JButton("OK");
            JButton cancelButton = new JButton("Cancel");

            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            panel.add(buttonPanel);

            //Dialogue 
            final JDialog dialog = new JDialog((Frame) null, formattedNomCompagnie, true);
            dialog.setContentPane(panel);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setModal(true);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.getRootPane().setDefaultButton(okButton); //Bouton par default...

            // Ajout d'action du bouton "X".
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    choixField.setText("0");
                    dialog.dispose();
                }
            });

            //Ajout d'actions des boutons...
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    choixField.setText("0");
                    dialog.dispose();
                }
            });

            //Focus sur le champ d'entré pour usage facile.
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    choixField.requestFocusInWindow();
                }
            });

            // Fenetre visible.
            dialog.setVisible(true);

            int choix;
            try {
                choix = Integer.parseInt(choixField.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Choix invalide!");
                continue;
            }

            switch (choix) { //Appel des methodes du menu.
                case 1:
                    compagnie.listerVols();
                    break;
                case 2:
                    compagnie.insererVol();
                    break;
                case 3:
                    compagnie.retirerVol();
                    break;
                case 4:
                    compagnie.modifierDate();
                    break;
                case 5:
                    compagnie.reserverVol();
                    break;
                case 0:
                    compagnie.sauvegarderFichierObjets();
                    System.exit(0); //Fin du program.
                    break;
                default: // message d'option invalide.
                    JOptionPane.showMessageDialog(null, "Choix invalide!");
            }
        }
    }
}
