package br.com.sistemaPlanoSaude.model.consulta;

import java.util.Date;

import br.com.sistemaPlanoSaude.model.pessoas.Paciente;

public class Horario {

    private final int idHorario;  // ID único do horário
    private Date data;
    private boolean disponibilidade;
    private Paciente paciente;
    private String crmMedico;  // para identificar o médico

    // ===============================
    //     Construtor
    // ===============================
    public Horario(Date data, boolean disponibilidade, String crmMedico) {
        this.idHorario = gerarIdAleatorio();
        this.data = new Date(data.getTime());
        this.disponibilidade = disponibilidade;
        this.crmMedico = crmMedico;
    }

    // ===============================
    //       Getters e Setters
    // ===============================
    public int getIdHorario() { return idHorario; }

    public Date getData() {
        return new Date(data.getTime());
    }

    public void setData(Date data) {
        if (data == null) throw new IllegalArgumentException("A data não pode ser nula.");
        this.data = new Date(data.getTime());
    }

    public boolean isDisponibilidade() { return disponibilidade; }
    public void setDisponibilidade(boolean disponibilidade) { this.disponibilidade = disponibilidade; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public String getCrm() { return crmMedico; }
    public void setCrm(String crm) { this.crmMedico = crm; }

    // ===============================
    //       Métodos auxiliares
    // ===============================
    public String exibirDetalhesHorario() {
        return "ID: " + idHorario + "\n" +
               "Data: " + data + "\n" +
               "Disponível: " + (disponibilidade ? "Sim" : "Não") + "\n" +
               "Paciente: " + (paciente != null ? paciente.getNome() : "Nenhum") +
               "\nCRM Médico: " + crmMedico;
    }

    private static int gerarIdAleatorio() {
        return (int) (Math.random() * 900000) + 100000; // gera um ID entre 100000 e 999999
    }

}