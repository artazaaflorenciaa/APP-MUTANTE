package com.example.appMutante.Service;

import com.example.appMutante.DTO.StatsResponse;
import com.example.appMutante.Entity.DnaRecord;
import com.example.appMutante.Repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final DnaRecordRepository repository;
    private final MutantDetector mutantDetector;

    public boolean analyzeDna(String[] dna) {
        String hash = calculateHash(dna);
        Optional<DnaRecord> existing = repository.findByDnaHash(hash);

        if (existing.isPresent()) return existing.get().isMutant();

        boolean isMutant = mutantDetector.isMutant(dna);
        repository.save(DnaRecord.builder().dnaHash(hash).isMutant(isMutant).build());

        return isMutant;
    }

    public StatsResponse getStats() {
        long mutants = repository.countByIsMutant(true);
        long humans = repository.countByIsMutant(false);
        double ratio = humans == 0 ? 0 : (double) mutants / humans;
        return new StatsResponse(mutants, humans, ratio);
    }

    private String calculateHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder sb = new StringBuilder();
            for (String s : dna) sb.append(s);
            byte[] encodedhash = digest.digest(sb.toString().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error calculando hash", e);
        }
    }
}