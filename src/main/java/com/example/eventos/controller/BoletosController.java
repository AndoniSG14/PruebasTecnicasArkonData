package com.example.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventos.models.infoBoletos;
import com.example.eventos.models.outputBoleto;
import com.example.eventos.service.boletosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(path = "/boletos")
public class BoletosController {
    
    @Autowired
    boletosService service;

    @GetMapping("/{ID}")
    public ResponseEntity<outputBoleto> getBoletosByIdEvent(@PathVariable("ID") Integer ID) {
        return service.getCantidadBoletosPorIdEvento(ID);
    }

    @PutMapping("/{ID}")
    public ResponseEntity<outputBoleto> saveBoleto (@PathVariable("ID") Integer ID) {
        return service.saveBoletos(ID);
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<infoBoletos> getinforBoleto(@PathVariable("token") String token) {
        return service.inforBoleto(token);
    }

    @PatchMapping("/{token}")
    public ResponseEntity<outputBoleto> canejarBoleto(@PathVariable("token") String token) {
        return service.canjearBoleto(token);
    }
    
}
