package br.com.sistemaPlanoSaude.service;

import br.com.sistemaPlanoSaude.model.Paciente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações relacionadas a pacientes.
 * Implementa lógica de negócio para CRUD, busca e validações.
 */
public class PacienteService {
    private List<Paciente> pacientes = new ArrayList<>();

    /**
     * Criar um novo paciente
     */
    public void criarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não pode ser nulo.");
        }
        if (pacienteExisteByCPF(paciente.getCpf())) {
            throw new IllegalArgumentException("Paciente com CPF " + paciente.getCpf() + " já existe.");
        }
        pacientes.add(paciente);
    }

    /**
     * Buscar paciente por CPF
     */
    public Optional<Paciente> buscarPacientePorCPF(String cpf) {
        return pacientes.stream()
                .filter(p -> p.getCpf().equals(cpf))
                .findFirst();
    }

    /**
     * Buscar paciente por número de carteirinha
     */
    public Optional<Paciente> buscarPacientePorCarteirinha(String numeroCarteirinha) {
        return pacientes.stream()
                .filter(p -> p.getNumeroCarteirinha().equals(numeroCarteirinha))
                .findFirst();
    }

    /**
     * Listar todos os pacientes
     */
    public List<Paciente> listarTodosPacientes() {
        return new ArrayList<>(pacientes);
    }

    /**
     * Atualizar dados do paciente
     */
    public void atualizarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não pode ser nulo.");
        }
        Optional<Paciente> pacienteExistente = buscarPacientePorCPF(paciente.getCpf());
        if (pacienteExistente.isPresent()) {
            int index = pacientes.indexOf(pacienteExistente.get());
            pacientes.set(index, paciente);
        } else {
            throw new IllegalArgumentException("Paciente com CPF " + paciente.getCpf() + " não encontrado.");
        }
    }

    /**
     * Deletar paciente por CPF
     */
    public void deletarPaciente(String cpf) {
        Optional<Paciente> paciente = buscarPacientePorCPF(cpf);
        if (paciente.isPresent()) {
            pacientes.remove(paciente.get());
        } else {
            throw new IllegalArgumentException("Paciente com CPF " + cpf + " não encontrado.");
        }
    }

    /**
     * Verificar se paciente existe por CPF
     */
    public boolean pacienteExisteByCPF(String cpf) {
        return pacientes.stream().anyMatch(p -> p.getCpf().equals(cpf));
    }
    /**
     * Listar pacientes com dependentes
     */
    public List<Paciente> listarPacientesComDependentes() {
        return pacientes.stream()
                .filter(Paciente::isPossuiDependentes)
                .collect(Collectors.toList());
    }
    

    /**
     * Contar total de pacientes
     */
    public int contarTotalPacientes() {
        return pacientes.size();
    }
}
