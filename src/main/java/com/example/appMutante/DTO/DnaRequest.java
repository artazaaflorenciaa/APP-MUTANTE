package com.example.appMutante.DTO;
import com.example.appMutante.Validator.ValidDna;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DnaRequest {
    @NotNull
    @ValidDna // Validación personalizada
    @Schema(example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]")
    private String[] dna;
}