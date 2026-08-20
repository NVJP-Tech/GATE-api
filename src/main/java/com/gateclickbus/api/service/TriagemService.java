package com.gateclickbus.api.service;

import com.gateclickbus.api.model.Plataforma;
import com.gateclickbus.api.model.enums.ResultadoTriagem;
import com.gateclickbus.api.model.enums.StatusOcupacao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class TriagemService {
    private static final int LIMITE_MINUTOS_ADIANTADO = 60;

    private record ContextoTriagem(int tempoRestanteMinutos, StatusOcupacao statusOcupacao) {
    }

    private record Regra(String descricao,
                         Predicate<ContextoTriagem> condicao,
                         Function<ContextoTriagem, ResultadoTriagem> decisao) {
    }

    private static final List<Regra> MATRIZ_DE_REGRAS = List.of(

            new Regra(
                    "Passageiro muito adiantado",
                    ctx -> ctx.tempoRestanteMinutos() > LIMITE_MINUTOS_ADIANTADO,
                    ctx -> ResultadoTriagem.AREA_DESCANSO
            ),

            new Regra(
                    "No horario mas plataforma cheia",
                    ctx -> ctx.tempoRestanteMinutos() <= LIMITE_MINUTOS_ADIANTADO
                            && ctx.statusOcupacao() == StatusOcupacao.CHEIA,
                    ctx -> ResultadoTriagem.AREA_DESCANSO
            ),

            new Regra(
                    "No horario e plataforma disponivel",
                    ctx -> ctx.tempoRestanteMinutos() <= LIMITE_MINUTOS_ADIANTADO
                            && ctx.statusOcupacao() != StatusOcupacao.CHEIA,
                    ctx -> ResultadoTriagem.EMBARQUE_IMEDIATO
            )
    );

    /**
     * Precondicao: tempoRestanteMinutos >= 0. O caso de atraso (< 0) e
     * tratado antes disso, pelo ValidacaoService, que aciona o
     * RemarcacaoService em vez de chamar este metodo.
     */
    public ResultadoTriagem decidir(int tempoRestanteMinutos, Plataforma plataforma) {
        ContextoTriagem contexto = new ContextoTriagem(tempoRestanteMinutos, plataforma.getStatusOcupacao());

        return MATRIZ_DE_REGRAS.stream()
                .filter(regra -> regra.condicao().test(contexto))
                .findFirst()
                .map(regra -> regra.decisao().apply(contexto))
                .orElse(ResultadoTriagem.AREA_DESCANSO); // fallback: situação não identificada
    }
}
