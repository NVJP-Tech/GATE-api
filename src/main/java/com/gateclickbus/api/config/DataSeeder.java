package com.gateclickbus.api.config;

import com.gateclickbus.api.model.*;
import com.gateclickbus.api.model.enums.StatusTicket;
import com.gateclickbus.api.model.enums.StatusViagem;
import com.gateclickbus.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private final PassageiroRepository passageiroRepository;
    private final GateRepository gateRepository;
    private final PlataformaRepository plataformaRepository;
    private final ViagemRepository viagemRepository;
    private final TicketRepository ticketRepository;

    public DataSeeder(PassageiroRepository passageiroRepository,
                      GateRepository gateRepository,
                      PlataformaRepository plataformaRepository,
                      ViagemRepository viagemRepository,
                      TicketRepository ticketRepository) {
        this.passageiroRepository = passageiroRepository;
        this.gateRepository = gateRepository;
        this.plataformaRepository = plataformaRepository;
        this.viagemRepository = viagemRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void run(String... args) {
        if (passageiroRepository.count() > 0) {
            return; // ja tem dado, nao duplica ao reiniciar a aplicacao
        }

        Passageiro passageiro = passageiroRepository.save(
                new Passageiro("Pedro Mon", "123.456.789-00", "pedro@teste.com", "11999990000"));

        Gate gate = gateRepository.save(new Gate("Totem Portaria 1"));

        Plataforma plataformaA = plataformaRepository.save(new Plataforma("A", 40));

        Viagem viagemNoHorario = new Viagem(
                "São Paulo", "Rio de Janeiro",
                LocalDateTime.now().plusMinutes(20),
                "ABC1D23", 44, null);
        viagemNoHorario.setPlataforma(plataformaA);
        viagemRepository.save(viagemNoHorario);

        Viagem viagemAdiantada = new Viagem(
                "São Paulo", "Curitiba",
                LocalDateTime.now().plusMinutes(90),
                "XYZ9A88", 44, null);
        viagemAdiantada.setPlataforma(plataformaA);
        viagemRepository.save(viagemAdiantada);

        Viagem viagemProxima = new Viagem(
                "São Paulo", "Rio de Janeiro",
                LocalDateTime.now().plusHours(4),
                "DEF4G56", 44, null);
        viagemProxima.setPlataforma(plataformaA);
        viagemProxima.setStatus(StatusViagem.AGENDADA);
        viagemRepository.save(viagemProxima);

        Ticket ticketNoHorario = new Ticket(
                1001L, "QR-NO-HORARIO-001", passageiro, viagemNoHorario, "12A", LocalDateTime.now());
        ticketRepository.save(ticketNoHorario);

        Ticket ticketAdiantado = new Ticket(
                1002L, "QR-ADIANTADO-002", passageiro, viagemAdiantada, "07B", LocalDateTime.now());
        ticketRepository.save(ticketAdiantado);

        Viagem viagemAtrasada = new Viagem(
                "São Paulo", "Rio de Janeiro",
                LocalDateTime.now().minusMinutes(30),
                "HIJ7K12", 44, null);
        viagemAtrasada.setPlataforma(plataformaA);
        viagemRepository.save(viagemAtrasada);

        Ticket ticketAtrasado = new Ticket(
                1003L, "QR-ATRASADO-003", passageiro, viagemAtrasada, "15C", LocalDateTime.now());
        ticketRepository.save(ticketAtrasado);

        System.out.println("Dados de teste carregados. Gate id=" + gate.getId());
    }
}
