package com.gateclickbus.api.repository;

import com.gateclickbus.api.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GateRepository extends JpaRepository<Gate, Long> {
}
