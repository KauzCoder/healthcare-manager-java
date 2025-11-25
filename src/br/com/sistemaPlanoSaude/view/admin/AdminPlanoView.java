package br.com.sistemaPlanoSaude.view.admin;

import br.com.sistemaPlanoSaude.database.PacienteDataBase;
import br.com.sistemaPlanoSaude.model.enums.PlanosDeSaude;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.model.planos.PlanoBasico;
import br.com.sistemaPlanoSaude.model.planos.PlanoPremium;
import br.com.sistemaPlanoSaude.service.PlanoDeSaudeService;
import br.com.sistemaPlanoSaude.util.ConsoleColors;
import java.util.List;
import java.util.Scanner;

public class AdminPlanoView {

    private final Scanner scanner = new Scanner(System.in);
    private final PlanoDeSaudeService service = new PlanoDeSaudeService();
    private final PacienteDataBase pacienteDB = new PacienteDataBase();

    // ============================================================
    // MENU PRINCIPAL
    // ============================================================
    public void exibirMenu() {

        limparTela();
        int opcao = -1;

        while (opcao != 0) {

            System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD +
                    "╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                              ║");
            System.out.println("║      🏥  PAINEL DO ADMINISTRADOR — PLANOS DE SAÚDE   🩺      ║");
            System.out.println("║                                                              ║");
            System.out.println("║   Gerencie planos, trocas, carteirinhas e verificações       ║");
            System.out.println("║      com total controle sobre os pacientes.                  ║");
            System.out.println("║                                                              ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝"
                    + ConsoleColors.RESET);

            System.out.println("\n📌 " + ConsoleColors.YELLOW + ConsoleColors.BOLD + "Opções Disponíveis:" + ConsoleColors.RESET + "\n");

            System.out.println(ConsoleColors.BLUE + " [ 1 ] ➜ Aplicar Plano Básico" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + " [ 2 ] ➜ Aplicar Plano Premium" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + " [ 3 ] ➜ Trocar para Plano Básico" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + " [ 4 ] ➜ Trocar para Plano Premium" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + " [ 5 ] ➜ Gerar Carteirinha" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + " [ 6 ] ➜ Listar Pacientes por Plano" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + " [ 7 ] ➜ Verificar se Paciente Possui Plano" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED + " [ 0 ] ➜ Voltar" + ConsoleColors.RESET);

            System.out.print(ConsoleColors.PURPLE + "👉 Digite sua opção: " + ConsoleColors.RESET);
            opcao = lerInteiro();

            processarOpcao(opcao);
        }
    }

    // ============================================================
    // PROCESSAMENTO DAS OPÇÕES
    // ============================================================
    private void processarOpcao(int opcao) {

        limparTela();

        switch (opcao) {

            case 1 -> aplicarPlanoBasico();
            case 2 -> aplicarPlanoPremium();
            case 3 -> trocarParaBasico();
            case 4 -> trocarParaPremium();
            case 5 -> gerarCarteirinha();
            case 6 -> listarPorPlano();
            case 7 -> verificarPossuiPlano();

            case 0 -> {
                System.out.println(ConsoleColors.YELLOW +
                        "Retornando ao menu principal... 💼" +
                        ConsoleColors.RESET);
                return;
            }

            default -> System.out.println(ConsoleColors.RED +
                    "❌ Opção inválida! Tente novamente." +
                    ConsoleColors.RESET);
        }

        System.out.println(ConsoleColors.CYAN +
                "\nPressione ENTER para continuar..." + ConsoleColors.RESET);
        scanner.nextLine();
        limparTela();
    }

    // ============================================================
    // APLICAR PLANO
    // ============================================================
    private void aplicarPlanoBasico() {

        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD +
                "╔══════════════════════════════════════╗");
        System.out.println("║      ➕  APLICAR PLANO BÁSICO         ║");
        System.out.println("╚══════════════════════════════════════╝" +
                ConsoleColors.RESET);

        Paciente p = buscarPacienteCPF();
        if (p == null) return;

        boolean ok = service.aplicarPlanoBasico(p, new PlanoBasico());
        System.out.println(ok
                ? ConsoleColors.GREEN + "✔ Plano Básico aplicado com sucesso!" + ConsoleColors.RESET
                : ConsoleColors.RED + "❌ Erro ao aplicar plano." + ConsoleColors.RESET);
    }

    private void aplicarPlanoPremium() {

        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD +
                "╔══════════════════════════════════════╗");
        System.out.println("║      ⭐  APLICAR PLANO PREMIUM         ║");
        System.out.println("╚══════════════════════════════════════╝" +
                ConsoleColors.RESET);

        Paciente p = buscarPacienteCPF();
        if (p == null) return;

        boolean ok = service.aplicarPlanoPremium(p, new PlanoPremium());
        System.out.println(ok
                ? ConsoleColors.GREEN + "✔ Plano Premium aplicado com sucesso!" + ConsoleColors.RESET
                : ConsoleColors.RED + "❌ Erro ao aplicar plano." + ConsoleColors.RESET);
    }

    // ============================================================
    // TROCAR PLANO
    // ============================================================
    private void trocarParaBasico() {
        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD +
                "╔══════════════════════════════════════╗");
        System.out.println("║      🔄 TROCAR PARA PLANO BÁSICO      ║");
        System.out.println("╚══════════════════════════════════════╝" +
                ConsoleColors.RESET);

        System.out.print(ConsoleColors.PURPLE + "Número da carteirinha: " + ConsoleColors.RESET);
        String cart = scanner.nextLine();

        boolean ok = service.trocarPlanoParaBasico(cart, new PlanoBasico());
        System.out.println(ok
                ? ConsoleColors.GREEN + "✔ Plano trocado para Básico!" + ConsoleColors.RESET
                : ConsoleColors.RED + "❌ Erro ao trocar plano." + ConsoleColors.RESET);
    }

    private void trocarParaPremium() {
        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD +
                "╔══════════════════════════════════════╗");
        System.out.println("║      🔄 TROCAR PARA PLANO PREMIUM     ║");
        System.out.println("╚══════════════════════════════════════╝" +
                ConsoleColors.RESET);

        System.out.print(ConsoleColors.PURPLE + "Número da carteirinha: " + ConsoleColors.RESET);
        String cart = scanner.nextLine();

        boolean ok = service.trocarPlanoParaPremium(cart, new PlanoPremium());
        System.out.println(ok
                ? ConsoleColors.GREEN + "✔ Plano trocado para Premium!" + ConsoleColors.RESET
                : ConsoleColors.RED + "❌ Erro ao trocar plano." + ConsoleColors.RESET);
    }

    // ============================================================
    // GERAR CARTEIRINHA
    // ============================================================
    private void gerarCarteirinha() {
        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD +
                "╔══════════════════════════════════════╗");
        System.out.println("║        🪪 GERAR CARTEIRINHA           ║");
        System.out.println("╚══════════════════════════════════════╝"
                + ConsoleColors.RESET);

        Paciente p = buscarPacienteCPF();
        if (p == null) return;

        String cart = service.gerarCarteirinha(p);
        System.out.println(cart != null
                ? ConsoleColors.GREEN + "✔ Carteirinha gerada: " + cart + ConsoleColors.RESET
                : ConsoleColors.RED + "❌ Erro ao gerar carteirinha." + ConsoleColors.RESET);
    }

    // ============================================================
    // LISTAR POR PLANO
    // ============================================================
    private void listarPorPlano() {

        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD +
                "╔══════════════════════════════════════╗");
        System.out.println("║      📋 LISTAR PACIENTES POR PLANO    ║");
        System.out.println("╚══════════════════════════════════════╝" +
                ConsoleColors.RESET);

        System.out.println(ConsoleColors.YELLOW + "1 - Básico" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "2 - Premium" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.PURPLE + "Escolha: " + ConsoleColors.RESET);

        int esc = lerInteiro();
        PlanosDeSaude plano = (esc == 1) ? PlanosDeSaude.PLANO_BASICO : PlanosDeSaude.PLANO_PREMIUM;

        List<Paciente> lista = service.listarPacientesPorPlano(plano);

        if (lista.isEmpty()) {
            System.out.println(ConsoleColors.RED +
                    "Nenhum paciente encontrado com o plano " + plano + "." +
                    ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.GREEN +
                "\nPacientes com o plano " + plano + ":\n" +
                ConsoleColors.RESET);

        for (Paciente p : lista) {
            System.out.println(ConsoleColors.CYAN +
                    "- " + p.getNome() + " | CPF: " + p.getCpf() +
                    ConsoleColors.RESET);
        }
    }

    // ============================================================
    // VERIFICAR PLANO
    // ============================================================
    private void verificarPossuiPlano() {

        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD +
                "╔══════════════════════════════════════╗");
        System.out.println("║        🔎 VERIFICAR PLANO DO PACIENTE ║");
        System.out.println("╚══════════════════════════════════════╝"
                + ConsoleColors.RESET);

        System.out.print(ConsoleColors.PURPLE + "Número da carteirinha: " + ConsoleColors.RESET);
        String cart = scanner.nextLine();

        boolean ok = service.possuiPlano(cart);
        System.out.println(ok
                ? ConsoleColors.GREEN + "✔ O paciente possui plano ativo." + ConsoleColors.RESET
                : ConsoleColors.RED + "❌ O paciente NÃO possui plano." + ConsoleColors.RESET);
    }

    // ============================================================
    // MÉTODO AUXILIAR
    // ============================================================
    private Paciente buscarPacienteCPF() {
        System.out.print(ConsoleColors.PURPLE + "Digite o CPF do paciente: " + ConsoleColors.RESET);
        String cpf = scanner.nextLine();

        for (Paciente p : pacienteDB.listarTodos()) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }

        System.out.println(ConsoleColors.RED + "❌ Paciente não encontrado." + ConsoleColors.RESET);
        return null;
    }

    // ============================================================
    // UTILITÁRIOS
    // ============================================================
    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED + "Digite um número válido: " + ConsoleColors.RESET);
            }
        }
    }
}
