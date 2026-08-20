package com.gateclickbus.api.controller;

import com.gateclickbus.api.dto.response.StatusPlataformaResponse;
import com.gateclickbus.api.service.PlataformaStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plataformas")
public class PlataformaController {

    private final PlataformaStatusService plataformaStatusService;

    public PlataformaController(PlataformaStatusService plataformaStatusService) {
        this.plataformaStatusService = plataformaStatusService;
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<StatusPlataformaResponse> consultarStatus(@PathVariable Long id) {
        var plataforma = plataformaStatusService.consultarStatus(id);
        return ResponseEntity.ok(StatusPlataformaResponse.fromEntity(plataforma));
    }
}