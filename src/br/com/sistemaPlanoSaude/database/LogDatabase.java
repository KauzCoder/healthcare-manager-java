package br.com.sistemaPlanoSaude.database;

import java.util.ArrayList;
import java.util.List;
import br.com.sistemaPlanoSaude.model.LogAdministrador;

public class LogDatabase {

    private static final List<LogAdministrador> logs = new ArrayList<>();

    public static List<LogAdministrador> getLogs() {
        return logs;
    }
}
