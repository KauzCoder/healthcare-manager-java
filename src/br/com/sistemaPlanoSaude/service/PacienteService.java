package br.com.sistemaPlanoSaude.service;

import br.com.sistemaPlanoSaude.database.PacienteDataBase;
import br.com.sistemaPlanoSaude.model.enums.StatusPaciente;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;

import java.util.List;

public class PacienteService {

    private final PacienteDataBase pacienteDB = new PacienteDataBase();

    // ===============================
    //          CREATE
    // ===============================
    public boolean cadastrarPaciente(Paciente paciente) {
        return pacienteDB.adicionarPaciente(paciente);
    }

    // ===============================
    //          READ
    // ===============================
    public List<Paciente> listarPacientes() {
        return pacienteDB.listarTodos();
    }

    public Paciente buscarPorCarteirinha(String numeroCarteirinha) {
        return pacienteDB.buscarCarteirinha(numeroCarteirinha);
    }

    public Paciente buscarPorCpf(String cpf) {
        return pacienteDB.buscarPorCpf(cpf);
    }

    // ===============================
    //          UPDATE
    // ===============================
    public boolean atualizarPaciente(String numeroCarteirinha, Paciente dadosNovos) {
        return pacienteDB.atualizarPaciente(numeroCarteirinha, dadosNovos);
    }

    public boolean bloquearPaciente(String numeroCarteirinha) {
        Paciente paciente = pacienteDB.buscarCarteirinha(numeroCarteirinha);
        if (paciente == null) return false;

        paciente.setStatus(StatusPaciente.BLOQUEADO); // ou paciente.setAtivo(false), depende do seu modelo
        return true;
    }

    public boolean desbloquearPaciente(String numeroCarteirinha) {
        Paciente paciente = pacienteDB.buscarCarteirinha(numeroCarteirinha);
        if (paciente == null) return false;

        paciente.setStatus(StatusPaciente.ATIVO);
        paciente.setNumeroCarteirinha("");
        return true;
    }

    public boolean marcarComoFalecido(String numeroCarteirinha) {
        Paciente paciente = pacienteDB.buscarCarteirinha(numeroCarteirinha);
        if (paciente == null) return false;

        paciente.setStatus(StatusPaciente.FALECIDO);
        paciente.setNumeroCarteirinha("");
        return true;
    }

    public boolean desativarPaciente(String numeroCarteirinha) {
        Paciente paciente = pacienteDB.buscarCarteirinha(numeroCarteirinha);
        if (paciente == null) return false;

        paciente.setStatus(StatusPaciente.INATIVO);
        paciente.setNumeroCarteirinha("");
        return true;
    }
    

    // ===============================
    //          DELETE
    // ===============================
    public boolean removerPaciente(String numeroCarteirinha) {
        return pacienteDB.removerPorCarteirinha(numeroCarteirinha);
    }
}
