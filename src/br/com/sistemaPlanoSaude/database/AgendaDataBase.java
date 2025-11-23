package br.com.sistemaPlanoSaude.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgendaDataBase {

    private static final List<AgendaService> agendas = new ArrayList<>();

    public static List<AgendaService> getAgendas() { 
        return agendas; 
    }
}