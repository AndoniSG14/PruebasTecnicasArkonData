package com.example.eventos.models;

import java.util.List;
import lombok.Data;

@Data
public class outputEvento {

    private String Mensaje;

    private String Descripcion;

    private String Version = "1.0";

    private List<eventos> event;

}
