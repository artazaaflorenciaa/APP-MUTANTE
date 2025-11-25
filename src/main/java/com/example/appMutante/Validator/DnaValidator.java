package com.example.appMutante.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DnaValidator implements ConstraintValidator<ValidDna, String[]> {
    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length == 0) return false;
        int n = dna.length;
        for (String row : dna) {
            if (row == null || row.length() != n) return false; // NxN
            if (!row.matches("[ATCG]+")) return false; // Solo letras válidas
        }
        return true;
    }
}
