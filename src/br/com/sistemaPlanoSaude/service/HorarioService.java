package br.com.sistemaPlanoSaude.service;

import br.com.sistemaPlanoSaude.database.AgendaGlobalDataBase;
import br.com.sistemaPlanoSaude.model.consulta.Horario;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;

import java.util.Date;
import java.util.List;

public class HorarioService {

    // ===============================
    //           CREATE
    // ===============================
    public boolean criarHorario(Date data, boolean disponibilidade, String crmMedico) {
        if (data == null || crmMedico == null || crmMedico.isEmpty()) {
            throw new IllegalArgumentException("Data e CRM são obrigatórios.");
        }

        Horario horario = new Horario(data, disponibilidade, crmMedico);
        return AgendaGlobalDataBase.adicionarHorario(horario);
    }

    // ===============================
    //            READ
    // ===============================
    public Horario buscarHorario(String crmMedico, int idHorario) {
        return AgendaGlobalDataBase.buscarHorario(crmMedico, idHorario);
    }

    public List<Horario> listarHorariosPorMedico(String crmMedico) {
        return AgendaGlobalDataBase.listarHorariosPorMedico(crmMedico);
    }

    public List<Horario> listarTodos() {
        return AgendaGlobalDataBase.listarTodos();
    }

    // ===============================
    //           UPDATE
    // ===============================
    public boolean atualizarHorarioData(String crmMedico, int idHorario, Date novaData) {
        Horario horario = buscarHorario(crmMedico, idHorario);
        if (horario == null) return false;

        horario.setData(novaData);
        return true;
    }

    public boolean atualizarDisponibilidade(String crmMedico, int idHorario, boolean disponibilidade) {
        Horario horario = buscarHorario(crmMedico, idHorario);
        if (horario == null) return false;

        horario.setDisponibilidade(disponibilidade);
        return true;
    }

    public boolean vincularPaciente(String crmMedico, int idHorario, 
                                   Paciente paciente) {

        Horario horario = buscarHorario(crmMedico, idHorario);
        if (horario == null) return false;

        if (!horario.isDisponibilidade()) return false; // horário ocupado

        horario.setPaciente(paciente);
        horario.setDisponibilidade(false);
        return true;
    }

    public boolean removerPaciente(String crmMedico, int idHorario) {
        Horario horario = buscarHorario(crmMedico, idHorario);
        if (horario == null) return false;

        horario.setPaciente(null);
        horario.setDisponibilidade(true);
        return true;
    }

    // ===============================
    //           DELETE
    // ===============================
    public boolean removerHorario(String crmMedico, int idHorario) {
        return AgendaGlobalDataBase.removerPorId(crmMedico, idHorario);
    }
}
