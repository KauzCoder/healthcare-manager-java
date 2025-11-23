package br.com.sistemaPlanoSaude.view.consulta;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import br.com.sistemaPlanoSaude.model.consulta.Consulta;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.enums.ConsultaStatus;

//Classe responsável pela geração e gerenciamento de consultas do ponto de vista do médico.
// Permite visualizar consultas agendadas, registrar receitas, anotações e marcar como realizada.
// Organização: Menu principal, métodos de criação, auxiliares e de coleta de dados.
 
public class GeraçãoConsultaMedico {

    // Lista simulada de consultas agendadas
    private static List<Consulta> consultasAgendadas = new ArrayList<>();

    // MENU PRINCIPAL 

    /**
     Exibe o menu principal de consultas para o médico.

     @param scanner objeto Scanner para leitura de entrada
     @param medico o médico logado
     */
    public static void menuConsultaMedico(Scanner scanner, Medico medico) {
        if (medico == null) {
            System.out.println("Erro: Médico não identificado.");
            return;
        }

        while (true) {
            System.out.println("\n========== MENU DE CONSULTAS - MÉDICO ==========");
            System.out.println("1. Ver minhas consultas agendadas");
            System.out.println("2. Registrar receita");
            System.out.println("3. Registrar anotações");
            System.out.println("4. Marcar consulta como realizada");
            System.out.println("5. Cancelar consulta");
            System.out.println("6. Voltar");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    exibirConsultasAgendadas(medico);
                    break;
                case "2":
                    registrarReceita(scanner, medico);
                    break;
                case "3":
                    registrarAnotacoes(scanner, medico);
                    break;
                case "4":
                    marcarComRealizada(scanner, medico);
                    break;
                case "5":
                    cancelarConsultaMedico(scanner, medico);
                    break;
                case "6":
                    System.out.println("Retornando ao menu anterior...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // MÉTODOS DE VISUALIZAÇÃO

    /**
     Exibe todas as consultas agendadas do médico.

     @param medico o médico
     */

    private static void exibirConsultasAgendadas(Medico medico) {
        System.out.println("\n========== MINHAS CONSULTAS AGENDADAS ==========");
        System.out.printf("Médico: %s (CRM: %s)%n", medico.getNome(), medico.getCrm());
        System.out.printf("Especialidade: %s%n", medico.getEspecialidade());

        List<Consulta> minhasConsultas = filtrarConsultasMedico(medico);

        if (minhasConsultas.isEmpty()) {
            System.out.println("\nVocê não possui consultas agendadas.");
        } else {
            System.out.println("\n--- Lista de Consultas ---");
            for (int i = 0; i < minhasConsultas.size(); i++) {
                Consulta c = minhasConsultas.get(i);
                System.out.printf("%d. ID: %d | Paciente: %s | Data: %s | Hora: %s | Status: %s%n",
                    i + 1,
                    c.getIdConsulta(),
                    c.getPaciente().getNome(),
                    c.getData(),
                    c.getHora(),
                    c.getStatus());
            }
        }

        System.out.println("===============================================\n");
    }

    /**
     Exibe detalhes completos de uma consulta selecionada.

     @param consulta a consulta
     */
    private static void exibirDetalhesConsulta(Consulta consulta) {
        System.out.println("\n--- Detalhes da Consulta ---");
        System.out.printf("ID: %d%n", consulta.getIdConsulta());
        System.out.printf("Paciente: %s%n", consulta.getPaciente().getNome());
        System.out.printf("CPF: %s%n", consulta.getPaciente().getCpf());
        System.out.printf("Carteirinha: %s%n", consulta.getNumeroCarteirinha());
        System.out.printf("Data: %s%n", consulta.getData());
        System.out.printf("Hora: %s%n", consulta.getHora());
        System.out.printf("Descrição: %s%n", consulta.getDescricao());
        System.out.printf("Receita: %s%n", 
            (consulta.getReceita() != null && !consulta.getReceita().isEmpty()) ? consulta.getReceita() : "Não registrada");
        System.out.printf("Anotações: %s%n", 
            (consulta.getAnotacoes() != null && !consulta.getAnotacoes().isEmpty()) ? consulta.getAnotacoes() : "Não registradas");
        System.out.printf("Status: %s%n", consulta.getStatus());
        System.out.println("============================\n");
    }

    // MÉTODOS DE OPERAÇÃO 

    /**
     Permite registrar uma receita para uma consulta específica.

     @param scanner objeto Scanner
     @param medico  o médico
     */
    private static void registrarReceita(Scanner scanner, Medico medico) {
        System.out.println("\n========== REGISTRAR RECEITA ==========");

        Consulta consulta = selecionarConsultaMedico(scanner, medico);
        if (consulta == null) {
            System.out.println("Operação cancelada.");
            return;
        }

        exibirDetalhesConsulta(consulta);

        System.out.print("Digite a receita (ou deixe vazio para cancelar): ");
        String receita = scanner.nextLine().trim();

        if (receita.isEmpty()) {
            System.out.println("Receita não foi registrada.");
            return;
        }

        consulta.setReceita(receita);
        System.out.println("Receita registrada com sucesso!");
    }

    /**
     Permite registrar anotações para uma consulta específica.

     @param scanner objeto Scanner
     @param medico  o médico
     */
    private static void registrarAnotacoes(Scanner scanner, Medico medico) {
        System.out.println("\n========== REGISTRAR ANOTAÇÕES ==========");

        Consulta consulta = selecionarConsultaMedico(scanner, medico);
        if (consulta == null) {
            System.out.println("Operação cancelada.");
            return;
        }

        exibirDetalhesConsulta(consulta);

        System.out.print("Digite as anotações (ou deixe vazio para cancelar): ");
        String anotacoes = scanner.nextLine().trim();

        if (anotacoes.isEmpty()) {
            System.out.println("Anotações não foram registradas.");
            return;
        }

        consulta.registrarAnotacao(anotacoes);
        System.out.println("Anotações registradas com sucesso!");
    }

    /**
     Marca uma consulta como realizada.

     @param scanner objeto Scanner
     @param medico  o médico
     */
    private static void marcarComRealizada(Scanner scanner, Medico medico) {
        System.out.println("\n========== MARCAR CONSULTA COMO REALIZADA ==========");

        Consulta consulta = selecionarConsultaMedico(scanner, medico);
        if (consulta == null) {
            System.out.println("Operação cancelada.");
            return;
        }

        if (consulta.getStatus() == ConsultaStatus.REALIZADA) {
            System.out.println("Esta consulta já foi marcada como realizada!");
            return;
        }

        if (consulta.getStatus() == ConsultaStatus.CANCELADA) {
            System.out.println("Não é possível marcar como realizada uma consulta cancelada!");
            return;
        }

        consulta.realizarConsultaStatus();
        System.out.println("Consulta marcada como realizada com sucesso!");
    }

    /**
     Permite cancelar uma consulta agendada.

     @param scanner objeto Scanner
     @param medico  o médico
     */

    private static void cancelarConsultaMedico(Scanner scanner, Medico medico) {
        System.out.println("\n========== CANCELAR CONSULTA ==========");

        Consulta consulta = selecionarConsultaMedico(scanner, medico);
        if (consulta == null) { System.out.println("Operação cancelada."); return; }

        if (consulta.getStatus() == ConsultaStatus.CANCELADA) {System.out.println("Esta consulta já está cancelada!");
            return;
        }

        if (consulta.getStatus() == ConsultaStatus.REALIZADA) {
            System.out.println("Não é possível cancelar uma consulta já realizada!");
            return;
        }

        exibirDetalhesConsulta(consulta);

        System.out.print("Tem certeza que deseja cancelar? (s/n): ");
        String confirmacao = scanner.nextLine().trim().toLowerCase();

        if (confirmacao.equals("s")) {
            consulta.cancelarConsultaStatus();
            System.out.println("Consulta cancelada com sucesso!");
        } else {
            System.out.println("Cancelamento abortado.");
        }
    }

    // MÉTODOS DE SELEÇÃO 

    /**
     Permite selecionar uma consulta da lista de consultas do médico.

     @param scanner objeto Scanner
     @param medico  o médico
     @return a consulta selecionada ou null
     */
    private static Consulta selecionarConsultaMedico(Scanner scanner, Medico medico) {
        List<Consulta> minhasConsultas = filtrarConsultasMedico(medico);

        if (minhasConsultas.isEmpty()) {
            System.out.println("Você não possui consultas para esta operação.");
            return null;
        }

        System.out.println("\n--- Suas Consultas ---");
        for (int i = 0; i < minhasConsultas.size(); i++) {
            Consulta c = minhasConsultas.get(i);
            System.out.printf("%d. Paciente: %s | Data: %s | Status: %s%n",
                i + 1,
                c.getPaciente().getNome(),
                c.getData(),
                c.getStatus());
        }

        System.out.print("Escolha o número da consulta (ou 0 para cancelar): ");
        try {
            int escolha = Integer.parseInt(scanner.nextLine().trim());

            if (escolha == 0) {
                return null;
            }

            if (escolha > 0 && escolha <= minhasConsultas.size()) {
                return minhasConsultas.get(escolha - 1);
            } else {
                System.out.println("Opção inválida.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return null;
        }
    }

    // MÉTODOS AUXILIARES 

    /**
     Filtra as consultas de um médico específico.

     @param medico o médico
     @return lista de consultas do médico
     */
    private static List<Consulta> filtrarConsultasMedico(Medico medico) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : consultasAgendadas) {
            if (c.getMedico().equals(medico)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    /**
     Adiciona uma consulta ao repositório simulado (para testes).

     @param consulta a consulta a ser adicionada
     */
    public static void adicionarConsultaAgendada(Consulta consulta) {
        if (consulta != null && !consultasAgendadas.contains(consulta)) {
            consultasAgendadas.add(consulta);
        }
    }

    /**
     Retorna a lista de todas as consultas agendadas (para fins de teste/relatório).

     @return lista de consultas
     */
    public static List<Consulta> obterTodasAsConsultas() {
        return new ArrayList<>(consultasAgendadas);
    }

    // Versão sem parâmetros (compatibilidade)
    public static void menuConsultaMedico() {
        Scanner scanner = new Scanner(System.in);
        // Nota: sem médico, retorna imediatamente
        menuConsultaMedico(scanner, null);
    }
}
