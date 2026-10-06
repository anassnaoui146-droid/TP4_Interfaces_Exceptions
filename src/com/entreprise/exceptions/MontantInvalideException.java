package com.entreprise.exceptions;

public class MontantInvalideException extends Exception {
    private double valeur;

    public MontantInvalideException(String champ, double valeur) {
        super(champ + " invalide : " + valeur);
        this.valeur = valeur;
    }

    public double getValeur() {
        return valeur;
    }
}
