package com.example.eventos.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_eventos")
public class eventos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    
    private String nombre;
    private String fechaIncio;
    private String fechaFin;
    private Integer boletosMaximos;
    private Integer boletosVendidos;

}
