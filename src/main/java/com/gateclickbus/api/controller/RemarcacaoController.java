package com.gateclickbus.api.controller;

import com.gateclickbus.api.dto.request.ConcluirRemarcacaoRequest;
import com.gateclickbus.api.dto.response.RemarcacaoResponse;
import com.gateclickbus.api.service.RemarcacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remarcacoes")
public class RemarcacaoController {

    private final RemarcacaoService remarcacaoService;

    public RemarcacaoController(RemarcacaoService remarcacaoService) {
        this.remarcacaoService = remarcacaoService;
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<RemarcacaoResponse>> listarPendentes() {
        return ResponseEntity.ok(remarcacaoService.listarPendentes());
    }

    @PostMapping("/{id}/concluir")
    public ResponseEntity<RemarcacaoResponse> concluir(
            @PathVariable Long id,
            @Valid @RequestBody ConcluirRemarcacaoRequest request) {
        var remarcacao = remarcacaoService.concluirManualmente(id, request.getViagemNovaId());
        return ResponseEntity.ok(RemarcacaoResponse.fromEntity(remarcacao));
    }
}