package com.entreprise.compta;

public class Facture implements Payable {
    private String numero;
    private String fournisseur;
    private double montant;

    public Facture(String numero, String fournisseur, double montant) {
        this.numero = numero;
        this.fournisseur = fournisseur;
        this.montant = montant;
    }

    @Override
    public double getMontantAPayer() {
        return montant;
    }

    @Override
    public String toString() {
        return "Facture " + numero + " (" + fournisseur + ") : " + montant;
    }
}
