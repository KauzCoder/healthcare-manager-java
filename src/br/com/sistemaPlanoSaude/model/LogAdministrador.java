package br.com.sistemaPlanoSaude.model;

import java.time.LocalDateTime;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;

public class LogAdministrador {

    private final Administrador administrador; // quem realizou a ação
    private final String descricao;           // o que foi feito
    private final LocalDateTime dataHora;     // quando foi feito

    public LogAdministrador(Administrador administrador, String descricao) {
        this.administrador = administrador;
        this.descricao = descricao;
        this.dataHora = LocalDateTime.now(); // marca o momento do log
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        return "[" + dataHora + "] " + administrador.getNome() + ": " + descricao;
    }
}
