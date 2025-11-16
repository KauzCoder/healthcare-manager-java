package br.com.sistemaPlanoSaude.service;

import br.com.sistemaPlanoSaude.model.Paciente;
import br.com.sistemaPlanoSaude.model.Medico;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações relacionadas a consultas.
 * Implementa lógica de agendamento, cancelamento e busca de consultas.
 */
public class ConsultaService {
    
    public static class Consulta {
        private int id;
        private Paciente paciente;
        private Medico medico;
        private LocalDateTime dataHoraConsulta;
        private String status; // AGENDADA, REALIZADA, CANCELADA
        private String descricao;
        private static int proximoId = 1;

        public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHoraConsulta, String descricao) {
            this.id = proximoId++;
            this.paciente = paciente;
            this.medico = medico;
            this.dataHoraConsulta = dataHoraConsulta;
            this.status = "AGENDADA";
            this.descricao = descricao;
        }

        // Getters e Setters
        public int getId() { return id; }
        public Paciente getPaciente() { return paciente; }
        public Medico getMedico() { return medico; }
        public LocalDateTime getDataHoraConsulta() { return dataHoraConsulta; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
    }

    private List<Consulta> consultas = new ArrayList<>();

    /**
     * Agendar uma nova consulta
     */
    public void agendarConsulta(Paciente paciente, Medico medico, LocalDateTime dataHoraConsulta, String descricao) {
        if (paciente == null || medico == null || dataHoraConsulta == null) {
            throw new IllegalArgumentException("Paciente, médico e data/hora não podem ser nulos.");
        }
        if (dataHoraConsulta.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data e hora da consulta não podem ser no passado.");
        }
        
        // Verificar se o médico não tem conflito de horário
        boolean temConflito = consultas.stream()
                .filter(c -> c.getMedico().equals(medico))
                .filter(c -> c.getStatus().equals("AGENDADA"))
                .anyMatch(c -> c.getDataHoraConsulta().equals(dataHoraConsulta));
        
        if (temConflito) {
            throw new IllegalArgumentException("Médico não está disponível neste horário.");
        }

        Consulta consulta = new Consulta(paciente, medico, dataHoraConsulta, descricao);
        consultas.add(consulta);
    }

    /**
     * Buscar consulta por ID
     */
    public Optional<Consulta> buscarConsultaPorId(int id) {
        return consultas.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    /**
     * Listar consultas de um paciente
     */
    public List<Consulta> listarConsultasPaciente(String cpfPaciente) {
        return consultas.stream()
                .filter(c -> c.getPaciente().getCpf().equals(cpfPaciente))
                .collect(Collectors.toList());
    }

    
    public void cancelarConsulta(int idConsulta) {
        Optional<Consulta> consulta = buscarConsultaPorId(idConsulta);
        if (consulta.isPresent()) {
            consulta.get().setStatus("CANCELADA");
        } else {
            throw new IllegalArgumentException("Consulta com ID " + idConsulta + " não encontrada.");
        }
    }

    /**
     * Marcar consulta como realizada
     */
    public void marcarComoRealizada(int idConsulta) {
        Optional<Consulta> consulta = buscarConsultaPorId(idConsulta);
        if (consulta.isPresent()) {
            consulta.get().setStatus("REALIZADA");
        } else {
            throw new IllegalArgumentException("Consulta com ID " + idConsulta + " não encontrada.");
        }
    }

    /**
     * Listar todas as consultas
     */
    public List<Consulta> listarTodasAsConsultas() {
        return new ArrayList<>(consultas);
    }

    /**
     * Contar total de consultas agendadas
     */
    public int contarConsultasAgendadas() {
        return (int) consultas.stream()
                .filter(c -> c.getStatus().equals("AGENDADA"))
                .count();
    }
}
