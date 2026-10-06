package com.entreprise.exceptions;

public class EquipeCompleteException extends RuntimeException {
    public EquipeCompleteException(int capacite) {
        super("équipe complète (capacité : " + capacite + ")");
    }
}
