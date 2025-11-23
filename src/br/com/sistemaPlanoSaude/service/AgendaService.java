package br.com.sistemaPlanoSaude.service;

import br.com.sistemaPlanoSaude.model.funcionarios.Medico;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AgendaService {

    public boolean adicionarHorario(Medico medico, String horario) {

        if (!validarFormatoHorario(horario)) return false;

        if (medico.getHorariosDisponiveis().contains(horario)) return false;

        medico.getHorariosDisponiveis().add(horario);
        return true;
    }

    public boolean removerHorario(Medico medico, String horario) {
        return medico.getHorariosDisponiveis().remove(horario);
    }

    private boolean validarFormatoHorario(String horario) {
        try {
            LocalTime.parse(horario); // verifica formato HH:mm
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
