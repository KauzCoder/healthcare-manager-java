package br.com.sistemaPlanoSaude.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import br.com.sistemaPlanoSaude.model.consulta.Consulta;
import br.com.sistemaPlanoSaude.model.enums.ConsultaStatus;

/**
 * Banco de dados em memória para consultas.
 * Todos os métodos são estáticos; não é necessário instanciar a classe.
 */
public final class ConsultaDatabase {

    private static final List<Consulta> CONSULTAS = new ArrayList<>();

    private ConsultaDatabase() {} // Construtor privado que evita instanciacao 
    //Mesmo se usasse abstract class ou interface (Como ensinado na aula) poderia instanciar com classe anonima.

    // ===============================
    //             CREATE
    // ===============================   
    public static void adicionarConsulta(Consulta consulta) {
        if (consulta != null) {
            CONSULTAS.add(consulta);
        }
    }
    // ===============================
    //             DELETE
    // ===============================
    public static void removerConsulta(Consulta consulta) {
        CONSULTAS.remove(consulta);
    }

    // ===============================
    //             READ
    // ===============================
    public static Consulta buscarPorId(int id) {
        for (Consulta consulta : CONSULTAS) {
            if (consulta.getIdConsulta() == id) return consulta;
        }
        return null;
    }

    public static List<Consulta> buscarPorMedico(String crm) {
        if (crm == null) return Collections.emptyList();

        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : CONSULTAS) {
            if (consulta.getMedico() != null && crm.equalsIgnoreCase(consulta.getMedico().getCrm())) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }

    public static List<Consulta> buscarPorPaciente(String cpf) {
        if (cpf == null) return Collections.emptyList();

        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : CONSULTAS) {
            if (consulta.getPaciente() != null && cpf.equals(consulta.getPaciente().getCpf())) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }

    // ===============================
    //             LIST
    // ===============================

    public static List<Consulta> listarTodas() {
        return Collections.unmodifiableList(CONSULTAS);
    }


    // ===============================
    //             UPDATE
    // ===============================
    public static boolean atualizarConsulta(Consulta consultaAtual, 
                                        String novaDescricao,
                                        String novaReceita,
                                        ConsultaStatus novoStatus,
                                        String novasAnotacoes) {
    if (consultaAtual == null) return false;

    consultaAtual.setDescricao(novaDescricao);
    consultaAtual.setReceita(novaReceita);
    consultaAtual.setStatus(novoStatus);
    consultaAtual.registrarAnotacao(novasAnotacoes);

    return true;
    }
}