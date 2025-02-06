package com.example.eventos.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventos.models.eventos;
import com.example.eventos.models.outputEvento;
import com.example.eventos.service.eventosService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(path = "/eventos")
public class eventoController {
    @Autowired
    eventosService service;

    @GetMapping
    public ResponseEntity<outputEvento> getAllEventos(){
        return service.getEventos();
    }

    @GetMapping("/{ID}")
    public ResponseEntity<outputEvento> getEventoById(@PathVariable("ID") Integer ID) {
        return service.getEventoById(ID);
    }
    
    @GetMapping("/Nombre/{name}")
    public ResponseEntity<outputEvento> getEventoByName(@PathVariable("name") String name) {
        return service.getEventosByName(name);
    }

    @PostMapping
    public ResponseEntity<outputEvento> getEventoByDate(@RequestParam String fechaIncio, @RequestParam String fechaFin) {
        return service.getEventosByDate(fechaIncio, fechaFin);
    }
    
    @PutMapping()
    public ResponseEntity<outputEvento> updateOrCreate (@RequestBody eventos event) {
        return service.updateOrCreateEvento(event);
    }

    @DeleteMapping("/{ID}")
    public ResponseEntity<outputEvento> deleteByID (@PathVariable("ID") Integer ID) {
        return service.deleteEvento(ID);
    }


}
