package br.com.sistemaPlanoSaude.view.admin;

import br.com.sistemaPlanoSaude.database.PacienteDataBase;
import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.service.PacienteService;
import br.com.sistemaPlanoSaude.util.ConsoleColors;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import br.com.sistemaPlanoSaude.view.formularios.FormularioPaciente;
import java.util.Scanner;

public class AdminPacienteView {

    private final Scanner scanner = new Scanner(System.in);
    private final PacienteDataBase pacienteDB = new PacienteDataBase();
    private final PacienteService pacienteService = new PacienteService();

    public void exibirMenu(Administrador admin) {

        MetodosAuxiliares.limparTela();

        int opcao = -1;

        while (opcao != 0) {

            System.out.println(ConsoleColors.CYAN + "+==============================================================+" + ConsoleColors.RESET);
            System.out.println("|                                                              |");
            System.out.println("|      PAINEL DO ADMINISTRADOR - PACIENTES                     |");
            System.out.println("|                                                              |");
            System.out.println("|   Gerencie pacientes, permissoes e status da conta           |");
            System.out.println("|   com facilidade e total controle administrativo.            |");
            System.out.println("|                                                              |");
            System.out.println("+==============================================================+");
            System.out.println();

            System.out.println(ConsoleColors.BLUE + "Opcoes disponiveis:\n" + ConsoleColors.RESET);
            System.out.println(" [ 1 ] -> Cadastrar paciente");
            System.out.println(" [ 2 ] -> Listar pacientes");
            System.out.println(" [ 3 ] -> Alterar status do paciente");
            System.out.println(" [ 4 ] -> Buscar paciente (carteirinha ou CPF)");
            System.out.println(" [ 5 ] -> Remover paciente");
            System.out.println(" [ 6 ] -> Alterar permissoes de acesso");
            System.out.println(" [ 0 ] -> Voltar");
            System.out.println();

            System.out.print(ConsoleColors.YELLOW + "Digite sua opcao: " + ConsoleColors.RESET);
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

            case 3 -> alterarStatusPaciente(admin);

            case 4 -> buscarPaciente(admin);

            case 5 -> removerPaciente(admin);

            case 6 -> alterarPermissoesPaciente(admin);

            case 0 -> {
                System.out.println(ConsoleColors.GREEN + "Retornando ao menu principal..." + ConsoleColors.RESET);
                return;
            }

            default -> System.out.println(ConsoleColors.RED + "Opcao invalida! Tente novamente." + ConsoleColors.RESET);
        }

        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
        MetodosAuxiliares.limparTela();
    }

    // ===============================================================
    // 1 - CADASTRAR PACIENTE
    // ===============================================================
    private void cadastrarPaciente(Administrador admin) {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.CYAN + "+======================================+");
        System.out.println("|        CADASTRAR PACIENTE            |");
        System.out.println("+======================================+\n" + ConsoleColors.RESET);

        Paciente novo = FormularioPaciente.cadastrarPaciente(scanner);

