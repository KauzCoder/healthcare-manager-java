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
    
    public String getNumeroCarteirinha() {
        return paciente != null ? paciente.getNumeroCarteirinha() : null;
    }

    // Métodos
    public void cancelarConsultaStatus() {
        if (this.status == ConsultaStatus.CANCELADA) {
            System.out.println("A consulta já está cancelada!");
            return;
        }
        this.status = ConsultaStatus.CANCELADA;
        System.out.println("Consulta cancelada com sucesso.");
        liberarHorario();
        System.out.println("Horário liberado.");
    }

    public void realizarConsultaStatus() {
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

    // Retorna se o horário associado a esta consulta está disponível.
    public boolean isHorarioDisponivel() {
        return this.horario != null && this.horario.isDisponibilidade();
    }

    //Reserva o horário associado a esta consulta (marca como indisponível e vincula o paciente ao horário). 
    // Lança IllegalArgumentException se o horário for nulo ou já estiver ocupado.
    public void reservarHorario() {
        if (this.horario == null) {
            throw new IllegalStateException("Nenhum horário associado à consulta.");
        }
        if (!this.horario.isDisponibilidade()) {
            throw new IllegalStateException("Horário já está reservado.");
        }
        this.horario.setDisponibilidade(false);
        this.horario.setPaciente(this.paciente);
    }

    //Libera o horário associado a esta consulta (marca como disponível 
    // e desvincula o paciente). Se não houver horário, não faz nada.
    public void liberarHorario() {
        if (this.horario == null) return;
        this.horario.setDisponibilidade(true);
        this.horario.setPaciente(null);
    }
    
    private static int gerarIdAleatorio() {
        return (int) (Math.random() * 900000) + 100000; 
    }

}
