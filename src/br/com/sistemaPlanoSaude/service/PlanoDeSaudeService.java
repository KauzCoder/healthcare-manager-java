package br.com.sistemaPlanoSaude.service;

import br.com.sistemaPlanoSaude.database.PacienteDataBase;
import br.com.sistemaPlanoSaude.model.enums.Cobertura;
import br.com.sistemaPlanoSaude.model.enums.PlanosDeSaude;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.model.planos.PlanoBasico;
import br.com.sistemaPlanoSaude.model.planos.PlanoPremium;

import java.util.List;
import java.util.ArrayList;

public class PlanoDeSaudeService {

    private final PacienteDataBase pacienteDB = new PacienteDataBase();

    // =====================================
    //          APLICAR PLANO AO PACIENTE
    // =====================================
    public boolean aplicarPlanoPremium(Paciente paciente, PlanoPremium novoPlano) {
        if (paciente == null || novoPlano == null) return false;

        paciente.setPlanoSaude(novoPlano);
        return true;
    }
    public boolean aplicarPlanoBasico(Paciente paciente, PlanoBasico novoPlano) {
        if (paciente == null || novoPlano == null) return false;

        paciente.setPlanoSaude(novoPlano);
        return true;
    }

    // =====================================
    //       TROCAR PLANO DO PACIENTE
    // =====================================
    public boolean trocarPlanoParaPremium(String Carteirinha, PlanoPremium novoPlano) {
        Paciente p = pacienteDB.buscarCarteirinha(Carteirinha);
        if (p == null || novoPlano == null) return false;

        p.setPlanoSaude(novoPlano);
        return true;
    }
    public boolean trocarPlanoParaBasico(String Carteirinha, PlanoBasico novoPlano) {
        Paciente p = pacienteDB.buscarCarteirinha(Carteirinha);
        if (p == null || novoPlano == null) return false;

        p.setPlanoSaude(novoPlano);
        return true;
    }

    // =====================================
    //     ATUALIZAR INFORMAÇÕES DO PLANO
    // =====================================
    public boolean atualizarNomePlanoBasico(PlanoBasico plano, PlanosDeSaude novoNome) {
        if (plano == null || novoNome == null) return false;
        plano.setNomePlano(novoNome);
        return true;
    }
    public boolean atualizarNomePlanoPremium(PlanoPremium plano, PlanosDeSaude novoNome) {
        if (plano == null || novoNome == null) return false;
        plano.setNomePlano(novoNome);
        return true;
    }

    public boolean atualizarCoberturaPlanoBasico(PlanoBasico plano, Cobertura  novaCobertura) {
        if (plano == null || novaCobertura == null) return false;
        plano.setCobertura(novaCobertura);
        return true;
    }

    public boolean atualizarCoberturaPlanoPremium(PlanoPremium plano, Cobertura novaCobertura) {
        if (plano == null || novaCobertura == null) return false;
        plano.setCobertura(novaCobertura);
        return true;
    }

    // =====================================
    //      GERAR CARTEIRINHA AUTOMÁTICA
    // =====================================
    public String gerarCarteirinha(Paciente paciente) {
        if (paciente == null || paciente.getPlanoSaude() == null) {
            return null;
        }

        String codigo = "CART-" + paciente.getCpf() + "-" + System.currentTimeMillis();
        paciente.setNumeroCarteirinha(codigo);
        return codigo;
    }

    // =====================================
    // LISTAR PACIENTES DE UM PLANO ESPECÍFICO
    // =====================================
    public List<Paciente> listarPacientesPorPlano(PlanosDeSaude nomePlano) {
        List<Paciente> todos = pacienteDB.listarTodos();
        List<Paciente> filtrados = new ArrayList<>();
        //Criei um lsta nova para armazenar os pacientes filtrados ja que nao quero modificar a lista original pra
        //evitar efeitos colaterais indesejados. Ate pq nao a ha a neciessidade de modificar a lista original aqui.

        for (Paciente p : todos) {
            if (p.getPlanoSaude() != null &&
                p.getPlanoSaude().getNomePlano() == nomePlano) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    // =====================================
    //       VERIFICAR SE PACIENTE POSSUI PLANO
    // =====================================
    public boolean possuiPlano(String Carteirinha) {
        Paciente p = pacienteDB.buscarCarteirinha(Carteirinha);
        return p != null && p.getPlanoSaude() != null;
    }
}