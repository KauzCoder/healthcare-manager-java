package br.com.sistemaPlanoSaude.database;

import java.util.ArrayList;
import java.util.List;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;

public class AdministradorDataBase {
    
    private final List<Administrador> administradores = new ArrayList<>();
    
    // ===============================
    //             CREATE
    // ===============================
    public boolean adicionarAdministrador(Administrador admin) {
        if (buscarPorCpf(admin.getCpf()) != null) return false; 
        administradores.add(admin);
        return true;
    }
    
    // ===============================
    //             READ
    // ===============================

    // Busca um administrador pelo CPF
  public Administrador buscarPorCpf(String cpf) {
        if (cpf == null) return null;
        for (Administrador administrador : administradores) {
            if (cpf.equals(administrador.getCpf())) {
                return administrador;
            }
        }
        return null;
    }

    // ===============================
    //             UPDATE
    // ===============================


    //Atualiza os campos do administrador correspondente ao CPF do objeto passado.
    //O CPF não é alterado aqui (assumimos CPF como identificador imutável).
    
    public boolean atualizarAdministrador(Administrador admin) {
        if (admin == null || admin.getCpf() == null) return false;
        Administrador existente = buscarPorCpf(admin.getCpf());
        if (existente == null) return false;

        // Atualize campo a campo (não altera CPF)
        existente.setNome(admin.getNome());
        existente.setSenhaHash(admin.getSenhaHash());         // se existir campo senha (hash em produção)
        // se tiver outros campos, atualize aqui...

        return true;
    }


    public boolean atualizarPorCpf(String cpf, Administrador dados) {
        if (cpf == null || dados == null) return false;
        Administrador existente = buscarPorCpf(cpf);
        if (existente == null) return false;

        existente.setNome(dados.getNome());
        existente.setSenhaHash(dados.getSenhaHash());
        // atualize outros campos conforme necessário

        return true;
    }

    // ===============================
    //             DELETE
    // ===============================
    public boolean removerAdministrador(Administrador admin) {
        return administradores.remove(admin);
    }


    // ===============================
    //             LIST
    // ===============================
    // Lista todos os administradores (cópia defensiva)
    public List<Administrador> listarTodos() {
        return new ArrayList<>(administradores);
    }
}
