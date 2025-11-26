package com.example.appMutante.Service;

import com.example.appMutante.Entity.DnaRecord;
import com.example.appMutante.Exception.DnaHashCalculationException;
import com.example.appMutante.Repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final DnaRecordRepository repository;
    private final MutantDetector mutantDetector;

    public boolean analyzeDna(String[] dna) {
        // 1. Calcular el Hash del ADN
        String hash = calculateHash(dna);

        // 2. Verificar si ya existe en la Base de Datos (Cache)
        Optional<DnaRecord> existing = repository.findByDnaHash(hash);
        if (existing.isPresent()) {
            return existing.get().isMutant(); // Retorna resultado guardado (Optimización)
        }

        // 3. Si no existe, ejecutar el algoritmo de detección
        boolean isMutant = mutantDetector.isMutant(dna);

        // 4. Guardar el nuevo registro
        DnaRecord record = DnaRecord.builder()
                .dnaHash(hash)
                .isMutant(isMutant)
                .build();

        repository.save(record);

        return isMutant;
    }

    private String calculateHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder sb = new StringBuilder();
            for (String s : dna) {
                sb.append(s);
            }
            byte[] encodedhash = digest.digest(sb.toString().getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            // Lanzamos nuestra excepción personalizada para que el GlobalExceptionHandler la capture
            throw new DnaHashCalculationException("Error calculando hash del ADN", e);
        }
    }
}