        if (novo != null) {
            // Gerar carteirinha temporaria unica baseada em CPF e timestamp
            String carteirinhaTemp = "TEMP-" + novo.getCpf().substring(0, 6) + "-" + System.currentTimeMillis() % 100000;
            novo.setNumeroCarteirinha(carteirinhaTemp);

            boolean added = pacienteDB.adicionarPaciente(novo);
            if (added) {
                admin.criarPaciente(novo);
                System.out.println(ConsoleColors.GREEN + "\nPaciente cadastrado com sucesso!" + ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW + "Carteirinha temporaria: " + carteirinhaTemp + ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN + "Aplique um plano para gerar a carteirinha definitiva." + ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.RED + "\nErro: CPF ou carteirinha ja cadastrada." + ConsoleColors.RESET);
            }
        } else {
            System.out.println(ConsoleColors.RED + "\nOperacao cancelada." + ConsoleColors.RESET);
        }
    }

    // ===============================================================
    // 2 - LISTAR PACIENTES
    // ===============================================================
    private void listarPacientes(Administrador admin) {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.CYAN + "+===============================+");
        System.out.println("|        LISTA DE PACIENTES     |");
        System.out.println("+===============================+\n" + ConsoleColors.RESET);

        java.util.List<Paciente> lista = pacienteDB.listarTodos();

        for (Paciente p : lista) {
            boolean presente = admin.getPacientes().stream()
                    .anyMatch(ap -> ap.getNumeroCarteirinha().equals(p.getNumeroCarteirinha()));
            if (!presente) admin.criarPaciente(p);
        }

        if (lista.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "Nenhum paciente cadastrado." + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.BLUE + "\n--- Lista de pacientes ---" + ConsoleColors.RESET);
        for (Paciente p : lista) {
            System.out.println(ConsoleColors.WHITE + p + ConsoleColors.RESET);
        }
    }

    // ===============================================================
    // 3 - ALTERAR STATUS DO PACIENTE (ATIVO/BLOQUEADO)
    // ===============================================================
    private void alterarStatusPaciente(Administrador admin) {
        MetodosAuxiliares.limparTela();

        System.out.println(ConsoleColors.CYAN + "+===================================+");
        System.out.println("|     ALTERAR STATUS DO PACIENTE    |");
        System.out.println("+===================================+\n" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "Informe o numero da carteirinha: " + ConsoleColors.RESET);
        String codigo = scanner.nextLine().trim();

        Paciente paciente = pacienteDB.buscarCarteirinha(codigo);
        if (paciente == null) {
            System.out.println(ConsoleColors.RED + "Paciente nao encontrado." + ConsoleColors.RESET);
            return;
        }

        System.out.println("\nPaciente encontrado: " + paciente.getNome());
        System.out.println("Status atual: " + paciente.getStatus());
        System.out.println("\nEscolha o novo status:");
        System.out.println("  [1] ATIVO");
        System.out.println("  [2] INATIVO");
        System.out.println("  [3] BLOQUEADO");
        System.out.println("  [4] FALECIDO");
        System.out.print(ConsoleColors.YELLOW + "Sua escolha: " + ConsoleColors.RESET);

        int escolha = lerInteiro();
        boolean sucesso = false;
        switch (escolha) {
            case 1 -> {
                sucesso = pacienteService.desbloquearPaciente(codigo);
                if (sucesso) System.out.println(ConsoleColors.GREEN + "\nStatus alterado para ATIVO!" + ConsoleColors.RESET);
            }
            case 2 -> {
                sucesso = pacienteService.desativarPaciente(codigo);
                if (sucesso) System.out.println(ConsoleColors.GREEN + "\nStatus alterado para INATIVO!" + ConsoleColors.RESET);
            }
            case 3 -> {
                sucesso = pacienteService.bloquearPaciente(codigo);
                if (sucesso) System.out.println(ConsoleColors.GREEN + "\nStatus alterado para BLOQUEADO!" + ConsoleColors.RESET);
            }
            case 4 -> {
                sucesso = pacienteService.marcarComoFalecido(codigo);
                if (sucesso) System.out.println(ConsoleColors.GREEN + "\nStatus alterado para FALECIDO!" + ConsoleColors.RESET);
            }
            default -> System.out.println(ConsoleColors.RED + "Opcao invalida!" + ConsoleColors.RESET);
        }
        
        if (!sucesso && escolha >= 1 && escolha <= 4) {
            System.out.println(ConsoleColors.RED + "Erro ao alterar status." + ConsoleColors.RESET);
        }
    }

    // ===============================================================
    // 5 - REMOVER PACIENTE
    // ===============================================================
    private void removerPaciente(Administrador admin) {
        MetodosAuxiliares.limparTela();

        System.out.println(ConsoleColors.CYAN + "+=========================================+");
        System.out.println("|           REMOVER PACIENTE              |");
        System.out.println("+=========================================+\n" + ConsoleColors.RESET);

        System.out.println("Escolha o tipo de busca:");
        System.out.println(" [ 1 ] -> Remover por Carteirinha");
        System.out.println(" [ 2 ] -> Remover por CPF");
        System.out.print(ConsoleColors.YELLOW + "Opcao: " + ConsoleColors.RESET);
        int opcaoBusca = lerInteiro();

        Paciente encontrado;
        String identificador;

        switch (opcaoBusca) {
            case 1 -> {
                System.out.print(ConsoleColors.YELLOW + "Informe o numero da carteirinha: " + ConsoleColors.RESET);
                identificador = scanner.nextLine().trim();
                encontrado = pacienteDB.buscarCarteirinha(identificador);
            }
            case 2 -> {
                System.out.print(ConsoleColors.YELLOW + "Informe o CPF: " + ConsoleColors.RESET);
                String cpf = scanner.nextLine().trim();
                encontrado = pacienteDB.buscarPorCpf(cpf);
                identificador = (encontrado != null) ? encontrado.getNumeroCarteirinha() : null;
            }
            default -> {
                System.out.println(ConsoleColors.RED + "Opcao invalida!" + ConsoleColors.RESET);
                return;
            }
        }

        if (encontrado == null) {
            System.out.println(ConsoleColors.RED + "Paciente nao encontrado." + ConsoleColors.RESET);
            return;
        }

        System.out.println("\nPaciente encontrado:");
        System.out.println(encontrado);
        System.out.print(ConsoleColors.RED + "\nConfirma a remocao? (S/N): " + ConsoleColors.RESET);
        String confirmacao = scanner.nextLine().trim().toUpperCase();

        if (confirmacao.equals("S")) {
            final String idFinal = identificador;
            boolean removido = pacienteDB.removerPorCarteirinha(idFinal);
            if (removido) {
                admin.getPacientes().removeIf(p -> p.getNumeroCarteirinha().equals(idFinal));
                System.out.println(ConsoleColors.GREEN + "\nPaciente removido com sucesso!" + ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.RED + "\nErro ao remover paciente." + ConsoleColors.RESET);
            }
        } else {
            System.out.println(ConsoleColors.YELLOW + "\nOperacao cancelada." + ConsoleColors.RESET);
        }
    }

    // ===============================================================
    // 6 - ALTERAR PERMISSOES
    // ===============================================================
    private void alterarPermissoesPaciente(Administrador admin) {
        MetodosAuxiliares.limparTela();

        System.out.println(ConsoleColors.CYAN + "+==================================+");
        System.out.println("|        ALTERAR PERMISSOES        |");
        System.out.println("+==================================+\n" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "Numero da carteirinha: " + ConsoleColors.RESET);
        String codigo = scanner.nextLine();

        System.out.println("\nEscolha o novo nivel de acesso:");
        System.out.println("  [1] PACIENTE");
        System.out.println("  [2] ADMINISTRADOR");
        System.out.print(ConsoleColors.YELLOW + "Sua escolha: " + ConsoleColors.RESET);

        NivelAcesso nivel = null;

        while (nivel == null) {
            String escolha = scanner.nextLine().trim();
            switch (escolha) {
                case "1" -> nivel = NivelAcesso.PACIENTE;
                case "2" -> nivel = NivelAcesso.ADMINISTRADOR;
                default -> System.out.print(ConsoleColors.RED + "Opcao invalida. Digite 1 ou 2: " + ConsoleColors.RESET);
            }
        }

        Paciente p = pacienteDB.buscarCarteirinha(codigo);
        if (p != null) p.setNivelAcesso(nivel);

        admin.alterarPermissoes(codigo, nivel);

        System.out.println(ConsoleColors.GREEN + "\nPermissoes atualizadas!" + ConsoleColors.RESET);
    }

    // ===============================================================
    // 4 - BUSCAR PACIENTE (por carteirinha ou CPF)
    // ===============================================================
    private void buscarPaciente(Administrador admin) {
        MetodosAuxiliares.limparTela();

        System.out.println(ConsoleColors.CYAN + "+=========================================+");
        System.out.println("|        BUSCAR PACIENTE                  |");
        System.out.println("+=========================================+\n" + ConsoleColors.RESET);

        System.out.println("Escolha o tipo de busca:");
        System.out.println(" [ 1 ] -> Buscar por Carteirinha");
        System.out.println(" [ 2 ] -> Buscar por CPF");
        System.out.print(ConsoleColors.YELLOW + "Opcao: " + ConsoleColors.RESET);
        int opcaoBusca = lerInteiro();

        final Paciente encontrado;

        switch (opcaoBusca) {
            case 1 -> {
                System.out.print(ConsoleColors.YELLOW + "Informe o numero da carteirinha: " + ConsoleColors.RESET);
                String codigo = scanner.nextLine().trim();
                encontrado = pacienteDB.buscarCarteirinha(codigo);
            }
            case 2 -> {
                System.out.print(ConsoleColors.YELLOW + "Informe o CPF: " + ConsoleColors.RESET);
                String cpf = scanner.nextLine().trim();
                encontrado = pacienteDB.buscarPorCpf(cpf);
            }
            default -> {
                System.out.println(ConsoleColors.RED + "Opcao invalida!" + ConsoleColors.RESET);
                return;
            }
        }

        if (encontrado == null) {
            System.out.println(ConsoleColors.RED + "Paciente nao encontrado." + ConsoleColors.RESET);
            return;
        }

        boolean presente = admin.getPacientes().stream()
                .anyMatch(ap -> ap.getNumeroCarteirinha().equals(encontrado.getNumeroCarteirinha()));
        if (!presente) admin.criarPaciente(encontrado);

        System.out.println(ConsoleColors.BLUE + "\nDados do paciente:\n" + ConsoleColors.RESET);
        encontrado.exibirInfo();
    }

    // ===============================================================
    // UTILITARIOS
    // ===============================================================

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(ConsoleColors.RED + "Digite um numero valido: " + ConsoleColors.RESET);
            }
        }
    }
}
