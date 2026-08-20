package com.gateclickbus.api.repository;

import com.gateclickbus.api.model.Viagem;
import com.gateclickbus.api.model.enums.StatusViagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {
    Optional<Viagem> findFirstByOrigemAndDestinoAndDataHoraPartidaAfterAndStatusNotOrderByDataHoraPartidaAsc(
            String origem,
            String destino,
            LocalDateTime apos,
            StatusViagem statusExcluido
    );
}
