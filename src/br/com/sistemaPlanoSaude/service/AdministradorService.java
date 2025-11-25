package br.com.sistemaPlanoSaude.service;

import br.com.sistemaPlanoSaude.database.AdministradorDataBase;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;

import java.util.List;

public class AdministradorService {

    private final AdministradorDataBase administradoresDB;

    public AdministradorService() {
        this.administradoresDB = new AdministradorDataBase();
    }

    // ===============================
    //           CREATE
    // ===============================
    public boolean criarAdministrador(Administrador administrador) {
        if (administrador.getNome() == null || administrador.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (administrador.getCpf() == null || administrador.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório.");
        }
        if (administrador.getSenhaHash() == null || administrador.getSenhaHash().length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres.");
        }
 // pode adicionar hash se quiser
        return administradoresDB.adicionarAdministrador(administrador);
    }

    // ===============================
    //            READ
    // ===============================
    public Administrador buscarPorCpf(String cpf) {
        return administradoresDB.buscarPorCpf(cpf);
    }

    public List<Administrador> listarTodos() {
        return administradoresDB.listarTodos();
    }

    // ===============================
    //           UPDATE
    // ===============================
    public boolean atualizarAdministrador(String cpf, String novoNome, String novaSenha) {
        Administrador existente = administradoresDB.buscarPorCpf(cpf);
        if (existente == null) return false;

        if (novoNome != null && !novoNome.isEmpty()) {
            existente.setNome(novoNome);
        }
        if (novaSenha != null && novaSenha.length() >= 6) {
            existente.setSenhaHash(novaSenha); // pode adicionar hash aqui
        }

        return administradoresDB.atualizarAdministrador(existente);
    }

    // ===============================
    //           DELETE
    // ===============================
    public boolean removerAdministrador(String cpf) {
        Administrador admin = administradoresDB.buscarPorCpf(cpf);
        if (admin == null) return false;
        return administradoresDB.removerAdministrador(admin);
    }
}

