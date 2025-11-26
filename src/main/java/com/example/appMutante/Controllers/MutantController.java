package com.example.appMutante.Controllers;

import com.example.appMutante.DTO.DnaRequest;
import com.example.appMutante.DTO.StatsResponse;
import com.example.appMutante.Service.MutantService;
import com.example.appMutante.Service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "Operaciones principales para la detección de mutantes")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService; // ✅ Inyección del nuevo servicio dedicado

    @Operation(summary = "Detectar mutante", description = "Analiza una secuencia de ADN para determinar si es mutante o humano.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es Mutante (Se encontró más de una secuencia)"),
            @ApiResponse(responseCode = "403", description = "Es Humano (No se encontraron suficientes secuencias)"),
            @ApiResponse(responseCode = "400", description = "ADN Inválido (Matriz no cuadrada, caracteres inválidos o null)")
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.analyzeDna(request.getDna());
        if (isMutant) {
            return ResponseEntity.ok().build(); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        }
    }

    @Operation(summary = "Obtener estadísticas", description = "Devuelve el conteo de mutantes, humanos y el ratio de verificación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente")
    })
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats()); // ✅ Delegación correcta a StatsService
    }
}