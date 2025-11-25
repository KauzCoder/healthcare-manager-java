package br.com.sistemaPlanoSaude.view.admin;

import br.com.sistemaPlanoSaude.database.MedicoDataBase;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.service.MedicoService;
import br.com.sistemaPlanoSaude.util.ConsoleColors;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import br.com.sistemaPlanoSaude.view.formularios.FormularioMedico;
import java.util.Scanner;

public class AdminMedicoView {

    private final Scanner scanner = new Scanner(System.in);
    private final MedicoDataBase medicoDB = new MedicoDataBase();
    private final MedicoService medicoService = new MedicoService();

    public void exibirMenu(Administrador admin) {

        MetodosAuxiliares.limparTela();

        int opcao = -1;

        while (opcao != 0) {

            System.out.println(ConsoleColors.BLUE + ConsoleColors.BOLD +
                "+==============================================================+\n" +
                "|                                                              |\n" +
                "|        PAINEL DO ADMINISTRADOR - MEDICOS                     |\n" +
                "|                                                              |\n" +
                "|  Gerencie profissionais, cadastre novos medicos,             |\n" +
                "|  visualize informacoes e mantenha tudo organizado.           |\n" +
                "|                                                              |\n" +
                "+==============================================================+"
                + ConsoleColors.RESET);

            System.out.println();

            System.out.println(ConsoleColors.CYAN + "Opcoes para gerenciamento de medicos:\n" + ConsoleColors.RESET);
            System.out.println(" [ 1 ] -> Cadastrar medico");
            System.out.println(" [ 2 ] -> Listar medicos");
            System.out.println(" [ 3 ] -> Buscar medico (CRM ou CPF)");
            System.out.println(" [ 4 ] -> Remover medico pelo CRM");
            System.out.println(" [ 0 ] -> Voltar");
            System.out.println();

            System.out.print(ConsoleColors.YELLOW + "Digite sua opcao: " + ConsoleColors.RESET);
            opcao = lerInteiro();

            processarOpcao(opcao, admin);
        }
    }

    // ===============================================================
    // PROCESSAMENTO DO MENU
    // ===============================================================
    private void processarOpcao(int opcao, Administrador admin) {

        MetodosAuxiliares.limparTela();

        switch (opcao) {
            case 1 -> cadastrarMedico(admin);
            case 2 -> listarMedicos(admin);
            case 3 -> buscarMedico(admin);
            case 4 -> removerMedico(admin);

            case 0 -> {
                System.out.println(ConsoleColors.BLUE + "Retornando ao menu principal..." + ConsoleColors.RESET);
                return;
            }

            default -> System.out.println(ConsoleColors.RED + "Opcao invalida! Tente novamente." + ConsoleColors.RESET);
        }

        System.out.println(ConsoleColors.PURPLE + "\nPressione ENTER para continuar..." + ConsoleColors.RESET);
        scanner.nextLine();
        MetodosAuxiliares.limparTela();
    }

    // ===============================================================
    // CADASTRAR MEDICO
    // ===============================================================
    private void cadastrarMedico(Administrador admin) {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.BLUE + ConsoleColors.BOLD +
            "+=============================+\n" +
            "|       CADASTRAR MEDICO      |\n" +
            "+=============================+\n" +
            ConsoleColors.RESET);

        Medico novo = FormularioMedico.cadastrarMedico(scanner);

        if (novo == null) {
            System.out.println(ConsoleColors.RED + "\nCadastro cancelado." + ConsoleColors.RESET);
            return;
        }

        boolean added = medicoService.cadastrar(novo);
        if (added) {
            admin.getMedicos().add(novo);
            System.out.println(ConsoleColors.GREEN + "\nMedico cadastrado com sucesso!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "\nNao foi possivel cadastrar: CRM ja existe." + ConsoleColors.RESET);
        }
    }

    // ===============================================================
    // LISTAR MEDICOS
    // ===============================================================
    private void listarMedicos(Administrador admin) {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.BLUE + ConsoleColors.BOLD +
            "+=========================+\n" +
            "|       LISTA DE MEDICOS  |\n" +
            "+=========================+\n" +
            ConsoleColors.RESET);

        java.util.List<Medico> lista = medicoService.listarTodos();

        admin.getMedicos().clear();
        admin.getMedicos().addAll(lista);

        if (lista.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "Nenhum medico cadastrado." + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.CYAN + "\n--- Lista de medicos ---" + ConsoleColors.RESET);
        for (Medico m : lista) {
            System.out.println(m);
        }
    }

    // ===============================================================
    // REMOVER MEDICO
    // ===============================================================
    private void removerMedico(Administrador admin) {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.BLUE + ConsoleColors.BOLD +
            "+==============================+\n" +
            "|        REMOVER MEDICO        |\n" +
            "+==============================+\n" +
            ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "Digite o CRM do medico para remover: " + ConsoleColors.RESET);
        String crm = scanner.nextLine();

        boolean removed = medicoService.remover(crm);
        if (removed) {
            admin.removerMedico(crm);
            System.out.println(ConsoleColors.GREEN + "\nMedico removido com sucesso!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "\nMedico nao encontrado." + ConsoleColors.RESET);
        }
    }

    // ===============================================================
    // BUSCAR MEDICO (por CRM ou CPF)
    // ===============================================================
    private void buscarMedico(Administrador admin) {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.BLUE + ConsoleColors.BOLD +
            "+==============================+\n" +
            "|        BUSCAR MEDICO         |\n" +
            "+==============================+\n" +
            ConsoleColors.RESET);

        System.out.println("Escolha o tipo de busca:");
        System.out.println(" [ 1 ] -> Buscar por CRM");
        System.out.println(" [ 2 ] -> Buscar por CPF");
        System.out.print(ConsoleColors.YELLOW + "Opcao: " + ConsoleColors.RESET);
        int opcaoBusca = lerInteiro();

        Medico m = null;

        switch (opcaoBusca) {
            case 1 -> {
                System.out.print(ConsoleColors.YELLOW + "Informe o CRM: " + ConsoleColors.RESET);
                String crm = scanner.nextLine().trim();
                m = medicoService.buscarPorCrm(crm);
            }
            case 2 -> {
                System.out.print(ConsoleColors.YELLOW + "Informe o CPF: " + ConsoleColors.RESET);
                String cpf = scanner.nextLine().trim();
                m = medicoDB.buscarPorCpf(cpf);
            }
            default -> {
                System.out.println(ConsoleColors.RED + "Opcao invalida!" + ConsoleColors.RESET);
                return;
            }
        }

        if (m != null) {
            if (!admin.getMedicos().contains(m)) {
                admin.getMedicos().add(m);
            }
            System.out.println("\n" + ConsoleColors.GREEN + "Medico encontrado!" + ConsoleColors.RESET);
            m.exibirInfo();
        } else {
            System.out.println(ConsoleColors.RED + "Medico nao encontrado!" + ConsoleColors.RESET);
        }
    }

    // ===============================================================
    // UTILITARIOS
    // ===============================================================

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED + "Digite um numero valido: " + ConsoleColors.RESET);
            }
        }
    }
}
