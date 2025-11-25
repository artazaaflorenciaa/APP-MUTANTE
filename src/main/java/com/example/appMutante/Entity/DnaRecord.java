package com.example.appMutante.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "dna_records")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DnaRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dnaHash; // El hash SHA-256

    private boolean isMutant;
}