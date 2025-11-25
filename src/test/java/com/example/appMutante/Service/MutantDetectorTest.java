package com.example.appMutante;

import com.example.appMutante.Service.MutantDetector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    @Test
    void testMutantHorizontal() {
        String[] dna = {"AAAA", "CAGT", "CAGT", "CAGT"};
        assertTrue(detector.isMutant(dna)); // Hay AAAA horizontal y verticales
    }

    @Test
    void testMutantVertical() {
        String[] dna = {"ATGC", "ATGC", "ATGC", "ATGC"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testHuman() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};
        assertFalse(detector.isMutant(dna));
    }

    // Agrega más casos de borde (Diagonal, Matriz vacía, etc.)
}