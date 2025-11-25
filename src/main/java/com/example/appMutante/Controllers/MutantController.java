package com.example.appMutante.Controllers;

import com.example.appMutante.DTO.DnaRequest;
import com.example.appMutante.DTO.StatsResponse;
import com.example.appMutante.Service.MutantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "Operaciones principales")
public class MutantController {

    private final MutantService mutantService;

    @Operation(summary = "Detectar mutante", description = "Retorna 200 si es mutante, 403 si es humano")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Es Mutante"),
            @ApiResponse(responseCode = "403", description = "Es Humano"),
            @ApiResponse(responseCode = "400", description = "ADN Inválido")
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.analyzeDna(request.getDna());
        return isMutant ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Estadísticas", description = "Devuelve ratio de mutantes vs humanos")
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(mutantService.getStats());
    }
}

