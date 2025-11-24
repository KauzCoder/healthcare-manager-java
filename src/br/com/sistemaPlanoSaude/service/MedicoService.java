package br.com.sistemaPlanoSaude.service;

import br.com.sistemaPlanoSaude.database.MedicoDataBase;
import br.com.sistemaPlanoSaude.model.enums.Especialidades;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;

import java.time.LocalDate;
import java.util.List;

public class MedicoService {

    private final MedicoDataBase medicoDB = new MedicoDataBase();

    // =============================
    //           CREATE
    // =============================
    public boolean cadastrar(Medico medico) {
        return medicoDB.adicionarMedico(medico);
    }

    // =============================
    //            READ
    // =============================
    public Medico buscarPorCrm(String crm) {
        return medicoDB.buscarPorCrm(crm);
    }

    public List<Medico> listarTodos() {
        return medicoDB.listarTodos();
    }

    // =============================
    //           DELETE
    // =============================
    public boolean remover(String crm) {
        return medicoDB.removerPorCrm(crm);
    }

    // =============================
    //           UPDATE
    // =============================

    // Atualização completa (exceto salário e CRM)
    public boolean atualizarMedico(String crm, Medico dados) {
        return medicoDB.atualizarMedico(crm, dados);
    }

    // Atualizar salário — restrito ao administrador
    public boolean atualizarSalario(String crm, int novoSalario) {
        return medicoDB.atualizarSalario(crm, novoSalario);
    }

    // =============================
    //  SUPORTE DIRETO À INTERFACE
    // =============================

    // --------- CAMPOS EDITÁVEIS ---------

    public boolean atualizarNome(String crm, String novoNome) {
        Medico m = medicoDB.buscarPorCrm(crm);
        if (m == null) return false;
        m.setNome(novoNome);
        return true;
    }

    public boolean atualizarEndereco(String crm, String novoEndereco) {
        Medico m = medicoDB.buscarPorCrm(crm);
        if (m == null) return false;
        m.setEndereco(novoEndereco);
        return true;
    }

    public boolean atualizarTelefone(String crm, String novoTel) {
        Medico m = medicoDB.buscarPorCrm(crm);
        if (m == null) return false;
        m.setTelefone(novoTel);
        return true;
    }

    public boolean atualizarEmail(String crm, String novoEmail) {
        Medico m = medicoDB.buscarPorCrm(crm);
        if (m == null) return false;
        m.setEmail(novoEmail);
        return true;
    }

    public boolean atualizarEspecialidade(String crm, Especialidades novaEsp) {
        Medico m = medicoDB.buscarPorCrm(crm);
        if (m == null) return false;
        m.setEspecialidade(novaEsp);
        return true;
    }

    public boolean atualizarDataContratacao(String crm, LocalDate novaData) {
        Medico m = medicoDB.buscarPorCrm(crm);
        if (m == null) return false;
        m.setDataContratacao(novaData);
        return true;
    }
}
