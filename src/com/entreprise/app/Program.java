package com.entreprise.app;

import com.entreprise.compta.Facture;
import com.entreprise.compta.Payable;
import com.entreprise.exceptions.EquipeCompleteException;
import com.entreprise.exceptions.MontantInvalideException;
import com.entreprise.rh.Augmentable;
import com.entreprise.rh.ChefProjet;
import com.entreprise.rh.Commercial;
import com.entreprise.rh.Employe;
import com.entreprise.rh.Permanent;

public class Program {
    public static void main(String[] args) {
        Service service = new Service(4);

        try {
            Commercial karim = new Commercial("Karim", 3000, 40000, "Rabat");
            service.ajouter(karim);
            ChefProjet salma = new ChefProjet("Salma", 6000, "Casablanca");
            service.ajouter(salma);
        } catch (MontantInvalideException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        String[] noms = {"Nadia", "Omar", "Yassine", "Leila", "Hamza"};
        String[] salaires = {"4500", "abc", "-800", "3800", "5200"};
        int tentatives = 0;

        for (int i = 0; i < noms.length; i++) {
            try {
                double salaire = Double.parseDouble(salaires[i]);
                Permanent p = new Permanent(noms[i], salaire);
                service.ajouter(p);
                System.out.println("Embauche de " + noms[i] + " : " + salaire);
            } catch (NumberFormatException e) {
                System.out.println("Saisie ignorée : \"" + salaires[i] + "\" n'est pas un montant");
            } catch (MontantInvalideException e) {
                System.out.println("Erreur : " + e.getMessage());
            } catch (EquipeCompleteException e) {
                System.out.println("Erreur : " + e.getMessage());
            } finally {
                tentatives++;
            }
        }

        System.out.println("Tentatives : " + tentatives + ", employés dans le service : " + service.getNb());

        service.trier();
        service.afficher();
        System.out.println("Masse salariale : " + service.getMasseSalariale());

        try {
            Employe moinsPaye = service.getEmploye(0);
            Augmentable a = (Augmentable) moinsPaye;
            a.augmenterStandard();
            System.out.println("Après augmentation : " + moinsPaye);
            a.augmenter(0.5);
            System.out.println("Cette ligne ne s'affiche jamais");
        } catch (MontantInvalideException e) {
            System.out.println("Erreur : " + e.getMessage() + " [valeur reçue : " + e.getValeur() + "]");
        }

        try {
            service.getEmploye(7);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        Payable[] paiements = {
            new Facture("F-102", "Bureau Plus", 450),
            service.getEmploye(1)
        };

        double total = 0;
        for (Payable p : paiements) {
            System.out.println("À payer : " + p);
            total += p.getMontantAPayer();
        }
        System.out.println("Total à payer : " + total);

        System.out.println("Nombre d'employés créés : " + Employe.getNbEmployes());

        // Omar et Yassine sont les deux employés fantômes.
        // Leur constructeur a déjà incrementé nbEmployes avant la validation du salaire.
        // Une exception a ensuite interrompu la création/ajout, donc ils ne sont pas dans le service.

        // ChefProjet est aussi augmentable sans rien écrire dans ChefProjet :
        // il hérite de Permanent, qui réalise déjà Augmentable.

        // EquipeCompleteException est non vérifiée car l'appelant peut vérifier getNb() avant d'ajouter.
        // MontantInvalideException est vérifiée car le montant doit être contrôlé et signalé.
    }
}
