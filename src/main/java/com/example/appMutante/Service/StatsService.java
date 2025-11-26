package com.example.appMutante.Service;

import com.example.appMutante.DTO.StatsResponse;
import com.example.appMutante.Repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository repository;

    public StatsResponse getStats() {
        long countMutant = repository.countByIsMutant(true);
        long countHuman = repository.countByIsMutant(false);

        double ratio = countHuman == 0 ? 0 : (double) countMutant / countHuman;

        return new StatsResponse(countMutant, countHuman, ratio);
    }
}