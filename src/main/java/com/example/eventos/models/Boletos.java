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
@Table(name = "tb_boletos")
public class Boletos {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer ID;

    private Integer IDEvento;
    private Integer NumeroBoleto;
    private String Token;
    private String Canjeado = "0";
    private String fechaVenta;
    private String fechaCanjeado;
    


}
