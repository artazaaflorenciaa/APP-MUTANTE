package com.example.appMutante.Service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {
    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        int n = dna.length;
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) matrix[i] = dna[i].toCharArray(); // Optimización char[][]

        int sequenceCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Horizontal
                if (j <= n - SEQUENCE_LENGTH && check(matrix, i, j, 0, 1)) {
                    if (++sequenceCount > 1) return true; // Early Termination
                }
                // Vertical
                if (i <= n - SEQUENCE_LENGTH && check(matrix, i, j, 1, 0)) {
                    if (++sequenceCount > 1) return true;
                }
                // Diagonal ↘
                if (i <= n - SEQUENCE_LENGTH && j <= n - SEQUENCE_LENGTH && check(matrix, i, j, 1, 1)) {
                    if (++sequenceCount > 1) return true;
                }
                // Diagonal ↙
                if (i <= n - SEQUENCE_LENGTH && j >= SEQUENCE_LENGTH - 1 && check(matrix, i, j, 1, -1)) {
                    if (++sequenceCount > 1) return true;
                }
            }
        }
        return false;
    }

    private boolean check(char[][] matrix, int row, int col, int dx, int dy) {
        char first = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row + i * dx][col + i * dy] != first) return false;
        }
        return true;
    }
}