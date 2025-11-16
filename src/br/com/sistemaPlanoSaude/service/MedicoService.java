package br.com.sistemaPlanoSaude.service;

import br.com.sistemaPlanoSaude.model.Medico;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações relacionadas a médicos.
 * Implementa lógica de negócio para CRUD, busca por especialidade e validações.
 */
public class MedicoService {
    private List<Medico> medicos = new ArrayList<>();

    /**
     * Criar um novo médico
     */
    public void criarMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("Médico não pode ser nulo.");
        }
        if (medicoExisteByCRM(medico.getCrm())) {
            throw new IllegalArgumentException("Médico com CRM " + medico.getCrm() + " já existe.");
        }
        medicos.add(medico);
    }

    /**
     * Buscar médico por CRM
     */
    public Optional<Medico> buscarMedicoPorCRM(String crm) {
        return medicos.stream()
                .filter(m -> m.getCrm().equals(crm))
                .findFirst();
    }

    /**
     * Buscar médico por CPF
     */
    public Optional<Medico> buscarMedicoPorCPF(String cpf) {
        return medicos.stream()
                .filter(m -> m.getCpf().equals(cpf))
                .findFirst();
    }

    /**
     * Listar médicos por especialidade
     */
    public List<Medico> listarMedicosPorEspecialidade(String especialidade) {
        return medicos.stream()
                .filter(m -> m.getEspecialidade().equalsIgnoreCase(especialidade))
                .collect(Collectors.toList());
    }

    /**
     * Listar todos os médicos
     */
    public List<Medico> listarTodosMedicos() {
        return new ArrayList<>(medicos);
    }

    /**
     * Atualizar dados do médico
     */
    public void atualizarMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("Médico não pode ser nulo.");
        }
        Optional<Medico> medicoExistente = buscarMedicoPorCRM(medico.getCrm());
        if (medicoExistente.isPresent()) {
            int index = medicos.indexOf(medicoExistente.get());
            medicos.set(index, medico);
        } else {
            throw new IllegalArgumentException("Médico com CRM " + medico.getCrm() + " não encontrado.");
        }
    }

    /**
     * Deletar médico por CRM
     */
    public void deletarMedico(String crm) {
        Optional<Medico> medico = buscarMedicoPorCRM(crm);
        if (medico.isPresent()) {
            medicos.remove(medico.get());
        } else {
            throw new IllegalArgumentException("Médico com CRM " + crm + " não encontrado.");
        }
    }

    /**
     * Verificar se médico existe por CRM
     */
    public boolean medicoExisteByCRM(String crm) {
        return medicos.stream().anyMatch(m -> m.getCrm().equals(crm));
    }

    /**
     * Contar total de médicos
     */
    public int contarTotalMedicos() {
        return medicos.size();
    }

   
}
