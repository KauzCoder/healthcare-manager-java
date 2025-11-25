package br.com.sistemaPlanoSaude.model.consulta;

import java.time.LocalTime;
import java.time.LocalDate;

import br.com.sistemaPlanoSaude.model.enums.ConsultaStatus;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;

import java.util.Date;
import java.time.ZoneId;

public class Consulta {    

    private Paciente paciente;
    private Medico medico;
    private Horario horario;

    private int idConsulta;
    private LocalTime hora;
    private LocalDate data;
    private String descricao;
    private String receita;
    private String anotacoes;
    private ConsultaStatus status;

    public Consulta(Paciente paciente, Medico medico, Horario horario,
                    String descricao, String receita, ConsultaStatus status) {
        
    if (!horario.isDisponibilidade()) {
            throw new IllegalArgumentException("Horário indisponível!");
        }
    
        horario.setDisponibilidade(false);

        this.idConsulta = gerarIdAleatorio();

        this.paciente = paciente;
        this.medico = medico;  
        this.horario = horario;
        Date date = horario.getData();
        this.data = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.hora = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        this.descricao = descricao;
        this.receita = receita;
        this.status = status != null ? status : ConsultaStatus.AGENDADA;
    
    }
    
    // Getters e Setters
    public int getIdConsulta() { return idConsulta; }

    public Horario getHorario() { return horario; }
    public void setHorario(Horario horario) { this.horario = horario; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public LocalDate getData() { return data; }
    public LocalTime getHora() { return hora; }

    public ConsultaStatus getStatus() { return status; }
    public void setStatus(ConsultaStatus status) { this.status = status; }
    
    public String getReceita() { return receita; }
    public void setReceita(String receita) { this.receita = receita; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public String getAnotacoes() { return anotacoes; }
    public void setAnotacoes(String anotacoes) { this.anotacoes = anotacoes; }
    
    public String getNumeroCarteirinha() {
        return paciente != null ? paciente.getNumeroCarteirinha() : null;
    }

    public void registrarAnotacao(String texto) {
        this.anotacoes = texto;
    }

    private static int gerarIdAleatorio() {
        return (int) (Math.random() * 900000) + 100000; 
    }

}