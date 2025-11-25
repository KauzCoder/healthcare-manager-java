package br.com.sistemaPlanoSaude.database;

import br.com.sistemaPlanoSaude.model.consulta.Horario;
import java.util.ArrayList;
import java.util.List;

public class AgendaGlobalDataBase {

    private static final List<Horario> horarios = new ArrayList<>();

    // ===============================
    //             CREATE
    // ===============================
    public static boolean adicionarHorario(Horario horario) {
        if (buscarHorario(horario.getCrm(), horario.getIdHorario()) != null) return false; // já existe
        horarios.add(horario);
        return true;
    }

    // ===============================
    //             DELETE
    // ===============================
    // Remove um horário pelo objeto
    public static boolean removerHorario(Horario horario) {
        return horarios.remove(horario);
    }

    // Remove um horário pelo CRM do médico e ID do horário
    
    // ===============================
    //             READ
    // ===============================

    public static boolean removerPorId(String crmMedico, int idHorario) {
        Horario horario = buscarHorario(crmMedico, idHorario);
        if (horario != null) {
            horarios.remove(horario);
            return true;
        }
        return false;
    }

    public static List<Horario> listarHorariosPorMedico(String crmMedico) {
        List<Horario> resultado = new ArrayList<>();
        for (Horario horario : horarios) {
            if (horario.getCrm().equals(crmMedico)) {
                resultado.add(horario);
            }
        }
        return resultado;
    }

    // Busca um horário pelo CRM do médico e ID do horário
    public static Horario buscarHorario(String crmMedico, int idHorario) {
        for (Horario horario : horarios) {
            if (horario.getCrm().equals(crmMedico) && horario.getIdHorario() == idHorario) {
                return horario;
            }
        }
        return null;
    }
    
    // ===============================
    //             LIST
    // ===============================
    // Lista todos os horários (cópia defensiva)
    public static List<Horario> listarTodos() {
        return new ArrayList<>(horarios);
    }
}