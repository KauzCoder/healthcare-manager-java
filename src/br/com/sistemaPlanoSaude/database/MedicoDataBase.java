package br.com.sistemaPlanoSaude.database;

import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import java.util.ArrayList;
import java.util.List;

public class MedicoDataBase {

    private static final List<Medico> MEDICOS = new ArrayList<>();


    // ===============================
    //             CREATE
    // ===============================

    // Adiciona um médico à lista, evitando duplicidade de CRM
    public boolean adicionarMedico(Medico medico) {
        if (buscarPorCrm(medico.getCrm()) != null) return false; // já existe
        MEDICOS.add(medico);
        return true;
    }

    // ===============================
    //             DELETE
    // ===============================
    // Remove um médico da lista
    public boolean removerPorCrm(String crm) {
        Medico medico = buscarPorCrm(crm);
        if (medico != null) {
            MEDICOS.remove(medico);
            return true;
        }
        return false;
    }

    // ===============================
    //             READ
    // ===============================
    // Busca um médico pelo CRM
    public Medico buscarPorCrm(String crm) {
        if (crm == null) return null;
        for (Medico medico : MEDICOS) {
            if (crm.equals(medico.getCrm())) {
                return medico;
            }
        }
        return null;
    }

    // Busca um médico pelo CPF
    public Medico buscarPorCpf(String cpf) {
        if (cpf == null) return null;
        for (Medico medico : MEDICOS) {
            if (cpf.equals(medico.getCpf())) {
                return medico;
            }
        }
        return null;
    }
    // ===============================
    //            UPDATE
    // ===============================

    /**
     * Atualiza TODOS os campos do médico, exceto:
     *  - CRM (imutável)
     *  - salário (somente administrador pode alterar)
     */
    public boolean atualizarMedico(String crm, Medico dados) {
        Medico existente = buscarPorCrm(crm);
        if (existente == null) return false;

        // NÃO alterar CRM nem salário
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setTelefone(dados.getTelefone());
        existente.setEndereco(dados.getEndereco());
        existente.setEspecialidade(dados.getEspecialidade());
        existente.setSexo(dados.getSexo());
        existente.setDataDeNascimento(dados.getDataDeNascimento());
        // adicione aqui qualquer outro campo da sua classe

        return true;
    }

    // UPDATE exclusivo do administrador (apenas salário)
    public boolean atualizarSalario(String crm, int novoSalario) {
        Medico medico = buscarPorCrm(crm);
        if (medico == null) return false;

        medico.setSalario(novoSalario);
        return true;
    }
    
    // ===============================
    //             LIST
    // ===============================
    // Lista todos os médicos (cópia defensiva)
    public List<Medico> listarTodos() {
        return new ArrayList<>(MEDICOS);
    }
}