package br.com.sistemaPlanoSaude.database;

import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import java.util.ArrayList;
import java.util.List;

public class PacienteDataBase {

    private static final List<Paciente> PACIENTES = new ArrayList<>();


    // ===============================
    //             CREATE
    // ===============================
    
    public boolean adicionarPaciente(Paciente paciente) {
        if (paciente == null || paciente.getNumeroCarteirinha() == null) {
            return false;
        }

        if (buscarCarteirinha(paciente.getNumeroCarteirinha()) != null) return false;
        PACIENTES.add(paciente);
        return true;
    }
    //Evita a duplicidade de carteirinhas ao adicionar um paciente.


    // ===============================
    //             READ
    // ===============================
    public Paciente buscarCarteirinha(String numeroCarteirinhaPesquisada) {
        if (numeroCarteirinhaPesquisada == null) {
            return null;
        }
        for (Paciente paciente : PACIENTES) {
            if (numeroCarteirinhaPesquisada.equals(paciente.getNumeroCarteirinha())) {
                return paciente;
            }
        }
        return null;
    }

    public Paciente buscarPorCpf(String cpf) {
        if (cpf == null) {
            return null;
        }
        for (Paciente paciente : PACIENTES) {
            if (cpf.equals(paciente.getCpf())) {
                return paciente;
            }
        }
        return null;
    }

    // ===============================
    //            UPDATE
    // ===============================
    /**
     * Atualiza todos os dados do paciente EXCETO:
     * - número da carteirinha (imutável)
     */
    public boolean atualizarPaciente(String numeroCarteirinha, Paciente dadosNovos) {
        Paciente existente = buscarCarteirinha(numeroCarteirinha);
        if (existente == null) return false;

        // NÃO alterar número da carteirinha!
        existente.setNome(dadosNovos.getNome());
        existente.setCpf(dadosNovos.getCpf());
        existente.setEmail(dadosNovos.getEmail());
        existente.setTelefone(dadosNovos.getTelefone());
        existente.setSexo(dadosNovos.getSexo());
        existente.setDataDeNascimento(dadosNovos.getDataDeNascimento());
        existente.setEndereco(dadosNovos.getEndereco());
        existente.setPlanoSaude(dadosNovos.getPlanoSaude());
        existente.setStatus(dadosNovos.getStatus());


        return true;
    }

    // ===============================
    //             DELETE
    // ===============================
    public boolean removerPorCarteirinha(String numeroCarteirinha) {
        Paciente paciente = buscarCarteirinha(numeroCarteirinha);
        if (paciente != null) {
            PACIENTES.remove(paciente);
            return true;
        }
        return false;
    }



    public List<Paciente> listarTodos() {
        return new ArrayList<>(PACIENTES);
    }
    //Criei um copia  da lista para evitar que a lista original seja modificada externamente.

}