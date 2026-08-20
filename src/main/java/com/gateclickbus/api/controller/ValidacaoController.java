package com.gateclickbus.api.controller;

import com.gateclickbus.api.dto.request.ValidacaoRequest;
import com.gateclickbus.api.dto.response.ValidacaoResponse;
import com.gateclickbus.api.model.ValidacaoEmbarque;
import com.gateclickbus.api.service.ValidacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validacoes")
public class ValidacaoController {

    private final ValidacaoService validacaoService;

    public ValidacaoController(ValidacaoService validacaoService) {
        this.validacaoService = validacaoService;
    }

    @PostMapping
    public ResponseEntity<ValidacaoResponse> validar(@Valid @RequestBody ValidacaoRequest request) {
        ValidacaoEmbarque validacao = validacaoService.validar(
                request.getCodigoQr(),
                request.getCanalValidacao(),
                request.getGateId(),
                request.getLatitude(),
                request.getLongitude()
        );
        return ResponseEntity.ok(ValidacaoResponse.fromEntity(validacao));
    }
}