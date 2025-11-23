package br.com.sistemaPlanoSaude.database;

import java.util.ArrayList;
import java.util.List;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;

public class PacienteDataBase {

    private static final List<Paciente> pacientes = new ArrayList<>();

    public static List<Paciente> getPacientes() {
        return pacientes;
    }
}