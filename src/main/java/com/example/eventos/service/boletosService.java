package com.example.eventos.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Set;

import com.example.eventos.models.Boletos;
import com.example.eventos.models.infoBoletos;
import com.example.eventos.models.inventarioBolets;
import com.example.eventos.models.outputBoleto;
import com.example.eventos.repository.boletosRepository;

@Service
public class boletosService {

   @Autowired
   boletosRepository bolrepo;

   private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
   private static final int TOKEN_LENGTH = 16;
   private static final SecureRandom random = new SecureRandom();
   private static final Set<String> generatedTokens = new HashSet<>();

   public ResponseEntity<outputBoleto> getCantidadBoletosPorIdEvento(Integer ID) {
      outputBoleto output = new outputBoleto();
      Integer cantidadBoletos = bolrepo.getCantidadByIDEvento(ID);
      output.setMensaje("Operación Exitosa");
      output.setDescripcion("La cantidad de boletos vendidos es de: " + cantidadBoletos);
      return new ResponseEntity<>(output, HttpStatus.OK);
   }

   public ResponseEntity<outputBoleto> saveBoletos(Integer ID) {
      outputBoleto output = new outputBoleto();
      Boletos boletos = new Boletos();

      Optional<inventarioBolets> invBol = bolrepo.getInventarioBoletos(ID);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate fechaIncio = LocalDate.parse(invBol.get().getFechaFin(), formatter);
      
      if (LocalDate.now().isAfter(fechaIncio)) {
         output.setMensaje("Operación Fallida");
         output.setDescripcion("La fecha limite del evento fue el:" + invBol.get().getFechaFin());
         return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
      } else if ((invBol.get().getVendBoletos() + 1) > invBol.get().getMaxBoletos()) {
         output.setMensaje("Operación Fallida");
         output.setDescripcion("Ya no hay boletos diponibles para el evento " + invBol.get().getNombre());
         return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
      }
      boletos.setIDEvento(ID);
      boletos.setNumeroBoleto(invBol.get().getVendBoletos() + 1);
      boletos.setFechaVenta(LocalDate.now().toString());
      boletos.setToken(generateUniqueToken());
      bolrepo.save(boletos);
      output.setMensaje("Operación Exitosa");
      output.setDescripcion("Se vendio un boleto para el evento " + invBol.get().getNombre() + " con el numero: " + boletos.getNumeroBoleto() + " con el Token: " + boletos.getToken());
      bolrepo.sumarBoletoVendido(ID);
      return new ResponseEntity<>(output, HttpStatus.OK);
   }

   public static String generateUniqueToken() {
      String token;
      do {
         token = generateRandomToken();
      } while (generatedTokens.contains(token));

      generatedTokens.add(token);
      return token;
   }

   private static String generateRandomToken() {
      StringBuilder token = new StringBuilder(TOKEN_LENGTH);
      for (int i = 0; i < TOKEN_LENGTH; i++) {
         token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
      }
      return token.toString();
   }
   
   public ResponseEntity<infoBoletos> inforBoleto(String token){
      infoBoletos infBol =  new infoBoletos();
      Optional<infoBoletos> infoboletos = bolrepo.getInfoBoleto(token);
      System.err.println(infoboletos.get().getNombre());
      
      if (infoboletos.get().getNombre() == null) {
         return new ResponseEntity<>(infBol, HttpStatus.NO_CONTENT);      
      }else{
         infBol.setNombre(infoboletos.get().getNombre());
         infBol.setNumBoleto(infoboletos.get().getNumBoleto());
         infBol.setFechaincio(infoboletos.get().getFechaincio());
         infBol.setFechafin(infoboletos.get().getFechafin());
         infBol.setBoletosDisponibles(infoboletos.get().getBoletosDisponibles());
         infBol.setBoletosVendidos(infoboletos.get().getBoletosVendidos());
         infBol.setBoletosCanjeado(infoboletos.get().getBoletosCanjeado());
         infBol.setCanjeado(infoboletos.get().getCanjeado());
         infBol.setFechacanjeado(infoboletos.get().getFechacanjeado());
      }
      return new ResponseEntity<>(infBol, HttpStatus.OK);
   }

   public ResponseEntity<outputBoleto> canjearBoleto(String token){
      outputBoleto output = new outputBoleto();
      Optional<infoBoletos> infoboletos = bolrepo.getInfoBoleto(token);
      if (!infoboletos.isPresent()) {
         output.setMensaje("Operación Fallida");
         output.setDescripcion("El token no existe");
         return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);      
      }else{
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         LocalDate fechaIncio = LocalDate.parse(infoboletos.get().getFechaincio(), formatter);
         LocalDate fechafin = LocalDate.parse(infoboletos.get().getFechafin(), formatter);
         if (infoboletos.get().getCanjeado().equals("1")) {
            output.setMensaje("Operación Fallida");
            output.setDescripcion("El " +  infoboletos.get().getNumBoleto()+ " Con el Token: " +  token + " Fue canjeado el: " +  infoboletos.get().getFechacanjeado());
            return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST); 
         }
         if (fechaIncio.isAfter(LocalDate.now())) {
            output.setMensaje("Operación Fallida");
            output.setDescripcion("El evento inicia hasta el "+ fechaIncio);
            return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST); 
         }else if (LocalDate.now().isAfter(fechafin)) {
            output.setMensaje("Operación Fallida");
            output.setDescripcion("El evento termino el "+ fechafin);
            return new ResponseEntity<>(output, HttpStatus.BAD_REQUEST);
         }

         bolrepo.canjearBoleto(token, LocalDate.now().toString());
         output.setMensaje("Operación Exitosa");
            output.setDescripcion("El " + infoboletos.get().getNumBoleto() + " ha sido canjeado ¡DISFRUTA EL EVENTO!");
      }
      return new ResponseEntity<>(output, HttpStatus.OK);
   }
   
}
