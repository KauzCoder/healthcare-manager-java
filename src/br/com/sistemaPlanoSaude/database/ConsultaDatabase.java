package br.com.sistemaPlanoSaude.database;

import java.util.ArrayList;
import java.util.List;
import br.com.sistemaPlanoSaude.model.consulta.Consulta;

public class ConsultaDatabase {

    private static final List<Consulta> consultasAgendadas = new ArrayList<>();

    public static List<Consulta> getConsultas() {
        return consultasAgendadas;
    }
}