package br.com.sistemaPlanoSaude.database;

import java.util.ArrayList;
import java.util.List;
import br.com.sistemaPlanoSaude.model.LogAdministrador;

public class LogDataBase {

    private static final List<LogAdministrador> logs = new ArrayList<>();

    public static void adicionarLog(LogAdministrador log) {
        if (log != null) {
            logs.add(log);
        }
    }

    public static List<LogAdministrador> listarTodos() {
        return new ArrayList<>(logs); // retorna cópia defensiva
    }

}
