package com.gateclickbus.api.repository;

import com.gateclickbus.api.model.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {
    Optional<Plataforma> findByNumero(String numero);

    @Modifying
    @Query("UPDATE Plataforma p SET p.ocupacaoAtual = p.ocupacaoAtual + 1 WHERE p.id = :id")
    int incrementarOcupacao(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Plataforma p SET p.ocupacaoAtual = p.ocupacaoAtual - 1 WHERE p.id = :id AND p.ocupacaoAtual > 0")
    int decrementarOcupacao(@Param("id") Long id);
}
