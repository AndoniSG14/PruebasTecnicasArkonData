package com.example.eventos.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.eventos.models.eventos;
import com.example.eventos.models.outputEvento;
import com.example.eventos.repository.eventoRepository;

@Service
public class eventosService {
    

    @Autowired
    eventoRepository eventRepo;
   
    

    public  ResponseEntity<outputEvento> getEventos(){
        outputEvento output = new outputEvento();
        output.setMensaje("Operación Exitosa");
        output.setDescripcion("Estos son los eventos Registrados");
        output.setEvent(eventRepo.findAll());

        if (output.getEvent().size()<1) {
            return new ResponseEntity<>(output, HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    public ResponseEntity<outputEvento> getEventoById(Integer ID){
        outputEvento output = new outputEvento();
        output.setMensaje("Operación Exitosa");
        output.setDescripcion("Estos son los eventos Registrados");
        List<eventos> listEvento = new ArrayList();
        listEvento.add(eventRepo.findById(ID).orElse(new eventos()));
        if (!eventRepo.existsById(ID)) {
            return new ResponseEntity<>(output, HttpStatus.NO_CONTENT);
        }
        output.setEvent(listEvento);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    public ResponseEntity<outputEvento> getEventosByName(String name){
        outputEvento output = new outputEvento();
        output.setMensaje("Operación Exitosa");
        output.setDescripcion("Estos son los eventos que se encontraron con un nombre similar a: " + name);
        output.setEvent(eventRepo.getEventosByName(name));

        if (output.getEvent().size()< 1) {
            output.setDescripcion("No se encontraron eventos con nombre similar a: " + name);
            return new ResponseEntity<>(output, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    public ResponseEntity<outputEvento> getEventosByDate(String fechaIncioStr, String fechaFinStr){
        outputEvento output = new outputEvento();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaIncio = LocalDate.parse(fechaIncioStr, formatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);
        if (fechaIncio.isAfter(fechaFin)){
            output.setMensaje("Operación fallida");
            output.setDescripcion("La fecha inicial no puede ser mayor a la fecha final");
            return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
        }
        output.setMensaje("Operación Exitosa");
        output.setEvent(eventRepo.getEventosByDate(fechaIncioStr,fechaFinStr));
        output.setMensaje("Estos son los eventos encontrados en el rango de fechas");

        if (output.getEvent().size()<1){
            output.setMensaje("No se han encontrado eventos en el rango de fechas");
            return new ResponseEntity<>(output, HttpStatus.NO_CONTENT);
        } 

        return new ResponseEntity<>(output, HttpStatus.OK);
        
    }

    public ResponseEntity<outputEvento> updateOrCreateEvento(eventos event){
        outputEvento output = new outputEvento();
        output.setMensaje("Operación Exitosa");
        output.setDescripcion("Se registro el evento " + event.getNombre() +" Con fecha incial del : " + event.getFechaIncio().toString() + " y fecha final: " + event.getFechaFin() + "Con la cantidad de: " +event.getBoletosMaximos()+ " boletos disponibles");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaIncio = LocalDate.parse(event.getFechaIncio(), formatter);
        LocalDate fechaFin = LocalDate.parse(event.getFechaFin(), formatter);
        if (fechaIncio.isAfter(fechaFin)) {
            output.setMensaje("Operación fallida");
            output.setDescripcion("La fecha inicial no puede ser mayor a la fecha final");
            return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
        }else if (LocalDate.now().isAfter(fechaIncio)) {
            output.setMensaje("Operación fallida");
            output.setDescripcion("La fecha inicial debe ser mayor a la fecha actual");
            return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
        }else if(event.getBoletosMaximos()< 1 || event.getBoletosMaximos() > 300){
            output.setMensaje("Operación fallida");
            output.setDescripcion("La cantidad mínima de boletos debe ser 1 y la cantidad máxima debe ser 300");
            return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
        }else if (event.getID() != null) {
            List<eventos> listEvento = new ArrayList();
            listEvento.add(eventRepo.findById(event.getID()).orElse(new eventos()));
            if (listEvento.get(0).getBoletosVendidos() > event.getBoletosMaximos()) {
                output.setMensaje("Operación fallida");
                output.setDescripcion("No se pueden actualizar la cantidad maxima de boletos a: " + event.getBoletosMaximos() + " Debido a que se han vendido: " + listEvento.get(0).getBoletosVendidos());
                return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
            }
            event.setBoletosVendidos(listEvento.get(0).getBoletosVendidos());
        }else{
            event.setBoletosVendidos(0);
        }
        
        eventRepo.save(event);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    public ResponseEntity<outputEvento> deleteEvento(Integer ID){
        outputEvento output = new outputEvento();
        List<eventos> listEvento = new ArrayList();
        
        listEvento.add(eventRepo.findById(ID).orElse(new eventos()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaIncio = LocalDate.parse(listEvento.get(0).getFechaIncio(), formatter);
        
        if (!eventRepo.existsById(ID)) {
            output.setMensaje("Operación Fallida");
            output.setDescripcion("No hay ningun Evento registrado con el ID: " + ID);
            return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
        }else if (listEvento.get(0).getBoletosVendidos()>0) {
            output.setMensaje("Operación Fallida");
            output.setDescripcion("Ya hay boletos vendidos para el evento: " + listEvento.get(0).getNombre());
            return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
        }else if (fechaIncio.isAfter(LocalDate.now())) {
            output.setMensaje("Operación Fallida");
            output.setDescripcion("La fecha de incio del evento es el dia " + listEvento.get(0).getFechaIncio() + " por lo cual no puede ser borrado");
            return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
        }

        eventRepo.deleteById(ID);
        output.setMensaje("Operación Exitosa");
        output.setDescripcion("Se ha eliminado el Evento: " + listEvento.get(0).getNombre());
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
    



}
