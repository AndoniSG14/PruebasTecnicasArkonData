package com.example.eventos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor  
public class infoBoletos {
    private String nombre;
    private String NumBoleto;
    private String fechaincio;
    private String fechafin;
    private String BoletosDisponibles;
    private String BoletosVendidos;
    private String BoletosCanjeado;
    private String canjeado;
    private String fechacanjeado;

}
