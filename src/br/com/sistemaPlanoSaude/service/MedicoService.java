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
    //           DELETE
    // =============================
    public boolean remover(String crm) {
        return medicoDB.removerPorCrm(crm);
    }

    // =============================
    //  SUPORTE DIRETO À INTERFACE
    // =============================

    // --------- CAMPOS EDITÁVEIS ---------

    public boolean atualizarNome(String crm, String novoNome) {
        Medico medico = medicoDB.buscarPorCrm(crm);
        if (medico == null) return false;
        medico.setNome(novoNome);
        return true;
    }

    public boolean atualizarEndereco(String crm, String novoEndereco) {
        Medico medico = medicoDB.buscarPorCrm(crm);
        if (medico == null) return false;
        medico.setEndereco(novoEndereco);
        return true;
    }

    public boolean atualizarTelefone(String crm, String novoTel) {
        Medico medico = medicoDB.buscarPorCrm(crm);
        if (medico == null) return false;
        medico.setTelefone(novoTel);
        return true;
    }

    public boolean atualizarEmail(String crm, String novoEmail) {
        Medico medico = medicoDB.buscarPorCrm(crm);
        if (medico == null) return false;
        medico.setEmail(novoEmail);
        return true;
    }

    public boolean atualizarEspecialidade(String crm, Especialidades novaEsp) {
        Medico medico = medicoDB.buscarPorCrm(crm);
        if (medico == null) return false;
        medico.setEspecialidade(novaEsp);
        return true;
    }

    public boolean atualizarDataContratacao(String crm, LocalDate novaData) {
        Medico medico = medicoDB.buscarPorCrm(crm);
        if (medico == null) return false;
        medico.setDataContratacao(novaData);
        return true;
    }
}
