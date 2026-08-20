package com.gateclickbus.api.repository;

import com.gateclickbus.api.model.Passageiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassageiroRepository  extends JpaRepository<Passageiro, Long> {
    Optional<Passageiro> findByCpf(String cpf);
}
