package com.example.eventos.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.eventos.models.Boletos;
import com.example.eventos.models.infoBoletos;
import com.example.eventos.models.inventarioBolets;

import jakarta.transaction.Transactional;

@Repository
public interface boletosRepository extends JpaRepository<Boletos,Integer>{

    @Query(value = "SELECT COUNT(*) FROM `tb_boletos` WHERE idevento = :ID", nativeQuery = true)
    Integer getCantidadByIDEvento(@Param("ID") Integer ID);

    @Query(value = "select te.boletos_maximos AS maxBoletos, te.boletos_vendidos as vendBoletos, te.nombre, te.fecha_fin AS fechaFin from tb_eventos te where te.id = :ID", nativeQuery = true)
    Optional<inventarioBolets> getInventarioBoletos(@Param("ID") Integer ID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_eventos te SET boletos_vendidos = te.boletos_vendidos + 1 WHERE id = :ID", nativeQuery = true)
    void sumarBoletoVendido(@Param("ID") Integer ID);


    @Query(value = "SELECT te.nombre,CONCAT('Boleto Numero: ', tb.numero_boleto) AS NumBoleto ,CAST(te.fecha_incio AS CHAR) AS fechaincio, CAST(te.fecha_fin AS CHAR) AS fechafin, CAST((te.boletos_maximos - te.boletos_vendidos) AS CHAR) AS BoletosDisponibles, CAST(te.boletos_vendidos AS CHAR) AS BoletosVendidos, CAST((SELECT COUNT(*) FROM tb_boletos WHERE tb.idevento = te.id AND canjeado = 1) AS CHAR) AS BoletosCanjeado,tb.canjeado,tb.fecha_canjeado as fechacanjeado FROM tb_boletos tb LEFT JOIN tb_eventos te ON te.id = tb.idevento WHERE tb.token = :token", nativeQuery = true)
    Optional<infoBoletos> getInfoBoleto(@Param("token") String token);

    @Modifying
    @Transactional
    @Query(value = "update tb_boletos te set canjeado = 1,fecha_canjeado = :fechaCanjeo where token = :token", nativeQuery = true)
    void canjearBoleto(@Param("token") String token,@Param("fechaCanjeo") String fechaCanjeo);

    

}
