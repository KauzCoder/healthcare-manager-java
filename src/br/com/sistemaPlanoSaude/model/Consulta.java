package br.com.sistemaPlanoSaude.model;

import java.time.LocalTime;
import java.time.LocalDate;

public class Consulta {    
    private Paciente paciente;
    
    private int idConsulta;
    private LocalTime hora;
    private LocalDate data;
    private String descricao;
    private String receita;
    private String anotacoes;
    private ConsultaStatus status;

    public Consulta(int idConsulta, Paciente paciente, LocalTime hora, LocalDate data,
                    String descricao, String receita, ConsultaStatus status) {
        
        this.idConsulta = idConsulta;
        this.paciente = paciente;
        this.hora = hora;
        this.data = data;
        this.descricao = descricao;
        this.receita = receita;
        this.status = status != null ? status : ConsultaStatus.AGENDADA;
    }
    
    // Getters e Setters
    public int getIdConsulta() { return idConsulta; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public ConsultaStatus getStatus() { return status; }
    public void setStatus(ConsultaStatus status) { this.status = status; }

    public String getReceita() { return receita; }
    public void setReceita(String receita) { this.receita = receita; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getAnotacoes() { return anotacoes; }

    // Métodos
    public void cancelarConsulta() {
        if (this.status == ConsultaStatus.CANCELADA) {
            System.out.println("A consulta já está cancelada!");
            return;
        }
        this.status = ConsultaStatus.CANCELADA;
        System.out.println("Consulta cancelada com sucesso.");
    }

    public void realizarConsulta() {
        if (this.status == ConsultaStatus.REALIZADA) {
            System.out.println("A consulta já foi realizada!");
            return;
        }
        this.status = ConsultaStatus.REALIZADA;
        System.out.println("Consulta marcada como realizada.");
    }
    
    public void registrarAnotacao(String texto) {
        this.anotacoes = texto;
    }

    public String getNumeroCarteirinha() {
        return paciente != null ? paciente.getNumeroCarteirinha() : null;
    }
}


    




