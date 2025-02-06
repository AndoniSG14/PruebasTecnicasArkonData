package com.example.eventos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor  
public class inventarioBolets {

    private Integer maxBoletos;
    private Integer vendBoletos;
    private String nombre;
    private String fechaFin;
}
