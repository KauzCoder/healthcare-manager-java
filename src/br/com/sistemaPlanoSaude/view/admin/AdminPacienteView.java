package br.com.sistemaPlanoSaude.view.admin;

import br.com.sistemaPlanoSaude.database.PacienteDataBase;
import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.util.ConsoleColors;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import br.com.sistemaPlanoSaude.view.formularios.FormularioPaciente;
import java.util.Scanner;

public class AdminPacienteView {

    private final Scanner scanner = new Scanner(System.in);
    private final PacienteDataBase pacienteDB = new PacienteDataBase();

    public void exibirMenu(Administrador admin) {

        MetodosAuxiliares.limparTela();

        int opcao = -1;

        while (opcao != 0) {

            System.out.println(ConsoleColors.CYAN + "╔══════════════════════════════════════════════════════════════╗" + ConsoleColors.RESET);
            System.out.println("║                                                              ║");
            System.out.println("║   " + ConsoleColors.BOLD + "🧑‍⚕️  PAINEL DO ADMINISTRADOR — PACIENTES   🧑‍⚕️" + ConsoleColors.RESET + "       ║");
            System.out.println("║                                                              ║");
            System.out.println("║   Gerencie pacientes, permissões e status da conta           ║");
            System.out.println("║      com facilidade e total controle administrativo.          ║");
            System.out.println("║                                                              ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println();

            System.out.println(ConsoleColors.BLUE + "📌 OPÇÕES DISPONÍVEIS:\n" + ConsoleColors.RESET);
            System.out.println(" [ 1 ] ➜ Cadastrar Paciente");
            System.out.println(" [ 2 ] ➜ Listar Pacientes");
            System.out.println(" [ 3 ] ➜ Bloquear Paciente");
            System.out.println(" [ 4 ] ➜ Desbloquear Paciente");
            System.out.println(" [ 6 ] ➜ Buscar Paciente por Carteirinha");
            System.out.println(" [ 0 ] ➜ Voltar");
            System.out.println();

            System.out.print(ConsoleColors.YELLOW + "👉 Digite sua opção: " + ConsoleColors.RESET);
            opcao = lerInteiro();

            processarOpcao(admin, opcao);
        }
    }

    // ===============================================================
    // PROCESSAR ESCOLHA DO MENU
    // ===============================================================
    private void processarOpcao(Administrador admin, int opcao) {

        MetodosAuxiliares.limparTela();

        switch (opcao) {

            case 1 -> cadastrarPaciente(admin);

            case 2 -> listarPacientes(admin);

            case 3 -> bloquearPaciente(admin);

            case 4 -> desbloquearPaciente(admin);

            case 5 -> alterarPermissoesPaciente(admin);

            case 6 -> resetarSenha(admin);

            case 7 -> buscarPaciente(admin);

            case 0 -> {
                System.out.println(ConsoleColors.GREEN + "Retornando ao menu principal... 💼" + ConsoleColors.RESET);
                return;
            }

            default -> System.out.println(ConsoleColors.RED + "❌ Opção inválida! Tente novamente." + ConsoleColors.RESET);
        }

        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
        MetodosAuxiliares.limparTela();
    }

    // ===============================================================
    // 1 — CADASTRAR PACIENTE
    // ===============================================================
    private void cadastrarPaciente(Administrador admin) {
        System.out.println(ConsoleColors.CYAN + "╔══════════════════════════════════════╗");
        System.out.println("║        📝 CADASTRAR PACIENTE         ║");
        System.out.println("╚══════════════════════════════════════╝\n" + ConsoleColors.RESET);

        Paciente novo = FormularioPaciente.cadastrarPaciente(scanner);

        if (novo != null) {
            boolean added = pacienteDB.adicionarPaciente(novo);
            if (added) {
                admin.criarPaciente(novo);
                System.out.println(ConsoleColors.GREEN + "\n✔ Paciente cadastrado com sucesso!" + ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.RED + "\n❌ Erro: carteirinha já cadastrada." + ConsoleColors.RESET);
            }
        } else {
            System.out.println(ConsoleColors.RED + "\n❌ Operação cancelada." + ConsoleColors.RESET);
        }
    }

    // ===============================================================
    // 2 — LISTAR PACIENTES
    // ===============================================================
    private void listarPacientes(Administrador admin) {

        System.out.println(ConsoleColors.CYAN + "╔═══════════════════════════════╗");
        System.out.println("║       📋 LISTA DE PACIENTES    ║");
        System.out.println("╚═══════════════════════════════╝\n" + ConsoleColors.RESET);

        java.util.List<Paciente> lista = pacienteDB.listarTodos();

        for (Paciente p : lista) {
            boolean presente = admin.getPacientes().stream()
                    .anyMatch(ap -> ap.getNumeroCarteirinha().equals(p.getNumeroCarteirinha()));
            if (!presente) admin.criarPaciente(p);
        }

        if (lista.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "⚠ Nenhum paciente cadastrado." + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.BLUE + "\n--- Lista de Pacientes ---" + ConsoleColors.RESET);
        for (Paciente p : lista) {
            System.out.println(ConsoleColors.WHITE + p + ConsoleColors.RESET);
        }
    }

    // ===============================================================
    // 3 — BLOQUEAR PACIENTE
    // ===============================================================
    private void bloquearPaciente(Administrador admin) {

        System.out.println(ConsoleColors.CYAN + "╔══════════════════════════════╗");
        System.out.println("║       🔒 BLOQUEAR PACIENTE    ║");
        System.out.println("╚══════════════════════════════╝\n" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "Informe o número da carteirinha: " + ConsoleColors.RESET);
        String codigo = scanner.nextLine();

        boolean dbBlock = pacienteDB.buscarCarteirinha(codigo) != null;
        if (dbBlock) {
            pacienteDB.buscarCarteirinha(codigo)
                    .setStatus(br.com.sistemaPlanoSaude.model.enums.StatusPaciente.BLOQUEADO);

            System.out.println(ConsoleColors.GREEN + "✔ Paciente bloqueado!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "❌ Paciente não encontrado no banco." + ConsoleColors.RESET);
        }

        admin.bloquearPaciente(codigo);
    }

    // ===============================================================
    // 4 — DESBLOQUEAR PACIENTE
    // ===============================================================
    private void desbloquearPaciente(Administrador admin) {

        System.out.println(ConsoleColors.CYAN + "╔════════════════════════════════╗");
        System.out.println("║      🔓 DESBLOQUEAR PACIENTE    ║");
        System.out.println("╚════════════════════════════════╝\n" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "Informe o número da carteirinha: " + ConsoleColors.RESET);
        String codigo = scanner.nextLine();

        boolean dbFound = pacienteDB.buscarCarteirinha(codigo) != null;
        if (dbFound) {
            pacienteDB.buscarCarteirinha(codigo)
                    .setStatus(br.com.sistemaPlanoSaude.model.enums.StatusPaciente.ATIVO);

            System.out.println(ConsoleColors.GREEN + "✔ Paciente desbloqueado!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "❌ Paciente não encontrado." + ConsoleColors.RESET);
        }

        admin.desbloquearPaciente(codigo);
    }

    // ===============================================================
    // 5 — ALTERAR PERMISSÕES
    // ===============================================================
    private void alterarPermissoesPaciente(Administrador admin) {

        System.out.println(ConsoleColors.CYAN + "╔══════════════════════════════════╗");
        System.out.println("║      🛂  ALTERAR PERMISSÕES       ║");
        System.out.println("╚══════════════════════════════════╝\n" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "Número da carteirinha: " + ConsoleColors.RESET);
        String codigo = scanner.nextLine();

        System.out.println("\nEscolha o novo nível de acesso:");
        System.out.println("  [1] PACIENTE");
        System.out.println("  [2] ADMINISTRADOR");
        System.out.print(ConsoleColors.YELLOW + "👉 Sua escolha: " + ConsoleColors.RESET);

        NivelAcesso nivel = null;

        while (nivel == null) {
            String escolha = scanner.nextLine().trim();
            switch (escolha) {
                case "1" -> nivel = NivelAcesso.PACIENTE;
                case "2" -> nivel = NivelAcesso.ADMINISTRADOR;
                default -> System.out.print(ConsoleColors.RED + "Opção inválida. Digite 1 ou 2: " + ConsoleColors.RESET);
            }
        }

        Paciente p = pacienteDB.buscarCarteirinha(codigo);
        if (p != null) p.setNivelAcesso(nivel);

        admin.alterarPermissoes(codigo, nivel);

        System.out.println(ConsoleColors.GREEN + "\n✔ Permissões atualizadas!" + ConsoleColors.RESET);
    }

    // ===============================================================
    // 6 — RESETAR SENHA
    // ===============================================================
    private void resetarSenha(Administrador admin) {

        System.out.println(ConsoleColors.CYAN + "╔════════════════════════════════╗");
        System.out.println("║        🔁 RESETAR SENHA         ║");
        System.out.println("╚════════════════════════════════╝\n" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "Número da carteirinha: " + ConsoleColors.RESET);
        String codigo = scanner.nextLine();

        Paciente p = pacienteDB.buscarCarteirinha(codigo);
        if (p != null) {
            System.out.println(ConsoleColors.GREEN +
                    "✔ Senha resetada (simulação no DB)." + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "❌ Paciente não encontrado." + ConsoleColors.RESET);
        }

        admin.resetarSenhaPaciente(codigo);
    }

    // ===============================================================
    // 7 — BUSCAR PACIENTE
    // ===============================================================
    private void buscarPaciente(Administrador admin) {

        System.out.println(ConsoleColors.CYAN + "╔════════════════════════════════════════╗");
        System.out.println("║     🔎 CONSULTAR PACIENTE POR CARTEIRINHA ║");
        System.out.println("╚════════════════════════════════════════╝\n" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "Informe o número da carteirinha: " + ConsoleColors.RESET);
        String codigo = scanner.nextLine();

        Paciente encontrado = pacienteDB.buscarCarteirinha(codigo);

        if (encontrado == null) {
            System.out.println(ConsoleColors.RED + "❌ Paciente não encontrado." + ConsoleColors.RESET);
            return;
        }

        boolean presente = admin.getPacientes().stream()
                .anyMatch(ap -> ap.getNumeroCarteirinha().equals(encontrado.getNumeroCarteirinha()));
        if (!presente) admin.criarPaciente(encontrado);

        System.out.println(ConsoleColors.BLUE + "\n📄 Dados do Paciente:\n" + ConsoleColors.RESET);
        encontrado.exibirInfo();
    }

    // ===============================================================
    // UTILITÁRIOS
    // ===============================================================

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
