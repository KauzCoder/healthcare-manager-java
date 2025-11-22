package br.com.sistemaPlanoSaude.model;

import java.util.Date;

public class Horario {

 private Date data;
 private boolean disponibilidade;
 private Paciente paciente;



    // ===============================
    //     Construtores
    // ===============================
 public Horario(Date data, boolean disponibilidade) {

    this.data = data;
    this.disponibilidade = disponibilidade;
 }

    // ===============================
    //        Getters e Setters
    // ===============================

    public Date getData() {
        return new Date(data.getTime());  // Retorna uma cópia para evitar modificações externas
    }
    public void setData(Date data) {
        if (data == null) {
            throw new IllegalArgumentException("A data não pode ser nula.");
        }
        this.data = new Date(data.getTime());  // Cria uma nova instância para evitar modificações externas
    }


    public boolean isDisponibilidade() { return disponibilidade; }
    public void setDisponibilidade(boolean disponibilidade) { this.disponibilidade = disponibilidade; }


    // ===============================
    //       Métodos auxiliares
    // ===============================


    public String exibirDetalhesHorario() {
        return "Data: " + data.toString() + "\n" +
               "Disponível: " + (disponibilidade ? "Sim" : "Não") + "\n" +
               "Paciente: " + (paciente != null ? paciente.getNome() : "Nenhum");
    }

    // Getters e setters para paciente
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    // equals/hashCode baseados na data (úteis para contains/remove por valor)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horario horario = (Horario) o;
        return data != null ? data.equals(horario.data) : horario.data == null;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}