package com.gateclickbus.api.service;

import com.gateclickbus.api.exception.PlataformaNaoEncontradaException;
import com.gateclickbus.api.model.Plataforma;
import com.gateclickbus.api.model.enums.StatusOcupacao;
import com.gateclickbus.api.repository.PlataformaRepository;
import org.springframework.transaction.annotation.Transactional;import org.springframework.stereotype.Service;

@Service
public class PlataformaStatusService {
    private static final double LIMITE_CHEIA = 0.8;
    private static final double LIMITE_NORMAL = 0.3;

    private final PlataformaRepository plataformaRepository;

    public PlataformaStatusService(PlataformaRepository plataformaRepository) {
        this.plataformaRepository = plataformaRepository;
    }

    @Transactional
    public void registrarSaidaDaPlataforma(Long plataformaId) {
        plataformaRepository.decrementarOcupacao(plataformaId);
        recalcularStatus(plataformaId);
    }

    @Transactional(readOnly = true)
    public Plataforma consultarStatus(Long plataformaId) {
        return buscarOuFalhar(plataformaId);
    }

    private void recalcularStatus(Long plataformaId) {
        Plataforma plataforma = buscarOuFalhar(plataformaId);
        StatusOcupacao statusRecalculado = calcularStatus(
                plataforma.getOcupacaoAtual(), plataforma.getCapacidadeMaxima());
        plataforma.setStatusOcupacao(statusRecalculado);
        // sem save() explicito: dirty checking persiste a mudanca ao fim da transacao
    }

    private StatusOcupacao calcularStatus(int ocupacaoAtual, int capacidadeMaxima) {
        if (capacidadeMaxima <= 0) {
            return StatusOcupacao.VAZIA;
        }
        double percentualOcupado = (double) ocupacaoAtual / capacidadeMaxima;

        if (percentualOcupado >= LIMITE_CHEIA) {
            return StatusOcupacao.CHEIA;
        }
        if (percentualOcupado >= LIMITE_NORMAL) {
            return StatusOcupacao.NORMAL;
        }
        return StatusOcupacao.VAZIA;
    }

    private Plataforma buscarOuFalhar(Long plataformaId) {
        return plataformaRepository.findById(plataformaId)
                .orElseThrow(() -> new PlataformaNaoEncontradaException(plataformaId));
    }

}
