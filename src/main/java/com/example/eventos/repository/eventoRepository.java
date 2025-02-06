package com.example.eventos.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.eventos.models.eventos;

@Repository
public interface eventoRepository extends JpaRepository<eventos,Integer>{

    @Query(value = "SELECT * FROM tb_eventos WHERE nombre LIKE %:name%", nativeQuery = true)
    List<eventos> getEventosByName(@Param("name") String name);


    @Query(value = "SELECT * from tb_eventos WHERE fecha_incio BETWEEN :fechaInicial AND :fechaFinal", nativeQuery = true)
    List<eventos> getEventosByDate(@Param("fechaInicial") String fechaInicial,@Param("fechaFinal") String fechaFinal);
}

