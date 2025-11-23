package br.com.sistemaPlanoSaude.database;

import java.util.ArrayList;
import java.util.List;

import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;

public class FuncionariosDataBase {

    private static final List<Medico> medicos = new ArrayList<>();
    private static final List<Administrador> administradores = new ArrayList<>();


    public static List<Medico> getMedicos() {
        return medicos;
    }

    public static List<Administrador> getAdministradores() {
        return administradores;
    }
}