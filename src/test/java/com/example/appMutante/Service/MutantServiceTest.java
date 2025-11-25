package com.example.appMutante.Service;

import com.example.appMutante.Entity.DnaRecord;
import com.example.appMutante.Repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @Mock
    private MutantDetector detector;

    @InjectMocks
    private MutantService service;

    @Test
    void testAnalyzeDna_NewMutant_ShouldSave() {
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};

        // Simular que NO existe en BD
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        // Simular que el detector dice que es mutante
        when(detector.isMutant(dna)).thenReturn(true);

        boolean result = service.analyzeDna(dna);

        assertTrue(result);
        // Verificar que se llamó al detector y se guardó en BD
        verify(detector, times(1)).isMutant(dna);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeDna_ExistingInDb_ShouldNotCallDetector() {
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};
        DnaRecord existingRecord = new DnaRecord(1L, "hash123", true);

        // Simular que YA existe en BD
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(existingRecord));

        boolean result = service.analyzeDna(dna);

        assertTrue(result);
        // Verificar que NO se llamó al detector (ahorro de recursos)
        verify(detector, never()).isMutant(dna);
        verify(repository, never()).save(any(DnaRecord.class));
    }
}
