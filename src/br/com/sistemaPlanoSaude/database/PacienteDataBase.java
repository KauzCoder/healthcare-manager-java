package br.com.sistemaPlanoSaude.database;

import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import java.util.ArrayList;
import java.util.List;

public class PacienteDataBase {

    private final List<Paciente> pacientes = new ArrayList<>();


    // ===============================
    //             CREATE
    // ===============================
    
    public boolean adicionarPaciente(Paciente paciente) {
    if (buscarCarteirinha(paciente.getNumeroCarteirinha()) != null) return false;
    pacientes.add(paciente);
    return true;
    }
    //Evita a duplicidade de carteirinhas ao adicionar um paciente.


    // ===============================
    //             READ
    // ===============================
    public Paciente buscarCarteirinha(String numeroCarteirinhaPesquisada) {
        for (Paciente paciente : pacientes) {
            if (paciente.getNumeroCarteirinha().equals(numeroCarteirinhaPesquisada)) {
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
            pacientes.remove(paciente);
            return true;
        }
        return false;
    }



    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes);
    }
    //Criei um copia  da lista para evitar que a lista original seja modificada externamente.

}